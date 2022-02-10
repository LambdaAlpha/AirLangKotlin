package airacle.air.interpreter

import airacle.air.api.Air
import airacle.air.api.AirVersion
import airacle.air.util.InterpreterReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class InterpreterTest {
    private val air = Air(AirVersion.V0)
    private val interpreterReader = InterpreterReader(air.lexer, air.parser, air.interpreter)

    private fun submoduleTest(name: String) {
        val ret = interpreterReader.readAndInterpret(getTestFile0(name))
        val expected = interpreterReader.readAndInterpret(getTestFile1(name))
        assertEquals(expected, ret)
    }

    private fun getTestFile0(module: String): String {
        return "/airacle/air/interpreter/${module}0.air"
    }

    private fun getTestFile1(module: String): String {
        return "/airacle/air/interpreter/${module}1.air"
    }

    @Test
    fun unitsTest() {
        submoduleTest("Units")
    }

    @Test
    fun booleansTest() {
        submoduleTest("Booleans")
    }

    @Test
    fun intsTest() {
        submoduleTest("Ints")
    }

    @Test
    fun decimalsTest() {
        submoduleTest("Decimals")
    }

    @Test
    fun numbersTest() {
        submoduleTest("Numbers")
    }

    @Test
    fun stringsTest() {
        submoduleTest("Strings")
    }

    @Test
    fun tuplesTest() {
        submoduleTest("Tuples")
    }

    @Test
    fun listsTest() {
        submoduleTest("Lists")
    }

    @Test
    fun mapsTest() {
        submoduleTest("Maps")
    }

    @Test
    fun comparatorsTest() {
        submoduleTest("Comparators")
    }

    @Test
    fun complexityTest() {
        submoduleTest("Complexity")
    }

    @Test
    fun computabilityTest() {
        submoduleTest("Computability")
    }

    @Test
    fun containersTest() {
        submoduleTest("Containers")
    }

    @Test
    fun contextsTest() {
        submoduleTest("Contexts")
    }

    @Test
    fun controlsTest() {
        submoduleTest("Controls")
    }

    @Test
    fun parsersTest() {
        submoduleTest("Parsers")
    }

    @Test
    fun typesTest() {
        submoduleTest("Types")
    }

    @Test
    fun modeTest() {
        submoduleTest("Interpreter")
    }
}