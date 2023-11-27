package frames;

import Structures.DataBase.Products.Category.Category;
import Structures.DataBase.Products.OemToProduct.OemToProduct;
import Structures.DataBase.Products.Product.Product;
import Structures.Intercars.Invoice.Invoice;
import Structures.Intercars.Invoice.poz;
import Structures.Intercars.InvoicesList.nag;
import frames.Helper.CategoryComboBoxModel;
import frames.Helper.ImportInvoiceTableModel;
import frames.Helper.InvoiceComboBoxModel;
import jakarta.xml.bind.JAXBException;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import services.DBController;
import services.IntercarsService;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;

public class InterCarsImportPanel extends JFrame {

    private JPanel panel;
    private IntercarsService intercarsService;
    private JDatePanelImpl dateFromPanel;
    private JDatePickerImpl dateFromPicker;
    private JDatePanelImpl dateToPanel;
    private JDatePickerImpl dateToPicker;
    private JComboBox invoiceComboBox;
    private JScrollPane importInvoiceScrollPane;
    private JTable importInvoiceTable;
    private JButton confirmInvoiceImportButton;
    private JButton importInvoiceButton;
    private JButton getInvoicesFromPeriodButton;
    private DBController dbController;

     public InterCarsImportPanel(){

         this.intercarsService = new IntercarsService();
         this.dbController = DBController.getInstance();

         this.setSize(870,640);
         this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         this.setResizable(false);
         this.setVisible(true);


         panel = new JPanel();
         panel.setLayout(null);

         UtilDateModel model = new UtilDateModel();
         Properties p = new Properties();
         p.put("text.today", "Dzisiaj");
         p.put("text.month", "Misiąc");
         p.put("text.year", "Rok");
         dateFromPanel = new JDatePanelImpl(model, p);
         dateFromPicker = new JDatePickerImpl(dateFromPanel, new DateLabelFormatter());
         dateFromPanel.addActionListener(e -> enableOrDisableSearchButton());

         UtilDateModel model2 = new UtilDateModel();
         dateToPanel = new JDatePanelImpl(model2, p);
         dateToPicker = new JDatePickerImpl(dateToPanel, new DateLabelFormatter());
         dateToPicker.addActionListener(e -> enableOrDisableSearchButton());

         JLabel informationLabel = new JLabel();
         informationLabel.setText("Wybierz zakres z jakiego chcesz wyszukać faktury Intercars");

         getInvoicesFromPeriodButton = new JButton();
         getInvoicesFromPeriodButton.setText("Wyszukaj");
         getInvoicesFromPeriodButton.addActionListener(e -> this.getInvoicesFromPeriodActionListener());

         invoiceComboBox = new JComboBox();
         invoiceComboBox.setModel(new InvoiceComboBoxModel(null));

         importInvoiceButton = new JButton();
         importInvoiceButton.setText("importuj dane z faktury");
         importInvoiceButton.addActionListener(e -> this.importInvoiceActionListener());

         importInvoiceTable = new JTable();
         importInvoiceTable.setModel(new ImportInvoiceTableModel(new Vector<>()));

         importInvoiceScrollPane = new JScrollPane(importInvoiceTable);

         confirmInvoiceImportButton = new JButton();
         confirmInvoiceImportButton.setText("zatwierdź");
         confirmInvoiceImportButton.addActionListener(e -> this.confirmInvoiceImportActionListener());

         informationLabel.setBounds(10,20,800,20);
         dateFromPicker.setBounds(10,60,400,25);
         dateToPicker.setBounds(440,60,400,25);
         getInvoicesFromPeriodButton.setBounds(10,100,830,25);
         invoiceComboBox.setBounds(10, 140 , 830,25);
         importInvoiceButton.setBounds(10,180,830,25);
         importInvoiceScrollPane.setBounds(10,220,830, 300);
         confirmInvoiceImportButton.setBounds(10,535,830, 25);

         confirmInvoiceImportButton.setEnabled(false);
         importInvoiceButton.setEnabled(false);
         getInvoicesFromPeriodButton.setEnabled(false);

         panel.add(dateFromPicker);
         panel.add(dateToPicker);
         panel.add(informationLabel);
         panel.add(invoiceComboBox);
         panel.add(importInvoiceButton);
         panel.add(getInvoicesFromPeriodButton);
         panel.add(importInvoiceScrollPane);
         panel.add(confirmInvoiceImportButton);

         this.setContentPane(panel);
     }

     public void getInvoicesFromPeriodActionListener(){

         Date dateFrom = (Date) this.dateFromPicker.getModel().getValue();
         Date dateTo = (Date) this.dateToPicker.getModel().getValue();

         if(dateFrom != null && dateTo != null){
            try{

                List<nag> invoiceList = this.intercarsService.getInvoicesFromChosenPeriod(dateFrom, dateTo);
                InvoiceComboBoxModel invoiceComboBoxModel = (InvoiceComboBoxModel)this.invoiceComboBox.getModel();
                invoiceComboBoxModel.updateComboBox(invoiceList);

                if(invoiceList.size() > 0){
                    importInvoiceButton.setEnabled(true);
                }else {
                    importInvoiceButton.setEnabled(false);
                }

            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
         }
     }

     public void importInvoiceActionListener(){
         nag selectedItem = (nag) invoiceComboBox.getModel().getSelectedItem();
         String selectedInvoiceId = String.valueOf(selectedItem.id);
         System.out.println(selectedInvoiceId);

         try {
             Invoice invoice = this.intercarsService.getInvoiceById(selectedInvoiceId);

             Vector<poz> invoiceItems = new Vector<>(invoice.poz);
             ImportInvoiceTableModel importInvoiceTableModel = (ImportInvoiceTableModel) this.importInvoiceTable.getModel();

             List<Category> categoryList = this.dbController.getEntities(Category.class);

             importInvoiceTableModel.updateData(invoiceItems,new Vector<>(categoryList));

             TableColumn categoryColumn = importInvoiceTable.getColumnModel().getColumn(5);
             JComboBox comboBox = new JComboBox();

             comboBox.setModel(new CategoryComboBoxModel(categoryList));

             categoryColumn.setCellEditor(new DefaultCellEditor(comboBox));

             importInvoiceTable.setSize(importInvoiceTable.getWidth(), importInvoiceTable.getRowCount()* importInvoiceTable.getRowHeight()+1);

             if(invoiceItems.size() > 0){
                 confirmInvoiceImportButton.setEnabled(true);
             }else {
                 confirmInvoiceImportButton.setEnabled(false);
             }
         } catch (IOException e) {
             e.printStackTrace();
         } catch (JAXBException e) {
             e.printStackTrace();
         }
         }
    public void enableOrDisableSearchButton(){
        if (dateFromPanel.getModel().getValue() != null && dateToPicker.getModel().getValue() != null){
            this.getInvoicesFromPeriodButton.setEnabled(true);
        }else{
            this.getInvoicesFromPeriodButton.setEnabled(false);
        }

    }




    public void confirmInvoiceImportActionListener(){
        ImportInvoiceTableModel importInvoiceTableModel = (ImportInvoiceTableModel) this.importInvoiceTable.getModel();
        List<poz> itemsList = new ArrayList<>(importInvoiceTableModel.getValues());

        for(poz p: itemsList){
            Product productInDb = this.dbController.getProductById(p.index);

            if(productInDb == null){
                Product product = new Product();
                product.setName(p.nazwa);
                product.setBuyPrice(p.cena);
                product.setSellPrice(p.cenadet);
                product.setQuantity(p.ilosc);
                product.setId(p.index);
                product.setDescription(p.opis);

                dbController.insertIntoTable(product);
                for(String code : p.kod_kre.replaceAll("\\s", "").split(",")) {
                    OemToProduct oemToProduct = new OemToProduct();
                    oemToProduct.setProductId(product);
                    oemToProduct.setOem(code);
                    dbController.insertIntoTable(oemToProduct);
                }
            }else{
                this.dbController.updateProductAmount(productInDb, productInDb.getQuantity() + p.ilosc);
            }

        }

        this.dispose();

     }
}

class ProductCategoryCellEditor extends DefaultCellEditor{

    public ProductCategoryCellEditor(JComboBox<?> comboBox) {
        super(comboBox);
        editorComponent = comboBox;
        comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        delegate = new EditorDelegate() {
            public void setValue(Object value) {
                comboBox.setSelectedItem(value);
            }

            public Object getCellEditorValue() {
                return comboBox.getSelectedItem();
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                if (anEvent instanceof MouseEvent) {
                    MouseEvent e = (MouseEvent)anEvent;
                    return e.getID() != MouseEvent.MOUSE_DRAGGED;
                }
                return true;
            }
            public boolean stopCellEditing() {
                if (comboBox.isEditable()) {
                    // Commit edited value.
                    comboBox.actionPerformed(new ActionEvent(
                            ProductCategoryCellEditor.this, 0, ""));
                }
                return super.stopCellEditing();
            }
        };
        comboBox.addActionListener(delegate);
    }
}
