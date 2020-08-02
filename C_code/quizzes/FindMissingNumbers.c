#include <stdio.h>

/*
    Given an array of integers where 1 ≤ a[i] ≤ n (n = size of the array),
    some elements appear twice and others appear once.
    Find all the elements of [1, n] inclusive that do not appear in this array.
    Could you do it without extra space and in O(n) runtime? You may
    assume the returned list does not count as extra space.
*/

void findMissingNumbers(int *arr, int sizeOfArr, int *res, int resultSize);
static void sortByIndex(int i, int* arr);


int main(){
    int array[] = {4,3,2,7,8,2,3,1};
    int res[10];

    findMissingNumbers(array, sizeof(array) / sizeof(array[0]), res, sizeof(res) /sizeof(res[0]));

    for (int i = 0; i < (sizeof(array) / sizeof(array[0])); ++i){
        printf("%d ", array[i]);
    }

    return 0;
}

void findMissingNumbers(int *arr, int sizeOfArr, int *res, int resultSize){
    int i = 0;
    resultSize = 0;

    for (i = 0; i < sizeOfArr; ++i){
        sortByIndex(i, arr);
    }

    for (i = 0; i < sizeOfArr; ++i){
        
    }
}

static void sortByIndex(int i, int* arr){
    while(arr[i] != i + 1){
        int tmp = arr[arr[i] - 1];
        arr[arr[i] - 1] = arr[i];
        i = tmp - 1;
    }
}

// static void placeInRes()