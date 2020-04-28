package base_classes;

import com.company.DatabaseConnector;

public abstract class Product {
    private int id;
    private String producer;
    private String name;
    private int ports_num;
    private float price;
    private String device_desc;
    private int type;
    private DatabaseConnector dc = new DatabaseConnector();

    public Product(int id, String producer, String name, int ports_num, float price, String device_desc) throws ClassNotFoundException {
        this.id=id;
        this.producer=producer;
        this.name=name;
        this.ports_num=ports_num;
        this.price=price;
        this.device_desc=device_desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPorts_num() {
        return ports_num;
    }

    public void setPorts_num(int ports_num) {
        this.ports_num = ports_num;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDevice_desc() {
        return device_desc;
    }

    public void setDevice_desc(String device_desc) {
        this.device_desc = device_desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String[] convToString(){
        String[] strings = {this.getProducer(), this.getName(), String.valueOf(this.getPorts_num()),
                String.valueOf(this.getPrice()), this.getDevice_desc(),
                dc.convertTypeIntStr(this.getType())};
        return strings;
    }
}
