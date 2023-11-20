package frames;

import Structures.DataBase.Products.OemToProduct.OemToProduct;
import Structures.DataBase.Products.Product.Product;
import Structures.Intercars.Invoice.Invoice;
import Structures.Intercars.Invoice.poz;
import Structures.Intercars.InvoicesList.nag;
import frames.Helper.ImportInvoiceTableModel;
import frames.Helper.InvoiceComboBoxModel;
import jakarta.xml.bind.JAXBException;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import services.DBController;
import services.IntercarsService;
import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class InterCarsImport extends JFrame {

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

     public InterCarsImport(){

         this.intercarsService = IntercarsService.getInstance();
         this.dbController = DBController.getInstance();

         this.setSize(470,640);
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

         informationLabel.setBounds(10,20,400,20);
         dateFromPicker.setBounds(10,60,200,25);
         dateToPicker.setBounds(240,60,200,25);
         getInvoicesFromPeriodButton.setBounds(10,100,430,25);
         invoiceComboBox.setBounds(10, 140 , 430,25);
         importInvoiceButton.setBounds(10,180,430,25);
         importInvoiceScrollPane.setBounds(10,220,430, 300);
         confirmInvoiceImportButton.setBounds(10,535,430, 25);

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
             importInvoiceTableModel.updateData(invoiceItems);
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
