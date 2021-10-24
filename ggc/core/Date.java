package ggc.core;

import ggc.core.exception.NonPositiveDateException;

import java.io.Serializable;

public class Date implements Serializable {
   private int _days;
   
   public Date(){
      _days = 0;
   }

   public int getDays(){//?
      return _days;
   }

   public Date add(int days) throws NonPositiveDateException {
      if (days <= 0)
         throw new NonPositiveDateException();
      _days += days;
      return this;
   }

   public int difference(Date other){
      return (_days -= other.getDays());//?
   }

   public Date now() {
       return this; // ASK PROF STATIC???
   }
}
