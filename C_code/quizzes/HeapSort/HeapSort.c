#include "HeapSort.h"


void HeapSortInPlace(void *base, size_t nitems, size_t elmSize, cmp_t cmp){
	size_t i = 0;
	size_t unsorted = 0;
	char *arr = (char*)base;

	for (i = 1; i < nitems; ++i){
		HeapUp(arr, elmSize, i, cmp);
	}

	for (unsorted = nitems; 0 < unsorted; --unsorted){
		char *last_elm = arr + (unsorted - 1) * elmSize;

		SwapDataByBytes(arr, last_elm, elmSize);
		HeapDown(arr, unsorted - 1, elmSize, cmp);
	}
}

/************************HeapUp************************************************/
void HeapUp(char *arr, size_t elmSize, size_t elmIndex, cmp_t cmp){
	
	while (elmIndex != 0){
		int cmp_res = 0;
		size_t parent_index = (elmIndex - 1) / 2;
		char *parent =  &arr[parent_index * elmSize];
		char *cur = &arr[elmIndex * elmSize];

		cmp_res = cmp(parent, cur);

		if (cmp_res < 0){
			SwapDataByBytes(parent, cur, elmSize);
			elmIndex = parent_index;
		}
		else{
			break;
		}
	}
}

/************************HeapDown**********************************************/
void HeapDown(char *arr, size_t arrSize, size_t elmSize, cmp_t cmp){
	char *cur = arr;
	char *end = arr + arrSize * elmSize;
	size_t index = 0;

	while (cur < end){
		int cmp_res = 0;
		size_t bigger_child_index = FindBiggerChild(arr, arrSize, elmSize, 
															cmp, index);
		char *bigger_child = arr + bigger_child_index * elmSize;
		cur = arr + index * elmSize;

		cmp_res = cmp(cur, bigger_child);

		if (cmp_res < 0){
			SwapDataByBytes(cur, bigger_child, elmSize);
			index = bigger_child_index;
		}
		else{
			break;
		}
	}
}

/************************FindBiggerChild***************************************/
size_t FindBiggerChild(char *arr, size_t arrSize, size_t elmSize, cmp_t cmp, 
																size_t index){
	size_t numOfChildren = 0;
	size_t child_index = index;
	
	numOfChildren = ((index * 2 + 1) < arrSize) + ((index * 2 + 2) < arrSize);

	if (1 == numOfChildren){
		child_index = (index * 2) + 1;
	}
	else if (2 == numOfChildren){
		child_index = BiggerOfTwoChildren(arr, elmSize, cmp, index);
	}

	return child_index;
}

/***********************BiggerOfTwoChildren************************************/
size_t BiggerOfTwoChildren(char *arr, size_t elmSize, cmp_t cmp, size_t index){
	int cmpRes = 0;
	size_t leftChildIndex =  index * 2 + 1;
	size_t rightChildIndex =  index * 2 + 2;

	cmpRes = cmp(&arr[leftChildIndex * elmSize] , &arr[rightChildIndex * 
																	elmSize]);

	return (cmpRes < 0) ? rightChildIndex : leftChildIndex;
}

/***************************SwapDataByBytes************************************/
void SwapDataByBytes(char *first, char *second, size_t numOfBytes){
	size_t i = 0;

	assert(NULL != first);
	assert(NULL != second);

	for (i = 0; i < numOfBytes; ++i){
		char tmp = first[i];
		first[i] = second[i];
		second[i] = tmp;
	}
}
