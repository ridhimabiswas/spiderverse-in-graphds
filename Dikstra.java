package spiderman;


public class Dikstra {


   private Dimension vertex;
   private int shortestDistance;
   private Dimension previousVertex;


   public Dikstra()
   {
       this.vertex = null;
       this.shortestDistance = Integer.MAX_VALUE;
       this.previousVertex = null; 
   }


   public Dikstra(Dimension vertex, int shortestDistance, Dimension previousVertex)
   {
       this.vertex = vertex;
       this.shortestDistance = shortestDistance;
       this.previousVertex = previousVertex;
   }


   public Dimension getVertex() { return this.vertex; }
   public int getShortestDistance() { return this.shortestDistance; }
   public Dimension getPreviousVertex() { return this.previousVertex; }


   public void setVertex(Dimension vertex) { this.vertex = vertex; }
   public void setShortestDistance(int shortestDistance) { this.shortestDistance = shortestDistance; }
   public void setPreviousVertex(Dimension previousVertex) { this.previousVertex = previousVertex; }
  
}



