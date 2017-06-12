package usmanali.kinarabakery;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SAJIDCOMPUTERS on 3/7/2017.
 */

public class products  {
    String productname;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    int quantity;

    public int getId() {
        return productid;
    }

    public void setId(int id) {
        this.productid = id;
    }

    int productid;

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    int price;

    public String getCatorgery() {
        return Catorgery;
    }

    public void setCatorgery(String catorgery) {
        this.Catorgery = catorgery;
    }

    String Catorgery;

public products(){}

    public products(String name,int price,int id,String image,int quantity){
        this.setProductname(name);
        this.setPrice(price);
        this.setId(id);
        this.setImage(image);
        this.setQuantity(quantity);
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
