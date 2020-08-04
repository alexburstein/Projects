#include "HeapSort.h"

#define OK(info) (printf("\033[1;32m%s - OK.\033[0m\n",info))
#define FAIL(info) (printf("\033[1;31m%s - PROBLEM line: %d \033[0m\n",info, __LINE__))
#define CHECK(x, y, info) (x == y ? OK(info) : FAIL(info))


static int LongCmp(const void *first, const void *second);
static int CharCmp(const void *first, const void *second);

static void basicTest1();
static void basicTest2();



int main(){
	basicTest1();
	basicTest2();

	return 0;
}


static void basicTest1()
{
	size_t i = 0;
	long testArr1[] = {77,3,65,-568,8,-32,1,7,7697,3,12,2,76,678,0,2,2134,67};
	long testArr2[] = {77,3,65,-568,8,-32,1,7,7697,3,12,2,76,678,0,2,2134,67};

	HeapSortInPlace(testArr1 ,sizeof(testArr1) / sizeof(*testArr1), sizeof(*testArr1), LongCmp);
	qsort(testArr2, sizeof(testArr2) / sizeof(*testArr2) ,sizeof(*testArr2), LongCmp);

	CHECK(memcmp(testArr1, testArr2, sizeof(testArr1)), 0, "comparing int sort results between heap sort and system quick soryt");
}


static void basicTest2()
{
	size_t i = 0;
	char *testArr1[] = {"hello", "alex", "name", "and", "am" "I", "my", "is", "coding"};
	char *testArr2[] = {"hello", "alex", "name", "and", "am" "I", "my", "is", "coding"};

	HeapSortInPlace(testArr1 ,sizeof(testArr1) / sizeof(*testArr1), sizeof(*testArr1), CharCmp);
	qsort(testArr2, sizeof(testArr2) / sizeof(*testArr2) ,sizeof(*testArr2), CharCmp);

	CHECK(memcmp(testArr1, testArr2, sizeof(testArr1)), 0, "comparing string sort results between heap sort and system quick soryt");
}


/*********************** LongComparator ************************************************/
static int LongCmp(const void *first, const void *second)
{
	return (*(long*)first - *(long*)second);
}

/*********************** CharComparator ************************************************/
static int CharCmp(const void *first, const void *second)
{
	return strcmp((char*)first, (char*)second);
}