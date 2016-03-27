/*Project to simulate link state routing protocol with the help of Dijktra's algorithm
Made by
Sanyam Sachdeva*/
package cs542;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class ProjectCS542 {
	//declaring variables 
	double [][]routerMatirx=new double  [100][100];
	int numberOfRouters=0;
	int sourceRouter=0;
	int destinationRouter=0;
	int []downRouter=new int[100];
	double  []distance=new double [100];
	double  []visitedNode=new double [100];
	int  []pathDetermineNode=new int  [100];
	
	/* function to read the text file and take the values from the file and 
	to save it into the routerMatrix.size is allocated on first row of the 
	text file how many input values it has */
	public double [][] readFile(String path)throws Exception
	{
	
	@SuppressWarnings("resource")
	BufferedReader buffer = new BufferedReader(new FileReader(path));
	double  [][] routerMatirx1=null;
    String line;
    int row = 0;
    int size = 0;
    while ((line = buffer.readLine()) != null) {
        String[] values = line.trim().split("\\s+");
        
        
        if (routerMatirx1 == null) {
            //setting the size for the matrix
        	size = values.length;
            numberOfRouters=size;
            routerMatirx1 = new double [size][size];
            
        }

        for (int col = 0; col < size; col++) {
        	routerMatirx1[row][col] = Double.parseDouble (values[col]);
        }

        row++;
    }
    //saving the read values from the file to the routermatrix that will be used for calculations
   for (int s=0;s<numberOfRouters;s++)
   { 
	   
	   System.out.print("R"+(s+1)+"  ");
	   for (int s1=0;s1<numberOfRouters;s1++)
	   { 
		  if(routerMatirx1[s][s1]==0.0 || routerMatirx1[s][s1]==-1)
	     {
		   routerMatirx[s][s1]=Double.POSITIVE_INFINITY;
			
		   
		   System.out.print(routerMatirx1[s][s1]);
		   System.out.print("   ");
	     }
	   else{
		   routerMatirx[s][s1]=routerMatirx1[s][s1];
		   System.out.print(routerMatirx1[s][s1]);
		   System.out.print("   ");
	       }
	   }
	   System.out.println("");
   }
   System.out.println("");
   //condition to check input values ,they are correct or not 
   int ctr = 0;
   int ctr1=0;
   for(int i=0;i<numberOfRouters;i++)
   {
	   for(int j=0;j<numberOfRouters;j++)
	   {
		   if(i==j)
		   {
		   if(routerMatirx1[i][j]!=0)
		   {
			   System.out.println("node to itself distance is not zero i.e diagonal is not 0");
			   throw new Exception();
		   }
		   }
		   if(routerMatirx1[i][j]<-1)
		   {
			   System.out.println("negative distance  ");
			   throw new Exception();
		   }
		   if(routerMatirx1[i][j]!=routerMatirx1[j][i])
		   {
			   System.out.println("wieghts not equal i.e bidirection weights are present");
			   throw new Exception();
		   }
		   if(routerMatirx1[i][j]==-1)
		   {ctr=ctr+1;
		    if(ctr==(numberOfRouters*numberOfRouters))
		    {
		    	System.out.println("disconnected nodes");
		    	throw new Exception();
		    }
			   
		   }
		   if(routerMatirx1[i][j]==0)
		   {ctr1=ctr1+1;
		    if(ctr1==(numberOfRouters*numberOfRouters))
		    {
		    	System.out.println("disconnected nodes");
		    	throw new Exception();
		    }
			   
		   }
		   
		 }
	   
	   
   }
    return routerMatirx;

	}
	//function to perform Dijstra's algorithm
  public void dijkstraAlgorithm()
 {
	
	
	//initializing the variables each time when the function is called
 for(int i =0;i<numberOfRouters;i++){
  visitedNode[i]=0;
  pathDetermineNode[i]=-1;
  distance[i]=Double.POSITIVE_INFINITY;
  }
 for(int i =0;i<numberOfRouters;i++){
 distance[i]=routerMatirx[sourceRouter][i];
 }
 distance[sourceRouter]=0;
 visitedNode[sourceRouter]=1;

 
   double minimum;

   int nextMove=0;
   //loop to run till the size of matrix is achieved
   for (int i =0;i<numberOfRouters;i++)
   { 
	   //setting minimum value to be infinity each time when it enters loop
	 minimum=Double.POSITIVE_INFINITY;
	
	 //loop to calculate the minimum value in the distance array
	for(int j=0;j<numberOfRouters;j++)
	{ 
		if(minimum>distance[j] && visitedNode[j]!=1)
	
		{ 
			
			minimum=distance[j];
			nextMove=j;
		}
	}
	//setting the node that is visited to 1
	visitedNode[nextMove]=1;
	
	
	  //loop to calculate the minimum distance of a node plus the minimum distance from the above loop
	//update  pathDetermineNode array that will be used to find the path by back tracking it
	   for(int k=0;k<numberOfRouters;k++)
	   { 
		   if(visitedNode[k]!=1)
		   { 
			   if(minimum + routerMatirx[nextMove][k]<distance[k])
			   { //updating distance array
				   distance[k]=minimum+routerMatirx[nextMove][k];
				  //updating  pathDetermineNode array
				   pathDetermineNode[k]=(nextMove);
			   }
			   
		   }
	   }
	   }
  //loop to print the connection table for the source node entered by the user
  for(int i=0;i<numberOfRouters;i++)
   { //initializing variable  
	  String path;//will be used to save the path of source to all nodes 
	   int temp;
	   int k = i+1;
	   
	   path=k+"";
	   temp=i;
	   //do while used to back track pathDetermineNode to get the path 
	   do{
		   temp=pathDetermineNode[temp];
		  
		   if (temp != -1 ) 
		   {	int l= temp+1;
			    
		         path=path+""+l+"";
			   
			} 
		  
	   }
	   while(temp!=-1 );
	  //used to print the connection table for the source node by adding the path string to string buffer
	   //reversing it and printing its 0th index for each iteration 
	   if(i!=sourceRouter){
	   System.out.println("\t Source "+ (sourceRouter+1)+" to Destination "+(i+1)+"  :"+ new StringBuffer(path).reverse().toString().charAt(0));   
	   }
	   else{
		   System.out.println("\t Source "+ (sourceRouter+1)+" to Destination "+(i+1)+"--"); 
	   }
	   }
  

 }
   
   //function to print the routing table for each router on reading the inout from text fil
  public void dijkstraAlgorithmToPrintAllROutingTable()
  { 
	// loop to initialize value of source node with each router 
	  for(int x=0;x<numberOfRouters;x++)	
	  { sourceRouter=x;
	//initializing the value each time function is called
 for(int i =0;i<numberOfRouters;i++){
  visitedNode[i]=0;
  pathDetermineNode[i]=-1;
  distance[i]=Double.POSITIVE_INFINITY;
  }
 //loop to set the distance array with values present in row of the source in the matrix 
 for(int i =0;i<numberOfRouters;i++){
 distance[i]=routerMatirx[sourceRouter][i];
 }
 distance[sourceRouter]=0;
 visitedNode[sourceRouter]=1;

 //initializing minimum
   double minimum;

   int nextMove=0;
   //starting loop for dijktra's algorithm
   for (int i =0;i<numberOfRouters;i++)
   { 
	 minimum=Double.POSITIVE_INFINITY;
	
	 //loop to find minimum with each iteration 
	for(int j=0;j<numberOfRouters;j++)
	{ 
		if(minimum>distance[j] && visitedNode[j]!=1)
	
		{ 
			//setting values of minimum and next move 
			minimum=distance[j];
			nextMove=j;
		}
	}
	//setting the node visited to 1
	visitedNode[nextMove]=1;
	
	
	  //updating the distance array and pathDetermineNod array
	   for(int k=0;k<numberOfRouters;k++)
	   { 
		   if(visitedNode[k]!=1)
		   { 
			   if(minimum + routerMatirx[nextMove][k]<distance[k])
			   { //updating if the above condition are true
				   distance[k]=minimum+routerMatirx[nextMove][k];
				  
				   pathDetermineNode[k]=(nextMove);
			   }
			   
		   }
	   }
	   }
   //printing the routing table 
   System.out.println("routing table for router  "+(sourceRouter+1));
  for(int i=0;i<numberOfRouters;i++)
   { //string to store path of  source node with each node in the topology 
	  String path;
	   int temp;
	   int k = i+1;
	   
	   path=k+"";
	   temp=i;
	   //do while loop to back track the pathDetermineNode array to get the path from source to evry other node in topology
	   do{
		   temp=pathDetermineNode[temp];
		  
		   if (temp != -1 ) 
		   {	int l= temp+1;
			    //appending the string with the router index
		         path=path+""+l+"";
			   
			} 
		  
	   }
	   while(temp!=-1 );
	  
	   if(i!=sourceRouter){
		 //used to print the connection table for the source node by adding the path string to string buffer
		  //reversing it and printing its 0th index for each iteration   
	   System.out.println("\t Source "+ (sourceRouter+1)+" to Destination "+(i+1)+"  :"+ new StringBuffer(path).reverse().toString().charAt(0));   
	   }
	   else{
		   System.out.println("\t Source "+ (sourceRouter+1)+" to Destination "+(i+1)+"--"); 
	   }
	   
   }System.out.println();
}
  }
   
  
    
  //function to print the path from source node to the destination node entered by the user 
  public void printPath()
	{ //setting i to the destinationRouter value input by the user
	   int i=destinationRouter;
	   int temp;
	   int k = i+1;
	   System.out.println("path with shortest cost:");
	   System.out.print("\n"+"path :"+k);
	   temp=i;
	   //do while to back track pathDetermineNode to print the path from one node to other
	   do{
		   temp=pathDetermineNode[temp];
		 
		   if (temp != -1) 
		   {	
			    System.out.print("<-"+(temp+1));
			} else {
				 System.out.print("<-"+(sourceRouter+1));
			}
		  
	   }
	   while(temp!=-1);
	   //displaying the cost of distance from source to destination node
	   System.out.println("\n"+"cost:"+ distance[destinationRouter]+"\n");
	   
	}
  //main function 
	public static void main(String[] args) {
		int stoppingConditon=1 ;
		//creating object 
		ProjectCS542 object=new ProjectCS542();
		//initializing flag to 0 as no input file has been taken it will be set to 1 when file input has taken plce
		int flag=0;
		//using scanner class for input 
		Scanner reader = new Scanner(System.in); 
		Scanner reader1 = new Scanner(System.in); 
		Scanner reader2 = new Scanner(System.in); 
		Scanner reader3 = new Scanner(System.in);
		Scanner reader4 = new Scanner(System.in);
		//while loop to run the menu of the project
		while(stoppingConditon==1)
		{ try{
			//dispays the menu of the project
			System.out.println("Enter 1 to enter the file path");
		  System.out.println("Enter 2 enter the source router");
		  System.out.println("Enter 3 enter destination router");
		  System.out.println("Enter 4 enter to add a new router ");
		  System.out.println("Enter 5 to exit");
		  //input choice from the user is save to condition 
		  int condition = reader.nextInt();
		//if condition check what user has input through the command prompt 
		  if(condition==1)
			{
			System.out.println("enter the path(example C:\\Users\\sam\\workspace\\demo\\src\\demo\\test.txt))");
			String path = reader1.nextLine();
			//calls the function to read the file from the path specified
			object.readFile(path);
			//calls the function to print the connection table for all the routers
			object.dijkstraAlgorithmToPrintAllROutingTable();
			//update flag value to 1 as file has been input through the command prompt
			flag=1;
		    
			
				
			}
			if(condition==2)
			{ 
				if (flag==0){
				System.out.println("no input file found ,please input the file then use this option");System.out.println("");}
				else
				{System.out.println("enter the source router");
			    int checkValue = reader2.nextInt();
			    //check if the router entered exist in topology or not 
			    if(checkValue>=1 && checkValue<=object.numberOfRouters)
			    { System.out.println("routing table for router "+checkValue+" :");
			    //setting the soutce router value
			    object.sourceRouter=checkValue-1;
			    //calls the dijktra's algorithm function
			    object.dijkstraAlgorithm();
			    }
			    else
			    {
			    	System.out.println("enter a valid input value***try again***");
			    	System.out.println("");
			    }
				}
			}
			if(condition==3)
			{if (flag==0){
				System.out.println("no input file found ,please input the file then use this option");
				System.out.println("");}
				else
				{
					System.out.println("enter the destination router");
				    int checkValue = reader3.nextInt();
				    //check if the router entered exist in topology or not 
				    if(checkValue>=1 && checkValue<=object.numberOfRouters )
				    { 
				     //sets the values for destination router
				    	object.destinationRouter=checkValue-1;
				     //call the function to print path and cost 
				     object.printPath();
				   
				    }
				    else
				    {
				    	System.out.println("enter a valid input value***try again***");
				    	System.out.println("");
				    }
				}
				
			}
			if(condition==4)
			{if (flag==0){
				System.out.println("no input file found ,please input the file then use this option");
				System.out.println("");}
				else
				{System.out.println("enter the new router details as follow:");
				double temp=0;
				
				     //loop to enter the values of wieghts for the new router to be added 
				     object.numberOfRouters=object.numberOfRouters+1;
				     for(int i=0;i<object.numberOfRouters;i++)
				     {   System.out.println("enter the router "+object.numberOfRouters +" cost/weight:to router "+(i+1));
				    	 
				    	 object.routerMatirx[object.numberOfRouters-1][i]= reader4.nextDouble();
				     //creating temp variable for validation at latter step
				    	 if(i==(object.numberOfRouters-1))
				      {
				    	  temp=object.routerMatirx[object.numberOfRouters-1][i];
				      }
				      
				     }
				     for(int i=0;i<(object.numberOfRouters-1);i++)
				     {  System.out.println("enter the router "+(i+1)+"cost/weight:to router "+object.numberOfRouters);
				    	 
				    	 object.routerMatirx[i][object.numberOfRouters-1]= reader4.nextDouble();
				    	 
				      
				     }
				     for(int i=0;i<object.numberOfRouters;i++)
				     {
				    	 for(int j=0;j<object.numberOfRouters;j++)
				    	 { 
				    		 if(object.routerMatirx[i][j]==0.0 || object.routerMatirx[i][j]==-1)
					     
				    	 {
				  		   object.routerMatirx[i][j]=Double.POSITIVE_INFINITY;
				  		   }
				    		 
				    	 }
				     }
				     //code to check the entered new router values are coorect or not
				     int ctrTemp=0;
				     for(int i=0;i<object.numberOfRouters;i++)
				     {
				    	 if(object.routerMatirx[i][object.numberOfRouters-1]<-1)
				    	 {System.out.println("negative weight ");
				    	 object.numberOfRouters=object.numberOfRouters-1;
				    	 throw new Exception(); 
				    	 }
				    	
				         for(int j=0;j<object.numberOfRouters;j++)
				    	 {  if(object.routerMatirx[object.numberOfRouters-1][j]<-1)
				    	 {System.out.println("negative weight ");
				    	 object.numberOfRouters=object.numberOfRouters-1;
				    	 throw new Exception();
		    			 
				    	 }
				    	 
				    	 if(object.routerMatirx[j][object.numberOfRouters-1]!=object.routerMatirx[object.numberOfRouters-1][j])
				    	 {
				    		 System.out.println("weights enteted not equal i.e. bidirected weights are present");
				    		 object.numberOfRouters=object.numberOfRouters-1;
				    		 throw new Exception();
				    	 }
				    	 if(temp!=0)
				    	 {
				    		 System.out.println("node to itself distance is not zero i.e diagonal is not 0");
				    		 object.numberOfRouters=object.numberOfRouters-1;
				    		 throw new Exception();
				    	 }
				    			 
				    	 }
				    	 
				     }
				     for(int i=0;i<object.numberOfRouters;i++)
				     {
				    	 if(object.routerMatirx[i][object.numberOfRouters-1]==Double.POSITIVE_INFINITY)
				    	 {ctrTemp=ctrTemp+1;
				    	 
				    	 } 
				     }
				     for(int j=0;j<object.numberOfRouters;j++)
				     {
				    	 if(object.routerMatirx[j][object.numberOfRouters-1]==Double.POSITIVE_INFINITY)
				    	 {ctrTemp=ctrTemp+1;
				    	   if(ctrTemp==(object.numberOfRouters+object.numberOfRouters-1))
				    	   {
				    		   System.out.println("disconnected node entered");
				    		   object.numberOfRouters=object.numberOfRouters-1;
				    		   throw new Exception();
				    	   }
				    	  }
				     }
				     
				     
				     //calls dijktra's algorithm function to update the topology and the connection table and cost
				     object.dijkstraAlgorithm();
				   
				    
				   
				}
				
			}
			if(condition==5)
			{//condition for  exit from the project
				System.out.println("exited from project"+"\n"+" ****thank you****");
				System.exit(0);
			}
		}
		catch(Exception e)
		{   
			System.out.println("**error** please rerun **error** ");
			System.out.println("");}
		}
		
		
		
	}
  
}
