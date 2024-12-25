



package spiderman;


import java.util.ArrayList;
import java.util.Stack;


/**
* Steps to implement this class main method:
*
* Step 1:
* DimensionInputFile name is passed through the command line as args[0]
* Read from the DimensionsInputFile with the format:
* 1. The first line with three numbers:
*      i.    a (int): number of dimensions in the graph
*      ii.   b (int): the initial size of the cluster table prior to rehashing
*      iii.  c (double): the capacity(threshold) used to rehash the cluster table
* 2. a lines, each with:
*      i.    The dimension number (int)
*      ii.   The number of canon events for the dimension (int)
*      iii.  The dimension weight (int)
*
* Step 2:
* SpiderverseInputFile name is passed through the command line as args[1]
* Read from the SpiderverseInputFile with the format:
* 1. d (int): number of people in the file
* 2. d lines, each with:
*      i.    The dimension they are currently at (int)
*      ii.   The name of the person (String)
*      iii.  The dimensional signature of the person (int)
*
* Step 3:
* HubInputFile name is passed through the command line as args[2]
* Read from the SpotInputFile with the format:
* One integer
*      i.    The dimensional number of the starting hub (int)
*
* Step 4:
* AnomaliesInputFile name is passed through the command line as args[3]
* Read from the AnomaliesInputFile with the format:
* 1. e (int): number of anomalies in the file
* 2. e lines, each with:
*      i.   The Name of the anomaly which will go from the hub dimension to their home dimension (String)
*      ii.  The time allotted to return the anomaly home before a canon event is missed (int)
*
* Step 5:
* ReportOutputFile name is passed in through the command line as args[4]
* Output to ReportOutputFile with the format:
* 1. e Lines (one for each anomaly), listing on the same line:
*      i.   The number of canon events at that anomalies home dimensionafter being returned
*      ii.  Name of the anomaly being sent home
*      iii. SUCCESS or FAILED in relation to whether that anomaly made it back in time
*      iv.  The route the anomaly took to get home
*
* @author Seth Kelley
*/


public class GoHomeMachine {
  
   public static void main(String[] args) {


    
       if ( args.length < 5 ) {
           StdOut.println(
               "Execute: java -cp bin spiderman.GoHomeMachine <dimension INput file> <spiderverse INput file> <hub INput file> <anomalies INput file> <report OUTput file>");
               return;
       }
    


       String dimensionFile = args[0];
       String spiderverseFile = args[1];
       String hubFile = args[2];
       String anomaliesFile = args[3];
       String outputFile = args[4];




       Dimension[] clusters = Clusters.makeHashTable(dimensionFile);
       ArrayList<Dimension> adjacencyList = Collider.createAdjacencyList(clusters);
       adjacencyList = Collider.insertPeople(spiderverseFile, adjacencyList);
       int hub = CollectAnomalies.getHub(hubFile);
       ArrayList<Person> listOfAnomalies = CollectAnomalies.listOfAnomalies(adjacencyList);


       ArrayList<Dikstra> List = calculatorShortPath(adjacencyList, hub);


       ArrayList<AnomalyCollectedData> order = CollectAnomalies.Anomalies(listOfAnomalies, hub, adjacencyList);
       adjacencyList = CollectAnomalies.updateAnomalyLocationToHub(order, hub, adjacencyList);


       ArrayList<GHMData> GHMlist = makeList(anomaliesFile, List, order, hub, adjacencyList);


       print(GHMlist, outputFile);




      
   }


   public static ArrayList<GHMData> makeList(String File, ArrayList<Dikstra> List, ArrayList<AnomalyCollectedData> order, int hub, ArrayList<Dimension> adjacencyList)
   {


       ArrayList<GHMData> Datas = new ArrayList<>();






       StdIn.setFile(File);


       int numOfAnomalies = StdIn.readInt();


       for (int i = 0; i < numOfAnomalies; i++)
       {
           String name = StdIn.readString(); // have name
           int timeAlloted = StdIn.readInt(); //have time alloted




           Person anomalyOfInterest = CollectAnomalies.findThem(name, adjacencyList);


           int home = anomalyOfInterest.getDimensionalSignature();
          




           //calculate path - for this we need the start and end


           ArrayList<Dikstra> path = path(List, hub, home, adjacencyList);
          
           //calculator time to return
           int time = path.get(path.size()-1).getShortestDistance();


           




           //bring them back home
           adjacencyList = bringThemHome(anomalyOfInterest, adjacencyList, hub);






           //need to compare time alloted vs actual reurn time
           boolean success = false;
           int hindex = Collider.doesDimensionExist(home, adjacencyList);
           int numberOfCE = adjacencyList.get(hindex).getnumberOfCanonEvents();


           if (time <= timeAlloted)
           {


               //number of canon events decrease
               numberOfCE =  numberOfCE - 1;
               adjacencyList.get(hindex).setnumberOfCanonEvents(numberOfCE);
               //set success to true
               success = true;
           }


           GHMData newEntry = new GHMData(numberOfCE, name, success, path);
           Datas.add(newEntry);
          
       }


       return Datas;
   }


   public static ArrayList<Dimension> bringThemHome(Person anomaly, ArrayList<Dimension> adjacencyList, int hub)
   {


       int index = Collider.doesDimensionExist(hub, adjacencyList);
       ArrayList<Person> peopleofInterest = adjacencyList.get(index).getpeople();   
       peopleofInterest.remove(anomaly);




       int newIndex = Collider.doesDimensionExist(anomaly.getDimensionalSignature(), adjacencyList);
       ArrayList<Person> newPeopleOfInterest = adjacencyList.get(newIndex).getpeople();
       if (newPeopleOfInterest != null)
       {
           newPeopleOfInterest.add(anomaly);
       }
       else
       {
           ArrayList<Person> newList = new ArrayList<>();
           newList.add(anomaly);
           adjacencyList.get(newIndex).setpeople(newList);
       }
       anomaly.setDimensionCurrentlyAt(anomaly.getDimensionalSignature());


       return adjacencyList;
   }






   public static ArrayList<Dikstra> calculatorShortPath(ArrayList<Dimension> adjacencyList, int hub)
   {
       //make simple variables
       boolean[] done = new boolean[adjacencyList.size()];
       Dikstra[] fringe = new Dikstra[done.length];
       ArrayList<Dikstra> List = new ArrayList<>();






       //update list and unvisited arraylist
       for (int i = 0; i < adjacencyList.size(); i++)
       {
           Dikstra vertexInfo = null;
           if (adjacencyList.get(i).getdimensionNumber() == hub)
           {
               vertexInfo = new Dikstra(adjacencyList.get(i), 0, null);
               List.add(vertexInfo);
           }
           else
           {
               vertexInfo = new Dikstra(adjacencyList.get(i), Integer.MAX_VALUE, null);
               List.add(vertexInfo);
           }
           fringe[i] = vertexInfo;
       }




       //until unvisited array is all null values
       int f = fringe.length;
       while (f > 0)
       {
           ///...find the min distance in unvisited array
           int mindex = findMin(fringe);
           Dikstra min = List.get(mindex);


           ///...explore its neighbors and update list
           List = exploreNeighbors(List, min.getVertex().getdimensionNumber(), adjacencyList, done);


           ///...remove from unvisited
           fringe[mindex] = null;


           ///...add to visited
           done[mindex] = true;






           //update empty value


           f--;


       }


       return List;


   }


   public static ArrayList<Dikstra> exploreNeighbors(ArrayList<Dikstra> List, int vertexDimensionNumber, ArrayList<Dimension> adjacencyList, boolean[] done)
   {




       Stack<Dimension> neighbors = new Stack<>();
       int index = Collider.doesDimensionExist(vertexDimensionNumber, adjacencyList);
       Dimension ptr = adjacencyList.get(index).getNextDimension();
       Dikstra vertexOfInterest = List.get(index);
       while (ptr != null) //add all the neighbors
       {
           int fullInfoIndex = Collider.doesDimensionExist(ptr.getdimensionNumber(), adjacencyList);
           neighbors.push(adjacencyList.get(fullInfoIndex));
           ptr = ptr.getNextDimension();
       }
       while (!neighbors.isEmpty()) //inspect every neighbor
       {
           Dimension neighbor = neighbors.pop();
           int check = Collider.doesDimensionExist(neighbor.getdimensionNumber(), adjacencyList);
           if (done[check] == false)
           {


               int distance = vertexOfInterest.getShortestDistance() + neighbor.getdimensionWeight() + vertexOfInterest.getVertex().getdimensionWeight(); //????????
               if (List.get(check).getShortestDistance() > distance)
               {
                   List.get(check).setShortestDistance(distance); //update shortest distanxe
                   List.get(check).setPreviousVertex(adjacencyList.get(index)); //update previous vertex


               }  
           }
          
       }
       return List;
   }






   public static int findMin(Dikstra[] fringe)
   {
       Dikstra min = null;
       int mini = -1;
      
       for (int i = 0; i < fringe.length; i++)
       {
           if (fringe[i] != null)
           {
               int current = fringe[i].getShortestDistance();


               if (min == null)
               {
                   min = fringe[i];
                   mini = i;
               }
               else if (current < min.getShortestDistance())
               {
                   min = fringe[i];
                   mini = i;
               }
           }
       }




       return mini;
   }


   public static void print(ArrayList<GHMData> List, String File)
   {
       StdOut.setFile(File);
       for (int i = 0; i < List.size(); i++)
       {
           StdOut.print(List.get(i).getNumberOfCanonEvents()+ " ");
           StdOut.print(List.get(i).getName() + " ");
           if (List.get(i).getSuccess() == false)
           {
               StdOut.print("FAILED" + " ");
           }
           else
           {
               StdOut.print("SUCCESS" + " ");
           }
           ArrayList<Dikstra> path = List.get(i).getPath();
           for (int j = 0; j < path.size(); j++)
           {
               StdOut.print(path.get(j).getVertex().getdimensionNumber() + " ");
           }


           StdOut.println();
       }
      
   }


   public static ArrayList<Dikstra> path(ArrayList<Dikstra> List, int start, int destination, ArrayList<Dimension> adjacencyList)
   {


       Stack<Dikstra> s = new Stack<Dikstra>();


       Dikstra ptr = null;
       //first find the final destination
       for (int i = 0; i < List.size(); i++)
       {
           if (List.get(i).getVertex().getdimensionNumber() == destination)
           {
               ptr = List.get(i);
               break;
           }
       }




       while (ptr.getVertex().getdimensionNumber() != start)
       {
           s.add(ptr);
           Dimension find = ptr.getPreviousVertex();


           int index = Collider.doesDimensionExist(find.getdimensionNumber(), adjacencyList);


           ptr = List.get(index);


       }


       int index = Collider.doesDimensionExist(start, adjacencyList);
       s.add(List.get(index));
      
       ArrayList<Dikstra> path = new ArrayList<>();
       while (!s.isEmpty())
       {
           path.add(s.pop());
       }


       return path;
      
      


   }


}






