package spiderman;
import java.util.ArrayList;


public class Dimension {
   private int dimensionNumber;
   private int numberOfCanonEvents;
   private int dimensionWeight;
   private ArrayList<Person> people; 
   private Dimension next;
  
   public Dimension()
   {
       this.dimensionNumber = 0;
       this.numberOfCanonEvents = 0;
       this.dimensionWeight = 0;
       this.people = null;
       this.next = null;
   }


   public Dimension(int dimensionNumber, int numberOfCanonEvents, int dimensionWeight, ArrayList<Person> people, Dimension next) {
       this.dimensionNumber = dimensionNumber;
       this.numberOfCanonEvents = numberOfCanonEvents;
       this.dimensionWeight = dimensionWeight;
       this.people = people;
       this.next = next;
   }


   // Getter and Setter methods
   public int getdimensionNumber() { return dimensionNumber; }
   public int getnumberOfCanonEvents() { return numberOfCanonEvents; }
   public int getdimensionWeight() { return dimensionWeight; }
   public ArrayList<Person> getpeople() { return people; }
   public Dimension getNextDimension() { return next; }




   public void setdimensionNumber(int dimensionNumber) { this.dimensionNumber = dimensionNumber;}
   public void setnumberOfCanonEvents(int numberOfCanonEvents) { this.numberOfCanonEvents = numberOfCanonEvents; }
   public void setdimensionWeight(int dimensionWeight) { this.dimensionWeight = dimensionWeight; }
   public void setpeople(ArrayList<Person> people) { this.people = people; }
   public void setNextDimension(Dimension next) { this.next = next; }


}
  
