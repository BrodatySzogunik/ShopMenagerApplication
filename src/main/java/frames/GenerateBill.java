package frames;

import Structures.Company;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import services.CEIDGService;
import services.ConfigService;
import services.PdfService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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

    private Company sellerCompanyInfo;
    private Company buyerCompanyInfo;

    private GenerateBill(){

        this.ceidgService = CEIDGService.getInstance();
        this.pdfService = PdfService.getInstance();
        this.configService = ConfigService.getInstance();

        this.panel1.setPreferredSize(new Dimension(200,200));

        try {
            this.sellerCompanyInfo = this.ceidgService.getCompanyDataByNip(this.configService.getCompanyNip()).companies.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.searchButton.addActionListener(e -> {
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



        });

        this.generateVatButton.addActionListener(e -> {
            String saleDate = this.dateField.getText();
            String paymentMethod = String.valueOf(this.paymentMethodSelect.getSelectedItem());
            String paymentDeadline = String.valueOf(this.paymentDeadline.getText());
            this.pdfService.generateVatPdf(sellerCompanyInfo, buyerCompanyInfo, null, "0",paymentMethod,paymentDeadline,saleDate);
        });



    }

    public static GenerateBill getInstance(){
        if(instance == null){
            instance = new GenerateBill();
        }
        return instance;
    }

}
