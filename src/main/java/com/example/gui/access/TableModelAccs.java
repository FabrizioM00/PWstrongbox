//package com.example.gui.access;
//
//import com.example.model.Acc;
//
//import javax.swing.table.AbstractTableModel;
//import javax.swing.table.DefaultTableModel;
//import java.util.List;
//
//public class TableModelAccs extends AbstractTableModel {
//    private List<Acc> accs;
//
//    private String[] columnNames = {"Username", "Email", "Password"};
//
//    public TableModelAccs(List<Acc> accs) {
//        this.accs = accs;
//    }
//
//    public void setloadAccs(List<Acc> accs) {
//        this.accs = accs;
//    }
//
//    @Override
//    public String getColumnName(int column) {
//        return columnNames[column];
//    }
//
//    @Override
//    public int getRowCount() {
//        return accs.size();
//    }
//
//    @Override
//    public int getColumnCount() {
//        return 3;
//    }
//
//    @Override
//    public Object getValueAt(int rowIndex, int columnIndex) {
//        Acc acc = accs.get(rowIndex);
//
//        return switch (columnIndex) {
//            case 0 -> acc.getUsername();
//            case 1 -> acc.getEmail();
//            case 2 -> acc.getPw();
//            default -> null;
//        };
//    }
//
//}
