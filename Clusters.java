
package spiderman;


/**
* Steps to implement this class main method:
*
* Step 1:
* DimensionInputFile name is passed through the command line as args[0]
* Read from the DimensionsInputFile with the format:
* 1. The first line with three numbers:
* i. a (int): number of dimensions in the graph
* ii. b (int): the initial size of the cluster table prior to rehashing
* iii. c (double): the capacity(threshold) used to rehash the cluster table
*
* Step 2:
* ClusterOutputFile name is passed in through the command line as args[1]
* Output to ClusterOutputFile with the format:
* 1. n lines, listing all of the dimension numbers connected to
* that dimension in order (space separated)
* n is the size of the cluster table.
*
* @author Seth Kelley
*/


public class Clusters {


   // hash table


   public static Dimension[] makeHashTable(String File) {
       StdIn.setFile(File); // input the file name
       // read the first line
       int a = StdIn.readInt(); // number of dimensions in the graph
       StdIn.readChar(); // space
       int b = StdIn.readInt(); // initial size of cluster table prior to rehasing
       StdIn.readChar();
       double c = StdIn.readDouble(); // capacity(threshold) used to rehash the cluster table
       Dimension[] clusters = new Dimension[b]; // create hash table
       int dimensionAddedSoFar = 0;
       for (int i = 0; i < a; i++) // for each dimension a
       {
           // step 1
           // add dimension to cluster table
           StdIn.readLine();
           int dimensionNumber = StdIn.readInt(); // read dimension number
           StdIn.readChar();
           int numberOfCanonEvents = StdIn.readInt();
           StdIn.readChar();
           int dimensionWeight = StdIn.readInt();
           int index = dimensionNumber % clusters.length; // get index
           // add item to front
           clusters[index] = new Dimension(dimensionNumber, numberOfCanonEvents, dimensionWeight, null, clusters[index]);
           dimensionAddedSoFar++;
           // step 2
           // if dimensions added so far >= c --> rehash
           if (dimensionAddedSoFar == c) {
               // rehash = size of cluster table doubled
               Dimension[] newClusterTable = new Dimension[clusters.length * 2];
               // items must be rehashed
               for (int j = 0; j < clusters.length; j++) // interate through the old cluster table
               {
                   Dimension ptr = clusters[j]; // reference to the front of the list
                   while (ptr != null) // if not at end of list
                   {
                       int dNumber = ptr.getdimensionNumber(); // get the dimension number
                       int ce = ptr.getnumberOfCanonEvents();
                       int dw = ptr.getdimensionWeight();
                       int indexx = dNumber % newClusterTable.length; // rehash
                       newClusterTable[indexx] = new Dimension(dNumber, ce, dw, null, newClusterTable[indexx]); // add it
                       ptr = ptr.getNextDimension(); // go to the next one
                   }
               }
               clusters = newClusterTable; // update - idk if this can happen?
               c = c * 2;
           }


       }


       for (int k = 0; k < clusters.length; k++) {
           // take first dimension of preivous two clusters - add them to end of current
           // cluster
           // for first two clusters - previous wrap around to end of list
           if (k == 0) // first one
           {
               int dimensionNum1 = clusters[clusters.length - 1].getdimensionNumber();
               int cE1 = clusters[clusters.length - 1].getnumberOfCanonEvents();
               int dW1 = clusters[clusters.length - 1].getdimensionWeight();
               int dimensionNum2 = clusters[clusters.length - 2].getdimensionNumber();
               int cE2 = clusters[clusters.length - 2].getnumberOfCanonEvents();
               int dW2 = clusters[clusters.length - 2].getdimensionWeight();
               // last reference
               Dimension ptr = clusters[k];
               while (ptr.getNextDimension() != null) {
                   ptr = ptr.getNextDimension();
               }
               ptr.setNextDimension(new Dimension(dimensionNum1, cE1, dW1, null, null));
               ptr = ptr.getNextDimension();
               ptr.setNextDimension(new Dimension(dimensionNum2, cE2, dW2, null, null));
           }
           if (k == 1) {
               int dimensionNum1 = clusters[0].getdimensionNumber();
               int cE1 = clusters[0].getnumberOfCanonEvents();
               int dW1 = clusters[0].getdimensionWeight();
               int dimensionNum2 = clusters[clusters.length - 1].getdimensionNumber();
               int cE2 = clusters[clusters.length - 1].getnumberOfCanonEvents();
               int dW2 = clusters[clusters.length - 1].getdimensionWeight();
               // last reference
               Dimension ptr = clusters[k];
               while (ptr.getNextDimension() != null) {
                   ptr = ptr.getNextDimension();
               }
               ptr.setNextDimension(new Dimension(dimensionNum1, cE1, dW1, null, null));
               ptr = ptr.getNextDimension();
               ptr.setNextDimension(new Dimension(dimensionNum2, cE2, dW2, null, null));
           }
           if (k > 1) {
               int dimensionNum1 = clusters[k - 1].getdimensionNumber();
               int cE1 = clusters[k-1].getnumberOfCanonEvents();
               int dW1 = clusters[k-1].getdimensionWeight();
               int dimensionNum2 = clusters[k - 2].getdimensionNumber();
               int cE2 = clusters[k - 2].getnumberOfCanonEvents();
               int dW2 = clusters[k - 2].getdimensionWeight();
               // last reference
               Dimension ptr = clusters[k];
               while (ptr.getNextDimension() != null) {
                   ptr = ptr.getNextDimension();
               }
               ptr.setNextDimension(new Dimension(dimensionNum1, cE1, dW1, null, null));
               ptr = ptr.getNextDimension();
               ptr.setNextDimension(new Dimension(dimensionNum2, cE2, dW2, null, null));
           }
       }


       return clusters;
   }




   // print
   public static void print(Dimension[] clusters)
   {
       for (int l = 0; l < clusters.length; l++) // interate through the old cluster table
       {
           Dimension ptr = clusters[l]; // reference to the front of the list
           while (ptr != null) // if not at end of list
           {
               StdOut.print(ptr.getdimensionNumber() + " "); // get the dimension number
               ptr = ptr.getNextDimension();
           }
           StdOut.println();
       }
   }


   public static void print(String File, Dimension[] clusters)
   {
       StdOut.setFile(File);


       for (int l = 0; l < clusters.length; l++) // interate through the old cluster table
       {
           Dimension ptr = clusters[l]; // reference to the front of the list
           while (ptr != null) // if not at end of list
           {
               StdOut.print(ptr.getdimensionNumber() + " "); // get the dimension number
               ptr = ptr.getNextDimension();
           }
           StdOut.println();
       }
   }


   public static void main(String[] args) {


       if (args.length < 2) {
           StdOut.println(
                   "Execute: java -cp bin spiderman.Clusters <dimension INput file> <collider OUTput file>");
           return;
       }


       //take in two line arguments = dimension file and output file


       Dimension[] clusters = makeHashTable(args[0]);
       print(args[1], clusters);




   }
}



