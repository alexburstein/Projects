#include <stdio.h>/*printf*/
#include <stdlib.h>/*free*/

#include "linked_list.h"/*API for all linked list action func*/

#define PASS(str) printf("\033[0;32m%s\033[0m\n", str)
#define FAIL(str) printf("\033[0;31m%s\033[0m in line %d \n", str, __LINE__)

enum { SUCCSSES, FAIL};

void TestLinkedListFunctionallity();
void TestLinkedListFindForEachLoop();

static int NumSubtractionForData(void *data, void *param);

static int IntCmp(const void *node_data, const void *user_data);

static int NumAdditionForData(void *data, void *param);

static int DivideDataByNum(void *data, void *param);

int main()
{
   	TestLinkedListFunctionallity();
	
	TestLinkedListFindForEachLoop();

	return 0;
}

void TestLinkedListFunctionallity()
{
	slist_node_t *head_node = NULL;
	slist_node_t *new_node1 = NULL;
	slist_node_t *new_node2 = NULL;
    slist_node_t *current_removed_node = NULL;
	int a = 5;
	int b  = 6;
	int c  = 7;
	int fails_counter = 0;
/***********Test1 - create head(5) - new_node1(6) linked list******************/


	head_node = SlistCreateNode(&a, NULL);
	new_node1 = SlistCreateNode(&b, NULL);
	SlistInsertAfterNode(head_node, new_node1);

	if (*((int*)head_node->data)!= a)
	{
		FAIL("failed to create head");
		++fails_counter;
	}

	if (*((int*)head_node->next_node->data) != b)
	{
		FAIL("failed to insert new_node1 after head");
		++fails_counter;
	}

	SlistFreeAll(head_node);
/******Test 2.1 - create head(5) new_node1(6)  new_node2(7)   linked list*****/
    new_node1 = SlistCreateNode(&a, NULL);
	new_node2 = SlistCreateNode(&b, NULL);
	head_node = SlistCreateNode(&c, NULL);
	SlistInsertNode(head_node, new_node1);
	SlistInsertNode(new_node1, new_node2);

	if (*((int*)head_node->data) != a)
	{
		FAIL("failed to create head");
		++fails_counter;
	}

	if (*((int*)head_node->next_node->data) != b)
	{
		FAIL("failed to insert new_node1 before head");
		++fails_counter;
	}

	if (*((int*)head_node->next_node->next_node->data) != c)
	{
		FAIL("failed to insert new_node2 before new_node1");
		++fails_counter;
	}

	if(SlistCount(head_node) != 3)
	{
		FAIL("failed on SlistCreateNode function,"
			 " wrong counting");
		++fails_counter;
	}
/**Test2.2 - remove current - new_node1  creating:create head(5) new_node2(7)**/
    current_removed_node = SlistRemove(new_node1);

	if (*((int*)current_removed_node->data) != b)
	{
		FAIL("failed to remove new_node1 SlistRemove");
		++fails_counter;
	}

	free(current_removed_node);
	
	if (*((int*)head_node->data) != a)
	{
		FAIL("failed to remove new_node1 SlistRemove");
		++fails_counter;
	}

	if (*((int*)head_node->next_node->data) != c)
	{
		FAIL("failed to remove new_node1 on SlistRemove");
		++fails_counter;
	}

	if(SlistCount(head_node) != 2)
	{
		FAIL("failed to remove new_node1 on SlistRemove,"
			 " wrong counting");
		++fails_counter;
	}

/**Test 3- create new_node1 and connact to the old list 
***new_node2(7) - new_node1(6) - head(5)****************/
	new_node1 = SlistCreateNode(&b, NULL);
	SlistInsertAfterNode(head_node, new_node1);

	if (*((int*)head_node->data) != a)
	{
		FAIL("failed to create head");
		++fails_counter;
	}

	if (*((int*)head_node->next_node->data) != b)
	{
		FAIL("failed to insert new_node1 before head");
		++fails_counter;
	}

	if (*((int*)head_node->next_node->next_node->data) != c)
	{
		FAIL("failed to insert new_node2 before new_node1");
		++fails_counter;
	}

	if (0 == fails_counter)
	{
		PASS("TestLinkedListFunctionallity");
	}
    
    SlistFreeAll(head_node);
}

void TestLinkedListFindForEachLoop()
{
	slist_node_t *head_node = NULL;
	slist_node_t *new_node1 = NULL;
	slist_node_t *new_node2 = NULL;
	slist_node_t *new_node3 = NULL;
    slist_node_t *find_node_return = NULL;
    slist_node_t *intersection_func_return = NULL;
	int a = 1;
	int b = 2;
	int c = 3;
	int d = 10;
	int f = 9;
	int fails_counter = 0;

/****** test 1 create head(5) - new_node1(6) - new_node2(7)  linked list*******/
	head_node = SlistCreateNode(&a, NULL);
    new_node1 = SlistCreateNode(&b, NULL);
	new_node2 = SlistCreateNode(&c, NULL);

	SlistInsertAfterNode(head_node, new_node1);
	SlistInsertAfterNode(new_node1, new_node2);
	
if (*((int*)head_node->data) != a)
	{
		FAIL("failed to create head");
		++fails_counter;
	}

	if (*((int*)head_node->next_node->data) != b)
	{
		FAIL("failed to insert new_node1 before head");
		++fails_counter;
	}

	if (*((int*)head_node->next_node->next_node->data) != c)
	{
		FAIL("failed to insert new_node2 before new_node1");
		++fails_counter;
	}
/*****Test 2- check if some node hold b value*********************************/
    find_node_return =  SlistFind(head_node , &b ,IntCmp);

	if (*((int*)find_node_return->data) != b)
	{
		FAIL("failed on SlistFind function ");
		++fails_counter;
	}

/*****Test 3- add each node d value *******************************************/
	if (SlistForEach(new_node2, NumAdditionForData, &d) != 0)
	{
		FAIL("failed on SlistForEach NumAdditionForData func");
		++fails_counter;
	}
/*****Test 4- divide each node by d value(0) **********************************/
	d = 0;

	if (SlistForEach(head_node, DivideDataByNum, &d) != 1)
	{
		FAIL("failed on SlistForEach DivideDataByNum func");
		++fails_counter;
	}
/*****Test 5- return old value before addition and check flip linked list *****/
	d = 10;

	if (SlistForEach(head_node, NumSubtractionForData, &d) != 0)
	{
		FAIL("failed on SlistForEach NumSubtractionForData func");
		++fails_counter;
	}

    head_node = SlistFlip(head_node);


	if (*((int*)head_node->data) != c )
	{
		FAIL("failed to create head");
		++fails_counter;
	}

    head_node = SlistFlip(head_node);
/*****Test 6- create loop and check whether the function discover it **********/
    new_node2->next_node = head_node;

	if (SlistHasLoops(new_node2) != 1)
	{
		FAIL("SlistHasLoops");
		++fails_counter;
	}

    new_node2->next_node = NULL;

	if (SlistHasLoops(head_node) != 0)
	{	
		FAIL("SlistHasLoops");
		++fails_counter;
	}
	
/*****Test 7- head(5) - new_node1(6) - new_node2(7) linked list 
	  with intersection to newnode1*******************************************/

	new_node3 = SlistCreateNode(&f, new_node1);
	intersection_func_return = SlistFindIntersection(head_node , new_node3);

	if (b != *((int*)intersection_func_return->data))
	{
		FAIL("SlistFindIntersection");
		++fails_counter;
	}

	free(new_node3);
/******************************************************************************/


	if (0 == fails_counter)
	{
		PASS("TestLinkedListFindForEachLoop");
	}

	SlistFreeAll(head_node);
}



static int IntCmp(const void *node_data, const void *user_data)
{
	if ((int*)user_data == (int*)node_data)
    {
        return 0;
    }
    else
    {
        return 1;
    }
    
}

static int NumAdditionForData(void *data, void *param)
{
	*((int*)data) += *((int*)param);

	return SUCCSSES;
}



static int NumSubtractionForData(void *data, void *param)
{
	*((int*)data) -= *((int*)param);

	return SUCCSSES;
}

static int DivideDataByNum(void *data, void *param)
{
	if (0 == *((int*)param))
	{
		return FAIL;
	}

	*((int*)data) /= *((int*)param);

	return SUCCSSES;
}












