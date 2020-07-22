import org.junit.Test
import kotlin.test.assertEquals

class RotateCharsByNumberKotlin {
    private fun rotateCharArrByNum(arr: CharArray, rotationNum: Int) {
        val length = arr.size
        val rotateRemains = rotationNum % length
        reverse(arr, 0, rotateRemains)
        reverse(arr, rotateRemains, length)
        reverse(arr, 0, length)
    }

    private fun reverse(arr: CharArray, from: Int, to: Int) {
        var startIndx = from
        var endIndx = to - 1
        while (startIndx <= endIndx) {
            val tmp = arr[startIndx]
            arr[startIndx] = arr[endIndx]
            arr[endIndx] = tmp
            ++startIndx
            --endIndx
        }
    }

    @Test
    fun test() {
        rotateCharsTest("1234567".toCharArray(), 5)
        rotateCharsTest("1234567".toCharArray(), 3)
        rotateCharsTest("a long string to check".toCharArray(), 1)
        rotateCharsTest("a long string to check".toCharArray(), 77)
    }

    private fun rotateCharsTest(arr: CharArray, numOfRotates: Int) {
        val copy = arr.clone()
        println("original arr is : ${String(arr)}")
        rotateCharArrByNum(arr, numOfRotates)
        print("rotated by $numOfRotates is : ")
        for (i in arr.indices) {
            print(arr[i])
        }
        println()
        for (i in arr.indices) {
            assertEquals(copy[(numOfRotates + i) % arr.size], arr[i])
        }
    }
}