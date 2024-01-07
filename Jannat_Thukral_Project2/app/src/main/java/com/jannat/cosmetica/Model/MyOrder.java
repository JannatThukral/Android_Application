package com.jannat.cosmetica.Model;

public class MyOrder {
    private String id;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    private String order;
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public MyOrder(){};
    public MyOrder(String id , String price , String name){
        this.id = id;
        this.price = price;
        this.order = order;

    }
}
