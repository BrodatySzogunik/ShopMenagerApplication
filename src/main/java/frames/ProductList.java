package frames;

import Structures.DataBase.Products.Product.Product;
import services.CartService;
import services.DBController;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class ProductList {
    public JPanel panel1;
    private JTable table1;
    private JButton searchButton;
    private JTextField searchTextField;
    private TableModel tableModel;
    private DBController dbController;
    private static ProductList instance = null ;

    private ProductList(){

        dbController = DBController.getInstance();

//        TODO: remove before final verision
        Product product = new Product();
        product.setName("chłodnica honda accord");
        dbController.insertIntoTable(product);

        this.setBasicTable();


    };

    public static ProductList getInstance(){
        if(instance == null){
            instance = new ProductList();
        }
        return instance;
    }

    private void setBasicTable(){
        Vector<Product> tableValues = new Vector<>() ;

        for(Product p : this.dbController.getEntities(Product.class)){
            tableValues.addElement(p);
        }

        tableModel = new ProductTableModel(tableValues);


        table1.setModel(tableModel);
        table1.getColumn("Koszyk").setCellRenderer(new JTableButtonRenderer());
        table1.addMouseListener(new JTableButtonMouseListener(table1));
    }


}

class ProductTableModel extends AbstractTableModel{
    private Vector<Product> products;
    private final String[] COLUMN_NAMES = new String[]{"id","Nazwa","Cena Kupna","Cena Sprzedarzy","Koszyk"};
    private final Class<?>[] COLUMN_TYPES = new Class<?>[] {Integer.class, String.class, Double.class, Double.class,  JButton.class};

    ProductTableModel(Vector<Product> data){
        this.products = data;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    @Override
    public int getRowCount() {
        return 1;
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
            case 3: return selectedProduct.getBuyPrice();
            case 4: final JButton button = new JButton(COLUMN_NAMES[columnIndex]);
                button.addActionListener(e ->{
                    CartService.getInstance().addProductToCart(selectedProduct);
                });
                return button;
            default: return "Error";
        }
    }
}

class JTableButtonRenderer implements TableCellRenderer {
    @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = (JButton)value;
        return button;
    }
}

 class JTableButtonMouseListener extends MouseAdapter {
    private final JTable table;

    public JTableButtonMouseListener(JTable table) {
        this.table = table;
    }

    public void mouseClicked(MouseEvent e) {
        int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
        int row    = e.getY()/table.getRowHeight(); //get the row of the button

        /*Checking the row or column is valid or not*/
        if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
            Object value = table.getValueAt(row, column);
            if (value instanceof JButton) {
                /*perform a click event*/
                ((JButton)value).doClick();
            }
        }
    }
}
