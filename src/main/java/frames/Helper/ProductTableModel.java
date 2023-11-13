package frames.Helper;

import Structures.Cart.CartItem;
import Structures.DataBase.Products.Product.Product;
import services.CartService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class ProductTableModel extends AbstractTableModel {
    private Vector<Product> products;
    private final String[] COLUMN_NAMES = new String[]{"id","Nazwa","Cena Sprzedarzy","Dostępna ilość","Koszyk"};
    private final Class<?>[] COLUMN_TYPES = new Class<?>[] {Integer.class, String.class, Double.class, Double.class,  JButton.class};

    public ProductTableModel(Vector<Product> data){
        this.products = data;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    @Override
    public int getRowCount() {
        return this.products.size();
    }
    public void updateData(Vector<Product> data){
        this.products = data;
        this.fireTableRowsUpdated(0,products.size()-1);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    @Override public Object getValueAt(final int rowIndex, final int columnIndex) {
        /*Adding components*/
        Product selectedProduct = this.products.get(rowIndex);
        switch (columnIndex) {
            case 0: return selectedProduct.getId();
            case 1: return selectedProduct.getName();
            case 2: return selectedProduct.getSellPrice();
            case 3: return selectedProduct.getQuantity();
            case 4: final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
                button.addActionListener(e ->{
                    CartService.getInstance().addProductToCart(selectedProduct);
                });
                return button;
            default: return "Error";
        }
    }
}
