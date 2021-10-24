package ggc.core;

public class Partner{
    private String _id;
    private String _name;
    private String _address;
    private String _status;
    private double _points;

    public Partner(String id, String name, String address){
        _id = id;
        _name = name;
        _address = address;
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

    public String getAddress(){
        return _address;
    }
}