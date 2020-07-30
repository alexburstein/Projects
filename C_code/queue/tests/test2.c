#include <stdio.h> /* printf */

#include "linked_list.h" /* slist_node_t */
#include "queue.h" /* queue_t */

#define OK (printf("\033[1;32mOK.\033[0m\n"))
#define FAIL(name_of_test) (printf("\033[1;31mFAIL:\033[0m\n%s\nline: %d\n",name_of_test, __LINE__))
#define NEW_LINE (printf("\n\n"))


static void TestFlow1();
static void TestFlow2();


int main()
{
	TestFlow1();
	TestFlow2();

	return 0;
}

static void TestFlow1()
{
	int a = 5;
	int b = 7;
	int c = 999;
	int d = 10000;
	int e = -300;
	
	queue_t *queue = QueueCreate();
	
	printf("This is a test for Flow1:\n\n");
	
	if (QueueIsEmpty(queue))
	{
		OK;
	}
	else
	{
		FAIL("Check if Q is empty after creation");
	}
	
	if (0 == QueueSize(queue))
	{
		OK;
	}
	else
	{
		FAIL("Check if Q's size is 0 as expected");
	}
	
	if (0 == QueueEnqueue(queue, &a))
	{
		if (1 == QueueSize(queue))
		{
			OK;
		}
		else
		{
			FAIL("Check if Q's size is 1 as expected");
		}
	}
	
	QueueDequeue(queue); /* now &b is the head data */
	
	if (QueueIsEmpty(queue))
	{
		OK;
	}
	else
	{
		FAIL("Check if Q is empty after Dequeue");
	}
	
	if (0 == QueueSize(queue))
	{
		OK;
	}
	else
	{
		FAIL("Check if Q's size is 0 as expected");
	}
	
	if (0 == QueueEnqueue(queue, &b))
	{
		if (1 == QueueSize(queue))
		{
			OK;
		}
		else
		{
			FAIL("Check if Q's size is 1 as expected");
		}
	}
	
	if (0 == QueueEnqueue(queue, &c))
	{
		if (2 == QueueSize(queue))
		{
			OK;
		}
		else
		{
			FAIL("Check if Q's size is 2 as expected");
		}
	}
	
	if (0 == QueueEnqueue(queue, &d))
	{
		if (3 == QueueSize(queue))
		{
			OK;
		}
		else
		{
			FAIL("Check if Q's size is 3 as expected");
		}
	}
	
	if (0 == QueueEnqueue(queue, &e))
	{
		if (4 == QueueSize(queue))
		{
			OK;
		}
		else
		{
			FAIL("Check if Q's size is 4 as expected");
		}
	}
	
	if (&b == QueuePeek(queue))
	{
		OK;
	}
	else
	{
		FAIL("Check if the data returned from Peek match the expected result");
	}
	
	QueueDestroy(queue);
	queue = NULL;
	
	NEW_LINE;
}

static void TestFlow2()
{
	int a = 1;
	int b = 2;
	int c = 3;
	int d = 4;
	int e = 5;
	
	queue_t *queue1 = QueueCreate();
	queue_t *queue2 = QueueCreate();
	
	printf("This is a test for Flow2:\n\n");
	
	QueueEnqueue(queue1, &a);
	QueueEnqueue(queue1, &b);
			
	QueueEnqueue(queue2, &c);			
	QueueEnqueue(queue2, &d);				
	QueueEnqueue(queue2, &e);
	
	if (2 == QueueSize(queue1))
	{
		OK;
	}
	else
	{
		FAIL("Check if Q1's size is 2 as expected");
	}
	
	if (3 == QueueSize(queue2))
	{
		OK;
	}
	else
	{
		FAIL("Check if Q2's size is 3 as expected");
	}
	
	if (5 == QueueSize(QueueAppend(queue1, queue2)))
	{
		OK;
	}
	else
	{
		FAIL("Check after Append if Q1's size is 5 as expected");
	}
	
	QueueDestroy(queue1);
	queue1 = NULL;
	
	QueueDestroy(queue2);
	queue2 = NULL;

	NEW_LINE;
}
