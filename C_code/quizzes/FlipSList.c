#include <stdio.h>
#include <assert.h>
#include "linked_list.h"


void basicTest1(void*(*func)(slist_node_t *head));
void basicTest2(void*(*func)(slist_node_t *head));


void *FlipSListIter(slist_node_t *head)
{
	slist_node_t *before = NULL;
	slist_node_t *after = NULL;

	while (head != NULL)
	{
		after = head->next_node;
		head->next_node = before;
		before = head;
		head = after;
	}

	return before;
}


void *FlipSListRec(slist_node_t *head)
{
	slist_node_t *new_head = NULL;
	
	if (NULL == head->next_node)
	{
		return head;
	}

	new_head = FlipSListRec(head->next_node);
	head->next_node->next_node = head;
	head->next_node = NULL;
	
	return new_head;	
}


int main(){
	basicTest1(&FlipSListRec);
	basicTest1(&FlipSListIter);
	basicTest2(&FlipSListRec);
	basicTest2(&FlipSListIter);
}


void basicTest2(void*(*func)(slist_node_t *head)){

	char a[] = "1";	
	char b[] = "2";	
	char c[] = "3";	
	char d[] = "4";
	char e[] = "5";	

	slist_node_t *node1 = SlistCreateNode(a, NULL);
	slist_node_t *node2 = SlistCreateNode(b, NULL);
	slist_node_t *node3 = SlistCreateNode(c, NULL);
	slist_node_t *node4 = SlistCreateNode(d, NULL);
	slist_node_t *node5 = SlistCreateNode(e, NULL);
	slist_node_t *newHead = NULL;

	node1->next_node  = node2;
	node2->next_node = node3;
	node3->next_node = node4;
	node4->next_node = node5;

	newHead = func(node1);

	assert(NULL != newHead);
	assert(node5->next_node == node4);
	assert(node4->next_node == node3);
	assert(node3->next_node == node2);
	assert(node2->next_node == node1);
	assert(node1->next_node == NULL);

	SlistFreeAll(node5); node5 = NULL;
}


void basicTest1(void*(*func)(slist_node_t *head)){

	char a[] = "1";	
	char b[] = "2";	
	char c[] = "3";	
	char d[] = "4";

	slist_node_t *node1 = SlistCreateNode(a, NULL);
	slist_node_t *node2 = SlistCreateNode(b, NULL);
	slist_node_t *node3 = SlistCreateNode(c, NULL);
	slist_node_t *node4 = SlistCreateNode(d, NULL);
	slist_node_t *newHead = NULL;

	node1->next_node  = node2;
	node2->next_node = node3;
	node3->next_node = node4;

	newHead = func(node1);

	assert(NULL != newHead);
	assert(node4->next_node == node3);
	assert(node3->next_node == node2);
	assert(node2->next_node == node1);
	assert(node1->next_node == NULL);

	SlistFreeAll(node4); node4 = NULL;
}