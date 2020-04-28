package base_classes;

import java.util.ArrayList;

public class Basket {
    private int[] id;
    private int owner_id;
    private ArrayList<Product> products=new ArrayList<>();

    public Basket(int[] id, int owner_id){
        this.id=id;
        this.owner_id=owner_id;
    }

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
