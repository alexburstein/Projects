import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/*
find size of max sub array
 */   class MaxSubArrayClassic {
    private fun findMaxSubArray(arr: IntArray): Int {
        var maxSum = 0
        var locSum = 0
        for (i in arr) {
            locSum = i.coerceAtLeast(locSum + i) // method that returns the max among the two.
            maxSum = maxSum.coerceAtLeast(locSum) // method that returns the max among the two.
        }
        return maxSum
    }

    @Test
    fun test() {
        assertEquals(15, findMaxSubArray(intArrayOf(1, 2, 3, 4, 5)))
        assertEquals(10, findMaxSubArray(intArrayOf(1, 2, -2, 4, 5)))
        assertEquals(5, findMaxSubArray(intArrayOf(5, -4, -3, -2, -1)))
        assertEquals(9, findMaxSubArray(intArrayOf(5, 4, -3, 2, 1)))
        assertEquals(0, findMaxSubArray(intArrayOf(-5, -4, -3, -2, -1)))
        assertEquals(1, findMaxSubArray(intArrayOf(1)))
    }

    @Test
    fun testEmptyArray() {
        assertEquals(0, findMaxSubArray(intArrayOf()))
    }

    @Test
    fun testExampleArray() {
        assertEquals(6, findMaxSubArray(intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4)))
    }
}