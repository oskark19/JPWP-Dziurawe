package com.company;

import base_classes.Basket;
import base_classes.Product;
import base_classes.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDialog extends JDialog {
    private DatabaseConnector dc = new DatabaseConnector();
    private User user;
    private Basket basket;
    private JPanel itemsPanel;
    private JPanel tableHeader;
    private TextField names;
    private TextArea address;
    private ArrayList<Integer> plzhelp = new ArrayList<>();
    int counter=0;

    public CartDialog(JFrame owner, User user) throws ClassNotFoundException, SQLException {
        super(owner, "Koszyk");
        var ramka = BorderFactory.createEtchedBorder();
        this.user=user;
        this.basket=dc.getBasketProducts(user.getId());
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

        var ok = new JButton("Złóż zamówienie");
        ok.addActionListener(event->{
            try {
                dc.order(basket, names.getText(), address.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                basket = dc.getBasketProducts(user.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                makeItemList();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        var nok = new JButton("Wróć do zakupów");
        nok.addActionListener(event-> setVisible(false));

        names = new TextField(40);
        address = new TextArea(3,40);

        var form = new JPanel();

        float suma = dc.sumPriceBasket(basket);

        form.setLayout(new GridLayout(4,2 ));
        form.setPreferredSize(new Dimension(470, 100));
        form.add(new JLabel("Suma:"));
        form.add(new JLabel(String.valueOf(suma)));
        form.add(new JLabel("Imię i nazwisko:"));
        form.add(names);
        form.add(new JLabel("Adres: "));
        form.add(address);
        form.add(nok);
        form.add(ok);
        add(form,BorderLayout.SOUTH);
        pack();
    }

    private int makeItemList() throws SQLException, ClassNotFoundException {
        basket = dc.getBasketProducts(user.getId());
        String[] build = new String[3];
        if(basket.getProducts().size()>0){
            for(int i=0; i<basket.getProducts().size();i++) {
                build[0] = basket.getProducts().get(i).getProducer();
                build[1] = basket.getProducts().get(i).getName();
                build[2] = String.valueOf(basket.getProducts().get(i).getPrice());
                plzhelp.add(basket.getId()[i]);
                addRow(build);
                counter++;
            }
        } else{
            return 0;
        }

         return basket.getProducts().size();
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


        var deleteButton = new JButton("Usuń");
        deleteButton.setName(String.valueOf(counter));
        deleteButton.addActionListener(event -> {

            try {
                dc.deleteFromBasket(plzhelp.get(Integer.valueOf(((JButton)event.getSource()).getName())), user.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            itemsPanel.removeAll();
            itemsPanel.add(tableHeader);
            int count = 0;
            counter=0;
            plzhelp = new ArrayList<Integer>();
            try {
                count = makeItemList();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            itemsPanel.setPreferredSize(new Dimension(470, 54*count + 60));
            revalidate();
            repaint();
        });

        row.add(deleteButton);

        itemsPanel.add(row);


    }
}
