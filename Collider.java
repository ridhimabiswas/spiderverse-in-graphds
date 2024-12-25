



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
* ColliderOutputFile name is passed in through the command line as args[2]
* Output to ColliderOutputFile with the format:
* 1. e lines, each with a different dimension number, then listing
*       all of the dimension numbers connected to that dimension (space separated)
*
Spiderverse Input File: Contains data about people in the Spider-Verse, including their current dimension, name, and dimensional signature.
Adjacency List Creation:
We’ll create an adjacency list to represent undirected links between dimensions.
For each cluster:
The first dimension in the cluster will be linked to every other dimension in the same cluster.
These links are undirected, so there will be an edge from the first dimension to every other dimension and vice versa.
For example, if the cluster starts with dimension d1, there will be edges: d1 → d2, d2 → d1, d1 → d3, d3 → d1, and so on.
Repeat this process for all dimensions in the cluster.


Inserting People:
Insert people from the Spiderverse input file into their corresponding dimensions.
Note that people belong to a specific dimension, but they are not directly connected via edges.
Output File Format:
The output file will have one line per dimension.
Each line starts with a dimension number, followed by a space-separated list of dimensions linked to that dimension.
The order of lines in the output file does not affect grading.




*
* @author Seth Kelley
*/


public class Collider {


   public static void main(String[] args) {


       if ( args.length < 3 ) {
           StdOut.println(
               "Execute: java -cp bin spiderman.Collider <dimension INput file> <spiderverse INput file> <collider OUTput file>");
               return;
       }
       //arguments = dimension, spiderver file name, output file
       String dimensionFile = args[0];
       String spiderverseFile = args[1];
       String outputFile = args[2];
       //make cluster table
       Dimension[] clusters = Clusters.makeHashTable(dimensionFile);
       //make adjacency list
       ArrayList<Dimension> adjacencyList = createAdjacencyList(clusters);
       //insert people
       adjacencyList = insertPeople(spiderverseFile, adjacencyList);
       //put into output file
       print(outputFile, adjacencyList);


   }


   //create adjaceny list


   public static void printAdjacencyList(ArrayList<Dimension> adjacencyList)
   {
       for (int i = 0; i < adjacencyList.size(); i++)
       {
           System.out.println(adjacencyList.get(i).getdimensionNumber() + " " + adjacencyList.get(i).getnumberOfCanonEvents() + " " + adjacencyList.get(i).getdimensionWeight());
       }
   }


   public static ArrayList<Dimension> createAdjacencyList(Dimension[] clusters) //success I think
   {
       //for each cluster
       ArrayList<Dimension> adjacencyList = new ArrayList<>();
       for (int i = 0; i < clusters.length; i++)
       {
           Dimension currentIndex = clusters[i];
           int firstDimAlreadyExists = doesDimensionExist(currentIndex.getdimensionNumber(), adjacencyList);
           //check if dimension is in arraylist


           // if it does not exist
           if (firstDimAlreadyExists == -1)
           {
               //add list to a new index
               adjacencyList.add(currentIndex);
           }
           //if it does exist
           if (firstDimAlreadyExists != -1)
           {
               //add links to the end of the linked list at the index
               //end of linked list
               AddToEnd(firstDimAlreadyExists, currentIndex.getNextDimension(), adjacencyList);
           }
           Dimension others = currentIndex.getNextDimension();
           addLinksfromOthertoFirst(currentIndex.getdimensionNumber(), others, adjacencyList);  
       }
       //first dimension linked to everything
       //add links of other dimension to first dimension too - check if it already exists
       return adjacencyList;
   }


   public static int doesDimensionExist(int numberOfDim, ArrayList<Dimension> adjacencyList)
   {
       int index = -1;
       for (int i = 0; i < adjacencyList.size(); i++)
       {
           Dimension currentDimension = adjacencyList.get(i);
           if (currentDimension != null && currentDimension.getdimensionNumber() == numberOfDim)
           {
               index = i;
               break;
           }
       }
       return index;
   }


   public static void AddToEnd(int indexExists, Dimension linksToAdd, ArrayList<Dimension> adjacencyList)
   {
       Dimension ptr = adjacencyList.get(indexExists);
       Dimension prev = null;
       while (ptr != null)
       {
           prev = ptr;
           ptr = ptr.getNextDimension();
       }
       if (prev != null)
       {
           prev.setNextDimension(linksToAdd);
       }
      
   }
  
   public static void addLinksfromOthertoFirst(int firstDim, Dimension listOfOthers, ArrayList<Dimension> adjacencyList)
   {
      
       while (listOfOthers != null)
       {
           Dimension firstDimensionToAdd = new Dimension(firstDim, 0, 0, null, null);
           int index = doesDimensionExist(listOfOthers.getdimensionNumber(), adjacencyList);
           if (index == -1)
           {
               Dimension newList = new Dimension(listOfOthers.getdimensionNumber(), listOfOthers.getnumberOfCanonEvents(), listOfOthers.getdimensionWeight(), listOfOthers.getpeople(), firstDimensionToAdd);
               adjacencyList.add(newList);
           }
           if (index > -1)
           {
               AddToEnd(index, firstDimensionToAdd, adjacencyList);
           }
           listOfOthers = listOfOthers.getNextDimension();
       }
   }


   public static void print(String File, ArrayList<Dimension> adjacencyList)
   {
       StdOut.setFile(File);
       for (int i = 0; i < adjacencyList.size(); i++)
       {
           Dimension ptr = adjacencyList.get(i);
           while (ptr != null)
           {
               StdOut.print(ptr.getdimensionNumber() + " ");
               ptr = ptr.getNextDimension();
           }
           StdOut.println();
       }
   }


   public static ArrayList<Dimension> insertPeople(String File, ArrayList<Dimension> adjacencyList)
   {


       StdIn.setFile(File);
       int numberOfPeople = StdIn.readInt();
       for (int i = 0; i < numberOfPeople; i++)
       {
           int dimensionCurrentlyAt = StdIn.readInt();
           StdIn.readChar();
           String name = StdIn.readString();
           StdIn.readChar();
           int dimensionalSignature = StdIn.readInt();


           Person person = new Person(dimensionCurrentlyAt, name, dimensionalSignature);


           for (int j = 0; j < adjacencyList.size(); j++)
           {
               int dimat = person.getDimensionCurrentlyAt();
               int dimnum = adjacencyList.get(j).getdimensionNumber();
               if (dimat == dimnum)
               {
                   if (adjacencyList.get(j).getpeople() == null)
                   {
                       ArrayList<Person> newList = new ArrayList<>();
                       newList.add(person);
                       adjacencyList.get(j).setpeople(newList);
                       break;
                   }
                   else
                   {
                       adjacencyList.get(j).getpeople().add(person);
                       break;
                   }
               }
           }


       }
       return adjacencyList;
   }




   public static ArrayList<Dimension> ListofDimensions(Dimension[] clusters)
   {
       ArrayList<Dimension> listOfDimensions = new ArrayList<>();
       for (int i = 0; i < clusters.length; i++)
       {
           Dimension ptr = clusters[i];
           while (ptr != null)
           {
               Dimension potentialDimensionToAdd = new Dimension(ptr.getdimensionNumber(), 0, 0, null, null);
               boolean found = false;
               for (int j = 0; j < listOfDimensions.size(); j++)
               {
                   if (listOfDimensions != null)
                   {
                       if (listOfDimensions.get(j).getdimensionNumber() == potentialDimensionToAdd.getdimensionNumber())
                       {
                           found = true;
                           break;
                       }
                   }
               }
               if (found == false)
               {
                   listOfDimensions.add(potentialDimensionToAdd);
               }
               ptr = ptr.getNextDimension();
           }
       }
       return listOfDimensions;
   }
      


      
}

