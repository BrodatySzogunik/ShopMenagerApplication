package frames;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class ProductList {
    public JPanel panel1;
    private JTable table1;
    private TableModel tableModel;
    private static ProductList instance = null ;

    private ProductList(){

//        table1.setModel();


        tableModel = new TableModel() {
            @Override
            public int getRowCount() {
                return 0;
            }

            @Override
            public int getColumnCount() {
                return 0;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return null;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return null;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return null;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            }

            @Override
            public void addTableModelListener(TableModelListener l) {

            }

            @Override
            public void removeTableModelListener(TableModelListener l) {

            }
        };
    };

    public static ProductList getInstance(){
        if(instance == null){
            instance = new ProductList();
        }
        return instance;
    }


}


//class myModel extends AbstractTableModel{
//
//}


