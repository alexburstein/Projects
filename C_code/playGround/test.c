#include <assert.h>
#include <string.h>
#include <stdio.h>

char *add(const char *a, const char *b);
void test1();

int main(){

    test1();

    return 0;
}

void test1(){
    assert(0 == strcmp("2", add("1", "1")));
    assert(0 == strcmp("246", add("123", "123")));
    assert(0 != strcmp("1", add("1", "1")));
    assert(0 == strcmp("14", add("2", "12")));
    assert(0 == strcmp("18", add("9", "9")));    
    assert(0 == strcmp("2", add("1", "1")));
    assert(0 == strcmp("2", add("1", "1")));
    assert(0 == strcmp("198", add("99", "99")));    
    assert(0 != strcmp("199", add("99", "99")));    
    assert(0 == strcmp("199999999999999998", add("99999999999999999", "99999999999999999")));  
    assert(0 == strcmp("91002328220491911630239667963", add("63829983432984289347293874", "90938498237058927340892374089")));  

}