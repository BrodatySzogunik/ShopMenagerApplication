package frames.Helper;

import Structures.Cart.CartItem;
import Structures.DataBase.Products.Category.Category;
import Structures.DataBase.Products.Product.Product;
import Structures.Intercars.Invoice.Invoice;
import Structures.Intercars.Invoice.poz;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class ImportInvoiceTableModel extends AbstractTableModel {
    private Vector<poz> itemList;
    private Vector<Category> categoryList;
    private Vector<Category> availableCategories;
    private final String[] COLUMN_NAMES = new String[]{"id","Nazwa","Cena Zakupu","Cena Sprzedarzy","Ilość","Kategoria"};
    private final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class, String.class, Double.class, Double.class,  Integer.class, String.class};

    public  ImportInvoiceTableModel(Vector<poz> itemList){
        this.itemList = itemList;
    }

    public void updateData(Vector<poz> itemList, Vector<Category> availableCategories){
        this.itemList = itemList;
        this.categoryList = new Vector<>();
        this.availableCategories = availableCategories;
        for(poz ignored : this.itemList){
            this.categoryList.add(new Category());
        }
        this.fireTableRowsUpdated(0,itemList.size()-1);
    }


    @Override
    public int getRowCount() {
        return this.itemList.size();
    }

    @Override
    public int getColumnCount() {
        return this.COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        poz selectedProduct = this.itemList.get(rowIndex);

        switch (columnIndex){
            case 0: return selectedProduct.tow_kod;
            case 1: return selectedProduct.nazwa;
            case 2: return selectedProduct.cena;
            case 3: return selectedProduct.cenadet;
            case 4: return selectedProduct.ilosc;
            case 5: return categoryList.get(rowIndex).getId();
            default: return "Error";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        poz selectedProduct = this.itemList.get(rowIndex);
        switch (columnIndex){
            case 3: selectedProduct.cenadet = (double) aValue;
            case 5: categoryList.get(rowIndex).setId((Long) aValue);
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        switch (columnIndex){
            case 3:
            case 5: return true;
            default: return false;
        }
    }

    public Vector<poz> getValues(){
        return this.itemList;
    }

    public Vector<Category> getCategories(){
        return this.categoryList;
    }

}
