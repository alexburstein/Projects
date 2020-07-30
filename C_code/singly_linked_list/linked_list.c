/***************************************************************
Singly Linked List.
created by Alex Burstein   alexburstein@gmail.com
****************************************************************/

#include <stdio.h> /* printf*/
#include <stdlib.h>	/*malloc, free*/
#include <assert.h>	/*assert*/

#include "linked_list.h"

void SwapPointers(void **first, void **second);


slist_node_t *SlistCreateNode(void *data, slist_node_t *next_node)
{
	slist_node_t *node = NULL;

	node = malloc (sizeof(slist_node_t));
	if (node == NULL)
	{
		return NULL;
	}

	node->data = data;
	node->next_node = next_node;	

	return node;
}


void SlistFreeAll(slist_node_t *head)
{
	slist_node_t *next_node_holder = NULL;

	while (NULL != head)
	{
		next_node_holder = head->next_node;
	 	free(head);
		head = next_node_holder;
	}
}


size_t SlistCount(const slist_node_t *head)
{
	size_t counter = 0;

	while (NULL != head)
	{
		++counter;
		head = head->next_node;
	}

	return counter;
}


slist_node_t *SlistInsertAfterNode(slist_node_t *current_node,
										slist_node_t *new_node)
{
	assert(NULL != current_node);
	assert(NULL != new_node);

	new_node->next_node = current_node->next_node;
	current_node->next_node = new_node;

	return new_node;
}


slist_node_t *SlistInsertNode(slist_node_t *current_node,
									slist_node_t *new_node)
{
	assert(NULL != current_node);
	assert(NULL != new_node);

	SlistInsertAfterNode(current_node, new_node);
	SwapPointers(&current_node->data, &new_node->data);

	return current_node;
}


slist_node_t *SlistRemoveAfter(slist_node_t *current_node)
{
	slist_node_t *removed_node = NULL;

	assert(NULL != current_node);

	removed_node = current_node->next_node;
	current_node->next_node = removed_node->next_node;
	removed_node->next_node = NULL;

	return removed_node;
}


slist_node_t *SlistRemove(slist_node_t *current_node)
{
	assert(NULL != current_node);

	SwapPointers(&current_node->data, &current_node->next_node->data);

	return 	SlistRemoveAfter(current_node);
}


void SwapPointers(void **first, void **second)
{
	void *tmp = NULL;

	assert(NULL != first);
	assert(NULL != second);

	tmp = *first;
	*first = *second;
	*second = tmp;
}



slist_node_t *SlistFind(const slist_node_t *head, const void *data,
											match_func_t match_func)
{
	slist_node_t *node_runner = NULL;

	assert(NULL != head);
	assert(NULL != data);
	assert(NULL != match_func);

	node_runner = (slist_node_t*)head;

	while((node_runner != NULL) && (0 != (match_func(node_runner->data, data))))
	{
		node_runner = node_runner->next_node;
	}

	return node_runner;
}


int SlistForEach(slist_node_t *head, action_func_t action_func,
									void *param_for_action_func)
{
	int res = 0;

	assert(NULL != head);
	assert(NULL != action_func);
	assert(NULL != param_for_action_func);

	while ((head != NULL) && (0 == res))
	{
		res = (action_func( head->data, param_for_action_func));
		head = head->next_node;
	}

	return res;
}


slist_node_t *SlistFlip(slist_node_t *head)
{
	slist_node_t *before_node = NULL;
	slist_node_t *after_node = NULL;

	assert(NULL != head);

	while (NULL != head)
	{
		after_node = head->next_node;
		head->next_node = before_node;
		before_node = head;
		head = after_node;
	}

	return before_node;
}


int SlistHasLoops(const slist_node_t *head)
{
	slist_node_t *slow = NULL;
	slist_node_t *fast = NULL;

	assert(NULL != head);

	slow = head->next_node;
	fast = head->next_node->next_node;

	while ((slow != fast) && (fast->next_node != NULL))
	{
		slow = slow->next_node;
		fast = fast->next_node->next_node;
	}

	return ((fast->next_node != NULL) && (fast != NULL));
}


slist_node_t *SlistFindIntersection(const slist_node_t *head1,
									const slist_node_t *head2)
{
	size_t head1_len = 0;
	size_t head2_len = 0;

	assert(NULL != head1);
	assert(NULL != head2);

	head1_len = SlistCount(head1);
	head2_len = SlistCount(head2);

	while (head2_len > head1_len)
	{
		head2 = head2->next_node;
		--head2_len;
	}

	while (head2_len < head1_len)
	{
		head1 = head1->next_node;
		--head1_len;
	}

	while (head1 != head2)
	{
		head1 = head1->next_node;
		head2 = head2->next_node;
	}

	return (slist_node_t *)head1;
}
