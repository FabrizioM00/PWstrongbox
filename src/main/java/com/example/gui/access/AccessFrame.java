package com.example.gui.access;

import com.example.filencrypt.ManageSafe;
import com.example.model.Acc;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AccessFrame extends JFrame {

    private ManageSafe manageSafe;

    private ToolBarPanel toolBarPanel;

    private SitesListScrollPane sitesListScrollPane;

    private AccsPanel accsPanel;

    private String selectedWebsite;
    private int accRow;

    public AccessFrame(ManageSafe manageSafe) {
        super("Strongbox");
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                ClassLoader.getSystemResource("opench.png")));

        this.manageSafe = manageSafe;
        setLayout(new BorderLayout());


        toolBarPanel = new ToolBarPanel();
        toolBarPanel.setBorder(new EmptyBorder(4, 3, 4, 3));
        sitesListScrollPane = new SitesListScrollPane();
        accsPanel = new AccsPanel();
        add(sitesListScrollPane, BorderLayout.LINE_START);
        add(accsPanel, BorderLayout.CENTER);


        Frame frame = this;
        boolean flag = true;
        // quando qualche bottone viene premuto dalla barra degli strumenti
        toolBarPanel.setTextListener(new NumListener() {
            @Override
            public void numEmesso(int num) {
                if(num == 1) { // 1 = bottone Websites
                    try {
                        if(manageSafe.getData().isEmpty()) {
                            toolBarPanel.getAddUrl().setEnabled(sitesListScrollPane.loadWebsites(manageSafe));

                        } else {
                            if(!sitesListScrollPane.loadWebsites(manageSafe)) { // in chiusura del bottone Websites avviene questo (e non in apertura)
                                selectedWebsite = null;
                                toolBarPanel.turnOffAddRemoveButtons();
                                toolBarPanel.getAddUrl().setEnabled(false);
                                accsPanel.toEmptyPanel();
                            } else {
                                toolBarPanel.getAddUrl().setEnabled(true);
                            }

                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame,
                                "Your data is invalid",
                                "Data erorr",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (num == 2) { // 2 = bottone Edit PW
                    String newPw = JOptionPane.showInputDialog(frame, "New password:", "Change Strongbox password", JOptionPane.QUESTION_MESSAGE);
                    try {
                        if(newPw != null) {
                            if(!(newPw.length() > 3)) {
                                JOptionPane.showMessageDialog(frame,
                                        "New password must be at least 4 characters long",
                                        "Warning",
                                        JOptionPane.WARNING_MESSAGE);
                            } else {
                                manageSafe.toEncrypt(manageSafe.genSKFromPwd(newPw));
                                newPw = null;
                                JOptionPane.showMessageDialog(frame, "Password has been changed!");
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (num == 3) { // 3 = bottone ADD ACC
                    // AGGIUNGI ACCOUNT AL SITO SELEZIONATO
                    if(selectedWebsite != null) {

                        accsPanel.aggiungiRigaVuota();

                        // AGGIUNGERE UN ACCOUNT TRAMITE POPUP
//                        JTextField username = new JTextField();
//                        username.addAncestorListener(new AncestorListener() {
//                            @Override
//                            public void ancestorAdded(AncestorEvent e) {
//                                final AncestorListener al = this;
//                                SwingUtilities.invokeLater(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//                                        final JComponent component = e.getComponent();
//                                        component.requestFocusInWindow();
//                                        component.removeAncestorListener(al);
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void ancestorRemoved(AncestorEvent event) {
//
//                            }
//
//                            @Override
//                            public void ancestorMoved(AncestorEvent event) {
//
//                            }
//                        });
//                        JTextField email = new JTextField();
//                        JTextField password = new JTextField();
//                        final JComponent[] inputs = new JComponent[] {
//                                new JLabel("Username:"),
//                                username,
//                                new JLabel("Email:"),
//                                email,
//                                new JLabel("Password:"),
//                                password
//                        };
//
//                        Object[] options = {"Add",
//                                "Cancel"};
//                        int result = JOptionPane.showOptionDialog(frame,
//                                inputs,
//                                "Add account to " + selectedWebsite,
//                                JOptionPane.YES_NO_OPTION,
//                                JOptionPane.QUESTION_MESSAGE,
//                                null,     //do not use a custom Icon
//                                options,  //the titles of buttons
//                                options[0]); //default button title
//                        if (result == JOptionPane.YES_OPTION) {
//                            if(!password.getText().equals("")) {
//                                try {
//                                    manageSafe.addAccToSite(selectedWebsite, username.getText(), email.getText(), password.getText());
//                                    accsPanel.loadAccs(manageSafe.getAccsByWebsiteUrl(selectedWebsite));
//                                    toolBarPanel.getRemoveAccountButton().setEnabled(false);
//                                } catch (Exception e) {
//                                    JOptionPane.showMessageDialog(frame,
//                                            "FAILED TO SAVE ADDED ACC",
//                                            "BAD ERROR",
//                                            JOptionPane.WARNING_MESSAGE);
//                                }
//                            } else {
//                                JOptionPane.showMessageDialog(frame,
//                                        "to enter a new account the password field cannot be empty",
//                                        "Warning",
//                                        JOptionPane.WARNING_MESSAGE);
//                            }
//                        }
                    }
                } else if (num == 4) { // 4 = bottone REMOVE ACC
                    // RIMUOVI ACCOUNT AL SITO SELEZIONATO
                    if(selectedWebsite != null) {
                        try {
                            manageSafe.removeAccFromSite(accRow, selectedWebsite);
                            accsPanel.loadAccs(manageSafe.getAccsByWebsiteUrl(selectedWebsite));
                            toolBarPanel.getRemoveAccountButton().setEnabled(false);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(frame,
                                    "FAILED TO SAVE THE REMOVEED ACC",
                                    "BAD ERROR",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else if(num == 5) { // 5 = bottone ADD URL
                    String newWebsite = JOptionPane.showInputDialog(frame, "", "New Website URL", JOptionPane.QUESTION_MESSAGE);
                    if(newWebsite != null) {
                        if(!newWebsite.equals("")) {
                            if(!newWebsite.contains("https://") && !newWebsite.contains("http://"))
                                newWebsite = "https://" + newWebsite;
                            try {
                                if(manageSafe.isValidURL(newWebsite)){
                                    int index = manageSafe.transformURLAndAddToList(newWebsite);
                                    sitesListScrollPane.updateWebsites(manageSafe);
                                    toolBarPanel.turnOffAddRemoveButtons();
                                    accsPanel.toEmptyPanel();
                                    sitesListScrollPane.getWebsitesList().setSelectedIndex(index);
                                } else {
                                    JOptionPane.showMessageDialog(frame,
                                            "Invalid URL",
                                            "Warning",
                                            JOptionPane.WARNING_MESSAGE);
                                }

                            } catch (URISyntaxException | IllegalStateException | IllegalArgumentException e) {
                                JOptionPane.showMessageDialog(frame,
                                        "Invalid URL",
                                        "Warning",
                                        JOptionPane.WARNING_MESSAGE);
                            } catch (NoSuchPaddingException | IOException | NoSuchAlgorithmException |
                                     InvalidKeyException | InvalidKeySpecException e1) {
                                JOptionPane.showMessageDialog(frame,
                                        "FAILED TO SAVE THE URL",
                                        "BAD ERROR",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "URL field can't be empty",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else if (num == 6) { // 6 = bottone REMOVE URL
                    if(sitesListScrollPane.getSelectedValues().size() < 2 ) {
                        int n = JOptionPane.showConfirmDialog(frame, "are you sure you want to DELETE "+ selectedWebsite.toUpperCase() +" with all related accounts?", "Delete website", JOptionPane.YES_NO_OPTION);

                        if(n == 0 ) {
                            try {
                                manageSafe.removeWebsiteFromList(selectedWebsite);
                                sitesListScrollPane.updateWebsites(manageSafe);
                                selectedWebsite = null;
                                toolBarPanel.turnOffAddRemoveButtons();
                                accsPanel.toEmptyPanel();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(frame,
                                        "FAILED TO SAVE THE REMOVED URL",
                                        "BAD ERROR",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } else {
                        // delete all selected websites
                        int n = JOptionPane.showConfirmDialog(frame, "are you sure you want to DELETE ALL SELECTED websites with all related accounts?", "Delete websites", JOptionPane.YES_NO_OPTION);

                        if(n == 0 ) {
                            try {
                                manageSafe.removeWebsitesFromList(sitesListScrollPane.getSelectedValues());
                                sitesListScrollPane.updateWebsites(manageSafe);
                                selectedWebsite = null;
                                toolBarPanel.turnOffAddRemoveButtons();
                                accsPanel.toEmptyPanel();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(frame,
                                        "FAILED TO SAVE THE REMOVED URLS",
                                        "BAD ERROR",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }

                    // nuclear delete popup
//                    String confirmSite = JOptionPane.showInputDialog(frame, "To delete the " + selectedWebsite.toUpperCase() + " site \nwith all its related accounts type \nthe domain name and press OK", "Delete website", JOptionPane.QUESTION_MESSAGE);
//                    if(confirmSite != null) {
//                            if(!confirmSite.equals("") && confirmSite.equals(selectedWebsite)) {
//
//                            } else {
//                                JOptionPane.showMessageDialog(frame,
//                                        "You did not enter the domain name correctly ");
//                            }
//                    }
                }
            }
        });

        // quando viene selezionato qualche sito dalla lista dei siti
        sitesListScrollPane.setStringListener(new StringListener() {
            @Override
            public void textEmesso(String text) {
                selectedWebsite = text;
                if(sitesListScrollPane.getSelectedValues().isEmpty()) {
                    toolBarPanel.turnOffAddRemoveButtons();
                    accsPanel.toEmptyPanel();
                }
                if(selectedWebsite != null) { // entra qui solo quando qualche URL viene selezionato dalla sitesList
                    toolBarPanel.turnOnAddRemoveButtons();
                    toolBarPanel.getRemoveAccountButton().setEnabled(false);
                    accsPanel.loadAccs(manageSafe.getAccsByWebsiteUrl(text));
                }
                if(sitesListScrollPane.getSelectedValues().size() > 1) {
                    toolBarPanel.getAddAccountButton().setEnabled(false);
                    accsPanel.toEmptyPanel();
                }

//                accsPanel.loadAccs(manageSafe.getAccsByWebsiteUrl(text));
            }
        });

        accsPanel.setNumListener(new NumListener() {
            @Override
            public void numEmesso(int num) {
                accRow = num;
                toolBarPanel.getRemoveAccountButton().setEnabled(accsPanel.getSelectedRows().length == 1);

            }
        });


        accsPanel.setTableOnEditListener(new TableOnEditListener() {
            @Override
            public void changeEvent(int row, String[] values) {
                try {
                    if(row >= manageSafe.getAccsByWebsiteUrl(selectedWebsite).size()) {
                        manageSafe.getAccsByWebsiteUrl(selectedWebsite).add(new Acc(values[0], values[1], values[2]));
                    } else {
                        manageSafe.getAccsByWebsiteUrl(selectedWebsite).set(row, new Acc(values[0], values[1], values[2]));
                    }
                    manageSafe.setNewDataIntoJsonStr(manageSafe.getData());
                    manageSafe.toEncrypt(manageSafe.getSk());
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame,
                            "FAILED TO SAVE THE ADDED ACCOUNT",
                            "BAD ERROR",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        add(toolBarPanel, BorderLayout.PAGE_START);

        // signature fm
        // make icon
        URL iconURL = ClassLoader.getSystemResource("kyactus.png");
        ImageIcon icon = new ImageIcon(iconURL);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);  // transform it back
        // make icon
        JLabel fmSignature = new JLabel("simple project by fabrizio matera", SwingConstants.RIGHT);
        fmSignature.setHorizontalTextPosition(SwingConstants.LEFT); // move icon to right side of label
        fmSignature.setIcon(icon);
        fmSignature.setFont(new Font("Monospaced", Font.ITALIC, 10));
        fmSignature.setBorder(new EmptyBorder(2,2,2,2));
        add(fmSignature, BorderLayout.PAGE_END);
        // signature fm

        setSize(800, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(false);
    }

    public ManageSafe getManageSafe() {
        return manageSafe;
    }


}
