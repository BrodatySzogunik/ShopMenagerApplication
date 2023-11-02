package frames;

import Structures.CEIDG.Company;
import services.CEIDGService;
import services.ConfigService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class NavigationPanel extends JFrame {
    public JPanel panel1;
    private JLabel companyNameLabel;
    private JLabel addressFirstLineLabel;
    private JLabel nipLabel;
    private JLabel addressSecondLineLabel;
    public JButton productSearch;
    public JButton getDelivery;
    public JButton searchCompany;

    private ConfigService configService;
    private CEIDGService ceidgService;

    public static NavigationPanel instance;
    private NavigationPanel(){
        panel1.setBackground(Color.gray);
        panel1.setPreferredSize(new Dimension(0,200));

        this.configService = ConfigService.getInstance();
        this.ceidgService = CEIDGService.getInstance();

        try {
            Company company = this.ceidgService.getCompanyDataByNip(this.configService.getCompanyNip()).companies.get(0);
            this.companyNameLabel.setText(company.getName());
            this.addressFirstLineLabel.setText(company.getCompanyAddress().getFirstAddressLane());
            this.addressSecondLineLabel.setText(company.getCompanyAddress().getSecondAddressLane());
            this.nipLabel.setText("NIP: "+company.getOwner().getNip());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NavigationPanel getInstance() {
        if(instance == null){
            instance = new NavigationPanel();
        }
        return instance;
    }
}
