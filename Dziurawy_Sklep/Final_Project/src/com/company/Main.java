package com.company;

import base_classes.Product;
import base_classes.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            browseFrame frame = null;
            try {
                frame = new browseFrame();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            frame.setTitle("Przeglądaj produkty");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setResizable(false);
        });
    }
}

class browseFrame extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private JPanel productPanel;
    private JPanel tableHeader;
    private JComboBox<String> prodCombo;
    private JComboBox<String> typeCombo;
    private JSlider priceLimitSlider;
    private LogOn logInDialog;
    private User user;
    private DatabaseConnector dc = new DatabaseConnector();
    private ArrayList<Product> items = dc.queryGet();
    private ArrayList<Integer> lastOne = new ArrayList<>();
    int counter=0;


    private Border ramka = BorderFactory.createEtchedBorder();

    browseFrame() throws ClassNotFoundException, SQLException {

        logInDialog = new LogOn(browseFrame.this);
        logInDialog.setLocationByPlatform(true);
        logInDialog.setVisible(true);
        user = logInDialog.getUser();

        setSize(WIDTH, HEIGHT);

        productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        var filterPanel = new JPanel();

        productPanel.setPreferredSize(new Dimension(700,600));
        tableHeader = new JPanel();

        var prodLabel = new JLabel("Producent");
        prodLabel.setPreferredSize(new Dimension(100, 30));
        prodLabel.setBorder(ramka);

        var nameLabel = new JLabel("Nazwa");
        nameLabel.setPreferredSize(new Dimension(170,30));
        nameLabel.setBorder(ramka);
        var portsLabel = new JLabel("Liczba portów");
        portsLabel.setPreferredSize(new Dimension(100,30));
        portsLabel.setBorder(ramka);
        var priceLabel = new JLabel("Cena");
        priceLabel.setPreferredSize(new Dimension(90,30));
        priceLabel.setBorder(ramka);
        tableHeader.add(prodLabel);
        tableHeader.add(nameLabel);
        tableHeader.add(portsLabel);
        tableHeader.add(priceLabel);
        tableHeader.setBorder(ramka);
        productPanel.add(tableHeader);

        int count = makeItemList();

        productPanel.setPreferredSize(new Dimension(700, count*54+60));
        var scroll = new JScrollPane(productPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.WEST);

        String[] producenci = dc.queryProducers();

        var kombosy = new JPanel(new GridLayout(2,2));
        var prodComLab = new JLabel("Producent:");
        prodCombo = new JComboBox<>();
        prodCombo.addItem("");
        for (String s : producenci) {
            prodCombo.addItem(s);
        }

        var typeComLab = new JLabel("Typ urządzenia:");
        typeCombo = new JComboBox<>();
        typeCombo.addItem("");
        typeCombo.addItem("Router");
        typeCombo.addItem("Switch");
        typeCombo.addItem("Hub");
        kombosy.add(prodComLab);
        kombosy.add(prodCombo);
        kombosy.add(typeComLab);
        kombosy.add(typeCombo);
        int priceMax = (int)dc.getMaxPrice()+1;

        var sliderLabel = new JLabel("Cena maksymalna:");

        priceLimitSlider = new JSlider(0, priceMax,priceMax);
        priceLimitSlider.setPaintTicks(true);
        priceLimitSlider.setPaintLabels(true);
        priceLimitSlider.setMajorTickSpacing(2000);
        priceLimitSlider.setMinorTickSpacing(500);

        var priceLimitDisplay = new JTextField(6);
        priceLimitDisplay.setEditable(false);

        ChangeListener listener = event -> {
          JSlider source = (JSlider) event.getSource();
          priceLimitDisplay.setText("" + source.getValue());
        };

        priceLimitSlider.addChangeListener(listener);

        var filter = new JButton("Filtruj");
        filter.addActionListener(new FilterProducts());

        var cartButton = new JButton("Do koszyka!");
        cartButton.addActionListener(event -> {
            CartDialog cartDialog = null;
            try {
                cartDialog = new CartDialog(browseFrame.this, user);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("To je error");
                e.printStackTrace();
            }
            cartDialog.setVisible(true);
        });

        var newItemButton = new JButton("Dodaj nowy przedmiot");
        newItemButton.addActionListener(event -> {
            AddEditItem addDialog = null;
            try {
                addDialog = new AddEditItem(browseFrame.this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            addDialog.setVisible(true);
            try {
                items=dc.queryGet();
                refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        var ordersButton = new JButton("Zamówienia");
        ordersButton.addActionListener(event -> {
            OrdersDialog ordersDialog = null;
            try {
                ordersDialog = new OrdersDialog(browseFrame.this, user);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            ordersDialog.setVisible(true);
        });

        filterPanel.add(kombosy);
        filterPanel.add(sliderLabel);
        filterPanel.add(priceLimitSlider);
        filterPanel.add(priceLimitDisplay);
        filterPanel.add(filter, BorderLayout.NORTH);
        if(logInDialog.isLoggedOn() && !logInDialog.isAdmin()) filterPanel.add(cartButton, BorderLayout.SOUTH);
        if(logInDialog.isLoggedOn() && logInDialog.isAdmin()) filterPanel.add(newItemButton, BorderLayout.SOUTH);
        if(logInDialog.isLoggedOn()) filterPanel.add(ordersButton);

        add(filterPanel);
    }

    private class FilterProducts implements ActionListener {
        public void actionPerformed(ActionEvent event){
            String product = String.valueOf(prodCombo.getSelectedItem());
            String type = String.valueOf(typeCombo.getSelectedItem());
            float limit = (float)priceLimitSlider.getValue();
            try {
                items = dc.queryGet(limit,dc.convertTypeStrInt(type),product);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                refresh();
            } catch (SQLException e) {
                System.out.println("Hello????");
                e.printStackTrace();
            }
        }
    }

    private void refresh() throws SQLException {
        productPanel.removeAll();
        productPanel.add(tableHeader);
        int count = makeItemList();
        productPanel.setPreferredSize(new Dimension(700, count*54+60));
        revalidate();
        repaint();
    }

    private int makeItemList() throws SQLException {
        if(items.size()>0){
            for(int i=0; i<items.size();i++){
                lastOne.add(items.get(i).getId());
                addRow(items.get(i).convToString());
                counter++;
            }
        } else {
            return 0;
        }


        return items.size();

    }

    private void addRow(String[] data){


        var row = new JPanel();

        var prod = new JLabel(data[0]);

        var ramka = BorderFactory.createEtchedBorder();
        prod.setPreferredSize(new Dimension(100,30));
        prod.setBorder(ramka);

        var name = new JLabel(data[1]);
        name.setPreferredSize(new Dimension(170,30));
        name.setBorder(ramka);

        var ports = new JLabel(data[2]);
        ports.setPreferredSize(new Dimension(100,30));
        ports.setBorder(ramka);

        var price = new JLabel(data[3]);
        price.setPreferredSize(new Dimension(90,30));
        price.setBorder(ramka);

        row.add(prod);
        row.add(name);
        row.add(ports);
        row.add(price);

        row.setBorder(ramka);


        var detailsButton = new JButton("Szczegóły");
        detailsButton.addActionListener(event ->{
            var detailsDialog = new Details(browseFrame.this, data[1], data[4]);
            detailsDialog.setLocationByPlatform(true);
            detailsDialog.setVisible(true);
        });

        var toCartButton = new JButton(new ImageIcon("cart.gif"));
        toCartButton.addActionListener(event ->{
        for(int i=0; i<items.size();i++){
            if(items.get(i).getName().equals(data[1]) && items.get(i).getDevice_desc().equals(data[4]) && items.get(i).getProducer().equals(data[0])){
                try {
                    System.out.println(items.get(i).getId());
                    System.out.println(user.getId());
                    dc.addToBasket(items.get(i).getId(), user.getId());
                    items=dc.queryGet();
                    refresh();
                    break;
                } catch (SQLException e) {
                    System.out.println("Nie klika :/");
                    e.printStackTrace();
                    break;
                }
            }
        }
        });

        var removeButton = new JButton("Usuń");
        removeButton.setName(String.valueOf(counter));
        removeButton.addActionListener(event->{
            try {
                dc.deleteFromDataBase(lastOne.get(Integer.valueOf(((JButton)event.getSource()).getName())));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            counter=0;
            lastOne = new ArrayList<>();
            try {
                items=dc.queryGet();
                refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        var editButton = new JButton("Edytuj");
        editButton.setName(String.valueOf(counter));
        editButton.addActionListener(event ->{
            AddEditItem editDialog = null;
            try {
                editDialog = new AddEditItem(browseFrame.this, data, lastOne.get(Integer.valueOf(((JButton)event.getSource()).getName())));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            editDialog.setVisible(true);
            try {
                items=dc.queryGet();
                refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        if(logInDialog.isLoggedOn() && !logInDialog.isAdmin()){
            row.add(detailsButton);
            row.add(toCartButton);
        }
        if(logInDialog.isLoggedOn() && logInDialog.isAdmin()){
            row.add(removeButton);
            row.add(editButton);
        }
        if(!logInDialog.isLoggedOn()){
            row.add(detailsButton);
        }
        productPanel.add(row);
    }

    public class Details extends JDialog{
        Details(JFrame owner, String prodName, String prodDesc){
            super(owner,"Szczegóły produktu " + prodName, true);
            var poleTekst = new JTextArea(8,40);
            poleTekst.setLineWrap(true);
            poleTekst.setEditable(false);
            poleTekst.setText(prodDesc);
            poleTekst.setBorder(ramka);
            add(poleTekst);

            var ok = new JButton("OK");
            ok.addActionListener(event-> setVisible(false));
            add(ok, BorderLayout.SOUTH);
            pack();
        }
    }
}
