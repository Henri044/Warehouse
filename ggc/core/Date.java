package ggc.core;

public class Date {
   private int _days;
   
   public Date(){
      _days = 0;
   }

   public int getDays(){//?//
      return _days;
   }

   public Date add(int days){
      _days += days;
   }

   public int difference(Date other){
      _days -= other.getDays();//?//
   }

   public static Date now(){
      
   }
}
