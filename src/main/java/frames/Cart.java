package frames;

import Structures.Cart.CartItem;
import frames.Helper.CartTableModel;
import frames.Helper.JTableButtonMouseListener;
import frames.Helper.JTableButtonRenderer;
import services.CartService;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.Vector;

public class Cart {

    private static Cart instance;
    private JTable cartItemsTable;
    public JPanel panel1;
    public JButton generateBillButton;
    private JLabel cartSumLabel;
    private final CartService cartService;

    private Cart(){
        this.cartService = CartService.getInstance();
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
        cartItemsTable.addMouseListener(new JTableButtonMouseListener(cartItemsTable));
    }

    public void updateCartTable(){
        Vector<CartItem> cartItems = new Vector<>(this.cartService.getProductList());
        CartTableModel cartTableModel = (CartTableModel)this.cartItemsTable.getModel();
        cartTableModel.updateData(cartItems);
    }
}
