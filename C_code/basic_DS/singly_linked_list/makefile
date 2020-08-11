NAME = linked_list
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
OBJ = test $(NAME)1_debug $(NAME)2_debug $(NAME)3_debug #$(NAME)_release 
INCLUDES = -I $(INCLUDE_PATH)

all : $(OBJ) clean

test : $(TESTS) $(H_FILES)
	$(CC) -c $(CFLAGS) $(DEBUG_FLAGS) $(TESTS) $(INCLUDES)

#$(NAME)_release : $(C_SRC) test.o $(H_FILES) 
#	$(CC) $(CFLAGS) $(RELEASE_FLAGS) $(C_SRC) test.o $(INCLUDES) -o $(NAME)_release.out

$(NAME)1_debug : $(C_SRC) test1.o $(H_FILES) 
	$(CC) $(CFLAGS) $(DEBUG_FLAGS) $(C_SRC) test1.o $(INCLUDES) -o $(NAME)1_debug.out

$(NAME)2_debug : $(C_SRC) test2.o $(H_FILES) 
	$(CC) $(CFLAGS) $(DEBUG_FLAGS) $(C_SRC) test2.o $(INCLUDES) -o $(NAME)2_debug.out

$(NAME)3_debug : $(C_SRC) test3.o $(H_FILES) 
	$(CC) $(CFLAGS) $(DEBUG_FLAGS) $(C_SRC) test3.o $(INCLUDES) -o $(NAME)3_debug.out

clean:
	rm *.o