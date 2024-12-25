package spiderman;


public class GoHomeMachineData {
   private String name;
   private int timeAllotedToReturn;


   public GoHomeMachineData()
   {
       this.name = null;
       this.timeAllotedToReturn = 0;
   }


   public GoHomeMachineData(String name, int timeAllotedToReturn)
   {
       this.name = name;
       this.timeAllotedToReturn = timeAllotedToReturn;
   }


   public String getName() { return this.name; }
   public int getTime() { return this.timeAllotedToReturn; }


   public void setName(String name) { this.name = name; }
   public void setTime(int timeAllotedToReturn) {this.timeAllotedToReturn = timeAllotedToReturn; }
}
