package com.company;

import base_classes.Product;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

class AddEditItem extends JDialog {
    private JTextField itemProd;
    private JTextField itemName;
    private JTextField itemPorts;
    private JTextField itemPrice;
    private JTextArea itemDesc;
    private JComboBox<String> itemType;
    private JButton cancel;
    private JButton done;
    private ArrayList<Product> items = new ArrayList<>();
    private DatabaseConnector dc = new DatabaseConnector();
    private ArrayList<Integer> willItWorkAgain = new ArrayList<>();
    private int counter;
    private int item_id;

    AddEditItem(JFrame owner, String[] data, int item_id) throws ClassNotFoundException, SQLException {
        super(owner, "Edycja przedmiotu", true);
        this.item_id=item_id;
        var panel = new JPanel(new GridLayout(7,2));
        items = dc.queryGet();
        itemProd = new JTextField(data[0]);
        itemName = new JTextField(data[1]);
        itemPorts = new JTextField(data[2]);
        itemPrice = new JTextField(data[3]);
        itemDesc = new JTextArea(data[4],4,40);
        itemDesc.setLineWrap(true);
        itemType = new JComboBox<>();
        itemType.addItem("Router");
        itemType.addItem("Switch");
        itemType.addItem("Hub");
        
        cancel = new JButton("Anuluj");
        cancel.addActionListener(event -> setVisible(false));
        done = new JButton("Edytuj");
        done.addActionListener(event -> {
            String prod = itemProd.getText();
            String name = itemName.getText();
            String ports = itemPorts.getText();
            String price = itemPrice.getText();
            String desc = itemDesc.getText();
            try {
                dc.updateItemInDatabase(item_id, prod, name, Integer.valueOf(ports), Float.valueOf(price), desc, String.valueOf(itemType.getSelectedItem()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        panel.add(new JLabel("Producent: "));
        panel.add(itemProd);
        panel.add(new JLabel("Nazwa: "));
        panel.add(itemName);
        panel.add(new JLabel("Ilosc portow: "));
        panel.add(itemPorts);
        panel.add(new JLabel("Cena: "));
        panel.add(itemPrice);
        panel.add(new JLabel("Opis: "));
        panel.add(itemDesc);
        panel.add(new JLabel("Typ urządzenia (PAMIĘTAJ ŻEBY ZAWSZE USTAWIć!!!): "));
        panel.add(itemType);
        panel.add(cancel);
        panel.add(done);
        add(panel);
        pack();
    }
    AddEditItem(JFrame owner) throws ClassNotFoundException {
        super(owner, "Dodawanie przedmiotu", true);
        var panel = new JPanel(new GridLayout(7,2));

        itemProd = new JTextField();
        itemName = new JTextField();
        itemPorts = new JTextField();
        itemPrice = new JTextField();
        itemDesc = new JTextArea(4,40);
        itemDesc.setLineWrap(true);
        itemType = new JComboBox<>();
        itemType.addItem("Router");
        itemType.addItem("Switch");
        itemType.addItem("Hub");
        cancel = new JButton("Anuluj");
        cancel.addActionListener(event -> setVisible(false));
        done = new JButton("Dodaj");
        done.addActionListener(event -> {
            String prod = itemProd.getText();   
            String name = itemName.getText();
            String ports = itemPorts.getText();
            String price = itemPrice.getText();
            String desc = itemDesc.getText();
            try {
                dc.addNewItem(prod,name,ports,price,desc,(String)itemType.getSelectedItem());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setVisible(false);
        });

        panel.add(new JLabel("Producent: "));
        panel.add(itemProd);
        panel.add(new JLabel("Nazwa: "));
        panel.add(itemName);
        panel.add(new JLabel("Ilosc portow: "));
        panel.add(itemPorts);
        panel.add(new JLabel("Cena: "));
        panel.add(itemPrice);
        panel.add(new JLabel("Opis: "));
        panel.add(itemDesc);
        panel.add(new JLabel("Typ urządzenia (PAMIĘTAJ ŻEBY ZAWSZE USTAWIć!!!): "));
        panel.add(itemType);
        panel.add(cancel);
        panel.add(done);
        add(panel);
        pack();
    }

}
