package ggc.core;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import ggc.core.Observer;
import ggc.core.Notification;

public class Partner implements Serializable, Observer {

    private String _id;
    private String _name;
    private String _address;
    private String _status;
    private double _points;
    private double _acquisitionsValue;
    private double _effectiveSalesValue;
    private double _paidSalesValue;
    private List<Notification> _notifications = new ArrayList<Notification>();

    public Partner(String id, String name, String address) {
        _id = id;
        _name = name;
        _address = address;
        _status = "NORMAL";
    }

    public void manageProductNotifications(String partnerId, String productId) {
        return; //TO DO
    }

    public String getId() {
        return _id;
    }
    public List<Notification> getNotifications(){
        return _notifications;
    }

    public String getName() {
        return _name;
    }

    public String getAddress() {
        return _address;
    }

    public String getStatus() {
        return _status;
    }

    public double getPoints() {
        return _points;
    }

    public double getAcquisitionsValue() {
        return _acquisitionsValue;
    }

    public double getEffectiveSalesValue() {
        return _effectiveSalesValue;
    }

    public double getPaidSalesValue() {
        return _paidSalesValue;
    }

    public void addAcquisitionValue(double n) {
        _acquisitionsValue += n;
    }

    public void update(Notification notif) {
        _notifications.add(notif);
    }

    public void clearNotifs() {
        _notifications.clear();
    }

    public String toString() {
        return (_id + "|" + _name + "|" + _address + "|" + _status + "|" + Math.round(_points) +
         "|" + Math.round(_acquisitionsValue) + "|" + Math.round(_effectiveSalesValue) + "|" + Math.round(_paidSalesValue));
    }
}