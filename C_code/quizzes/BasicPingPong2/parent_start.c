/*******************************************************************************
 * PingPong2 																   *
 * by Alex Burstein   alexburstein@gmail.com 								   *
 ******************************************************************************/
#include <stdlib.h> /* exit */
#include <unistd.h> /* fork*/
#include <sys/wait.h> /* wait, WEXITSTATUS, WIFEXITED macros */
#include <string.h> /* memset */

#define UNUSED(x) ((void)x)

typedef struct sigaction sigact_t;

int flag = 0;

static void StartPingPong();
static void InitSigusr1();
static void DoOrExit(int func_res);
static void Sigusr1Handler(int signal_number);


int main()
{ 
	StartPingPong();

	return 0;
}

/*********************************StartPingPong********************************/
static void StartPingPong()
{
	pid_t c_pid = 0;
	InitSigusr1();

	c_pid = fork();
	DoOrExit(c_pid);

	while (c_pid)
	{
		if (flag)
		{
			write(0, "ping ", 6);
			flag = !flag;
			kill(c_pid, SIGUSR1);
		}
	}
	
	DoOrExit(execl("child.out", "child.out", NULL));
}

/*********************************DoOrExit*************************************/
static void DoOrExit(int func_res)
{
	if (0 > func_res)
	{
		exit(EXIT_FAILURE);
	}
}

/*********************************InitSigusr1**********************************/
static void InitSigusr1()
{
	sigact_t sa;
	memset (&sa, 0, sizeof(sa));
	sa.sa_handler = Sigusr1Handler;
	sa.sa_flags = SA_RESTART;
	sigaction(SIGUSR1, &sa, NULL);
}

/*********************************Sigusr1Handler*******************************/
static void Sigusr1Handler(int signal_number)
{
	UNUSED(signal_number);
	flag = !flag;
}

