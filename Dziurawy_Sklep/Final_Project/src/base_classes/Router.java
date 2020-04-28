package base_classes;

public class Router extends Product {
    private int type = 1;
    public Router(int id, String producer, String name, int ports_num, float price, String device_desc) throws ClassNotFoundException {
        super(id, producer, name, ports_num, price, device_desc);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
