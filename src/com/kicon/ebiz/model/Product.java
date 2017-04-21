package com.kicon.ebiz.model;

import com.kicon.ebiz.model.Tax;


public interface Product {

    String getId();

    String getItemNo();

    String getName();

    double getPrice();

    String getShortDescription();

    Tax getTax();

    public String getType();
}
