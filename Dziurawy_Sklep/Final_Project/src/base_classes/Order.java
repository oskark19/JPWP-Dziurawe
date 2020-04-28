package base_classes;

import com.company.DatabaseConnector;

import java.sql.SQLException;
import java.util.ArrayList;

public class Order {
    private ArrayList<Product> products;
    private int status;
    private int owner;
    private String lname;
    private String fname;
    private String address;
    private int id;
    private static DatabaseConnector dc;

    public Order(int status, int owner, String lname, String fname, String address, int id) throws ClassNotFoundException {
        dc = new DatabaseConnector();
        this.status=status;
        this.owner=owner;
        this.lname=lname;
        this.fname=fname;
        this.address=address;
        this.id=id;
        this.products = new ArrayList<>();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getItem(int i){
        return products.get(i);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String parseStatus(){
        switch (this.status){
            case 1:
                return "Nowy";
            case 2:
                return "W trakcie realizacji";
            case 3:
                return "Wyslano";
            case 4:
                return "Anulowano";
        }
        return null;
    }

    public String getOwnerName() throws SQLException {
        return dc.getUserById(this.owner);
    }

    public void addItem(Product p){
        products.add(p);
    }
}
