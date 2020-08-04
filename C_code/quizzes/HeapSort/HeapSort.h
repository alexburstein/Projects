#ifndef HEAP_SORT_IN_PLACE_H
#define HEAP_SORT_IN_PLACE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

typedef int (*cmp_t)(const void *data1, const void *data2);

void HeapSortInPlace(void *base, size_t nitems, size_t elm_size, cmp_t cmp_func);
static void HeapUp(char *arr, size_t elm_size, size_t index, cmp_t cmp_func);
static void HeapDown(char *arr, size_t arr_size, size_t elm_size, cmp_t cmp_func);
static size_t FindBiggerChild(char *arr, size_t arr_size, size_t elm_size, cmp_t cmp_func, size_t index);
static size_t BiggerOfTwoChildren(char *arr, size_t elm_size, cmp_t cmp_func, size_t index);
static size_t NumOfCHildren(size_t arr_size, size_t index);
static void SwapDataByBytes(char *first, char *second, size_t elm_size);
static int LongCmp(const void *first, const void *second);


#endif /*"HEAP_SORT_IN_PLACE_H"*/