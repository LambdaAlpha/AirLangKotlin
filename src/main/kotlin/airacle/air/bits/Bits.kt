package airacle.air.bits

import java.math.BigInteger
import kotlin.math.min

/**
 * 使用字节数组存储，第一个比特的最低位为字符串最右比特，字符串向左增长
 * 额外需要一个 int 记录总比特数
 */
public class Bits : Comparable<Bits> {
    val length: Int
    internal val bytes: ByteArray

    constructor(bytes: ByteArray, length: Int) {
        if (length < 0) {
            throw IllegalArgumentException("length < 0")
        }
        this.length = length
        this.bytes = ByteArray(getEnoughSize(length))
        System.arraycopy(bytes, 0, this.bytes, 0, min(this.bytes.size, bytes.size))
        clearFreeBits()
    }

    private fun getEnoughSize(length: Int): Int {
        return (length - 1).shr(3) + 1
    }

    private fun getFreeLength(): Int {
        return bytes.size.shl(3) - length
    }

    private fun clearFreeBits() {
        if (bytes.isEmpty()) {
            return
        }
        bytes[bytes.size - 1] = (bytes[bytes.size - 1].toInt() and (0xff ushr getFreeLength())).toByte()
    }

    // 第 l 个 Bits
    private constructor(l: Long) {
        if (l < 0) {
            throw IllegalArgumentException("index < 0")
        }
        val l1 = l + 1
        length = 64 - l1.countLeadingZeroBits() - 1
        bytes = ByteArray(getEnoughSize(length))
        initFromLong(l1)
    }

    private fun initFromLong(l: Long) {
        for (i in bytes.indices) {
            bytes[i] = l.shr(i.shl(3)).toByte()
        }
        clearFreeBits()
    }

    // 第 i 个 Bits
    private constructor(i: BigInteger) {
        if (i.signum() < 0) {
            throw IllegalArgumentException("index < 0")
        }
        val i1 = i.inc()
        length = i1.bitLength() - 1
        this.bytes = ByteArray(getEnoughSize(length))
        val bytes = i1.clearBit(length).toByteArray()
        bytes.reverse()
        System.arraycopy(bytes, 0, this.bytes, 0, min(this.bytes.size, bytes.size))
    }

    fun ordinal(): BigInteger {
        // 有两种情况原数组装不下，
        // 一种是原来已经装满了，最高位只能放到新字节里，
        // 另一种是加上最高位装满了，导致需要增加一个空字节来表明符号是正的
        val rem = length.and(7)
        val bytes1 = if (rem == 7 || rem == 0) {
            ByteArray(bytes.size + 1).also {
                for (i in bytes.indices) {
                    it[it.size - 1 - i] = bytes[i]
                }
                if (rem == 7) {
                    it[1] = it[1].toInt().or(1 shl 7).toByte()
                } else {
                    it[0] = it[0].toInt().or(1).toByte()
                }
            }
        } else {
            bytes.reversedArray().also {
                it[0] = it[0].toInt().or(1 shl length.and(7)).toByte()
            }
        }
        return BigInteger(bytes1).dec()
    }

    constructor(s: String) {
        length = s.length
        bytes = ByteArray(getEnoughSize(s.length))
        initFromString(s)
    }

    private fun initFromString(s: String) {
        if (bytes.isEmpty()) {
            return
        }
        var index = length
        for (i in 0 until bytes.size - 1) {
            val s1 = s.substring(index - Byte.SIZE_BITS, index)
            val b = s1.toInt(2).toByte()
            bytes[i] = b
            index -= Byte.SIZE_BITS
        }
        val lastStr = s.substring(0, index)
        bytes[bytes.size - 1] = lastStr.toInt(2).toByte()
    }

    override fun toString(): String {
        if (length == 0) {
            return ""
        }
        val sb = StringBuilder()
        val i0 = bytes[bytes.size - 1].toInt().and(0xff)
        sb.append(i0.toString(2).padStart(Byte.SIZE_BITS, '0'))
        sb.delete(0, getFreeLength())
        for (i in bytes.size - 2 downTo 0) {
            val int = bytes[i].toInt().and(0xff)
            sb.append(int.toString(2).padStart(Byte.SIZE_BITS, '0'))
        }
        return sb.toString()
    }

    fun mutable(): MutableBits {
        return MutableBits(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Bits) {
            return false
        }
        if (length != other.length) {
            return false
        }
        for (i in bytes.indices) {
            if (bytes[i] != other.bytes[i]) {
                return false
            }
        }
        return true
    }

    override fun hashCode(): Int {
        var result = length
        result = 31 * result + bytes.contentHashCode()
        return result
    }

    override fun compareTo(other: Bits): Int {
        return when {
            length > other.length -> 1
            length < other.length -> -1
            else -> {
                for (i in bytes.size - 1..0) {
                    if (bytes[i] > other.bytes[i]) {
                        return 1
                    } else if (bytes[i] < other.bytes[i]) {
                        return -1
                    }
                }
                return 0
            }
        }
    }

    companion object {
        private val CACHE = Array(128) {
            Bits(it.toLong())
        }
        private val CACHE_SIZE = BigInteger.valueOf(CACHE.size.toLong())

        val N = CACHE[0]
        val O = CACHE[1]
        val I = CACHE[2]

        fun valueOf(l: Long): Bits {
            if (l < 0) {
                throw java.lang.IllegalArgumentException("index < 0")
            }
            if (l < CACHE.size) {
                return CACHE[l.toInt()]
            }
            return Bits(l)
        }

        fun valueOf(i: BigInteger): Bits {
            if (i.signum() < 0) {
                throw java.lang.IllegalArgumentException("index < 0")
            }
            if (i < CACHE_SIZE) {
                return CACHE[i.toInt()]
            }
            return Bits(i)
        }
    }
}