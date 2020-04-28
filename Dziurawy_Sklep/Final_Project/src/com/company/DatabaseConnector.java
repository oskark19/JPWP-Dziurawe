package com.company;

import base_classes.*;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnector {
    private static Connection con;
    private Statement stmt1;
    private Statement stmt2;
    private Statement stmt3;

    public DatabaseConnector() throws ClassNotFoundException {
        /*#################################################################

        Zadanie 1. Uzupelnij brakujaca sciezke do bazy danch, login i haslo mozna
                   pozostawic puste/wpisac losowe litery

         ##################################################################*/
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection(/*.......*/);
            con=c;
            stmt1=con.createStatement();
            stmt2=con.createStatement();
            stmt3=con.createStatement();
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public Connection getConnection(){
        return con;
    }

    public Statement getStmt(){
        return stmt1;
    }

    /*#################################################################

        Zadanie 2. Napisz funkcje pobierajaca z bazy danych najwyzsza cene
                   produktu i zwracacajaca ja.

                   tabela: products
                   kolumna: price

         ##################################################################*/
    public float getMaxPrice() throws SQLException {
        //.......//
    }

    public String getTypeForQuery(ArrayList<Integer> types){
        if(types.size()==1){
            return String.valueOf(types.get(0));
        } else{
            return "1,2,3";
        }
    }

    public ArrayList<Product> queryGet() throws SQLException {
        try {
            ResultSet rs = stmt1.executeQuery("SELECT * FROM Products");
            ArrayList<Product> p = new ArrayList<>();
            while (rs.next()){
                switch (rs.getInt(7)){
                    case 1:
                        p.add(new Router(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getString(6)));
                        break;
                    case 2:
                        p.add(new Switch(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getString(6)));
                        break;
                    case 3:
                        p.add(new HUB(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getString(6)));
                        break;
                }
            }
            for(int i=0; i<p.size();i++){
                System.out.println(p.get(i).convToString());
            }
            return p;
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }


    public ArrayList<Product> queryGet(float maxPrice, int type, String producer) throws SQLException {
        System.out.println(maxPrice);
        System.out.println(type);
        System.out.println(producer);
        producer = producer == null ? "0" : "\'"+producer+"\'";
        maxPrice = maxPrice == 0 ? getMaxPrice() : maxPrice;
        ArrayList<Integer> types = new ArrayList<Integer>();
        if(type==0){
            types.add(1);
            types.add(2);
            types.add(3);
        } else{
            types.add(type);
        }
        try {
            ResultSet rs = stmt1.executeQuery("SELECT * FROM Products WHERE price<=" + String.valueOf(maxPrice)
                    + " AND device_type IN (" + getTypeForQuery(types) + ") AND producer=" + producer);
            ArrayList<Product> p = new ArrayList<>();
            while (rs.next()){
                switch (rs.getInt(7)){
                    case 1:
                        p.add(new Router(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getString(6)));
                        break;
                    case 2:
                        p.add(new Switch(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getString(6)));
                        break;
                    case 3:
                        p.add(new HUB(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getString(6)));
                        break;
                }
            }
            for(Product i: p){
                System.out.println(i.getId());
            }
            return p;
        } catch (Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    public int convertTypeStrInt(String type){
        if(type.equals("Router")){
            return 1;
        } else if(type.equals("Switch")){
            return 2;
        } else if(type.equals("HUB")){
            return 3;
        } else{
            return 0;
        }
    }
    public String convertTypeIntStr(int type){
        switch (type){
            case 1:
                return "Router";
            case 2:
                return "Switch";
            case 3:
                return "HUB";
            default:
                return "Nie wiadomo";
        }
    }

    public User logIn(String nick, String pass) throws SQLException {
        ResultSet rs = stmt1.executeQuery("SELECT * FROM Users WHERE nickname='" + nick + "' AND password='" + pass + "'");
            if(rs.next()){
                return new User(rs.getInt(1), rs.getString(2), rs.getInt(4), true);
            } else{
                return new User(0, null, 0, false);
            }
    }

    /*#################################################################

            Zadanie 3. Napisz zapytanie tworzace obiekt w koszyku z podanymi
                       w argumentach funkcji product_id, owner_id

                       tabela: baskets
                       kolumny: product_id, owner_id

             ##################################################################*/

    public void addToBasket(int product_id, int owner_id) throws SQLException {
        //.......//
    }

    public Basket getBasketProducts(int owner_id) throws SQLException, ClassNotFoundException {
        ArrayList<Product> result = new ArrayList<Product>();
        ArrayList<Integer> ids = new ArrayList<>();
        ResultSet rs = stmt1.executeQuery("SELECT product_id FROM baskets WHERE owner_id=" + owner_id);
        while(rs.next()){
            ResultSet rs3 = stmt2.executeQuery("SELECT * FROM Products WHERE id=" + String.valueOf(rs.getInt(1)));
            while (rs3.next()) {
                switch (rs3.getInt(7)) {
                    case 1:
                        result.add(new Router(rs3.getInt(1), rs3.getString(2), rs3.getString(3), rs3.getInt(4), rs3.getFloat(5), rs3.getString(6)));
                        break;
                    case 2:
                        result.add(new Switch(rs3.getInt(1), rs3.getString(2), rs3.getString(3), rs3.getInt(4), rs3.getFloat(5), rs3.getString(6)));
                        break;
                    case 3:
                        result.add(new HUB(rs3.getInt(1), rs3.getString(2), rs3.getString(3), rs3.getInt(4), rs3.getFloat(5), rs3.getString(6)));
                        break;
                }
            }
            rs3.close();
        }
        ResultSet rs2 = stmt1.executeQuery("SELECT id FROM baskets WHERE owner_id=" + owner_id);
        while (rs2.next()){
            ids.add(rs2.getInt(1));
        }
        int[] num = new int[ids.size()];
        int c=0;

        for(int i : ids){
            num[c]=i;
            c++;
        }
        Basket b = new Basket(num,owner_id);
        b.setProducts(result);
        return b;
    }

    public void updateItemInDatabase(int prod_id, String producer, String name, int ports, float price, String desc, String type) throws SQLException {
        stmt1.executeUpdate("UPDATE Products SET producer = '" + producer + "', name='" + name + "', ports_num=" + String.valueOf(ports) + ", price = " + String.valueOf(price) + ", device_desc='"+desc + "', device_type="+ convertTypeStrInt(type) +" WHERE id=" + String.valueOf(prod_id));
    }

    public float sumPriceBasket(Basket b) throws SQLException {
        ResultSet rs = stmt2.executeQuery("SELECT product_id, COUNT(product_id) FROM baskets WHERE owner_id=" +String.valueOf(b.getOwner_id())+ " GROUP BY product_id ORDER BY product_id");
        float result=0;
        ResultSet rs2;
        while (rs.next()){
            rs2=stmt3.executeQuery("SELECT price FROM products WHERE id=" + String.valueOf(rs.getInt(1)));
            rs2.next();
            result+=rs2.getFloat(1) * rs.getInt(2);
            rs2.close();
        }
        System.out.println(result);
        return result;
    }

    public void deleteFromBasket(int basket_id, int owner_id) throws SQLException {
        stmt1.executeUpdate("DELETE FROM baskets WHERE id=" + String.valueOf(basket_id));
    }

        /*#################################################################

            Zadanie 4. Napisz zapytanie usuwajace produkt z bazy danych przy
                       uzyciu podanego w argumencie funkcji item_id

                       tabela: products
                       kolumna: id

             ##################################################################*/

    public void deleteFromDataBase(int item_id) throws SQLException {
        //.......//
    }

    public void order(Basket b, String flname, String mail) throws SQLException {
        String[] splitName = flname.split(" ");
        String fname = splitName[0];
        String lname = splitName[1];
        int orderNum = getOrderNum();
        for(Product p : b.getProducts()){
            stmt1.executeUpdate("INSERT INTO orders(name, lname, mail, product_id, owner_id, orderNum) values(\""+fname+"\",\""+lname+"\",\""+mail+"\","+String.valueOf(p.getId())+","+String.valueOf(b.getOwner_id())+","+String.valueOf(orderNum)+")");
            stmt1.executeUpdate("DELETE FROM baskets WHERE product_id=" + String.valueOf(p.getId()) + " AND owner_id=" + String.valueOf(b.getOwner_id()));
        }
        System.out.println("doszlo tu!?");

    }

    public String[] queryProducers() throws SQLException {
        ResultSet rs = stmt1.executeQuery("SELECT DISTINCT producer FROM Products");
        ArrayList<String> als = new ArrayList<>();
        while(rs.next()){
            als.add(rs.getString(1));
        }
        String[] result = als.toArray(new String[0]);
        return result;
    }

    public void addNewItem(String producer, String name, String ports, String price, String desc, String type) throws SQLException {
        stmt1.executeUpdate("INSERT INTO Products(producer, name, ports_num, price, device_desc, device_type) values('" + producer + "', '" + name + "', " + ports + ", " + price + ", '" + desc + "', " + convertTypeStrInt(type) + ")");
        System.out.println("Dzioło?");
    }

    public String getUserById(int id) throws SQLException {
        ResultSet rs = stmt1.executeQuery("SELECT name FROM users WHERE id = " + id);
        rs.next();
        return rs.getString(1);
    }

    public ArrayList<Order> getOrderList() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = new ArrayList<>();
        ResultSet rs1 = stmt1.executeQuery("SELECT * FROM orders ORDER BY orderNum");
        ResultSet rs2 = stmt2.executeQuery("SELECT orderNum, COUNT(orderNum) FROM orders GROUP BY orderNum");
        rs1.next();
        while (rs2.next()){
            orders.add(new Order(rs1.getInt(1), rs1.getInt(7), rs1.getString(4), rs1.getString(3), rs1.getString(5), rs1.getInt(8)));
            for(int i=0; i<rs2.getInt(2); i++){
                orders.get(orders.size()-1).addItem(getProduct(rs1.getInt(6)));
                rs1.next();
            }
        }
        return orders;
    }

    public ArrayList<Order> getOrderList(int owner_id) throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = new ArrayList<>();
        ResultSet rs1 = stmt1.executeQuery("SELECT * FROM orders WHERE owner_id ="+String.valueOf(owner_id)+" ORDER BY orderNum");
        ResultSet rs2 = stmt2.executeQuery("SELECT orderNum, COUNT(orderNum) FROM orders GROUP BY orderNum");
        rs1.next();
        while (rs2.next()){
            orders.add(new Order(rs1.getInt(1), rs1.getInt(7), rs1.getString(4), rs1.getString(3), rs1.getString(5), rs1.getInt(8)));
            for(int i=0; i<rs2.getInt(2); i++){
                orders.get(orders.size()-1).addItem(getProduct(rs1.getInt(6)));
                rs1.next();
            }
        }
        return orders;
    }

    public void updateStatus(int value, int orderNum) throws SQLException {
        stmt1.executeUpdate("UPDATE orders SET status=" + String.valueOf(value) + " WHERE orderNum=" + String.valueOf(orderNum));
    }


    public int getOrderNum() throws SQLException {
        ResultSet rs = stmt1.executeQuery("SELECT MAX(orderNum) FROM orders");
        rs.next();
        return rs.getInt(1)+1;
    }

    public Product getProduct(int product_id) throws SQLException, ClassNotFoundException {
        ResultSet rs = stmt3.executeQuery("SELECT * FROM products WHERE id=" + String.valueOf(product_id));
        rs.next();
        switch (rs.getInt(7)){
            case 1:
                return new Router(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getString(6));
            case 2:
                return new Switch(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getString(6));
            case 3:
                return new HUB(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getString(6));
        }
        return null;
    }

    public int parseStatus(String status){
        if(status.equals("Nowy")){
            return 1;
        } else if(status.equals("W trakcie realizacji")){
            return 2;
        } else if(status.equals("Wyslano")){
            return 3;
        } else if(status.equals("Anulowano")){
            return 4;
        } else {
            return 0;
        }
    }

    public String parseStatus(int status){
        switch (status){
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

}
