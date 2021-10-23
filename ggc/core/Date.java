package ggc.core;

public class Date {
   private int _days;
   
   public Date(){
      _days = 0;
   }

   public Date add(int days){
      _days += days;
      return this;
   }

   public int difference(Date other){
      return (other._days - this._days);
   }
   public static Date now(){
       return this;
   }
}
