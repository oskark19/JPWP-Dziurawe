package com.company;

import base_classes.Order;
import base_classes.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDialog extends JDialog {
    private JPanel tableHeader;
    private JPanel itemsPanel;
    private User user;
    private boolean isAdmin;
    private JComboBox<String> statusType;
    private DatabaseConnector dc;
    private JFrame o;
    private ArrayList<Order> orders;
    private int counter=0;
    private ArrayList<Integer> last_plz = new ArrayList<>();
    private ArrayList<JComboBox> umm = new ArrayList<>();
    public OrdersDialog(JFrame owner, User u) throws ClassNotFoundException, SQLException {
        super(owner, "Zamówienia");
        this.dc = new DatabaseConnector();
        this.user=u;
        o = owner;
        var ramka = BorderFactory.createEtchedBorder();
        if(user.getCredentails()==2){
            isAdmin=true;
        } else{
            isAdmin=false;
        }

        itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tableHeader = new JPanel();

        var prodLabel = new JLabel("Numer zamówienia");
        prodLabel.setPreferredSize(new Dimension(150, 30));
        prodLabel.setBorder(ramka);

        var nameLabel = new JLabel("Adres");
        nameLabel.setPreferredSize(new Dimension(300,30));
        nameLabel.setBorder(ramka);
        var priceLabel = new JLabel("Status");
        priceLabel.setPreferredSize(new Dimension(120,30));
        priceLabel.setBorder(ramka);

        tableHeader.add(prodLabel);
        tableHeader.add(nameLabel);
        tableHeader.add(priceLabel);
        tableHeader.setBorder(ramka);
        itemsPanel.add(tableHeader);
        int count = makeItemList();
        if(isAdmin) itemsPanel.setPreferredSize(new Dimension(965, 54*count + 60));
        else itemsPanel.setPreferredSize(new Dimension(710, 54*count + 60));
        add(itemsPanel);
        pack();
    }

    private int makeItemList() throws SQLException, ClassNotFoundException {
        counter=0;
        if(isAdmin){
            orders = dc.getOrderList();
        } else{
            orders = dc.getOrderList(user.getId());
        }
        for(int i=0;i<orders.size();i++){
            String[] input = {String.valueOf(orders.get(i).getId()), orders.get(i).getAddress(), orders.get(i).parseStatus()};
            last_plz.add(orders.get(i).getId());
            addRow(input);
            counter++;
        }
        return orders.size();
    }

    private void addRow(String[] data) {
        var row = new JPanel();
        var orderNo = new JLabel(data[0]);
        var ramka = BorderFactory.createEtchedBorder();
        orderNo.setPreferredSize(new Dimension(150, 30));
        orderNo.setBorder(ramka);
        var address = new JLabel(data[1]);
        address.setPreferredSize(new Dimension(300, 30));
        address.setBorder(ramka);
        var status = new JLabel(data[2]);
        status.setPreferredSize(new Dimension(120, 30));
        status.setBorder(ramka);


        row.add(orderNo);
        row.add(address);
        row.add(status);
        row.setBorder(ramka);

        var viewItems = new JButton("Przedmioty");
        viewItems.setName(String.valueOf(counter));
        viewItems.addActionListener(event -> {
            var orderList = new OrderList(o, orders.get(Integer.valueOf(((JButton)event.getSource()).getName())));
            orderList.setVisible(true);

        });

        row.add(viewItems);

        statusType = new JComboBox<>();
        statusType.addItem("Nowy");
        statusType.addItem("Anulowano");
        statusType.addItem("W trakcie realizacji");
        statusType.addItem("Wysłano");
        umm.add(statusType);


        var setStatusButton = new JButton("Zmień status");
        setStatusButton.setName(String.valueOf(counter));
        setStatusButton.addActionListener(event -> {
            String statusik = String.valueOf(umm.get(Integer.valueOf(((JButton)event.getSource()).getName())).getSelectedItem());
            try {
                dc.updateStatus(dc.parseStatus(statusik), orders.get(Integer.valueOf(((JButton)event.getSource()).getName())).getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            itemsPanel.removeAll();
            itemsPanel.add(tableHeader);
            int count = 0;
            try {
                count = makeItemList();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            itemsPanel.setPreferredSize(new Dimension(965, 54*count + 60));
            revalidate();
            repaint();
        });


        if(isAdmin) {
            row.add(statusType);
            row.add(setStatusButton);
        }

        itemsPanel.add(row);


    }
}

class OrderList extends JDialog {
    private Order currentOrder;
    private JPanel itemsPanel;
    private JPanel tableHeader;
    public OrderList(JFrame owner, Order order){
        super(owner, "Lista przedmiotów");
        this.currentOrder=order;
        var ramka = BorderFactory.createEtchedBorder();

        itemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tableHeader = new JPanel();

        var prodLabel = new JLabel("Producent");
        prodLabel.setPreferredSize(new Dimension(100, 30));
        prodLabel.setBorder(ramka);

        var nameLabel = new JLabel("Nazwa");
        nameLabel.setPreferredSize(new Dimension(170,30));
        nameLabel.setBorder(ramka);
        var priceLabel = new JLabel("Cena");
        priceLabel.setPreferredSize(new Dimension(90,30));
        priceLabel.setBorder(ramka);

        tableHeader.add(prodLabel);
        tableHeader.add(nameLabel);
        tableHeader.add(priceLabel);
        tableHeader.setBorder(ramka);
        itemsPanel.add(tableHeader);

        int count = makeItemList();
        System.out.println(count);

        itemsPanel.setPreferredSize(new Dimension(470, 54*count + 60));
        add(itemsPanel,BorderLayout.NORTH);

        pack();
    }
    private int makeItemList(){
        for(int i=0;i<currentOrder.getProducts().size();i++){
            String[] row = {currentOrder.getItem(i).getProducer(), currentOrder.getItem(i).getName(), String.valueOf(currentOrder.getItem(i).getPrice())};
            System.out.println(row[0]);
            addRow(row);
        }
        return currentOrder.getProducts().size();
    }
    private void addRow(String[] data) {
        var row = new JPanel();
        var prod = new JLabel(data[0]);
        var ramka = BorderFactory.createEtchedBorder();
        prod.setPreferredSize(new Dimension(100, 30));
        prod.setBorder(ramka);
        var name = new JLabel(data[1]);
        name.setPreferredSize(new Dimension(170, 30));
        name.setBorder(ramka);
        var price = new JLabel(data[2]);
        price.setPreferredSize(new Dimension(90, 30));
        price.setBorder(ramka);
        row.add(prod);
        row.add(name);
        row.add(price);
        row.setBorder(ramka);

        itemsPanel.add(row);


    }
}