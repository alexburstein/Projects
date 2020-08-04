#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
    Created in 2019,
    By Alexander Burstein.

    a function that receives a string, and prints all of its possible permutations
*/


void PrintAllPerm(char *originalString);
static void RotateString(char *str);
static void PrintAllPremLogic(char *originalString, char *rotateString, size_t strLen);



/*
* copy the original string to a mutable char array, print all possible permutation. 
* param: originalString - a pointer to a string
*/
void PrintAllPerm(char *originalString)
{
	char *rotateString = NULL;

	size_t strLen = strlen(originalString);

	rotateString = (char *)malloc(strLen + 1);
	strcpy(rotateString, originalString);

	PrintAllPremLogic(rotateString, rotateString, strLen);

	free(rotateString); rotateString = NULL;
}


static void PrintAllPremLogic(char *originalString, char *rotateString, size_t strLen)
{
	size_t i = 0;

	if (1 == strLen)
	{
		printf("%s\n", originalString);
		return;
	}

	for (i = 0; i < strLen; ++i)
	{
		RotateString(rotateString);
		PrintAllPremLogic(originalString, rotateString + 1, strLen - 1);
	}
}


static void RotateString(char *str)
{
	char tmp = *str;

	for (; *(str + 1) != '\0'; ++str)
	{
		*str = *(str + 1);
	}

	*str = tmp;
}


int main()
{
    PrintAllPerm("hat");
	
    /*PrintAllPerm("alexander");*/
    
	return 0;
}
