#!/bin/bash

gcc -ansi -pedantic-errors -Wall -Wextra -g circular_queue.c tests/test.c -I include/ -o circular_queue.out

./circular_queue.out

#rm circular_queue.out
