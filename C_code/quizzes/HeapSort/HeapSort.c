#include "HeapSort.h"


void HeapSortInPlace(void *base, size_t nitems, size_t elm_size, cmp_t cmp_func)
{
	size_t i = 0;
	size_t unsorted = 0;
	char *arr = (char*)base;

	for (i = 1; i < nitems; ++i)
	{
		HeapUp(arr, elm_size, i, cmp_func);
	}

	for (unsorted = nitems; 0 < unsorted; --unsorted)
	{
		char *last_elm = arr + (unsorted - 1) * elm_size;

		SwapDataByBytes(arr, last_elm, elm_size);
		HeapDown(arr, unsorted - 1, elm_size, cmp_func);
	}
}

/************************HeapUp************************************************/
void HeapUp(char *arr, size_t elm_size, size_t index, cmp_t cmp_func)
{
	while (index != 0)
	{
		int cmp_res = 0;
		size_t parent_index = ((index - 1) / 2);
		char *parent = arr + (parent_index * elm_size);
		char *cur = arr + index * elm_size;

		cmp_res = cmp_func(parent, cur);

		if (cmp_res < 0)
		{
			SwapDataByBytes(parent, cur, elm_size);
			index = parent_index;
		}
		else
		{
			break;
		}
	}
}

/************************HeapDown**********************************************/
void HeapDown(char *arr, size_t arr_size, size_t elm_size, cmp_t cmp_func)
{
	char *cur = arr;
	char *end = arr + arr_size * elm_size;
	size_t index = 0;

	while (cur < end)
	{
		int cmp_res = 0;
		size_t bigger_child_index = FindBiggerChild(arr, arr_size, elm_size, 
															cmp_func, index);
		char *bigger_child = arr + bigger_child_index * elm_size;
		cur = arr + index * elm_size;

		cmp_res = cmp_func(cur, bigger_child);

		if (cmp_res < 0)
		{
			SwapDataByBytes(cur, bigger_child, elm_size);
			index = bigger_child_index;
		}
		else
		{
			break;
		}
	}
}

/************************FindBiggerChild***************************************/
size_t FindBiggerChild(char *arr, size_t arr_size, size_t elm_size, cmp_t cmp_func, size_t index)
{
	size_t num_of_children = 0;
	size_t child_index = index;

	assert(NULL != arr);
	assert(NULL != cmp_func);

	num_of_children = NumOfCHildren(arr_size, index);

	if (1 == num_of_children)
	{
		child_index = ((index * 2) + 1);
	}
	else if (2 == num_of_children)
	{
		child_index = BiggerOfTwoChildren(arr, elm_size, cmp_func, index);
	}

	return child_index;
}

/***********************BiggerOfTwoChildren************************************/
size_t BiggerOfTwoChildren(char *arr, size_t elmSize, cmp_t cmp, size_t index)
{
	int cmpRes = 0;
	size_t leftChildIndex =  index * 2 + 1;
	size_t rightChildIndex =  index * 2 + 2;

	cmpRes = cmp(&arr[leftChildIndex * elmSize] ,
				 &arr[rightChildIndex * elmSize]);

	return (cmpRes < 0) ? rightChildIndex : leftChildIndex;
}

/***********************NumOfCHildren******************************************/
size_t NumOfCHildren(size_t arrSize, size_t index)
{
	size_t leftChildIndex =  index * 2 + 1;
	size_t rightChildIndex =  index * 2 + 2;		
	
	return (leftChildIndex < arrSize) + (rightChildIndex < arrSize);
}

/***************************SwapDataByIndex************************************/
void SwapDataByBytes(char *first, char *second, size_t numOfBytes)
{
	size_t i = 0;

	assert(NULL != first);
	assert(NULL != second);

	for (i = 0; i < numOfBytes; ++i)
	{
		char tmp = first[i];
		first[i] = second[i];
		second[i] = tmp;
	}
}
