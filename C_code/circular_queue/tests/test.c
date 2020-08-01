#include <stdio.h>
#include <assert.h>
#include <string.h>
#include "circular_queue.h"

static void Test1();
static void Test2();

int main(){
	Test1();
	Test2();

	return 0;
}

void Test1(){
	queue_t *queue = createCircularQueue(5);
	int a = 9;
	int res = enqueue(queue, &a);
	assert(0 == res);
	assert(9 == *(int*)dequeue(queue));
	
	destroyCircularQueue(queue);
}

void Test2(){
	int i = 0;
	queue_t *queue = createCircularQueue(5);
	char *arr[] = {"hello", "my", "name", "is", "alex"};
	
	for(i = 0; i < 5; ++i){
		int res = enqueue(queue, arr[i]);
		assert(0 == res);
	}

	assert(-1 == enqueue(queue, arr[0]));
	
	assert(0 == strcmp((char*)dequeue(queue), "hello"));
	assert(0 == enqueue(queue, "all good"));

	for(i = 0; i < 4; ++i){
		assert(0 == strcmp((char*)dequeue(queue), arr[i + 1]));
	}
	
	assert(0 == strcmp((char*)dequeue(queue), "all good"));
	
	destroyCircularQueue(queue);
}






