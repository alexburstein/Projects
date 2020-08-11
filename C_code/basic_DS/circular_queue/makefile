NAME = circular_queue
# compilation instructions
CC = gcc
CFLAGS = -ansi -pedantic-errors -Wall -Wextra -Wshadow
DEBUG_FLAGS = -g3
RELEASE_FLAGS = -DNDEBUG -O3
# files
C_SRC = $(NAME).c
TESTS = $(TEST_PATH)/*.c
H_FILES = $(INCLUDE_PATH)/$(NAME).h
# paths
TEST_PATH = tests
INCLUDE_PATH = include
# miscellaneous
OBJ = test $(NAME)_release $(NAME)_debug
INCLUDES = -I $(INCLUDE_PATH)

all : $(OBJ) clean

test : $(TESTS) $(H_FILES)
	$(CC) -c $(CFLAGS) $(DEBUG_FLAGS) $(TESTS) $(INCLUDES)

$(NAME)_release : $(C_SRC) test.o $(H_FILES) 
	$(CC) $(CFLAGS) $(RELEASE_FLAGS) $(C_SRC) test.o $(INCLUDES) -o $(NAME)_release.out

$(NAME)_debug : $(C_SRC) test.o $(H_FILES) 
	$(CC) $(CFLAGS) $(DEBUG_FLAGS) $(C_SRC) test.o $(INCLUDES) -o $(NAME)_debug.out

clean:
	rm test.o