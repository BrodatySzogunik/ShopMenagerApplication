package services;

import Structures.CEIDG.Company;
import Structures.Cart.CartItem;
import Structures.DataBase.Products.Product.Product;
import Structures.DataBase.Sales.BillToProductToPrice.BillToProductToPrice;
import Structures.PdfHelpers.SaleReportTableItem;
import Utils.Utils;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.PageSet;
import com.aspose.words.PageSetup;
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
    private DocumentBuilder builder;
    private PageSetup pageSetup;
    private ConfigService configService;

    public PdfService(){
        this.configService = ConfigService.getInstance();
    }

    private void createDocumentBuilder(){
        try {
            builder = new DocumentBuilder();
            pageSetup = builder.getPageSetup();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateSaleReport(Company sellerCompany,Date dateFrom, Date dateTo, List<SaleReportTableItem> tableItems){

        try{
            createDocumentBuilder();
            pageSetup.setMargins(1);
            String htmlTemplate = this.readHTMLFileToString("./template/SaleReport.html");

            SimpleDateFormat fullDate = new SimpleDateFormat("dd-MM-yyyy");

            Date date = new Date();

            Map<String, String> pdfValues = new HashMap<>();
            pdfValues.put("sellerCompanyName", sellerCompany.getName());
            pdfValues.put("sellerAddress", sellerCompany.getCompanyAddress().getFirstAddressLane());
            pdfValues.put("sellerPostCodeCity", sellerCompany.getCompanyAddress().getSecondAddressLane());
            pdfValues.put("sellerNip", sellerCompany.getOwner().getNip());
            pdfValues.put("dateFrom", fullDate.format(dateFrom));
            pdfValues.put("dateTo", fullDate.format(dateTo));
            pdfValues.put("generationDate", fullDate.format(date));pdfValues.put("sellerAddress", sellerCompany.getCompanyAddress().getFirstAddressLane());



            String productTemplate = "<tr>\n" +
                    "                    <td>${lp}</td>\n" +
                    "                    <td>${productName}</td>\n" +
                    "                    <td>${productAmount}</td>\n" +
                    "                    <td>${buyNetto}</td>\n" +
                    "                    <td>${buyBrutto}</td>\n" +
                    "                    <td>${sellNetto}</td>\n" +
                    "                    <td>${sellBrutto}</td>\n" +
                    "                    <td>${income}</td>" +
                    "</tr>";

            StringBuilder productListHtml= new StringBuilder();
            tableItems.forEach(e -> System.out.println(e));

            Double buyNettoSum = 0d;
            Double buyBruttoSum = 0d;
            Double sellNettoSum = 0d;
            Double sellBruttoSum = 0d;
            Double incomeSum = 0d;

            int lp=1;
            for(SaleReportTableItem p : tableItems){
                Map<String, String> productValues = new HashMap<>();

                buyNettoSum += p.getBuyNetto();
                buyBruttoSum += p.getBuyBrutto();
                sellNettoSum += p.getSellNetto();
                sellBruttoSum += p.getSellBrutto();
                incomeSum += p.getIncome();

                productValues.put("lp",String.valueOf(lp++));
                productValues.put("productName",p.getName());
                productValues.put("productAmount",String.valueOf(p.getAmount()));
                productValues.put("buyNetto",String.valueOf(p.getBuyNetto()));
                productValues.put("buyBrutto",String.valueOf(p.getBuyBrutto()));
                productValues.put("sellNetto",String.valueOf(p.getSellNetto()));
                productValues.put("sellBrutto",String.valueOf(p.getSellBrutto()));
                productValues.put("income", String.valueOf(p.getIncome()));
                StringSubstitutor productSubstitutor = new StringSubstitutor(productValues);
                productListHtml.append(productSubstitutor.replace(productTemplate));
            }
            pdfValues.put("tableBody",productListHtml.toString());


            pdfValues.put("buyNettoSum",String.valueOf(Utils.trimDecimal(buyNettoSum)));
            pdfValues.put("buyBruttoSum", String.valueOf(Utils.trimDecimal(buyBruttoSum)));
            pdfValues.put("sellNettoSum", String.valueOf(Utils.trimDecimal(sellNettoSum)));
            pdfValues.put("sellBruttoSum", String.valueOf(Utils.trimDecimal(sellBruttoSum)));
            pdfValues.put("incomeSum", String.valueOf(Utils.trimDecimal(incomeSum)));

            StringSubstitutor pdfSubstitutor = new StringSubstitutor(pdfValues);
            String result = pdfSubstitutor.replace(htmlTemplate);

            this.builder.insertHtml(result);


            this.builder.getDocument().save("./template/SaleReport-"+fullDate.format(date)+".pdf");

        }catch (Exception exception) {
            exception.printStackTrace();
        }

    }


    public String generateVatPdf(Company sellerCompany, Company buyerCompany, List<CartItem> productList, String productsValue, String paymentMethod, String paymentDeadline , String saleDate, Date date){
        try {

            createDocumentBuilder();
            String htmlTemplate = this.readHTMLFileToString("./template/Vat.html");

            this.configService.increaseInvoiceNumber();
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
                    "            <td>${lp}</td>\n" +
                    "            <td>${productName}</td>\n" +
                    "            <td>szt.</td>\n" +
                    "            <td>${productAmount}</td>\n" +
                    "            <td>${nettoValue}</td>\n" +
                    "            <td>${bruttoValue}</td>\n" +
                    "            <td>23</td>\n" +
                    "            <td>${nettoSum}</td>\n" +
                    "            <td>${bruttoSum}</td>\n" +
                    "        </tr>";

            StringBuilder productListHtml= new StringBuilder();
            int lp=1;
            for(CartItem p : productList){
                Map<String, String> productValues = new HashMap<>();
                productValues.put("lp",String.valueOf(lp++));
                productValues.put("productName",p.getProduct().getName());
                productValues.put("productAmount",p.getAmount().toString());
                productValues.put("nettoValue",String.valueOf(Utils.getNettoValue(p.getProduct().getSellPrice())));
                productValues.put("bruttoValue",String.valueOf(p.getProduct().getSellPrice()));
                productValues.put("nettoSum",String.valueOf(Utils.getNettoValue(p.getAmount()*p.getProduct().getSellPrice())));
                productValues.put("bruttoSum",String.valueOf(p.getAmount()*p.getProduct().getSellPrice()));
                StringSubstitutor productSubstitutor = new StringSubstitutor(productValues);
                productListHtml.append(productSubstitutor.replace(productTemplate));
            }
            pdfValues.put("products",productListHtml.toString());


            StringSubstitutor pdfSubstitutor = new StringSubstitutor(pdfValues);
            String result = pdfSubstitutor.replace(htmlTemplate);

            this.builder.insertHtml(result);


            this.builder.getDocument().save("./template/"+createFileName+".pdf");
            return invoiceNumber;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public String generateNoVatPdf(Company sellerCompany, Company buyerCompany, List<CartItem> productList, String productsValue, String paymentMethod, String paymentDeadline , String saleDate, Date date){
        try {

            createDocumentBuilder();
            String htmlTemplate = this.readHTMLFileToString("./template/NoVat.html");

            this.configService.increaseInvoiceNumber();
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
                    "            <td>${lp}</td>\n" +
                    "            <td>${productName}</td>\n" +
                    "            <td>szt.</td>\n" +
                    "            <td>${productAmount}</td>\n" +
                    "            <td>${productValue}</td>\n" +
                    "            <td>${productSummaryValue}</td>\n" +
                    "        </tr>";

            StringBuilder productListHtml= new StringBuilder();
            int lp=1;
            for(CartItem p : productList){
                Map<String, String> productValues = new HashMap<>();
                productValues.put("lp",String.valueOf(lp++));
                productValues.put("productName",p.getProduct().getName());
                productValues.put("productAmount",p.getAmount().toString());
                productValues.put("productValue",String.valueOf(p.getProduct().getSellPrice()));
                productValues.put("productSummaryValue",String.valueOf(p.getProduct().getSellPrice()*p.getAmount()));
                StringSubstitutor productSubstitutor = new StringSubstitutor(productValues);
                productListHtml.append(productSubstitutor.replace(productTemplate));
            }
            pdfValues.put("products",productListHtml.toString());


            StringSubstitutor pdfSubstitutor = new StringSubstitutor(pdfValues);
            String result = pdfSubstitutor.replace(htmlTemplate);

            this.builder.insertHtml(result);


            this.builder.getDocument().save("./template/"+createFileName+".pdf");
            return invoiceNumber;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String generateBillPdf(Company sellerCompany, List<CartItem> productList, String productsValue, String paymentMethod, String paymentDeadline , String saleDate, Date date){
        try {

            createDocumentBuilder();
            String htmlTemplate = this.readHTMLFileToString("./template/bill.html");

            this.configService.increaseInvoiceNumber();
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

            pdfValues.put("invoiceNumber",invoiceNumber);
            pdfValues.put("invoiceDate",fullDate.format(date));
            pdfValues.put("saleDate",saleDate);
            pdfValues.put("paymentMethod",paymentMethod);
            pdfValues.put("paymentDeadline",paymentDeadline);
            pdfValues.put("bankAccountNumber",this.configService.getBankAccountNumber());

            pdfValues.put("productsValue", productsValue);

            String productTemplate = "<tr>\n" +
                    "            <td>${lp}</td>\n" +
                    "            <td>${productName}</td>\n" +
                    "            <td>szt.</td>\n" +
                    "            <td>${productAmount}</td>\n" +
                    "            <td>${productValue}</td>\n" +
                    "            <td>${productSummaryValue}</td>\n" +
                    "        </tr>";

            StringBuilder productListHtml= new StringBuilder();
            int lp=1;
            for(CartItem p : productList){
                Map<String, String> productValues = new HashMap<>();
                productValues.put("lp",String.valueOf(lp++));
                productValues.put("productName",p.getProduct().getName());
                productValues.put("productAmount",p.getAmount().toString());
                productValues.put("productValue",String.valueOf(p.getProduct().getSellPrice()));
                productValues.put("productSummaryValue",String.valueOf(p.getProduct().getSellPrice()*p.getAmount()));
                StringSubstitutor productSubstitutor = new StringSubstitutor(productValues);
                productListHtml.append(productSubstitutor.replace(productTemplate));
            }
            pdfValues.put("products",productListHtml.toString());


            StringSubstitutor pdfSubstitutor = new StringSubstitutor(pdfValues);
            String result = pdfSubstitutor.replace(htmlTemplate);

            this.builder.insertHtml(result);


            this.builder.getDocument().save("./template/"+createFileName+".pdf");
            return invoiceNumber;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
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
