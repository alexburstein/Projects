/******************************************************************************
 * Created on 10.2019 														  *
 * by Alex Burstein   alexburstein@gmail.com 								  *
 ******************************************************************************/

#include <stdio.h>
#include <string.h>
#include <assert.h>

#define WORD_SIZE (sizeof(size_t))

void *Memcpy(void *dest, const void *src, size_t n);
static void BasicTest();
/*
int main()
{
	BasicTest();

	return 0;
}
*/


void *Memcpy(void *dest, const void *src, size_t n)
{
	void *start_of_dest = dest;

	assert(NULL != dest);
	assert(NULL != src);
 /* aligning */
	while((n > 0) && (0 =! ((long unsigned int)dest & (WORD_SIZE - 1))))
	{
		*(char*)dest = *(char*)src;
		dest = (char*)dest + 1;
		src = (char*)src + 1;
		--n;
	}
 /* copying word by word */
	while((n > 0) && (n >= WORD_SIZE))
	{
		*(size_t*)dest = *(size_t*)src;		
		dest = (size_t*)dest + 1;
		src = (size_t*)src + 1;
		n -= WORD_SIZE;
	}
 /* copying tail */
	while(n > 0)
	{
		*(char*)dest = *(char*)src;
		dest = (char*)dest + 1;
		src = (char*)src + 1;
		--n;
	}

	return start_of_dest;
}


/***************************BasicTest***************************************/
static void BasicTest()
{
	char arr1[] = "1111111111111111111111111111111111111111111111111111111111111";
	char arr2[] = "2222222222222222222222222222222222222222222222222222222222222";
	int num_arr[] = { 0, 1, 2, 5, 10, 40};
	size_t i = 0;

	for (i = 0; i < sizeof(num_arr)/sizeof(*num_arr); ++i)
	{
		Memcpy(arr1, arr2, num_arr[i]);
		assert(strncmp(arr1,arr2, i) == 0)
		memset(arr1, '1', 50);
	}

	for (i = 0; i < sizeof(num_arr)/sizeof(*num_arr); ++i)
	{
		Memcpy(arr1, arr2 + 3, num_arr[i]);
		assert(strncmp(arr1,arr2 + 3, i) == 0)
		memset(arr1, '1', 50);
	}

}


