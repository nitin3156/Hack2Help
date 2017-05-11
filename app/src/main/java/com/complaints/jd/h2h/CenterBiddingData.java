package com.complaints.jd.h2h;

/**
 * Created by sagar on 8/4/17.
 */

public class CenterBiddingData {
    public String price;
    public String quantity;

    public CenterBiddingData(String price,String quantity)
    {
        this.price = price;
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
