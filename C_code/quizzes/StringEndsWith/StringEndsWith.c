#include <stdbool.h>
#include <string.h>


bool solution(const char *string, const char *ending){
    int lenDiff = 0;

    if (string == NULL || ending == NULL){
        return false;
    }

    lenDiff = strlen(string) - strlen(ending);

    return (0 > lenDiff) ? false : (strcmp(string + lenDiff, ending) == 0);
}