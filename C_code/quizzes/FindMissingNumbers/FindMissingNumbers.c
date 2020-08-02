#include "FindMissingNumbers.h"

/*
    Given an array of integers where 1 ≤ a[i] ≤ n (n = size of the array),
    some elements appear twice and others appear once.
    Find all the elements of [1, n] inclusive that do not appear in this array.
    Could you do it without extra space and in O(n) runtime? You may
    assume the returned list does not count as extra space.
*/

static void sortByIndexLoop(int i, int* arr);
static void putTheMissingInArr(int *arr, int sizeOfArr, int *res, int *resultSize);


void findMissingNumbers(int *arr, int sizeOfArr, int *res, int *resultSize){
    int i = 0;
    *resultSize = 0;

    for (i = 0; i < sizeOfArr; ++i){
        sortByIndexLoop(i, arr);
    }

    putTheMissingInArr(arr, sizeOfArr, res, resultSize);
}

static void sortByIndexLoop(int elmIndex, int* arr){
    int element = arr[elmIndex];

    while(element != elmIndex + 1){
        elmIndex = element - 1;
        int nextElm = arr[elmIndex];
        arr[elmIndex] = element;
        element = nextElm;
    }
}

static void putTheMissingInArr(int *arr, int sizeOfArr, int *res, int *resultSize){
        for (int i = 0; i < sizeOfArr; ++i){
        if(arr[i] != i + 1){
            *res = i + 1;
            ++res;
            ++*resultSize;
        }
    }
}
