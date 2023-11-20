package frames;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import Structures.CEIDG.Company;
import Structures.DataBase.Sales.BillToProductToPrice.BillToProductToPrice;
import Structures.PdfHelpers.SaleReportTableItem;
import Utils.Utils;
import org.jdatepicker.impl.*;
import services.CEIDGService;
import services.ConfigService;
import services.DBController;
import services.PdfService;

public class generateSaleRaport extends JFrame{

    public JPanel panel;
    private DBController dbController;
    private PdfService pdfService;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl dateFromPicker;
    private JDatePanelImpl datePanel2;
    private JDatePickerImpl dateToPicker;

    generateSaleRaport(){

        this.dbController = DBController.getInstance();
        this.pdfService = PdfService.getInstance();

        this.setSize(470,600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        panel = new JPanel();
        panel.setLayout(null);

        UtilDateModel model = new UtilDateModel();
        //model.setDate(20,04,2014);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
        dateFromPicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        UtilDateModel model2 = new UtilDateModel();
        //model.setDate(20,04,2014);
        Properties p2 = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel2 = new JDatePanelImpl(model2, p2);
        dateToPicker = new JDatePickerImpl(datePanel2, new DateLabelFormatter());

        JLabel informationLabel = new JLabel();
        informationLabel.setText("Wybierz zakres z którego chcesz wygenerować raport");

        JButton generateReportButton = new JButton();
        generateReportButton.setText("Generuj Raport");
        generateReportButton.addActionListener(e -> {
            Date dateFrom = (Date) dateFromPicker.getModel().getValue();
            Date dateTo = (Date) dateToPicker.getModel().getValue();
            Company sellerCompanyInfo = null;

            try {
                sellerCompanyInfo = CEIDGService.getInstance().getCompanyDataByNip(ConfigService.getInstance().getCompanyNip()).companies.get(0);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


            ArrayList<Object[]> billToProductToPriceList = new ArrayList<>(this.dbController.getSalesFromPeriod(dateFrom, dateTo));

            Double sumOfBoughtItems =0d;
            Double sumOfSoldItems =0d;
            ArrayList<SaleReportTableItem> saleReportTableItemList = new ArrayList<>();

            System.out.println(billToProductToPriceList);
            for(Object[] billToProductToPriceItem :billToProductToPriceList){
                String name = (String) billToProductToPriceItem[1];
                int amount = (Integer) billToProductToPriceItem[2];
                double buyBrutto = (Double) billToProductToPriceItem[4];
                double buyNetto = Utils.getNettoValue(buyBrutto);
                double sellBrutto = (Double) billToProductToPriceItem[3];
                double sellNetto = Utils.getNettoValue(sellBrutto);
                double income = sellBrutto - buyBrutto;

                saleReportTableItemList.add(new SaleReportTableItem(name,amount, buyNetto,buyBrutto, sellNetto, sellBrutto,income));
            }

            this.pdfService.generateSaleReport(sellerCompanyInfo,dateFrom,dateTo,saleReportTableItemList);

            System.out.println(sumOfBoughtItems);
            System.out.println(sumOfSoldItems);
        });

        informationLabel.setBounds(10,20,400,20);
        dateFromPicker.setBounds(10,60,200,25);
        dateToPicker.setBounds(240,60,200,25);
        generateReportButton.setBounds(10,100,430,25);
        panel.add(dateFromPicker);
        panel.add(dateToPicker);
        panel.add(informationLabel);
        panel.add(generateReportButton);

        this.setContentPane(panel);
    }
}


class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}
