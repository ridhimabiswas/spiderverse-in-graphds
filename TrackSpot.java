
package spiderman;


import java.util.ArrayList;
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
* SpotInputFile name is passed through the command line as args[2]
* Read from the SpotInputFile with the format:
* Two integers (line seperated)
*      i.    Line one: The starting dimension of Spot (int)
*      ii.   Line two: The dimension Spot wants to go to (int)
*
* Step 4:
* TrackSpotOutputFile name is passed in through the command line as args[3]
* Output to TrackSpotOutputFile with the format:
* 1. One line, listing the dimenstional number of each dimension Spot has visited (space separated)
*
* @author Seth Kelley
*/


public class TrackSpot {


   
   public static void main(String[] args) {


  
       if ( args.length < 4 ) {
           StdOut.println(
               "Execute: java -cp bin spiderman.TrackSpot <dimension INput file> <spiderverse INput file> <spot INput file> <trackspot OUTput file>");
               return;
       }
  
       //four arguments - dimension file, spiderverse file, spot input file, outfile name


       String dimensionFile = args[0];
       String spiderverseFile = args[1];
       String spotFile = args[2];
       String outputFile = args[3];


       ArrayList<Integer> visitedDim = new ArrayList<>();


       Dimension[] clusters = Clusters.makeHashTable(dimensionFile);
       visitedDim = SpotTracking(spotFile, spiderverseFile, clusters);
       print (outputFile, visitedDim);
      
      
   }


   public static ArrayList<Integer> SpotTracking(String File, String File2, Dimension[] clusters)
   {
       StdIn.setFile(File); //input
       int startDimension = StdIn.readInt(); //start dimension
       int finalDimension = StdIn.readInt(); //end dimension
       ArrayList<Dimension> adjacencyList = Collider.createAdjacencyList(clusters); //create adjacency list
       adjacencyList = Collider.insertPeople(File2, adjacencyList);
       boolean[] marked = makeMarkedArray(adjacencyList); //marked array
       ArrayList<Integer> visitedDim = new ArrayList<>();
       visitedDim = DFS(finalDimension, startDimension, visitedDim, adjacencyList, marked);
       return visitedDim;
   }


   public static void print(String File, ArrayList<Integer> visitedDim)
   {
       StdOut.setFile(File);
       for (int i = 0; i < visitedDim.size(); i++)
       {
           StdOut.print(visitedDim.get(i) + " ");
       }
   }


   public static int findDimensionInList(ArrayList<Dimension> adjacencyList, int dimensionNumber)
   {
       int index = -1;
       for (int i = 0; i < adjacencyList.size(); i++)
       {
           if (dimensionNumber == adjacencyList.get(i).getdimensionNumber())
           {
               index = i;
           }
       }
       return index;
   }




   public static ArrayList<Integer> DFS(int finalDimension, int dimAt, ArrayList<Integer> visitedDim, ArrayList<Dimension> adjacencyList, boolean[] marked) {
       int index = findDimensionInList(adjacencyList, dimAt); // The index of the dimension we are currently looking at
       if (!marked[index]) {
           visitedDim.add(dimAt);
           marked[index] = true; // Mark as visited
       }
  
       // Check if final dimension is reached
       if (finalDimension == dimAt) {
           return visitedDim;
       }
  
       Dimension ptr = adjacencyList.get(index).getNextDimension();
       while (ptr != null) {
           int neighborNumber = ptr.getdimensionNumber();
           int neighborIndex = findDimensionInList(adjacencyList, neighborNumber);
           if (!marked[neighborIndex]) {
               // Recursive call to explore the neighbor
               visitedDim = DFS(finalDimension, neighborNumber, visitedDim, adjacencyList, marked);
  
               // If final dimension found in the recursive call, return immediately
               if (visitedDim.contains(finalDimension)) {
                   return visitedDim;
               }
           }
           ptr = ptr.getNextDimension();
       }
  
       // If final dimension not found in neighbors, return the visitedDim list
       return visitedDim;
   }


   public static boolean[] makeMarkedArray(ArrayList<Dimension> adjacencyList)
   {
       boolean[] marked = new boolean[adjacencyList.size()];
       return marked;
   }


   public static Dimension[] makeEdgeList(ArrayList<Dimension> adjacencyList)
   {
       Dimension[] edgeTo = new Dimension[adjacencyList.size()];
       return edgeTo;
   }
}




