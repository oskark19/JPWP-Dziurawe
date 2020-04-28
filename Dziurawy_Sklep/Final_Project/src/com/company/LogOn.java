package com.company;

import base_classes.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class LogOn extends JDialog {
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JButton tryLogIn;
    private JButton guestLogIn;
    private DatabaseConnector dc = new DatabaseConnector();
    private User user;

    private boolean loggedOn = false;
    private boolean isAdmin = false;
    public LogOn(JFrame owner) throws ClassNotFoundException {
        super(owner, "Zaloguj się", true);
        var panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));

        panel.add(new JLabel("Login:"));

        usernameInput = new JTextField("Nazwa użytkownika",25);
        passwordInput = new JPasswordField("Hasło", 25);

        panel.add(usernameInput);
        panel.add(new JLabel("Hasło:"));
        panel.add(passwordInput);

        tryLogIn = new JButton("Zaloguj");
        tryLogIn.addActionListener(event -> {
            try {
                checkLogIn();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        guestLogIn = new JButton("Wejdź jako gość");
        guestLogIn.addActionListener(event -> {
            loggedOn = false;
            setVisible(false);
        });
        panel.add(guestLogIn);
        panel.add(tryLogIn);
        add(panel);
        pack();
        this.getRootPane().setDefaultButton(tryLogIn);
    }

    private void checkLogIn() throws SQLException, ClassNotFoundException {
        String username = usernameInput.getText();
        String password = String.valueOf(passwordInput.getPassword());
        user = dc.logIn(username,password);
        System.out.println(user.getCredentails());
        if (user.isLogged()){
            switch (user.getCredentails()){
                case 1:
                    loggedOn=true;
                    isAdmin=false;
                    setVisible(false);
                    return;
                case 2:
                    loggedOn=true;
                    isAdmin=true;
                    setVisible(false);
                    return;
            }
        }
        var okienko = new JOptionPane("Logowanie nie powiodło się!", ERROR_MESSAGE);
        okienko.showMessageDialog(this, "Logowanie nie powiodło się!", "Błąd!", ERROR_MESSAGE);

    }

    public boolean isLoggedOn() {
        return loggedOn;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
