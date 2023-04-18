package com.example.gui.login;

import com.example.filencrypt.ManageSafe;
import com.example.gui.access.AccessFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class LoginFrame extends JFrame {
    LoginPanel loginPanel;

    JLabel errorLabel;

    JLabel pwmLabel;

//    JLabel errorLabel;

    public LoginFrame(ManageSafe ms, AccessFrame accessFrame) {
        super("Login");
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                ClassLoader.getSystemResource("closech.png")));


        setLayout(new BorderLayout());

        loginPanel = new LoginPanel();
        loginPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setBorder(new EmptyBorder(0,0,10,0));
        pwmLabel = new JLabel("҉PWstrongbox҉");
        pwmLabel.setHorizontalAlignment(JLabel.CENTER);
        pwmLabel.setBorder(new EmptyBorder(0,0,0,0));
        pwmLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        add(pwmLabel, BorderLayout.PAGE_START);
        add(errorLabel, BorderLayout.PAGE_END);
        Frame frame = this;

        loginPanel.setTextListener(new ComunicatorListener() {
            @Override
            public void azione(String text) {
                try {
                    if(accessFrame.getManageSafe().checkPw(text)) {
                        ms.toDecrypt(); // decrypting data...
                        accessFrame.setVisible(true); // make AccessFrame visible
                        dispose(); //closing LoginFrame
                    } else {
                            errorLabel.setText("INVALID PW ");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame,
                            "Your data files are invalid or corrupted", "Bad error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(loginPanel, BorderLayout.CENTER);



        setSize(260, 140);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }

}
