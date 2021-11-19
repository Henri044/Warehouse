package ggc.core;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import ggc.core.Observer;
import ggc.core.Notification;
import ggc.core.DeliverNotificationMethod;
import ggc.core.DeliverNotification;
import ggc.core.status.StatusContext;

public class Partner implements Serializable, Observer {

    private String _id;
    private String _name;
    private String _address;
    private StatusContext _status;
    private double _acquisitionsValue;
    private double _effectiveSalesValue;
    private double _paidSalesValue;
    private ArrayList<Notification> _notifications = new ArrayList<Notification>();
    private DeliverNotificationMethod _deliverNotificationMethod = new DeliverNotification();


    public Partner(String id, String name, String address) {
        _id = id;
        _name = name;
        _address = address;
        _status = new StatusContext();
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

    public double getPoints() {
        return _status.getPoints();
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

    public StatusContext getStatus() {
        return _status;
    }

    public void addAcquisitionValue(double n) {
        _acquisitionsValue += n;
    }

    public void addEffectiveSalesValue(double n) {
        _effectiveSalesValue += n;
    }

    public void addPaidSalesValue(double n) {
        _paidSalesValue += n;
    }

    public void update(Notification notif) {
        //_notifications.add(notif);
        _deliverNotificationMethod.addNotification(notif, _notifications);
    }

    public void clearNotifs() {
        _notifications.clear();
    }

    public DeliverNotificationMethod getDeliverNotificationMethod() {
        return _deliverNotificationMethod;
    }

    public void setDeliverNotificationMethod(DeliverNotificationMethod dnm) {
        _deliverNotificationMethod = dnm;
    }

    public String toString() {
        return (_id + "|" + _name + "|" + _address + "|" + _status.statusToString() + "|" + Math.round(_status.getPoints()) +
         "|" + Math.round(_acquisitionsValue) + "|" + Math.round(_effectiveSalesValue) + "|" + Math.round(_paidSalesValue));
    }
}