package br.com.renandeldotti.inventoryapp.database;

public class QuantityAndPrice {
    private float sold_price;

    private int quantity_sold;

    public QuantityAndPrice(float sold_price, int quantity_sold) {
        this.sold_price = sold_price;
        this.quantity_sold = quantity_sold;
    }

    public float getSold_price() {
        return sold_price;
    }

    public void setSold_price(float sold_price) {
        this.sold_price = sold_price;
    }

    public int getQuantity_sold() {
        return quantity_sold;
    }

    public void setQuantity_sold(int quantity_sold) {
        this.quantity_sold = quantity_sold;
    }
}
