package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;
import java.io.IOException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.NonPositiveDateException;
import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;
  private Date _date;
  private int _nextTransictionId;
  private HashMap<String, Partner> _partners;
  private float _balance;
  //private float _contabilisticBalance;
  private List<Product> _products;

  Warehouse(){
    _date = new Date();
    _nextTransictionId = 0;
    _partners = new HashMap<>();
    _products = new ArrayList<>();
  }

  public Partner getPartner(String id){
    return _partners.get(id);
  }

  public List<Partner> getPartners() {
    List<Partner> list = new ArrayList<>();

    for(Partner p : _partners.values())
      list.add(p);

    return list;
  }

  public int getDate() {
    return _date.getDays();
  }

  public void advanceDays(int days) throws NonPositiveDateException {
    try {
      _date.add(days);
    }catch (NonPositiveDateException ide) { throw ide; }
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
