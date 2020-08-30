#include <stdlib.h>
#include <string.h>
#include <stdio.h>

static void makeDecimal(char* parr);


char *add(const char *a, const char *b) {
    assert(a != NULL && b != NULL);
    
    int aLength = strlen(a);
    int bLength = strlen(b);
    int longerLen = 0; 
    int shorterLen = 0;
    char resArr[1000] = {0};  
    char *resRunner = NULL;
    char *longerStr = a;

    if (aLength < bLength){
        longerLen = bLength;
        shorterLen = aLength;
        longerStr = (char*)b;
    }
    else{
        longerLen = aLength;
        shorterLen = bLength;
    }
    
    resRunner = resArr + longerLen;
    *(resRunner + 1) = '\0';  
  
    for(int i = 0; i < shorterLen; ++i, --resRunner){
        *resRunner += a[aLength - i - 1];
        *resRunner += b[bLength - i - 1] - '0';
        makeDecimal(resRunner);
    }

    for(int i = shorterLen; i < longerLen; ++i, --resRunner){
        *resRunner += longerStr[longerLen - i - 1];
        makeDecimal(resRunner);
    }

    if (*resArr == 1){
        *resArr += '0';
    }

    return strdup(*resArr == '\0' ? resArr + 1 : resArr);
}

static void makeDecimal(char* parr){
    if (*parr - '0' > 9){
        *(parr - 1) += 1;
        *parr -= 10;
    }
}