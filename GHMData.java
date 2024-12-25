


package spiderman;


import java.util.ArrayList;


public class GHMData {
   private int numberOfCanonEvents;
   private String name;
   private boolean success;
   private ArrayList<Dikstra> path;


   public GHMData()
   {
       this.numberOfCanonEvents = 0;
       this.name = null;
       this.success = false;
       this.path = null;
   }


   public GHMData(int numberOfCanonEvents, String name, boolean success, ArrayList<Dikstra> path)
   {
       this.numberOfCanonEvents = numberOfCanonEvents;
       this.name = name;
       this.success = success;
       this.path = path;
   }


   public int getNumberOfCanonEvents() { return this.numberOfCanonEvents;  }
   public String getName() { return this.name; }
   public boolean getSuccess() { return this.success; }
   public ArrayList<Dikstra> getPath() { return this.path; }




   public void setNumberOfCanonEvents(int numberOfCanonEvents) { this.numberOfCanonEvents = numberOfCanonEvents; }
   public void setName(String name) { this.name = name; }
   public void setSuccess(boolean success) { this.success = success; }
   public void setPath(ArrayList<Dikstra> path)  { this.path = path; }


}
