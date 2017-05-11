package com.complaints.jd.h2h;

/**
 * Created by Nitin Tonger on 07-04-2017.
 */

public class Crop {
    public String product ,imgurl,barcode;
String company;
    public String price,id;
    public Crop(String product,String barcode,String imgurl,String price,String id,String company)
    {
        this.product=product;
        this.price=price;
        this.barcode=barcode;
        this.imgurl=imgurl;
        this.id=id;
        this.company=company;
    }
    public String getId()
    {
        return id;
    }
    public  void setId()
    {
       this.id=id;
    }
    public  void setcompany()
    {
        this.company=company;
    }
    public  String  getcompany()
    {
        return company;
    }
    public String  getproduct()
    {
        return product;
    }
    public void setProduct(String product)
    {
        this.product=product;
    }
    public String  getBarcode()
    {
        return barcode;
    }
    public  void setBarcode(String  barcode)
    {
        this.barcode=barcode;
    }
    public  String getImgurl()
    {
        return imgurl;
    }
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    public String  getPrice()
    {
        return  price;
    }
    public void setPrice(String price)
    {
        this.price=price;
    }
}
