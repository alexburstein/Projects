#include <assert.h>
#include <stdbool.h>


bool solution(const char *string, const char *ending);
void test1();

int main(){

    test1();

    return 0;
}

void test1(){
    assert(true == solution("abc", "bc"));
    assert(true == solution("samurai", "ai"));
    assert(false == solution("sumo", "omo"));
    assert(true == solution("ninja", "ja"));
    assert(true == solution("sensei", "i"));
    assert(false == solution("samurai", "ra"));
    assert(false == solution("abc", "abcd"));
    assert(true == solution("abc", "abc"));
    assert(true == solution("abcabc", "bc"));
    assert(false == solution("ails", "fails"));
    assert(true == solution("fails", "ails"));
    assert(false == solution("this", "fails"));
    assert(false == solution("abc", "d"));
}