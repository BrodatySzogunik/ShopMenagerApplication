package frames.Helper;

import Structures.Cart.CartItem;
import Structures.DataBase.Products.Product.Product;
import services.CartService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class CartTableModel extends AbstractTableModel {
    private Vector<CartItem> cartItems;
    private final String[] COLUMN_NAMES = new String[]{"id","Nazwa","Cena Sprzedarzy","Ilość","+","-","x"};
    private final Class<?>[] COLUMN_TYPES = new Class<?>[] {Integer.class, String.class, Double.class, Integer.class,  JButton.class, JButton.class};

    public CartTableModel(Vector<CartItem> data){
        this.cartItems = data;
    }

    public void updateData(Vector<CartItem> data){
        this.cartItems = data;
        this.fireTableRowsUpdated(0,cartItems.size()-1);
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    @Override
    public int getRowCount() {
        return this.cartItems.size();
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
        CartItem selectedProduct = this.cartItems.get(rowIndex);
        switch (columnIndex) {
            case 0: return selectedProduct.getProduct().getId();
            case 1: return selectedProduct.getProduct().getName();
            case 2: return selectedProduct.getProduct().getSellPrice();
            case 3: return selectedProduct.getAmount();
            case 4: final JButton button1 = new JButton(COLUMN_NAMES[columnIndex]);
                button1.addActionListener(e ->{
                    CartService.getInstance().increaseProductAmount(rowIndex);
                    this.fireTableDataChanged();
                });
                return button1;
            case 5: final JButton button2 = new JButton(COLUMN_NAMES[columnIndex]);
                button2.addActionListener(e ->{
                    CartService.getInstance().decreaseProductAmount(rowIndex);
                    this.fireTableDataChanged();
                });
                return button2;
            case 6: final JButton button3 = new JButton(COLUMN_NAMES[columnIndex]);
                button3.addActionListener(e ->{
                    CartService.getInstance().removeProductFromCart(rowIndex);
                    this.fireTableDataChanged();
                });
                return button3;
            default: return "Error";
        }
    }
}
