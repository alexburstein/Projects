#include <stdio.h> /* printf*/
#include <stdlib.h>	/*malloc, free*/


#include "linked_list.h"

#define TEST_RES(expretion) ((!expretion) ?\
printf("\033[0;32m%s is OK\033[0m\n", #expretion) :\
printf("\033[0;31m%s PROBLEM\033[0m\n", #expretion));

int SlistCreateNode_test();
int SlistInsertAfterNode_test();
int SlistInsertNode_test();
int SlistRemoveAfter_test();
int SlistRemove_test();
int SlistFind_test();
int SlistForEach_test();
int SlistFlip_test();

int IntComparator(const void *node_data, const void *user_data);
int ChangeToA(void *data, void *param);

int SlistHasLoops_test();
int SlistFindIntersection_test();
int SlistCount_test();

int main()
{
	TEST_RES(SlistCreateNode_test());
	TEST_RES(SlistCount_test());
	TEST_RES(SlistInsertAfterNode_test());
	TEST_RES(SlistInsertNode_test());
	TEST_RES(SlistRemoveAfter_test());
	TEST_RES(SlistRemove_test());
	TEST_RES(SlistFind_test());
	TEST_RES(SlistForEach_test());
	TEST_RES(SlistFlip_test());
	TEST_RES(SlistFindIntersection_test());
	TEST_RES(SlistHasLoops_test());

	return 0;
}


int SlistCreateNode_test()
{
	size_t i = 8;

	slist_node_t *test = SlistCreateNode(&i, NULL);

	SlistFreeAll(test);

	return 0;
}


int SlistCount_test()
{
	size_t i = 8;

	slist_node_t *test = SlistCreateNode(&i, NULL);
	slist_node_t *test2 = NULL;

	if 	(SlistCount(test) != 1)
	{
		printf("%lu\n", SlistCount(test));
		return -1;
	}

	test2 = SlistCreateNode(&i, NULL);

	test->next_node = test2;

	if 	(SlistCount(test) != 2)
	{
		printf("%lu\n", SlistCount(test));
		return -1;
	}


	SlistFreeAll(test);
	test = NULL;

if 	(SlistCount(test) != 0)
	{
		printf("%lu\n", SlistCount(test));
		return -1;
	}

	return 0;
}



int SlistInsertAfterNode_test()
{
	size_t x = 3;
	size_t y = 7;
	size_t z = 11;

	slist_node_t *test = NULL;
	slist_node_t *test2 = NULL;
	slist_node_t *test3 = NULL;

	test = SlistCreateNode(&x, NULL);
	test2 = SlistCreateNode(&y, NULL);
	test3 = SlistCreateNode(&z, NULL);

	test->next_node = test2;

	if (SlistInsertAfterNode(test, test3) != test3)
	{
		printf("wrong return value for insert after\n");
	}

	if 	(SlistCount(test) != 3)
	{
		printf("wrong number of nodes after insert, %lu\n", SlistCount(test));
		return -1;
	}

	if 	(test->next_node != test3)
	{
		printf("next node of current node incorect\n");
		return -1;
	}

	if 	(test3->next_node != test2)
	{
		printf("next node of new node incorect\n");
		return -1;
	}

	SlistFreeAll(test);
	test = NULL;

	return 0;
}


int SlistInsertNode_test()
{
	size_t x = 3;
	size_t y = 7;
	size_t z = 11;

	slist_node_t *test = NULL;
	slist_node_t *test2 = NULL;
	slist_node_t *test3 = NULL;

	test = SlistCreateNode(&x, NULL);
	test2 = SlistCreateNode(&y, NULL);
	test3 = SlistCreateNode(&z, NULL);

	test->next_node = test2;


	if (SlistInsertNode(test, test3) != test)
	{
		printf("wrong return value for insert n");
	}

	if 	(SlistCount(test) != 3)
	{
		printf("wrong number of nodes after\n");
		return -1;
	}

	if 	(test3->next_node != test2)
	{
		printf("next node of current node incorect1\n");
		return -1;
	}

	if 	(test->next_node != test3)
	{
		printf("next node of new node incorect2\n");
		return -1;
	}

	if ((*(size_t*)test->data) !=  11)
	{
		printf("value of swaped data incorect\n");
		return -1;
	}

	if ((*(size_t*)test2->data) !=  7)
	{
		printf("value of swaped data incorect\n");
		return -1;
	}

	if ((*(size_t*)test3->data) !=  3)
	{
		printf("value of swaped data incorect\n");
		return -1;
	}

	SlistFreeAll(test);
	test = NULL;

	return 0;
}



int SlistRemoveAfter_test()
{
	size_t x = 3;
	size_t y = 7;
	size_t z = 11;

	slist_node_t *test = NULL;
	slist_node_t *test2 = NULL;
	slist_node_t *test3 = NULL;

	test = SlistCreateNode(&x, NULL);
	test2 = SlistCreateNode(&y, NULL);
	test3 = SlistCreateNode(&z, NULL);

	test->next_node = test2;
	test2->next_node = test3;

	if (SlistRemoveAfter(test) != test2)
	{
		printf("wrong return value for remove after\n");
	}

	if 	(SlistCount(test) != 2)
	{
		printf("wrong number of nodes after remove after\n");
		return -1;
	}

	if 	(test->next_node != test3)
	{
		printf("next node of current node incorect\n");
		return -1;
	}

	SlistFreeAll(test);
	test = NULL;

	return 0;
}



int SlistRemove_test()
{
	size_t x = 3;
	size_t y = 7;
	size_t z = 11;

	slist_node_t *test = NULL;
	slist_node_t *test2 = NULL;
	slist_node_t *test3 = NULL;

	test  = SlistCreateNode(&x, NULL);
	test2 = SlistCreateNode(&y, NULL);
	test3 = SlistCreateNode(&z, NULL);

	test->next_node = test2;
	test2->next_node = test3;

	if (SlistRemove(test2) != test3)
	{
		printf("wrong return value for remove n");
	}

	if 	(SlistCount(test) != 2)
	{
		printf("wrong number of nint SlistFindIntersection_test()odes after remove\n");
		return -1;
	}

	if 	(test->next_node != test2)
	{
		printf("next node of current node incorect1\n");
		return -1;
	}


	if ((*(size_t*)test->data) !=  3)
	{
		printf("value of swaped data incorect1\n");
		return -1;
	}

	if ((*(size_t*)test2->data) !=  11)
	{
		printf("value of swaped data incorect2\n");
		return -1;
	}

	SlistFreeAll(test3);
	SlistFreeAll(test);
	test = NULL;

	return 0;
}



int SlistFind_test()
{
	int a = 3;
	int b = 7;
	int c = 11;
	int d = 13;
	int findme = 7;

	slist_node_t *test = NULL;
	slist_node_t *test2 = NULL;
	slist_node_t *test3 = NULL;
	slist_node_t *test4 = NULL;
	slist_node_t *test5 = NULL;

	test  = SlistCreateNode(&a, NULL);
	test2 = SlistCreateNode(&b, NULL);
	test3 = SlistCreateNode(&c, NULL);
	test4 = SlistCreateNode(&d, NULL);

	test->next_node = test2;
	test2->next_node = test3;
	test3->next_node = test4;

	test5 = SlistFind(test, &findme, IntComparator);

	if ((*(int*)test5->data) !=  7)
	{
	printf("%u\n", *(int*)test5->data);
		return -1;
	}


	SlistFreeAll(test);
	test = NULL;
	test5 = NULL;

	return 0;
}




int SlistForEach_test()
{
	char a[] = "abc";
	char b[] = "def";
	char c[] = "ghi";
	char d[] = "jkl";
	char addme = 'A';
	int check = 0;

	slist_node_t *test = NULL;
	slist_node_t *test2 = NULL;
	slist_node_t *test3 = NULL;
	slist_node_t *test4 = NULL;


	test  = SlistCreateNode(a, NULL);
	test2 = SlistCreateNode(b, NULL);
	test3 = SlistCreateNode(c, NULL);
	test4 = SlistCreateNode(d, NULL);

	test->next_node  = test2;
	test2->next_node = test3;
	test3->next_node = test4;

	check = SlistForEach(test, ChangeToA, &addme);

	if (0 != check)
	{
		printf("problem with action function");
		return -1;
	}

	if ('A' != *a)
	{
		printf("change to data did not aply\n");
		return -1;
	}

	if ('A' != *b)
	{
		printf("change to data did not aply2\n");
		return -1;
	}

	if ('A' != *c)
	{
		printf("change to data did not aply3\n");
		return -1;
	}


	SlistFreeAll(test);
	test = NULL;

	return 0;
}




int SlistFlip_test()
{
	char a[] = "abc";
	char b[] = "def";
	char c[] = "ghi";
	char d[] = "jkl";

	slist_node_t *test = NULL;
	slist_node_t *test2 = NULL;
	slist_node_t *test3 = NULL;
	slist_node_t *test4 = NULL;
	slist_node_t *dummy = NULL;

	test  = SlistCreateNode(a, NULL);
	test2 = SlistCreateNode(b, NULL);
	test3 = SlistCreateNode(c, NULL);
	test4 = SlistCreateNode(d, NULL);

	test->next_node  = test2;
	test2->next_node = test3;
	test3->next_node = test4;


	dummy = SlistFlip(test);

	if (NULL == (dummy))
	{
		printf("new first node adress is NULL;...  problem with flip");
		return -1;
	}

	if (test4->next_node != test3)
	{
		printf("wrong next node adresses after flip");
		return -1;
	}

	if (test3->next_node != test2)
	{
		printf("wrong next node adresses after flip");
		return -1;
	}

	if (test2->next_node != test)
	{
		printf("wrong next node adresses after flip");
		return -1;
	}

	if (test->next_node != NULL)
	{
		printf("wrong next node adresses after flip");
		return -1;
	}

	SlistFreeAll(test4);
	test4 = NULL;

	return 0;
}



int SlistFindIntersection_test()
{
	char a[] = "abc";

	slist_node_t *test = NULL;
	slist_node_t *test2 = NULL;
	slist_node_t *test3 = NULL;
	slist_node_t *test4 = NULL;
	slist_node_t *test5 = NULL;
	slist_node_t *test6 = NULL;
	slist_node_t *dummy = NULL;

	test  = SlistCreateNode(a, NULL);
	test2 = SlistCreateNode(a, NULL);
	test3 = SlistCreateNode(a, NULL);
	test4 = SlistCreateNode(a, NULL);
	test5 = SlistCreateNode(a, NULL);
	test6 = SlistCreateNode(a, NULL);

	test->next_node = test2;
	test2->next_node = test3;
	test3->next_node = test6;

	test4->next_node = test5;
	test5->next_node = test3;

	dummy = SlistFindIntersection(test, test4);

	if (dummy != test3)
	{
		printf("incorect intersection node in return value");
		return -1;
	}

	test5->next_node = test;

	SlistFreeAll(test4);
	test = NULL;


	return 0;
}


int SlistHasLoops_test()
{
	char a[] = "abc";

	slist_node_t *test = NULL;
	slist_node_t *test2 = NULL;
	slist_node_t *test3 = NULL;
	slist_node_t *test4 = NULL;
	slist_node_t *test5 = NULL;

	test  = SlistCreateNode(a, NULL);
	test2 = SlistCreateNode(a, NULL);
	test3 = SlistCreateNode(a, NULL);
	test4 = SlistCreateNode(a, NULL);
	test5 = SlistCreateNode(a, NULL);

	test->next_node = test2;
	test2->next_node = test3;
	test3->next_node = test4;
	test4->next_node = test5;
	test5->next_node = test2;

	if (SlistHasLoops(test) != 1)
	{
		printf("unable to find a prefectly loopy loop");
		return -1;
	}

	test5->next_node = NULL;

	if (SlistHasLoops(test) != 0)
	{
		printf("detects a loopy loop where there is none");
		return -1;
	}

	SlistFreeAll(test);
	test = NULL;

	return 0;
}


int ChangeToA(void *data, void *param)
{
	if (NULL == data || NULL == param)
	{
		return -1;
	}

	*(char *)data = *(char *)param;

	return 0;
}


int IntComparator(const void *node_data, const void *user_data)
{
	return (!(*(int *)node_data == *(int *)user_data));
}
