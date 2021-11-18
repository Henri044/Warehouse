package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import pt.tecnico.uilib.forms.Form;

import ggc.app.transactions.Message;

import java.util.ArrayList; 

import ggc.core.exception.NonExistentPartnerKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.NonExistentProductKeyException;
import ggc.app.exception.UnknownProductKeyException;

public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  private int i = 0;
  private double _price;
  private int _stock;;
  private String _idProduct;
  private String _idPartner;
  private String _hasRecipe;
  private double _alpha;;
  private int _numberOfComponents;
  private ArrayList<Integer> _quantities = new ArrayList<Integer>();
  private ArrayList<String> _products = new ArrayList<String>();

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("idPartner", Message.requestPartnerKey());
    addStringField("idProduct", Message.requestProductKey());
    addRealField("price", Message.requestPrice());
    addIntegerField("stock", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException, UnknownPartnerKeyException, UnknownProductKeyException {
    Form form = new Form();


    _idPartner = stringField("idPartner");
    _idProduct = stringField("idProduct");
    _price = realField("price");
    _stock = integerField("stock");
    String idException = new String();

    if (!_receiver.hasProduct(_idProduct)) {
        form.addStringField("hasRecipe", Message.requestAddRecipe());
        form.parse();
        _hasRecipe = form.stringField("hasRecipe");

        if (_hasRecipe.equals("s")) {
          form = new Form();

          form.addIntegerField("numberOfComponents", Message.requestNumberOfComponents());
          form.addRealField("alpha", Message.requestAlpha());

          form.parse();

          _alpha = form.realField("alpha");
          _numberOfComponents = form.integerField("numberOfComponents");

          for (int i = 0; _numberOfComponents > i; i++) {
            Form form2 = new Form();

            form2.addStringField("idComponents", Message.requestProductKey());
            form2.addIntegerField("quantities", Message.requestAmount());

            form2.parse();

            _quantities.add(i,(form2.integerField("quantities")));
            _products.add(i, (form2.stringField("idComponents")));
          }

          for (String id : _products) {
            if (!_receiver.hasProduct(id)) {
              idException = id;
              break;
            }
          }

          try {
            _receiver.registerAggregateProduct(_idProduct, _price, (_receiver.registerRecipe(_alpha, _products, _quantities)));
            _receiver.registerNewProductNotification(_idProduct, _idPartner, _price);
          } catch (NonExistentProductKeyException nepke) {
            throw new UnknownProductKeyException(idException);
          }

        }

        if (_hasRecipe.equals("n")) {
          _receiver.registerSimpleProduct(_idProduct, _price);
          _receiver.registerNewProductNotification(_idProduct, _idPartner, _price);

        }
      }

      try {
        _receiver.registerAcquisition(_price, _stock, _idPartner, _idProduct);
      } catch (NonExistentPartnerKeyException nepke) {
      throw new UnknownPartnerKeyException(_idPartner);
      }

    }
  }

