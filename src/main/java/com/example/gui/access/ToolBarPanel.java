package com.example.gui.access;

import com.example.model.WebData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBarPanel extends JPanel implements ActionListener{

    private JButton changePwButton;
    private JButton showWebsitesButton;
    private JButton addAccountButton;
    private JButton removeAccountButton;
    private JButton addUrl;

    private JButton removeUrl;
    private boolean flag;
    private NumListener numListener;

    public ToolBarPanel() {
//        setLayout(new FlowLayout(FlowLayout.LEFT));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        showWebsitesButton = new JButton("Websites");
        addUrl = new JButton("URL +");
        removeUrl = new JButton("URL -");
        addAccountButton = new JButton("ACC +");
        removeAccountButton = new JButton("ACC -");
        changePwButton = new JButton("Edit PW");

        showWebsitesButton.setMargin(new Insets(0, 0,0,0));
        showWebsitesButton.setPreferredSize(new Dimension(70, 20));

        addAccountButton.setFont(new Font("Default", Font.BOLD, 9));
        addAccountButton.setPreferredSize(new Dimension(32, 20));
        addAccountButton.setMargin(new Insets(1, 0,0,0));
        removeAccountButton.setFont(new Font("Default", Font.BOLD, 9));
        removeAccountButton.setPreferredSize(new Dimension(32, 20));
        removeAccountButton.setMargin(new Insets(1, 0,0,0));

        addUrl.setFont(new Font("Default", Font.BOLD, 9));
        addUrl.setPreferredSize(new Dimension(31, 20));
        addUrl.setMargin(new Insets(1, 0, 0, 0));
        removeUrl.setFont(new Font("Default", Font.BOLD, 9));
        removeUrl.setPreferredSize(new Dimension(31, 20));
        removeUrl.setMargin(new Insets(1, 0, 0, 0));

        changePwButton.setPreferredSize(new Dimension(70, 20));
        changePwButton.setMargin(new Insets(0, 0, 0, 0));



        showWebsitesButton.addActionListener(this);
        addUrl.addActionListener(this);
        removeUrl.addActionListener(this);
        addAccountButton.addActionListener(this);
        removeAccountButton.addActionListener(this);
        changePwButton.addActionListener(this); // lego il bottone al pannello

        addAccountButton.setEnabled(false);
        removeAccountButton.setEnabled(false);
        addUrl.setEnabled(false);
        removeUrl.setEnabled(false);

        add(showWebsitesButton);
        Dimension minSize = new Dimension(4, 0);
        Dimension prefSize = new Dimension(4, 0);
        Dimension maxSize = new Dimension(4, 0);
        add(new Box.Filler(minSize, prefSize, maxSize));
        add(addUrl);
        add(removeUrl);
        add(new Box.Filler(minSize, prefSize, maxSize));
        add(addAccountButton);
        add(removeAccountButton);
        add(Box.createGlue());
        add(changePwButton);


    }

    public JButton getAddUrl() {
        return addUrl;
    }

    public JButton getRemoveUrl() {
        return removeUrl;
    }

    public JButton getRemoveAccountButton() {
        return removeAccountButton;
    }

    public void onoffAddRemoveButtons() {
        if(!flag) {
            flag = true;
            addAccountButton.setEnabled(true);
            removeAccountButton.setEnabled(true);
            addUrl.setEnabled(true);
            removeUrl.setEnabled(true);
            return;
        }
        addAccountButton.setEnabled(false);
        removeAccountButton.setEnabled(false);
        addUrl.setEnabled(false);
        removeUrl.setEnabled(false);
        flag = false;
    }

    public void turnOnAddRemoveButtons() {
        addAccountButton.setEnabled(true);
        removeUrl.setEnabled(true);
    }

    public JButton getAddAccountButton() {
        return addAccountButton;
    }

    public void turnOffAddRemoveButtons() {
        addAccountButton.setEnabled(false);
        removeAccountButton.setEnabled(false);
        removeUrl.setEnabled(false);
    }

    public void setTextListener(NumListener numListener) {
        this.numListener = numListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(numListener != null) {
            JButton pressedButton = (JButton) e.getSource();

            if (pressedButton == showWebsitesButton) {
                numListener.numEmesso(1); // 1 = bottone Websites
            } else if (pressedButton == changePwButton) {
                numListener.numEmesso(2); // 2 = bottone Edit PW
            } else if (pressedButton == addAccountButton) {
                numListener.numEmesso(3); // 3 = bottone ADD ACC
            } else if (pressedButton == removeAccountButton) {
                numListener.numEmesso(4); // 4 = bottone REMOVE ACC
            } else if (pressedButton == addUrl) {
                numListener.numEmesso(5); // 5 = bottone ADD URL
            } else if (pressedButton == removeUrl) {
                numListener.numEmesso(6); // 6 = bottone REMOVE URL
            }
        }
    }
}
