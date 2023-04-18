package com.example.gui.login;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private JLabel labelPassword;
    private JPasswordField fieldPassword;
    private JButton loginButton;
    private ComunicatorListener comunicatorListener;

    public LoginPanel() {
        setLayout(new GridBagLayout());

        labelPassword = new JLabel("PW:");
        fieldPassword = new JPasswordField(13);
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(60, 19));
        loginButton.setMargin(new Insets(0, 0,2,0));

        fieldPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comunicatorListener != null) {
                    if(!String.valueOf(fieldPassword.getPassword()).equals("")) {
                        comunicatorListener.azione(String.valueOf(fieldPassword.getPassword()));
                        fieldPassword.setText("");
                        fieldPassword.requestFocus();
                    }
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comunicatorListener != null) {
                    if(!String.valueOf(fieldPassword.getPassword()).equals("")) {
                        comunicatorListener.azione(String.valueOf(fieldPassword.getPassword()));
                        fieldPassword.setText("");
                        fieldPassword.requestFocus();
                    }
                }
            }
        });


        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.weightx = 0.01;
        gbc.weighty = 0.01;

        add(labelPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;

        gbc.weightx = 0.01;
        gbc.weighty = 0.01;

        add(fieldPassword, gbc);


        gbc.gridx = 2;
        gbc.gridy = 1;

        gbc.weightx = 0.01;
        gbc.weighty = 0.01;

        add(loginButton, gbc);

    }

    public void setTextListener(ComunicatorListener comunicatorListener) {
        this.comunicatorListener = comunicatorListener;
    }


}
