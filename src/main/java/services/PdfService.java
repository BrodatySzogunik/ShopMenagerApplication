package services;

import Structures.CEIDG.Company;
import Structures.Cart.CartItem;
import Structures.DataBase.Products.Product.Product;
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


    public void generateVatPdf(Company sellerCompany, Company buyerCompany, List<CartItem> productList, String productsValue, String paymentMethod, String paymentDeadline , String saleDate){
        try {
            String htmlTemplate = this.readHTMLFileToString("C:/MyFiles/ShopMenagerApplication/template/template.html");

            this.configService.increaseInvoiceNumber();
            Date date = new Date();
            SimpleDateFormat fullDate = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat year = new SimpleDateFormat("yy");

            String invoiceNumber = "R/"+ this.configService.getLastInvoiceNumber() +"/"+year.format(date);
            String createFileName = "R_"+ this.configService.getLastInvoiceNumber() +"_"+year.format(date);

            Map<String, String> pdfValues = new HashMap<>();
            pdfValues.put("sellerCompanyName", sellerCompany.getName());
            pdfValues.put("sellerAddress", sellerCompany.getCompanyAddress().getFirstAddressLane());
            pdfValues.put("sellerPostCodeCity", sellerCompany.getCompanyAddress().getSecondAddressLane());
            pdfValues.put("sellerNip", sellerCompany.getOwner().getNip());
            pdfValues.put("invoicePlace",sellerCompany.getCompanyAddress().city);

            pdfValues.put("buyerCompanyName", buyerCompany.getName());
            pdfValues.put("buyerAddress", buyerCompany.getCompanyAddress().getFirstAddressLane());
            pdfValues.put("buyerPostCodeCity", buyerCompany.getCompanyAddress().getSecondAddressLane());
            pdfValues.put("buyerNip", buyerCompany.getOwner().getNip());
            pdfValues.put("invoiceNumber",invoiceNumber);
            pdfValues.put("invoiceDate",fullDate.format(date));
            pdfValues.put("saleDate",saleDate);
            pdfValues.put("paymentMethod",paymentMethod);
            pdfValues.put("paymentDeadline",paymentDeadline);
            pdfValues.put("bankAccountNumber",this.configService.getBankAccountNumber());

            pdfValues.put("productsValue", productsValue);

            String productTemplate = "<tr>\n" +
                    "            <th>${productName}</th>\n" +
                    "            <th>${productAmount}</th>\n" +
                    "            <th>${productPrice}</th>\n" +
                    "            <th>${productSummaryPrice}(PLN)</th>\n" +
                    "        </tr>";

            StringBuilder productListHtml= new StringBuilder();
            for(CartItem p : productList){
                Map<String, String> productValues = new HashMap<>();
                productValues.put("productName",p.getProduct().getName());
                productValues.put("productAmount",p.getAmount().toString());
                productValues.put("productPrice",String.valueOf(p.getProduct().getSellPrice()));
                productValues.put("productSummaryPrice",String.valueOf(p.getAmount()*p.getProduct().getSellPrice()));
                StringSubstitutor productSubstitutor = new StringSubstitutor(productValues);
                productListHtml.append(productSubstitutor.replace(productTemplate));
            }
            pdfValues.put("products",productListHtml.toString());


            StringSubstitutor pdfSubstitutor = new StringSubstitutor(pdfValues);
            String result = pdfSubstitutor.replace(htmlTemplate);

            this.builder.insertHtml(result);


            this.builder.getDocument().save("C:/MyFiles/ShopMenagerApplication/template/"+createFileName+".pdf");


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
