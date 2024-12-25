package spiderman;


public class Person {
   private int dimensionCurrentlyAt;
   private String name;
   private int dimensionalSignature;


   public Person()
   {
       this.dimensionCurrentlyAt = -1;
       this.name = null;
       this.dimensionalSignature = -1;
   }
   public Person(int dimensionCurrentlyAt, String name, int dimensionalSignature){
       this.dimensionCurrentlyAt = dimensionCurrentlyAt;
       this.name = name;
       this.dimensionalSignature = dimensionalSignature;
   }


   public int getDimensionCurrentlyAt() { return dimensionCurrentlyAt; }
   public String getName() { return name; }
   public int getDimensionalSignature() { return dimensionalSignature; }


   public void setDimensionCurrentlyAt(int dimensionCurrentlyAt) { this.dimensionCurrentlyAt = dimensionCurrentlyAt; }
   public void setName(String name) { this.name = name; }
   public void setDimensionalSignature( int dimensionalSignature ) { this.dimensionalSignature = dimensionalSignature; }


  
}
