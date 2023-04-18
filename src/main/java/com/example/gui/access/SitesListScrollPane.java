package com.example.gui.access;

import com.example.filencrypt.ManageSafe;
import com.example.model.WebData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class SitesListScrollPane extends JScrollPane implements ListSelectionListener{

    private JList<String> websitesList;
    private boolean flag;
    private StringListener stringListener;

    public SitesListScrollPane() {
        //cambio la larghezza di questo JPanel
        Dimension dims = getPreferredSize();
        dims.width = 208;
        setPreferredSize(dims);


//        setViewportView(new JList<String>());
    }

    public boolean loadWebsites(ManageSafe ms) {
        if(!flag) {
            flag = true;
            DefaultListModel<String> model = new DefaultListModel<String>();
            for (WebData wb: ms.getData()) {
                model.addElement(wb.getWebsiteUrl());
            }
            websitesList = new JList<String>(model);
//            websitesList.setSelectionMode(0);
            websitesList.addListSelectionListener(this);
            setViewportView(websitesList);
            return true;
        }
//        remove(websitesList);
        setViewportView(null);
        flag = false;
        return false;
    }

    public void updateWebsites(ManageSafe ms) {
        DefaultListModel<String> model = new DefaultListModel<String>();
        for (WebData wb: ms.getData()) {
            model.addElement(wb.getWebsiteUrl());
        }
        websitesList = new JList<String>(model);
//        websitesList.setSelectionMode(0);
        websitesList.addListSelectionListener(this);
        setViewportView(websitesList);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }


    public List<String> getSelectedValues() {
        return websitesList.getSelectedValuesList();
    }

    public JList<String> getWebsitesList() {
        return websitesList;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(stringListener != null) {
            if(!e.getValueIsAdjusting())
                stringListener.textEmesso(websitesList.getSelectedValue());
//                if(!(websitesList.getSelectedIndices().length > 1)) {
//
//                } else {
//                    stringListener.textEmesso(websitesList.getSelectedValue());
//                }
        }
    }
}
