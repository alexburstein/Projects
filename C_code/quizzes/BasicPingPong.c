/*****************************************************************************
 * proccess signal PingPong 													
 * Created by Alex Burstein   alexburstein@gmail.com 	
 *					 
 * creates two proccesses that play ping pong with SIGUSR1 signals. 
 * busy wait when idle. comunicate with help of a signal handler.
 *****************************************************************************/
#include <unistd.h> /* fork*/
#include <signal.h> /* sigaction */
#include <string.h> /* memset */
#define UNUSED(x) ((void)x)

typedef struct sigaction sigact_t;

static void SignalHandler(int signal_number);
static void StartPingPong();
static void InitSigaction();
static void SendSignal(pid_t pid_to_send);

volatile int flag = 0;

/*********************************MAIN*****************************************/
int main()
{
	StartPingPong();

	return 0;
}

/*********************************StartPingPong********************************/
static void StartPingPong()
{
	InitSigaction();
	pid_t c_pid = fork();
	SendSignal(c_pid ? c_pid : getppid());

	return 0;
}

/*********************************SendSignal***********************************/
static void SendSignal(pid_t pid_to_send)
{
	int id = (pid_to_send == getppid());

	while (1)
	{
		if (flag == id)
		{
			write(0, id ? "pong " : "ping ", 6);
			flag = !flag;
			kill(pid_to_send, SIGUSR1);
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


