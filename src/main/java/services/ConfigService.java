package services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigService {
    private static ConfigService instance;
    private String companyNip = "0";
    private Integer lastInvoiceNumber= 0;
    private String bankAccountNumber="0";
    private String interCarsClientNumber = "";
    private String interCarsAccessToken = "";

    private ConfigService(){};

    public static ConfigService getInstance(){
        if(instance == null){
            instance = new ConfigService();
        }
        return instance;
    }

    public void readConfig(){
        Properties properties = new Properties();
        String configFile = "./template/config";

        try {
            // Attempt to load the existing configuration file
            FileInputStream fileInputStream = new FileInputStream(configFile);
            properties.load(fileInputStream);
            fileInputStream.close();
            this.companyNip = properties.getProperty("companyNip");
            this.lastInvoiceNumber = Integer.parseInt(properties.getProperty("lastInvoiceNumber"));
            this.bankAccountNumber = properties.getProperty("bankAccountNumber");
            this.interCarsAccessToken = properties.getProperty("interCarsAccessToken");
            this.interCarsClientNumber = properties.getProperty("interCarsClientNumber");

        } catch (IOException e) {
            // If the file doesn't exist, create it
            System.out.println("Creating a new configuration file: " + configFile);
            this.saveConfig();
        }
    }

    public void saveConfig(){
        Properties properties = new Properties();
        String configFile = "./template/config";

        properties.setProperty("companyNip", this.companyNip);
        properties.setProperty("lastInvoiceNumber", Integer.toString(this.lastInvoiceNumber));
        properties.setProperty("bankAccountNumber", this.bankAccountNumber);
        properties.setProperty("interCarsAccessToken", this.interCarsAccessToken);
        properties.setProperty("interCarsClientNumber", this.interCarsClientNumber);

        try {
            // Save the configuration file (this will create it if it doesn't exist)
            FileOutputStream fileOutputStream = new FileOutputStream(configFile);
            properties.store(fileOutputStream, "Configuration File");
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getCompanyNip() {
        return companyNip;
    }

    public void setCompanyNip(String companyNip) {
        this.companyNip = companyNip;
    }

    public Integer getLastInvoiceNumber() {
        return lastInvoiceNumber;
    }

    public void increaseInvoiceNumber(){
        this.lastInvoiceNumber++;
    }
    public void setLastInvoiceNumber(Integer lastInvoiceNumber) {
        this.lastInvoiceNumber = lastInvoiceNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getInterCarsClientNumber() {
        return interCarsClientNumber;
    }

    public void setInterCarsClientNumber(String interCarsClientNumber) {
        this.interCarsClientNumber = interCarsClientNumber;
    }

    public String getInterCarsAccessToken() {
        return interCarsAccessToken;
    }

    public void setInterCarsAccessToken(String interCarsAccessToken) {
        this.interCarsAccessToken = interCarsAccessToken;
    }
}
