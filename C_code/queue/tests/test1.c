/***************************************************************
Queue -  TEST
created by Alex Burstein   alexburstein@gmail.com
     8/2019 
****************************************************************/
#include <stdio.h>

#include "queue.h"

#define TEST_RES(expretion) ((expretion == 1) ?\
printf("\033[0;32m%s is OK\033[0m\n", #expretion) :\
printf("\033[0;31m%s PROBLEM\033[0m\n", #expretion));


enum status
{
	ALL_WELL = 1,
	FAILED = -1
};


int QueueCreateCountDestroy_test();
int QueueEnqueuePeekDequeue_test();
int QueueAppend_test();


int main()
{
	TEST_RES(QueueCreateCountDestroy_test());
	TEST_RES(QueueEnqueuePeekDequeue_test());
	TEST_RES(QueueAppend_test());

    return ALL_WELL;
}

int QueueCreateCountDestroy_test()
{
	queue_t *test = QueueCreate();

	if (QueueSize(test) != 0)
	{
		printf("incorect size of empty Q\n");
		return FAILED;
	}

	if (QueueIsEmpty(test) != 1)
	{
		printf("incorect IsEmpty Q is not recognized corectly\n");
		return FAILED;
	}

	QueueDestroy(test);

	return ALL_WELL;
}


int QueueEnqueuePeekDequeue_test()
{
	size_t i = 0;
	size_t array[] = {10, 20, 30, 40, 50, 60, 70, 80};

	queue_t *test = QueueCreate();

	for(i = 0; i < sizeof(array)/sizeof(*array); ++i)
	{
		if (QueueEnqueue(test, &array[i]) != 0)
		{
			return FAILED;
		}
	}

	if (QueueSize(test) != 8)
	{
		printf("incorect size of full Q\n");
		return FAILED;
	}

	if (*(size_t*)QueuePeek(test) != 10)
	{
		printf("incorect peek return value Q %lu \n", *(size_t*)QueuePeek(test));
		return FAILED;
	}

	QueueDequeue(test);

	if (*(size_t*)QueuePeek(test) != 20)
	{
		printf("incorect peek return value Q %lu \n", *(size_t*)QueuePeek(test));
		return FAILED;
	}

	for(i = 0; i < sizeof(array)/sizeof(*array) -1 ; ++i)
	{
		QueueDequeue(test);
	}

	if (QueueSize(test) != 0)
	{
		printf("incorect size of empty Q\n");
		return FAILED;
	}

	QueueDestroy(test);

	return ALL_WELL;
}



int QueueAppend_test()
{
	size_t i = 0;
	size_t source_arr[] = {10, 20, 30, 40, 50, 60, 70, 80};
	size_t dest_arr[] = {11, 22, 33, 44, 55, 66, 77, 88};

	queue_t *dest_queue = QueueCreate();
	queue_t *src_queue = QueueCreate();

	for(i = 0; i < sizeof(source_arr)/sizeof(*source_arr); ++i)
	{
		if (QueueEnqueue(src_queue, &source_arr[i]) != 0)
		{
			return FAILED;
		}
	}

	for(i = 0; i < sizeof(dest_arr)/sizeof(*dest_arr); ++i)
	{
		if (QueueEnqueue(dest_queue, &dest_arr[i]) != 0)
		{
			return FAILED;
		}
	}

	QueueAppend(dest_queue, src_queue);

	if (QueueSize(dest_queue) != 16)
	{
		printf("incorect size after append Q\n");
		return FAILED;
	}

	if (*(size_t*)QueuePeek(dest_queue) != 11)
	{
		printf("incorect peek return value Q %lu \n", *(size_t*)QueuePeek(dest_queue));
		return FAILED;
	}

	for(i = 0; i < sizeof(source_arr)/sizeof(*source_arr) ; ++i)
	{
		QueueDequeue(dest_queue);
	}

	if (*(size_t*)QueuePeek(dest_queue) != 10)
	{
		printf("incorect peek return value Q %lu \n", *(size_t*)QueuePeek(dest_queue));
		return FAILED;
	}
	

	QueueDestroy(dest_queue);
	QueueDestroy(src_queue);

	return ALL_WELL;	
}
