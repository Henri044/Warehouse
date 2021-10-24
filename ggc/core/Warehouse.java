package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;
import java.io.IOException;

import ggc.core.exception.BadEntryException;
import ggc.core.exception.NonPositiveDateException;
import ggc.core.exception.SamePartnerKeyException;
import ggc.core.exception.NonExistentPartnerKeyException;
import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;
import ggc.core.Parser;

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

  public void registerPartner(String id, String name, String address) throws SamePartnerKeyException{
    Partner newPartner = new Partner(id, name, address);

    if (_partners.containsKey(id))
      throw new SamePartnerKeyException();

    _partners.put(id, newPartner);
  }

  public String showPartner(String id) throws NonExistentPartnerKeyException {
    if (!_partners.containsKey(id))
      throw new NonExistentPartnerKeyException();

    Partner partner = _partners.get(id);

    return (partner.getId() + "|" + partner.getName() + "|" + partner.getAddress());
  }

  /*
  public String showPartners {
    Set<String> unsortedPartnersId = new HashSet<>();

    unsortedPartnersId = _partners.keySet();

    List<String> sortedPartnersId = new ArrayList<String>(unsortedPartnersId);

    Collections.sort(sortedPartnersId);

    List<String> sortedPartners = new ArrayList<>();

    for (int i = 0; i < sortedPartnersId.size(); i++) {
      sortedPartners.add(showPartner(sortedPartnersId.get()) + "\n");
    }

    return sortedPartners;
  }
  */
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
    Parser parser = new Parser(this);

    try {
      parser.parseFile(txtfile);
    } catch (IOException | BadEntryException e) {
      throw e;
    }
  }

}
