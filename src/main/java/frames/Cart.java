package frames;

import Structures.Cart.CartItem;
import frames.Helper.CartTableModel;
import frames.Helper.JTableButtonMouseListener;
import frames.Helper.JTableButtonRenderer;
import services.CartService;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import java.util.stream.Stream;

public class Cart {

    private static Cart instance;
    private JTable cartItemsTable;
    public JPanel panel1;
    public JButton generateBillButton;
    private JLabel cartSumLabel;

    private PropertyChangeSupport propertyChangeSupport;

    Stream<Double> cartValueStream ;
    private final CartService cartService;

    private Cart(){
        this.cartService = CartService.getInstance();
        this.cartValueStream = Stream.generate(() -> this.cartService.getProductValue());
//        this.
        this.generateCartTable();
    }

    public static Cart getInstance(){
        if(instance == null){
            instance = new Cart();
        }
        return instance;
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    private void updateSumOfItems(){
        this.cartSumLabel.setText();
    }

    private void generateCartTable(){
        Vector<CartItem> cartItems = new Vector<>(this.cartService.getProductList());
        CartTableModel cartTableModel = new CartTableModel(cartItems);
        this.cartItemsTable.setModel(cartTableModel);
        cartItemsTable.getColumn("+").setCellRenderer(new JTableButtonRenderer());
        cartItemsTable.getColumn("-").setCellRenderer(new JTableButtonRenderer());
        cartItemsTable.addMouseListener(new JTableButtonMouseListener(cartItemsTable));
    }

    public void updateCartTable(){
        Vector<CartItem> cartItems = new Vector<>(this.cartService.getProductList());
        CartTableModel cartTableModel = (CartTableModel)this.cartItemsTable.getModel();
        cartTableModel.updateData(cartItems);
    }
}
