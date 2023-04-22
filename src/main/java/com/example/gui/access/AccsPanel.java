package com.example.gui.access;

import com.example.model.Acc;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.List;

public class AccsPanel extends JPanel implements ListSelectionListener, TableModelListener {

    private JTable table;
//    private TableModelAccs tableModelAccs;

    private NumListener numListener;

    private AccsModelDefaultTable defaultModel;

    private TableOnEditListener tableOnEditListener;


    public AccsPanel() {
        setLayout(new BorderLayout());


    }

    public void loadAccs(List<Acc> accs) {
        if (table != null)
            removeAll();

//        tableModelAccs = new TableModelAccs(accs);
//        tableModelAccs.setloadAccs(accs);

        Object[][] objMultiArr = new Object[accs.size()][3]; // Object[rows][columns]

        for (int i = 0; i < objMultiArr.length; i++) { // build Object[rows][columns]
            objMultiArr[i][0] = accs.get(i).getUsername();
            objMultiArr[i][1] = accs.get(i).getEmail();
            objMultiArr[i][2] = accs.get(i).getPw();
        }

        String[] columNames = new String[]{"Username", "Email", "Password"};

        this.defaultModel = new AccsModelDefaultTable(objMultiArr, columNames);
        table = new JTable(defaultModel);
        table.getModel().addTableModelListener(this);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        Dimension dim = table.getTableHeader().getPreferredSize();
        dim.height = 30;
        table.getTableHeader().setPreferredSize(dim);

        table.getSelectionModel().addListSelectionListener(this);
        table.getTableHeader().setReorderingAllowed(false);

        // rende la tabella copiabile solo per cella e non per riga
        table.getActionMap().put("copy", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(table.getSelectedRows().length == 0)) {
                    String cellValue = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                    StringSelection stringSelection = new StringSelection(cellValue);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        revalidate();
    }

    public void toEmptyPanel() {
        removeAll();
        revalidate();
        repaint();
    }

    public void setNumListener(NumListener numListener) {
        this.numListener = numListener;
    }


    public int[] getSelectedRows() {
        return table.getSelectedRows();
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (numListener != null) {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                numListener.numEmesso(table.getSelectedRow());
            }
        }
    }

    public void setTableOnEditListener(TableOnEditListener tableOnEditListener) {
        this.tableOnEditListener = tableOnEditListener;
    }

    public void aggiungiRigaVuota() {
        defaultModel.addRow(new Object[]{"","",""});
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (tableOnEditListener != null) {
            String[] strArr = new String[] {(String) table.getValueAt(e.getLastRow(), 0),
                    (String) table.getValueAt(e.getLastRow(), 1), (String) table.getValueAt(e.getLastRow(), 2)};
            tableOnEditListener.changeEvent(e.getLastRow(), strArr);
        }
    }
}
