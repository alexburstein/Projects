/*****************************************************************************
 * PingPong2 																 *
 * by Alex Burstein   alexburstein@gmail.com 								 *
 *****************************************************************************/
#include <unistd.h> /* fork*/
#include <signal.h> /* wait, WEXITSTATUS, WIFEXITED macros */
#include <string.h> /* memset */

#define UNUSED(x) ((void)x)

typedef struct sigaction sigact_t;

int flag = 1;

static void StartPingPong();
static void InitSigaction();
static void SignalHandler(int signal_number);


int main()
{
	StartPingPong();

	return 0;
}

/*********************************StartPingPong********************************/
static void StartPingPong()
{
	pid_t p_pid = getppid();
	InitSigaction();
	while (1)
	{
		if (flag)
		{
			write(0, "pong ", 6);
			flag = !flag;
			kill(p_pid, SIGUSR1);
		}
	}
}

/*********************************InitSigaction********************************/
static void InitSigaction()
{
	sigact_t sa;
	memset (&sa, 0, sizeof(sa));
	sa.sa_handler = SignalHandler;
	sa.sa_flags = SA_RESTART; 
	sigaction(SIGUSR1, &sa, NULL);
}

/*********************************SignalHandler********************************/
static void SignalHandler(int signal_number)
{
	UNUSED(signal_number);
	flag = !flag;
}
