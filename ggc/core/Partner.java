package ggc.core;

public class Partner{
    private String _name;
    private String _adress;
    private String _id;
    private String _status;
    private double _points;

    public Partner(String name, String adress, String id){
        _name = name;
        _adress = adress;
        _id = id;
    }

    public void manageProductNotifications(String partnerId, String productId){
        return; //TO DO
    }

    public String getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public String getAdress(){
        return _adress;
    }
}