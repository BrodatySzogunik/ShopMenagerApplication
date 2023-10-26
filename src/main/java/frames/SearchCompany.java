package frames;

import Structures.CompanyInfo;
import services.CEIDGService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SearchCompany {
    private JTextField nipTextField;
    private JTextField regonTextField;
    private JButton searchButton;
    private JLabel companyNameLabel;
    private JLabel companyAdresLabel;
    private JLabel CompanyNipLabel;
    public JPanel panel1;

    private static SearchCompany instance;
    private CEIDGService ceidgService;

    private SearchCompany(){

        this.ceidgService = CEIDGService.getInstance();

        this.panel1.setPreferredSize(new Dimension(200,200));

        this.searchButton.addActionListener(e -> {
            String nipTextValue = this.nipTextField.getText();
            if(nipTextValue.length() == 10){
                try {
                    CompanyInfo companyInfo = this.ceidgService.getCompanyDataByNip(nipTextValue);
                    companyNameLabel.setText(companyInfo.getCompanyName());
                    companyAdresLabel.setText(companyInfo.getCompanyAddress().toString());
                    CompanyNipLabel.setText(companyInfo.getCompanyNip());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }

    public static SearchCompany getInstance(){
        if(instance == null){
            instance = new SearchCompany();
        }
        return instance;
    }

}
