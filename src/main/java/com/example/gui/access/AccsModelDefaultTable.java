package com.example.gui.access;

import com.example.model.Acc;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AccsModelDefaultTable extends DefaultTableModel {

    private TableOnEditListener tableOnEditListener;

    public AccsModelDefaultTable(Object[][] objMultiArr, String[] columNames) {
        super(objMultiArr, columNames);
    }


    }
