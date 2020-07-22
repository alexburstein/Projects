/*
receive array of chars and a target char. if the target char in the array return true, else false.
DONOT use if \ ternary
 */

class FindIfCharInArrKotlin{
    fun isCharInArray(chars: CharArray , target: Char): Boolean{
        var index = 0

        while(index < chars.size && chars[index] != target){
            ++index
        }

        return index != chars.size;
    }
}