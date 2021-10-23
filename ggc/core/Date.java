package ggc.core;

public class Date {
   private int _days;
   
   public Date(){
      _days = 0;
   }

   public int getDays(){//?
      return _days;
   }

   public Date add(int days) throws InvalidDateException {
      if (days <= 0)
         throw new InvalidDateException();
      _days += days;
      return this;
   }

   public int difference(Date other){
      return (_days -= other.getDays());//?
   }

   public static Date now(){
       return this;
   }
}
