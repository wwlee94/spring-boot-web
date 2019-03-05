#include <stdio.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>
#include </usr/include/mysql/mysql.h>

MYSQL mysql;
MYSQL_RES* res;
MYSQL_ROW row;
MYSQL_FIELD* field;
int fields;
int i,j;
FILE* fp;

char dir[255];
char title[255];

void mysqlInit();
void startScoring();

//DB init
void mysqlInit(){
	mysql_init(&mysql);
	mysql_options(&mysql, MYSQL_SET_CHARSET_NAME, "utf8");
	mysql_options(&mysql, MYSQL_INIT_COMMAND, "SET NAMES utf8");
	//mysqlObjectPointer,  hostIP,dbId,dbPw,dbName,port
	if(!mysql_real_connect(&mysql, NULL, "spring","spring","springboot",3306, (char *)NULL, 0))
	{printf("%s\n",mysql_error(&mysql));
	exit(1) ;}
	printf("success mysql_connect\n") ;
}
void startScoring(){

	//query excute
	if((mysql_query(&mysql,"select * from ProblemStatus")) < 0){
		perror("query error");
		exit(1);
	}
	//return query result
	res = mysql_store_result(&mysql);
	//if get result
	if(res){
  	//get fields count;
	fields = mysql_num_fields(res);
	printf("fields count = %d\n",fields);

	//classfiy index
	int index = 0;
	int sNoIndex = 0;
	int emailIndex = 0;
	int proNoIndex = 0;
	int sourceIndex = 0;
	int resultIndex = 0;

	while(field = mysql_fetch_field(res)){
		if(!strcmp(field->name,"sNo")){
			sNoIndex=index;
			//printf("field : %s\n",field->name);
			//printf("index : %d\n",sNoIndex);
		}
		else if(!strcmp(field->name,"email")){
			emailIndex=index;
		}
		else if(!strcmp(field->name,"proNo")){
			proNoIndex=index;
		}
		else if(!strcmp(field->name,"source")){
			sourceIndex=index;
		}
		else if(!strcmp(field->name,"result")){
			resultIndex=index;
		}
		index++;
	}

	//get row
	while((row = mysql_fetch_row(res))){
		int sNo = atoi(row[sNoIndex]);		//scoring number
		char *p_sNo = row[sNoIndex];	
		char *email = row[emailIndex];		//email(ID)
		int proNo = atoi(row[proNoIndex]);	//problem number
		char *p_proNo = row[proNoIndex];
		char *contents = row[sourceIndex];	//source code
		int result = atoi(row[resultIndex]);	//result

		//proccessing problem
		if(result==0){

			printf("sNo : %d\n",sNo);
			sprintf(dir,"../problem/p_%d/sNo_%d",proNo,sNo);
			//create directory 
			mkdir(dir,0777);

			sprintf(title,"%s/%d.c",dir,sNo);
			fp = fopen(title,"w");
			fputs(contents,fp);
			fclose(fp);
			
			int pid=fork();
			if(pid==0){
			//compile run
			const char* cp[]={"./compile",title,p_proNo,p_sNo,NULL};
			execvp(cp[0],(char* const*)cp);				
			}//pid==0
			else { 
				//parent
				int status = 0;
				waitpid(pid,&status,0);

				char answerOutDir[255];
				char dataOutDir[255];
				char errorOutDir[255];
				int isCompile = 0;
				sprintf(answerOutDir,"../problem/p_%d/answer.out",proNo);
				sprintf(dataOutDir,"%s/data.out",dir);
				sprintf(errorOutDir,"%s/error.out",dir);
				FILE* answerOut = fopen(answerOutDir,"r");
				FILE* dataOut = fopen(dataOutDir,"r");
				FILE* errorOut = fopen(errorOutDir,"r");


				//Is it compile perfect?
				//get size
				int fSize;
				fseek(errorOut, 0, SEEK_END);    
   				fSize = ftell(errorOut);       
				if(fSize>0){
					//fSize > 0 ->> Compile Error
					isCompile = -1;
				}  
				else{
					//success Compile
					isCompile = 1;
				}

				//compare answer output
				char answerOutBuf[50];
				char dataOutBuf[50];

				//perfect compile
				if(isCompile==1){
				//get per line
				while(!feof(answerOut) || !feof(dataOut)){
					fgets(answerOutBuf,sizeof(answerOutBuf),answerOut);
					fgets(dataOutBuf,sizeof(dataOutBuf),dataOut);
					//delete last \n
					if(answerOutBuf[strlen(answerOutBuf)-1] == '\n'){
						answerOutBuf[strlen(answerOutBuf)-1] = 0;
					}
					if(dataOutBuf[strlen(dataOutBuf)-1]=='\n'){
						dataOutBuf[strlen(dataOutBuf)-1] = 0;
					}
					printf("answerOutBuf %s\n",answerOutBuf);
					printf("dataOutBuf %s\n",dataOutBuf);	
					if(strcmp(answerOutBuf,dataOutBuf)!=0){
						result = -1;
						break;
					}//if
				}//while
				}//isCompile==1
				else{	
					//compile error code
					result = -2;
				}
			
				//file close
				fclose(answerOut);
				fclose(dataOut);
				fclose(errorOut);

				//update query
				char temp[100];
				if(result==-1){

				printf("Not Correct!\n\n");
					
				sprintf(temp,"update ProblemStatus set result=%d where sNo=%d",result,sNo);
				mysql_query(&mysql,temp);
				}
				else if(result == -2){
				printf("Compile Error\n\n");
				sprintf(temp,"update ProblemStatus set result=%d where sNo=%d",result,sNo);
				mysql_query(&mysql,temp);
				}
				else {
				//correct
				result = 1;
				printf("Correct!!\n\n");
				sprintf(temp,"update ProblemStatus set result=%d where sNo=%d",result,sNo);
				mysql_query(&mysql,temp);
				}
			}//else
		}//result==0;
	}//while
	
	mysql_free_result(res);	
	}
	else{
		//PASS 
	}
	//mysql_close(&mysql);
}
int main(){
	mysqlInit();
	sleep(2);
	//loop
	while(1){
		printf("startScoring\n");
		startScoring();
		sleep(10);
	}
return 0;
}
