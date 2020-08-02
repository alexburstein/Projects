#include <stdio.h> /* for printf */
#include <stdlib.h> /* for malloc / free */
#include "circular_queue.h" /*circular queue API*/

/*
	a circular queue. 
	that gets all kinds of data.
*/


struct c_queue{
	void **buff;
	int queueStart;
	int queueSize;
	int buffSize;
};


queue_t *createCircularQueue(int numberOfElements){
	queue_t* queue = malloc(sizeof(queue_t));
	if(queue == NULL){
		return NULL;
	}

	queue->buff = malloc(numberOfElements * sizeof(void*));
	if(queue->buff == NULL){
		free(queue);
		return NULL;
	}
	
	queue->queueStart = 0;
	queue->queueSize = 0;
	queue->buffSize = numberOfElements;

	return queue;
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



