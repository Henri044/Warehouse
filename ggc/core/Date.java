package ggc.core;

import ggc.core.exception.NonPositiveDateException;

import java.io.Serializable;

public class Date implements Serializable {

   // ATRIBUTES

   private int _days;
   
   // CONSTRUCTOR

   public Date() {
      _days = 0;
   }

   /**
    * Gets the current days.
    *
    * @return the current days.
    */

   public int getDays() {
      return _days;
   }

   /**
    * Adds days to the Date.
    *
    * @param days This is the number of days to add
    * @return the modded Date.
    */

   public Date add(int days) throws NonPositiveDateException {
      if (days < 0)
         throw new NonPositiveDateException();
      _days += days;
      return this;
   }

   public Date now() {
      return this;
   }

   public boolean equals(Date obj) {
      return _days == obj.getDays();
   }
}
