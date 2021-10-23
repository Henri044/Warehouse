package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.sql.Date;
import java.io.IOException;
import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private Date _date;
  private int _nextTransictionId;
  private Set<Partner> _partner;

  Warehouse(Date date, int _nextTransictionId){
    _date = Date//?//;
    _nextTransictionId = 0;
    _partner = new HashSet<>();
  }

  Partner getPartner(String id){
    return _partner;
  }

  public List<Partner> getPartners() {
    List<Partner> list = new ArrayList<>();

    for(Partner p : _partner)
      list.add(p);

    return list;
}
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */ {
    //FIXME implement method
  }

}
