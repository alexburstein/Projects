/***************************************************************
Queue
created by Alex Burstein   alexburstein@gmail.com
8/2019 
****************************************************************/
#include <stdlib.h>	/* malloc, free */
#include <assert.h>	/*assert*/

#include "queue.h" 	/* declerations for Queue functions */
#include "linked_list.h" /* declerations for linked list functions */

struct queue
{
	slist_node_t *dummy_head;	/* the first in line is the first node after the dummy head*/
	slist_node_t *last_in_line;
};


enum status
{
	SUCCESS = 0, 
	FAILED = -1
};


queue_t *QueueCreate()
{
	queue_t *queue = malloc(sizeof(queue_t));
	if (NULL == queue)
	{
		return NULL;
	}

	queue->dummy_head = SlistCreateNode(NULL, NULL);
	if (NULL == queue->dummy_head)
	{
		free(queue);

		return NULL;
	}
	
	queue->last_in_line = queue->dummy_head;

	return queue;
}


void QueueDestroy(queue_t *queue)
{
	assert(NULL != queue);

	SlistFreeAll(queue->dummy_head);	
	queue->dummy_head = NULL; 
	queue->last_in_line = NULL;
	free(queue);
	queue = NULL;
}


size_t QueueSize(const queue_t *queue)
{ 									
	assert(NULL != queue);

	return (SlistCount(queue->dummy_head) - 1); 
}


int QueueIsEmpty(const queue_t *queue)
{
	assert(NULL != queue);
	
	return (queue->last_in_line == queue->dummy_head);
}


int QueueEnqueue(queue_t *queue, void *data)
{
	slist_node_t *new_element = NULL; 
	assert(NULL != queue);

	new_element = SlistCreateNode(data, NULL);
	if (NULL == new_element)
	{
		return FAILED;
	}

	SlistInsertAfterNode(queue->last_in_line, new_element);
	queue->last_in_line = new_element;

	return SUCCESS;
}


void QueueDequeue(queue_t *queue)
{		
	assert(NULL != queue);
	assert(!QueueIsEmpty(queue));
	
	if (queue->dummy_head->next_node == queue->last_in_line)
	{
		queue->last_in_line = queue->dummy_head;
	}

	free(SlistRemoveAfter(queue->dummy_head));
}


void* QueuePeek(const queue_t *queue)
{
	assert(NULL != queue);
	assert(!QueueIsEmpty(queue));

	return ((queue->dummy_head)->next_node)->data;
}


queue_t *QueueAppend(queue_t *dest_queue, queue_t *src_queue)
{
	assert(NULL != dest_queue);
	assert(NULL != src_queue);

	dest_queue->last_in_line->next_node = src_queue->dummy_head->next_node;
	dest_queue->last_in_line = src_queue->last_in_line;
	src_queue->dummy_head->next_node = NULL;
	src_queue->last_in_line = NULL;

	return dest_queue;
}

