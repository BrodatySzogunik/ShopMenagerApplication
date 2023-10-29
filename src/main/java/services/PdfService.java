package services;

import Structures.Company;
import com.aspose.words.DocumentBuilder;
import org.apache.commons.text.StringSubstitutor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfService {
    private static PdfService instance;
    private DocumentBuilder builder;
    private ConfigService configService;

    private PdfService(){

        this.configService = ConfigService.getInstance();
        try {
            builder = new DocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PdfService getInstance(){
        if(instance == null){
            instance = new PdfService();
        }
        return instance;
    }


    public void generateVatPdf(Company sellerCompany, Company buyerCompany, List<String> productList, String productsValue, String paymentMethod, String paymentDeadline , String saleDate){
        try {
            String htmlTemplate = this.readHTMLFileToString("D:\\inżynierka\\template\\template.html");

            this.configService.increaseInvoiceNumber();
            Date date = new Date();
            SimpleDateFormat fullDate = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat year = new SimpleDateFormat("yy");

            String invoiceNumber = "R/"+ this.configService.getLastInvoiceNumber() +"/"+year.format(date);
            String createFileName = "R_"+ this.configService.getLastInvoiceNumber() +"_"+year.format(date);

            Map<String, String> values = new HashMap<>();
            values.put("sellerCompanyName", sellerCompany.getName());
            values.put("sellerAddress", sellerCompany.getCompanyAddress().getFirstAddressLane());
            values.put("sellerPostCodeCity", sellerCompany.getCompanyAddress().getSecondAddressLane());
            values.put("sellerNip", sellerCompany.getOwner().getNip());
            values.put("invoicePlace",sellerCompany.getCompanyAddress().city);

            values.put("buyerCompanyName", buyerCompany.getName());
            values.put("buyerAddress", buyerCompany.getCompanyAddress().getFirstAddressLane());
            values.put("buyerPostCodeCity", buyerCompany.getCompanyAddress().getSecondAddressLane());
            values.put("buyerNip", buyerCompany.getOwner().getNip());
            values.put("invoiceNumber",invoiceNumber);
            values.put("invoiceDate",fullDate.format(date));
            values.put("saleDate",saleDate);
            values.put("paymentMethod",paymentMethod);
            values.put("paymentDeadline",paymentDeadline);
            values.put("bankAccountNumber",this.configService.getBankAccountNumber());


            values.put("products","<tr>\n" +
                    "            <th>Nazwa Produktu</th>\n" +
                    "            <th>1</th>\n" +
                    "            <th>1</th>\n" +
                    "            <th>1 (PLN)</th>\n" +
                    "        </tr>");
            values.put("productsValue", productsValue);

            StringSubstitutor sub = new StringSubstitutor(values);
            String result = sub.replace(htmlTemplate);

            this.builder.insertHtml(result);


            this.builder.getDocument().save("D:/inżynierka/template/"+createFileName+".pdf");


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    public String readHTMLFileToString(String filePath) {
        // Specify the path to your HTML file

        // Create a StringBuilder to store the HTML content
        StringBuilder htmlContent = new StringBuilder();

        try {
            // Open and read the file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The HTML content is now stored in the 'htmlContent' StringBuilder
        String htmlString = htmlContent.toString();

        // Print the HTML string
        return htmlString;
    }

}
