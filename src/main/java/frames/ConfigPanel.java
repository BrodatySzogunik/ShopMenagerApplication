package frames;

import services.ConfigService;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JFrame {
    private JTextField NIPField;
    private JTextField BankAccountNumberField;
    private JTextField LastInvoiceNumberField;
    private JTextField IntercarsClientNumberField;
    private JPasswordField AccessTokenField;
    public JPanel configPanel;
    private JButton confirmButton;
    private ConfigService configService;

    public ConfigPanel(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(600,400));
        this.setVisible(true);
        this.setContentPane(configPanel);



        this.configService = ConfigService.getInstance();
        this.NIPField.setText(this.configService.getCompanyNip());
        this.BankAccountNumberField.setText(this.configService.getBankAccountNumber());
        this.LastInvoiceNumberField.setText(String.valueOf(this.configService.getLastInvoiceNumber()));
        this.IntercarsClientNumberField.setText(this.configService.getInterCarsClientNumber());
        this.confirmButton.addActionListener(e -> confirmButtonActionListener());


    }

    private void confirmButtonActionListener(){
        String newNip = this.NIPField.getText();
        String newBankAccountNumber = this.BankAccountNumberField.getText();
        Integer newLastInvoiceNumber = Integer.parseInt(this.LastInvoiceNumberField.getText());
        String newIntercarsClientNumber = this.IntercarsClientNumberField.getText();
        String newIntercarsAccessToken = new String(this.AccessTokenField.getPassword());

        if(!this.configService.getCompanyNip().equals(newNip)){
            this.configService.setCompanyNip(newNip);
        }
        if(!this.configService.getBankAccountNumber().equals(newBankAccountNumber)){
            this.configService.setBankAccountNumber(newBankAccountNumber);
        }
        if(this.configService.getLastInvoiceNumber() != newLastInvoiceNumber){
            this.configService.setLastInvoiceNumber(newLastInvoiceNumber);
        }
        if(!this.configService.getInterCarsClientNumber().equals(newIntercarsClientNumber)){
            this.configService.setInterCarsClientNumber(newIntercarsClientNumber);
        }
        if(!newIntercarsAccessToken.equals("")){
            this.configService.setInterCarsAccessToken(newIntercarsAccessToken);
        }

        this.configService.saveConfig();
        this.dispose();


    }
}
