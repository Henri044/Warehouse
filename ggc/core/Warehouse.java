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
import gcc.core.product.Product;
import gcc.core.Partner;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private Date _date;
  private int _nextTransictionId;
  private Set<Partner> _partners;
  private float _balance;
  //private float _contabilisticBalance;
  private List<Product>

  Warehouse(int _nextTransictionId){
    _date = new Date();
    _nextTransictionId = 0;
    _partners = new HashSet<>();
  }

  Partner getPartner(String id){
    return _partner;
  }

  public List<Partner> getPartners() {
    List<Partner> list = new ArrayList<>();

    for(Partner p : _partners)
      list.add(p);

    return list;
  }

  public int getDate() {
    return _date.getDays();
  }

  public void advanceDays(int days) {
    _date.add(days);
  }

  public int getBalance() {
    return (int)_balance;
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
