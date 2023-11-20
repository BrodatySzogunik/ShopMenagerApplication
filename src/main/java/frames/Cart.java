package frames;

import Structures.Cart.CartItem;
import Structures.DataBase.Products.Product.Product;
import frames.Helper.CartTableModel;
import frames.Helper.JTableButtonMouseListener;
import frames.Helper.JTableButtonRenderer;
import frames.Helper.ProductTableModel;
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

    private final CartService cartService;

    private Cart(){
        this.cartService = CartService.getInstance();
        this.cartSumLabel.setText(String.valueOf(0));

        this.cartService.addPropertyChangeListener(evt -> {
            this.cartSumLabel.setText(String.valueOf(this.cartService.getProductValue()));
            if(evt.getPropertyName().equals("removeItem")){
                this.updateCartTable();
            }
        });
        this.generateCartTable();
    }

    public static Cart getInstance(){
        if(instance == null){
            instance = new Cart();
        }
        return instance;
    }



    private void generateCartTable(){
        Vector<CartItem> cartItems = new Vector<>(this.cartService.getProductList());
        CartTableModel cartTableModel = new CartTableModel(cartItems);
        this.cartItemsTable.setModel(cartTableModel);
        cartItemsTable.getColumn("+").setCellRenderer(new JTableButtonRenderer());
        cartItemsTable.getColumn("-").setCellRenderer(new JTableButtonRenderer());
        cartItemsTable.getColumn("x").setCellRenderer(new JTableButtonRenderer());
        cartItemsTable.addMouseListener(new JTableButtonMouseListener(cartItemsTable));
    }

    public void updateCartTable(){
        Vector<CartItem> cartItems = new Vector<>(this.cartService.getProductList());
        CartTableModel cartTableModel = (CartTableModel)this.cartItemsTable.getModel();
        cartTableModel.updateData(cartItems);
        cartItemsTable.setSize(cartItemsTable.getWidth(), cartItemsTable.getRowCount()* cartItemsTable.getRowHeight()+1);
    }
}
