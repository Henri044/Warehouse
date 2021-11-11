package ggc.core.comparators;

import java.util.Comparator;

import ggc.core.Partner;

public class PartnersComparator implements Comparator<Partner> {

    // Compare and sort the partners by their ID's
    public int compare(Partner p1, Partner p2){
        return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
    }
}