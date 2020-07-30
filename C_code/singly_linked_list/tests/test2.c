#include <stdio.h> /* printf */
#include <string.h> /* memcmp */

#include "linked_list.h" /* slist_node_t */

#define OK (printf("\033[1;32mOK.\033[0m\n"))
#define FAIL(exp) (printf("\033[1;31mFAIL:\033[0m\n%s\nline: %d",exp, __LINE__))
#define CHECK(x, y, exp) (x == y ? OK : FAIL(exp))
#define NEW_LINE (printf("\n\n"))


static void TestFlow1();
static void TestFlow2();
static void TestFlow3();
static void TestFlow4();

int DataCmp(const void *node_data, const void *user_data);
int AddNumToData(void *data, void* num);

int main()
{
	TestFlow1();
	TestFlow2();
	TestFlow3();
	TestFlow4();

	return 0;
}

static void TestFlow1()
{
	int a = 5;
	int b = 7;
	int c = 999;
	int d = 10000;
	int e = -300;
	char f = 't';
	
	slist_node_t *slist_node1 = SlistCreateNode(&a, NULL); /* head */
	slist_node_t *slist_node2 = SlistCreateNode(&b, NULL);
	slist_node_t *slist_node4 = SlistCreateNode(&d, NULL);
	slist_node_t *slist_node3 = SlistCreateNode(&c, slist_node4);
	slist_node_t *slist_node5 = SlistCreateNode(&e, NULL);
	slist_node_t *slist_node6 = SlistCreateNode(&f, NULL);
	
	slist_node_t *removed_node1 = NULL;
	slist_node_t *removed_node2 = NULL;
	
	printf("This is a test for Flow1:\n\n");
	
	
	CHECK(1, SlistCount(slist_node1), "Count 1 node after creation");
	CHECK(&b, SlistInsertAfterNode(slist_node1, slist_node2)->data, "Check if data of inserted(after) node is equal to expected value #2");
	CHECK(2, SlistCount(slist_node1), "Count 2 nodes after 1 insertion(after)");
	CHECK(&c, SlistInsertAfterNode(slist_node2, slist_node3)->data, "Check if data of inserted(after) node is equal to expected value #4");
	
	/* #5*/
	if (&d == (SlistInsertAfterNode(slist_node3, slist_node4))->data)
	{
		OK;
	}
	else
	{
		FAIL("Check if data of inserted(after) node is equal to expected value #5");
	}
	
	/* #6*/
	if (&e == (SlistInsertAfterNode(slist_node4, slist_node5))->data)
	{
		OK;
	}
	else
	{
		FAIL("Check if data of inserted(after) node is equal to expected value #6");
	}
	
	
	/* #7 - Count 5 nodes after 5 insertions(after)*/
	if (5 == (SlistCount(slist_node1)))
	{
		OK;
	}
	else
	{
		FAIL("Count 5 nodes after 4 insertions(after)");
	}

	
	/* #8 - Check if data of removed(after) node is equal to expected value*/
	removed_node1 = SlistRemoveAfter(slist_node1);
	if (&b == removed_node1->data) 
	{
		OK;
		
		removed_node1->next_node = NULL;
		SlistFreeAll(removed_node1);
	}
	else
	{
		FAIL("Check if data of removed(after) node is equal to expected value");
	}
	
	
	/* #9 - Check if data of removed(current) node is equal to expected value*/
	removed_node2 = SlistRemove(slist_node3);
	if (&c == removed_node2->data)
	{
		OK;
		
		removed_node2->next_node = NULL;
		SlistFreeAll(removed_node2);
	}
	else
	{
		FAIL("Check if data of removed(current) node is equal to expected value");
	}
	
	
	/* #10 - Count 3 nodes after removing 2 of the 5*/
	if (3 == (SlistCount(slist_node1)))
	{
		OK;
	}
	else
	{
		FAIL("Count 3 nodes after removing 2 of the 5");
	}
	
	
	/* #11 - Check if data of inserted(before) node is equal to expected value*/
	if (&f == (SlistInsertNode(slist_node3, slist_node6))->data)
	{
		OK;
	}
	else
	{
		FAIL("Check if data of inserted(before) node is equal to expected value");
	}
	
	
	/* #12 - Count 4 nodes after insert(before)*/
	if (4 == (SlistCount(slist_node1)))
	{
		OK;
	}
	else
	{
		FAIL("Count 4 nodes after insert(before)");
	}
	
	
	/* #13 - Try to find a node with a matched data*/
	if (NULL != SlistFind(slist_node1, &e, DataCmp))
	{
		OK;
	}
	else
	{
		FAIL("Try to find a node with a matched data");
	}
	
	
	/* #14 - Try to search data that does not exsist in any of the nodes*/
	if (NULL == SlistFind(slist_node1, &b, DataCmp))
	{
		OK;
	}
	else
	{
		FAIL("Try to search data that does not exsist in any of the nodes");
	}
	
	
	SlistFreeAll(slist_node1);
	
	NEW_LINE;
}

									
static void TestFlow2()
{
	int a = 1;
	int b = 2;
	int c = 3;
	int d = 4;
	int e = 5;
	int test_param_for_action = 10;
	int expected_results_after_action[] = {11, 12, 13, 14, 15};
	int *expected_results_after_flip[5]; 
	size_t i = 0; 
	
	
	slist_node_t *test_head_node_for_action = NULL;
	slist_node_t *new_head_after_flip = NULL;
	slist_node_t *new_head_after_flip_cpy = NULL;
	
	slist_node_t *slist_node1 = SlistCreateNode(&a, NULL); /* head */
	slist_node_t *slist_node2 = SlistCreateNode(&b, NULL);
	slist_node_t *slist_node4 = SlistCreateNode(&d, NULL);
	slist_node_t *slist_node3 = SlistCreateNode(&c, NULL);
	slist_node_t *slist_node5 = SlistCreateNode(&e, NULL);
	
	printf("This is a test for Flow2:\n\n");
	
	SlistInsertAfterNode(slist_node1, slist_node2);
	SlistInsertAfterNode(slist_node2, slist_node3);
	SlistInsertAfterNode(slist_node3, slist_node4);
	SlistInsertAfterNode(slist_node4, slist_node5);
	
	expected_results_after_flip[0] = &e;
	expected_results_after_flip[1] = &d;
	expected_results_after_flip[2] = &c;
	expected_results_after_flip[3] = &b;
	expected_results_after_flip[4] = &a;
	
	
	/* #1 Try ForEach */
	if (0 == SlistForEach(slist_node1, AddNumToData, &test_param_for_action))
	{
		test_head_node_for_action = slist_node1;
		
		while (NULL != test_head_node_for_action)
		{
			if (expected_results_after_action[i] !=
					 *((int*)test_head_node_for_action->data))
			{
				FAIL("Check if data of each of the nodes match expected results");
				break;
			}
			else
			{
				OK;
			}
			
			++i;
			test_head_node_for_action = test_head_node_for_action->next_node;
		}
	}
	else
	{
		FAIL("Try to action func");
	}
	
	
	/* #2 Count same amount of nodes to flipped list */
	new_head_after_flip = SlistFlip(slist_node1);
	
	if (5 == (SlistCount(new_head_after_flip)))
	{
		OK;
	}
	else
	{
		FAIL("Count 5 nodes");
	}
	
	
	new_head_after_flip_cpy = new_head_after_flip;
	
	/* #3 Check the order of the nodes in the flipped list by matching their 
		data to the expected results */
	i = 0;
	while (NULL != new_head_after_flip)
	{
		if (expected_results_after_flip[i] == new_head_after_flip->data)
		{
			OK;
		}
		else
		{
			FAIL("Check if the flipped list match the expected result");
		}
		
		++i;
		
		new_head_after_flip = new_head_after_flip->next_node;
	}
	
	
	SlistFreeAll(new_head_after_flip_cpy);
	
	NEW_LINE;
}

static void TestFlow3()
{
	int a = 5;
	int b = 7;
	int c = 999;
	int d = 10000;
	
	slist_node_t *slist_node1 = SlistCreateNode(&a, NULL); /* head */
	slist_node_t *slist_node2 = SlistCreateNode(&b, NULL);
	slist_node_t *slist_node3 = SlistCreateNode(&c, NULL);
	slist_node_t *slist_node4 = SlistCreateNode(&d, NULL);
	
	printf("This is a test for Flow3:\n\n");
	
	SlistInsertAfterNode(slist_node1, slist_node2);
	SlistInsertAfterNode(slist_node2, slist_node3);
	SlistInsertAfterNode(slist_node3, slist_node4);
	
	slist_node4->next_node = slist_node2; /* cause a loop in the list */
	
	/* Test HasLoops */
	if (SlistHasLoops(slist_node1))
	{
		OK;
	}
	else
	{
		FAIL("Test HasLoops");
	}
	
	/* Break the loop */
	slist_node4->next_node = NULL;
	
	SlistFreeAll(slist_node1);
	
	NEW_LINE;
}

static void TestFlow4()
{
	int a = 5;
	int b = 7;
	int c = 999;
	int d = 10000;
	char e = 't';
	char f = 'T';
	
	slist_node_t *slist_node1 = SlistCreateNode(&a, NULL); /* head */
	slist_node_t *slist_node2 = SlistCreateNode(&b, NULL);
	slist_node_t *slist_node3 = SlistCreateNode(&c, NULL);
	slist_node_t *slist_node4 = SlistCreateNode(&d, NULL);
	slist_node_t *slist_node5 = SlistCreateNode(&e, NULL);
	slist_node_t *slist_node6 = SlistCreateNode(&f, NULL);
	slist_node_t *intersection_node = NULL;
	
	printf("This is a test for Flow4:\n\n");
	
	/* list #1 */
	SlistInsertAfterNode(slist_node1, slist_node2);
	SlistInsertAfterNode(slist_node2, slist_node3);
	SlistInsertAfterNode(slist_node3, slist_node4);
	
	/* list #2 (intersectes with list #1) */
	SlistInsertAfterNode(slist_node5, slist_node6);
	slist_node6->next_node = slist_node4;
	
	
	/* Try to find intersection */
	if (NULL != SlistFindIntersection(slist_node1, slist_node5))
	{
		OK;
	}
	else
	{
		FAIL("Try to find intersection");
	}
	
	/* Try to find intersection - switch argument's places */
	intersection_node = SlistFindIntersection(slist_node5, slist_node1);
	if (NULL != intersection_node)
	{
		OK;
		
		intersection_node->next_node = NULL; /* Destroy intersection */
	}
	else
	{
		FAIL("Try to find intersection - switch argument's places");
	}
	
	
	SlistFreeAll(slist_node1);
	SlistFreeAll(slist_node5);
	
	
	NEW_LINE;
}

int DataCmp(const void *node_data, const void *user_data)
{
	if (node_data == user_data)
	{
		return 0;
	}
	else
	{
		return -1;
	}
}

int AddNumToData(void *data, void* num)
{
	*((int*)data) += *((int*)num);
	
	return 0;
}
