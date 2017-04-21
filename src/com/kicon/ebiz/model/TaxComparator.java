package com.kicon.ebiz.model;

import java.io.Serializable;
import java.util.Comparator;


public class TaxComparator implements Comparator<Tax>, Serializable {

    public int compare(Tax tax1, Tax tax2) {
        return tax1.getName().compareTo(tax2.getName());
    }
}
