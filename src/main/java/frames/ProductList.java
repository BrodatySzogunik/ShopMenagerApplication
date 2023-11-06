package frames;

import Structures.DataBase.Products.Category.Category;
import Structures.DataBase.Products.OemToProduct.OemToProduct;
import Structures.DataBase.Products.Product.Product;
import frames.Helper.CategoryComboBoxModel;
import frames.Helper.JTableButtonMouseListener;
import frames.Helper.JTableButtonRenderer;
import frames.Helper.ProductTableModel;
import services.DBController;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;

public class ProductList {
    public JPanel panel1;
    private JTable productListTable;
    private JButton searchButton;
    private JTextField searchTextField;
    private JComboBox categoryComboBox;
    private TableModel tableModel;
    private DBController dbController;
    private static ProductList instance = null ;

    private ProductList(){

        dbController = DBController.getInstance();

        this.setupDatabase();

        this.setBasicTable();


        List<Category> categoryList = this.dbController.getEntities(Category.class);


        this.categoryComboBox.setModel(new CategoryComboBoxModel(categoryList));

        this.searchButton.addActionListener(e -> {
            this.updateTable();
        });

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


        productListTable.setModel(tableModel);
        productListTable.getColumn("Koszyk").setCellRenderer(new JTableButtonRenderer());
        productListTable.addMouseListener(new JTableButtonMouseListener(productListTable));
    }

    private void updateTable(){
        CategoryComboBoxModel selectedCategoryModel = (CategoryComboBoxModel)categoryComboBox.getModel();
        Long selectedCategoryId = (Long)selectedCategoryModel.getElementIdAt(categoryComboBox.getSelectedIndex());

        Vector<Product> products = new Vector<>(this.dbController.searchProduct(this.searchTextField.getText(),selectedCategoryId));
        ProductTableModel productTableModel = (ProductTableModel)this.productListTable.getModel();
        productTableModel.updateData(products);
        productListTable.setSize(productListTable.getWidth(), productListTable.getRowCount()* productListTable.getRowHeight()+1);
    }

    private void setupDatabase(){
//                TODO: remove before final verision

        Category category = new Category();
        category.setCategoryName("CHŁODZENIE");
        category.setId(1L);

        dbController.insertIntoTable(category);



        Product product = new Product();
        product.setName("CHŁODNICA HONDA ACCORD V 1.9-2.2");
        product.setBuyPrice(300);
        product.setSellPrice(383);
        product.setQuantity(5);
        product.setId("53328L");
        product.setCategoryId(category);

        dbController.insertIntoTable(product);

        OemToProduct oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("HONDA 19010P0A902");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("HONDA 19010P0DJ51");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("HONDA 19010P0DJ52");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("HONDA 19010P0DJ53");
        dbController.insertIntoTable(oemToProduct);

//        product = new Product();
//        product.setName("Olej 5w-40");
//        product.setBuyPrice(100);
//        product.setSellPrice(150);
//        product.setQuantity(20);
//        dbController.insertIntoTable(product);

//        Category categoryFilter = new Category();
//        category.setCategoryName("FILTR POWIETRZA");
//        category.setId(2L);

//        dbController.insertIntoTable(category);

        product = new Product();
        product.setName("Mann-Filter C 35 154 Filtr powietrza");
        product.setBuyPrice(30);
        product.setSellPrice(45);
        product.setQuantity(10);
        product.setId("C 35 154");
//        product.setCategoryId(categoryFilter);
        dbController.insertIntoTable(product);

        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("VAG 1K0 129 620 D");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("VAG 1K0 129 620 F");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("VAG 1K0 129 620 G");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("VAG 3C0 129 620 B");
        dbController.insertIntoTable(oemToProduct);


        product = new Product();
        product.setName("Filtron AP 130/9 Filtr powietrza");
        product.setBuyPrice(20);
        product.setSellPrice(32);
        product.setQuantity(10);
        product.setId("AP 130/9");
//        product.setCategoryId(categoryFilter);
        dbController.insertIntoTable(product);

        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("CITROËN 1444 TJ");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("CITROËN 1444 X5");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("CITROËN E147242");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("PEUGEOT 1444 CP");
        dbController.insertIntoTable(oemToProduct);

        product = new Product();
        product.setName("Bosch 1 457 432 200 Filtr powietrza");
        product.setBuyPrice(28);
        product.setSellPrice(35);
        product.setQuantity(10);
        product.setId("1 457 432 200");
//        product.setCategoryId(categoryFilter);
        dbController.insertIntoTable(product);

        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("CITROËN 1444 F3");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("CITROËN 1444 R2");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("CITROËN 1444 R7");
        dbController.insertIntoTable(oemToProduct);
        oemToProduct = new OemToProduct();
        oemToProduct.setProductId(product);
        oemToProduct.setOem("CITROËN 1444 T0");
        dbController.insertIntoTable(oemToProduct);

    }



}



