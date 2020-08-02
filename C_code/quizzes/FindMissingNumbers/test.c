#include <stdio.h>
#include "FindMissingNumbers.h"

void test1();
void test2();


int main(){
    test1();
    test2();


    return 0;
}

void test2(){
    int array[] = {1,4,7,8,5,2,3,6,9,8,5,2,1,4,7,8,5,2,3,6,9};
    int res[10];
    int resSize = sizeof(res) /sizeof(res[0]);

    findMissingNumbers(array, sizeof(array) / sizeof(array[0]), res, &resSize);

    for (int i = 0; i < (sizeof(array) / sizeof(array[0])); ++i){
        printf("%d ", array[i]);
    }

    printf("\n");

    for (int i = 0; i < resSize; ++i){
        printf("%d ", res[i]);
    }
}


void test1(){

    int array[] = {4,3,2,7,8,2,3,1};
    int res[10];
    int resSize = sizeof(res) /sizeof(res[0]);
    findMissingNumbers(array, sizeof(array) / sizeof(array[0]), res, &resSize);

    for (int i = 0; i < (sizeof(array) / sizeof(array[0])); ++i){
        printf("%d ", array[i]);
    }

    for (int i = 0; i < resSize; ++i){
        printf("%d ", res[i]);
    }

}