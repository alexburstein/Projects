#ifndef FIND_MISSING_NUMBERS_H
#define FIND_MISSING_NUMBERS_H

/*
    Given an array of integers where 1 ≤ a[i] ≤ n (n = size of the array),
    some elements appear twice and others appear once.
    Find all the elements of [1, n] inclusive that do not appear in this array.
    Could you do it without extra space and in O(n) runtime? You may
    assume the returned list does not count as extra space.
*/

/*
* a function that finds the missing elements in an array.
* param: arr - a pointer to an array of integers, from 1 to n including. not sorted.
* param: sizeOfArr - size of the array in arr.
* param: res - a pointer to a result array.
* param: resultSize - a pointer to an integer, representing the size of the result array.
* 
*/
void findMissingNumbers(int *arr, int sizeOfArr, int *res, int *resultSize);


#endif