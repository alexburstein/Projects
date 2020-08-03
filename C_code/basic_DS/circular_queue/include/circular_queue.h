#ifndef CIRCULAR_QUEUE_H
#define CIRCULAR_QUEUE_H

typedef struct c_queue queue_t;

/* creating a circular queue.
* param: arraySize - number of elements for that can be fit into the 
	circular queue at once.
* param: sizeOfElement - size of each element in the queue.
* returns: a pointer to a circular queue. if allocation failed, returns NULL; */
queue_t *createCircularQueue(int arraySize);


/* destroy a circular queue.
* param: queue - an instance of the circular queue.
*/
void destroyCircularQueue(queue_t *queue);


/* enqueue element in the queue.
* param: queue - an instance of the circular queue.
* param: element - a pointer to an element to add to the queue.
* returns: a pointer to a circular queue. if alocation failed, returns NULL; */
int enqueue(queue_t *queue, void *element);


/* dequeue element from the queue.
* param: queue - an instance of the circular queue.
* returns: a pointer to the element in the queue. */
void *dequeue(queue_t *queue);


#endif /*CIRCULAR_QUEUE_H*/
