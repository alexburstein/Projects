#include <stdio.h> /* for printf */
#include <stdlib.h> /* for malloc / free */
#include "circular_queue.h" /*circular queue API*/

/*
	a circular queue. 
	that gets all kinds of data.
	the user gives a pointer to the buffer and its size.
	implement push and pop.
*/


struct c_queue{
	void **buff;
	int queueStart;
	int queueSize;
	int buffSize;
};


queue_t *createCircularQueue(int numberOfElements){
	queue_t* newQueue = malloc(sizeof(queue_t));
	if(newQueue == NULL){
		return NULL;
	}

	newQueue->buff = malloc(numberOfElements * sizeof(void*));
	if(newQueue->buff == NULL){
		free(newQueue);
		return NULL;
	}
	
	newQueue->queueStart = 0;
	newQueue->queueSize = 0;
	newQueue->buffSize = numberOfElements;

	return newQueue;
}


void destroyCircularQueue(queue_t *queue){
	if (queue == NULL) {
		return;
	}
	free(queue->buff);
	free(queue);
}


int enqueue(queue_t *queue, void *element){
	int elementIndex = 0;
	
	if (queue == NULL || queue->queueSize + 1 > queue->buffSize) {
		return -1;
	}
	
	elementIndex = (queue->queueStart + queue->queueSize) % queue->buffSize;
	++queue->queueSize;
	queue->buff[elementIndex] = element;

	return 0;
}


void *dequeue(queue_t *queue){
	void *element = NULL;
	
	if (queue == NULL || queue->queueSize < 1) {
		return NULL;
	}
	
	element = queue->buff[queue->queueStart];
	queue->queueStart = (queue->queueStart + 1) % queue->buffSize;
	--queue->queueSize;

	return element;
}



