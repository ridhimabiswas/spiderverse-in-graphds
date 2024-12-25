package spiderman;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;




public class CollectAnomalies {


   public static void main(String[] args) {
       //four arguments - dimension input, spiderverse input, hub input, outfile name
       String dimensionFile = args[0];
       String spiderverseFile = args[1];
       String hubFile = args[2];
       String outputFile = args[3];






       // Load dimensions and create adjacency list
       Dimension[] clusters = Clusters.makeHashTable(dimensionFile);
       ArrayList<Dimension> adjacencyList = Collider.createAdjacencyList(clusters);


      
       // Collect anomalies and their routes
       adjacencyList = Collider.insertPeople(spiderverseFile, adjacencyList);
       ArrayList<Person> listOfAnomalies = listOfAnomalies(adjacencyList);


       int hub = getHub(hubFile);


       ArrayList<AnomalyCollectedData> order = Anomalies(listOfAnomalies, hub, adjacencyList);
       adjacencyList = updateAnomalyLocationToHub(order, hub, adjacencyList);
       print(outputFile, order);
      
   }


   public static int getHub(String File)
   {
       StdIn.setFile(File);
       int hub = StdIn.readInt();
       return hub;
   }


   public static void print(String File, ArrayList<AnomalyCollectedData> order)
   {
       StdOut.setFile(File);
       for (int i = 0; i < order.size(); i++)
       {
           StdOut.print(order.get(i).anomaly + " ");
           if (order.get(i).spider != null)
           {
               StdOut.print(order.get(i).spider + " ");
           }
           for (int j = 0; j < order.get(i).wholePath.size(); j++)
           {
               StdOut.print(order.get(i).wholePath.get(j) + " ");
           }
           StdOut.println();
       }
   }




   public static ArrayList<Integer> noSpiderHelp(ArrayList<Integer> shortestPath)
   {
       Stack<Integer> s = new Stack<>();
       for (int i = 1; i < shortestPath.size(); i++)
       {
           s.push(shortestPath.get(i));
       }
       ArrayList<Integer> newList = new ArrayList<>();
       while (!s.isEmpty())
       {
           newList.add(s.pop());
       }
       for (int i = 0; i < shortestPath.size(); i++)
       {
           newList.add(shortestPath.get(i));
       }
       return newList;
   }


   public static ArrayList<AnomalyCollectedData> Anomalies(ArrayList<Person> listOfAnomalies, int hub, ArrayList<Dimension> adjacencyList)
   {
       ArrayList<AnomalyCollectedData> order = new ArrayList<>();
       for (int i = 0; i < listOfAnomalies.size(); i++)
       {
           int destinationOfAnomaly = listOfAnomalies.get(i).getDimensionCurrentlyAt();
           String spider = spiderName(listOfAnomalies.get(i), adjacencyList);
           String anomaly = listOfAnomalies.get(i).getName();
           ArrayList<Integer> shortestPath = new ArrayList<>();
           if (spider == null)
           {
               BFS(adjacencyList, hub, shortestPath, destinationOfAnomaly);
               shortestPath = noSpiderHelp(shortestPath);
           }
           else
           {
               BFS(adjacencyList, hub, shortestPath, destinationOfAnomaly);
           }
           AnomalyCollectedData add = new AnomalyCollectedData(spider, anomaly, shortestPath, null);
           order.add(add);
          
       }




       return order;
   }


   public static ArrayList<Dimension> updateAnomalyLocationToHub(ArrayList<AnomalyCollectedData> order, int hub, ArrayList<Dimension> adjacencyList)
   {
       for (int i = 0; i < order.size(); i++)
       {
           Person spider = null;
           AnomalyCollectedData relocate = order.get(i);
           if (relocate.getSpiderName() != null)
           {
               spider = findThem(relocate.getSpiderName(), adjacencyList);
           }
           Person anomaly = findThem(order.get(i).getAnomalyName(), adjacencyList);
           int theyAreAt = anomaly.getDimensionCurrentlyAt();
           anomaly.setDimensionCurrentlyAt(hub);
           if (spider != null)
           {
               spider.setDimensionCurrentlyAt(hub);
           }
           //remove
           for (int j = 0; j < adjacencyList.size(); j++)
           {
               Dimension ptr = adjacencyList.get(j);
               {
                   while (ptr != null)
                   {
                       if (ptr.getdimensionNumber() == hub)
                       {
                          
                           if (ptr.getpeople() != null)
                           {
                               ptr.getpeople().add(anomaly);
                               if (spider != null)
                               {
                                   ptr.getpeople().add(spider);
                               }
                           }
                       }
                       if (ptr.getdimensionNumber() == theyAreAt)
                       {
                           if (ptr.getpeople() != null)
                           {
                               ptr.getpeople().remove(anomaly);
                           if (spider != null)
                           {
                               ptr.getpeople().remove(spider);
                           }
                           }
                       }
                       ptr = ptr.getNextDimension();
                   }
               }
           }
       }
       return adjacencyList;
   }


   public static Person findThem(String Name, ArrayList<Dimension> adjacencyList)
   {
       Person anomaly = null;
       for (int i = 0; i < adjacencyList.size(); i++)
       {
           Dimension ptr = adjacencyList.get(i);
           while (ptr != null)
           {
               if (ptr.getpeople() != null)
               {
                   ArrayList<Person> people = ptr.getpeople();
                   {
                       for (int j = 0; j < people.size(); j++)
                       {
                           if (people.get(j).getName().equals(Name))
                           {
                               return people.get(j);
                           }
                       }
                   }
               }
               ptr = ptr.getNextDimension();
           }
       }
       return anomaly;
   }


   public static String spiderName(Person Anomaly, ArrayList<Dimension> peopleList)
   {
       String spider = null;
       int index = Collider.doesDimensionExist(Anomaly.getDimensionCurrentlyAt(), peopleList);
       ArrayList<Person> peopleAtDimension = peopleList.get(index).getpeople();
       for (int i = 0; i < peopleAtDimension.size(); i++)
       {
           String otherPerson = peopleAtDimension.get(i).getName();
           if (!otherPerson.equals(Anomaly.getName()))
           {
               if (peopleAtDimension.get(i).getDimensionCurrentlyAt() == peopleAtDimension.get(i).getDimensionalSignature())
               {
                   spider = otherPerson;
               }
           }
       }
       return spider;
   }




   public static ArrayList<Person> listOfAnomalies(ArrayList<Dimension> peopleList)
   {
       ArrayList<Person> listOfAnomalies = new ArrayList<>();
       for (int i = 0; i < peopleList.size(); i++)
       {
           Dimension dptr = peopleList.get(i);
           while (dptr != null)
           {
               ArrayList<Person> pptr = dptr.getpeople();
               if (pptr != null)
               {
                   for (int j = 0; j < pptr.size(); j++)
                   {
                       int dimensionCurrentlyAt = pptr.get(j).getDimensionCurrentlyAt();
                       int dimensionalSignature = pptr.get(j).getDimensionalSignature();
                       if ((dimensionCurrentlyAt != dimensionalSignature) && (dimensionCurrentlyAt != 928))
                       {
                           listOfAnomalies.add(pptr.get(j));
                       }
                   }
               }
               dptr = dptr.getNextDimension();
           }
       }


       return listOfAnomalies;
   }




   public static void BFS(ArrayList<Dimension> adjacencyList, int s, ArrayList<Integer> shortestPath, int destination) {
       Queue<Integer> q = new LinkedList<>();
       boolean[] marked = new boolean[adjacencyList.size()];
       int[] parent = new int[adjacencyList.size()]; // Array to store the parent of each node
       q.add(s);
       int sindex = Collider.doesDimensionExist(s, adjacencyList);
       marked[sindex] = true;
       parent[sindex] = -1; // Set parent of source node as -1
      
       while (!q.isEmpty()) {
           int v = q.poll();
           if (v == destination) { // If the destination is reached, backtrack to find the shortest path
               int current = destination;
               while (current != -1) {
                   shortestPath.add(current);
                   current = parent[Collider.doesDimensionExist(current, adjacencyList)];
               }
               break;
           }
           int vindex = Collider.doesDimensionExist(v, adjacencyList);
           Dimension ptr = adjacencyList.get(vindex);
           while (ptr != null) {
               int p = ptr.getdimensionNumber();
               int pindex = Collider.doesDimensionExist(p, adjacencyList);
               if (!marked[pindex]) {
                   marked[pindex] = true;
                   q.add(p);
                   parent[pindex] = v; // Set the parent of the visited node
               }
               ptr = ptr.getNextDimension();
           }
       }
   }




}

