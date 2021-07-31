package airacle.air

import airacle.air.bits.Bits
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.util.*

internal class BitsTest {

    @Test
    fun testBitsString() {
        val rand = Random()
        for (i in 1..600) {
            val s0 = BigInteger(i, rand).toString(2)
            val bits = Bits(s0)
            val s1 = bits.toString()
            assertEquals(s0, s1)
        }
    }

    @Test
    fun testBitsBigInteger() {
        val rand = Random()
        for (i in 1..600) {
            val s0 = BigInteger(i, rand)
            val bits = Bits.valueOf(s0)
            val s1 = bits.ordinal()
            assertEquals(s0, s1)
        }
    }

    @Test
    fun testBitsLong() {
        val rand = Random()
        for (i in 0..600L) {
            val s0 = rand.nextLong() and Long.MAX_VALUE
            val bits = Bits.valueOf(s0)
            val s1 = bits.ordinal()
            assertEquals(BigInteger.valueOf(s0), s1)
        }
    }
}