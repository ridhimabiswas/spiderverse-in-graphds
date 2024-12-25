package spiderman;


import java.util.ArrayList;


public class AnomalyCollectedData {
   String spider;
   String anomaly;
   ArrayList<Integer> wholePath;
   AnomalyCollectedData next;




   public AnomalyCollectedData()
   {
       this.spider = null;
       this.anomaly = null;
       this.wholePath = null;
       this.next = null;
   }


   public AnomalyCollectedData(String spider, String anomaly, ArrayList<Integer> wholePath, AnomalyCollectedData next)
   {
       this.spider = spider;
       this.anomaly = anomaly;
       this.wholePath = wholePath;
       this.next = next;
   }


   public String getSpiderName() { return spider; }
   public String getAnomalyName() { return anomaly; }
   public ArrayList<Integer> getWholePath() { return getWholePath(); }
   public AnomalyCollectedData getNext() { return next; }


   public void setSpiderName(String spider) { this.spider = spider; }
   public void setAnomalyName(String anomaly) { this.anomaly = anomaly; }
   public void setWholePath(ArrayList<Integer> wholePath) { this.wholePath = wholePath; }
   public void setNext(AnomalyCollectedData next) { this.next = next; }


}

