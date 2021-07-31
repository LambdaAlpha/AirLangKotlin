package airacle.air.bits

public class MutableBits {
    var length: Int
    internal var bytes: ByteArray

    constructor(bits: Bits) {
        length = bits.length
        bytes = ByteArray(bits.bytes.size)
        System.arraycopy(bits.bytes, 0, bytes, 0, bytes.size)
    }

    fun immutable(): Bits {
        return Bits(bytes, length)
    }
}