#include <stdio.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>

char dir[255];
char resFile[255];
char dataInDir[255];
char dataOutDir[255];
char errorOutDir[255];
int execvp_res;

void solution();
int compile(char* argv[]);

int compile(char* argv[]){
	int pid;
	
	printf("'%s' ",argv[1]);
	printf("compile to '%s'\n",resFile);
	const char *cp[]={"gcc",argv[1],"-o",resFile,NULL};
	pid = fork();
	if(pid==0){
		freopen(errorOutDir,"w",stderr);
		execvp_res = execvp(cp[0],(char* const*) cp);
		fclose(stderr);
		exit(0);
	}
	else{
		int status = 0;
		waitpid(pid,&status,0);
		//execvp run result
		printf("execvp value= %d\n",execvp_res);	
		if(execvp_res<0){
			printf("compile fail!!\n");
			exit(1);
		}
		else{
			printf("compile exit!!\n");
			solution();
			exit(0);
		}
		return status;
	}
}
void solution(){

	printf("solution running!!\n");
	
	freopen(dataInDir,"r",stdin);
	freopen(dataOutDir,"w",stdout);

	execl(resFile,resFile,(char *)NULL);
}

int main(int argc,char* argv[]){
	
	sprintf(dir,"../problem/p_%s/sNo_%s",argv[2],argv[3]);
	sprintf(resFile,"%s/sNo_%s.exe",dir,argv[3]);
	sprintf(dataInDir,"../problem/p_%s/answer.in",argv[2]);
	sprintf(dataOutDir,"%s/data.out",dir);
	sprintf(errorOutDir,"%s/error.out",dir);
	
	//init
	remove(resFile);
	remove(dataOutDir);
	remove(errorOutDir);

	int status = compile(argv);
	return 0;
}
