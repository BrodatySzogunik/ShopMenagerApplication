package frames;

import Structures.CEIDG.Company;
import Structures.Cart.CartItem;
import Structures.DataBase.Sales.Bill.Bill;
import Structures.DataBase.Sales.BillToProductToPrice.BillToProductToPrice;
import services.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

public class GenerateBill {
    private JTextField nipTextField;
    private JTextField regonTextField;
    private JButton searchButton;
    private JLabel companyNameLabel;
    private JLabel companyAdresLabel;
    private JLabel CompanyNipLabel;
    public JPanel panel1;
    private JButton generateVatButton;
    private JButton paragonButton;
    private JButton nieVatButton;
    private JComboBox paymentMethodSelect;
    private JTextField dateField;
    private JTextField paymentDeadline;

    private static GenerateBill instance;
    private CEIDGService ceidgService;
    private PdfService pdfService;
    private ConfigService configService;
    private DBController dbController;


    private Company sellerCompanyInfo;
    private Company buyerCompanyInfo;
    private CartService cartService;

    private GenerateBill(){

        this.ceidgService = CEIDGService.getInstance();
        this.pdfService = PdfService.getInstance();
        this.configService = ConfigService.getInstance();
        this.cartService = CartService.getInstance();
        this.dbController = DBController.getInstance();

        this.panel1.setPreferredSize(new Dimension(200,200));

        try {
            this.sellerCompanyInfo = this.ceidgService.getCompanyDataByNip(this.configService.getCompanyNip()).companies.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.searchButton.addActionListener(e -> {
            this.searchCustomer();
        });

        this.generateVatButton.addActionListener(e -> {
            this.generateVat();
        });



    }

    public static GenerateBill getInstance(){
        if(instance == null){
            instance = new GenerateBill();
        }
        return instance;
    }

    private void searchCustomer(){
        String nipTextValue = this.nipTextField.getText();
        String regonTextValue = this.regonTextField.getText();

        try{
            Company companyInfo = null;
            if(nipTextValue.length() == 10){
                companyInfo = this.ceidgService.getCompanyDataByNip(nipTextValue).companies.get(0);

            }else if(regonTextValue.length() ==9 ){
                companyInfo = this.ceidgService.getCompanyDataByRegon(regonTextValue).companies.get(0);
            }
            if(companyInfo != null){
                companyNameLabel.setText(companyInfo.name);
                String companyAddress = companyInfo.companyAddress.street;
                companyAdresLabel.setText(companyAddress);
                CompanyNipLabel.setText(companyInfo.owner.nip);
                buyerCompanyInfo = companyInfo;
            }


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private String generateVat(){
        try{
            String saleDate = this.dateField.getText();
            String paymentMethod = String.valueOf(this.paymentMethodSelect.getSelectedItem());
            String paymentDeadline = String.valueOf(this.paymentDeadline.getText());
            String productsValue = String.valueOf(this.cartService.getProductValue());
            Date creationDate= new Date();
            String invoiceId = this.pdfService.generateVatPdf(sellerCompanyInfo, buyerCompanyInfo,this.cartService.getProductList() ,productsValue ,paymentMethod,paymentDeadline,saleDate,creationDate);

            if(invoiceId != null){
                Bill bill = new Bill();
                bill.setId(invoiceId);
                bill.setSaleDate(creationDate);
                if(buyerCompanyInfo != null){
                    bill.setNip(buyerCompanyInfo.getOwner().getNip());
                }
                this.dbController.insertIntoTable(bill);
                for(CartItem cartItem : this.cartService.getProductList()){
                    BillToProductToPrice billToProductToPrice = new BillToProductToPrice();
                    billToProductToPrice.setSalePrice(cartItem.getProduct().getSellPrice());
                    billToProductToPrice.setBuyPrice(cartItem.getProduct().getBuyPrice());
                    billToProductToPrice.setAmount(cartItem.getAmount());
                    billToProductToPrice.setBillId(bill);
                    billToProductToPrice.setProductId(cartItem.getProduct());
                    this.dbController.insertIntoTable(billToProductToPrice);
                    this.dbController.updateProductAmount(cartItem.getProduct(), cartItem.getProduct().getQuantity()-cartItem.getAmount());
                }
            }

            this.cartService.clearCart();
            return invoiceId;
        }catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }

}
