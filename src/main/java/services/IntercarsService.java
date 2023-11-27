package services;

import Structures.Intercars.Invoice.Invoice;
import Structures.Intercars.InvoicesList.InvoicesList;
import Structures.Intercars.InvoicesList.nag;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IntercarsService {
    private ConfigService configService;
    private OkHttpClient client;

    public IntercarsService(){
        this.client = new OkHttpClient().newBuilder().build();
        this.configService = ConfigService.getInstance();
    };

    public List<nag> getInvoicesFromChosenPeriod (Date dateFrom, Date dateTo) throws IOException, JAXBException {
        SimpleDateFormat dateCompressed = new SimpleDateFormat("yyyyMMdd");

        String khCode = this.configService.getInterCarsClientNumber();
        String accessToken = this.configService.getInterCarsAccessToken();

        Request request = new Request.Builder()
                .url("https://katalog.intercars.com.pl/api/v2/External/GetInvoices?from="+dateCompressed.format(dateFrom)+"&to="+dateCompressed.format(dateTo))
                .method("GET",null)
                .addHeader("kh_kod", khCode)
                .addHeader("token", accessToken)
                .build();

        String response = this.client.newCall(request).execute().body().string();

        var context = JAXBContext.newInstance(InvoicesList.class);
        var um = context.createUnmarshaller();

        var invoicesListRoot = (InvoicesList) um.unmarshal(new StringReader(response));
        return invoicesListRoot.nag;
    }

    public Invoice getInvoiceById (String id) throws IOException, JAXBException {
        SimpleDateFormat dateCompressed = new SimpleDateFormat("yyyyMMdd");

        String khCode = this.configService.getInterCarsClientNumber();
        String accessToken = this.configService.getInterCarsAccessToken();

        Request request = new Request.Builder()
                .url("https://katalog.intercars.com.pl/api/v2/External/GetInvoice?id="+id)
                .method("GET",null)
                .addHeader("kh_kod", khCode)
                .addHeader("token", accessToken)
                .build();

        String response = this.client.newCall(request).execute().body().string();

        var context = JAXBContext.newInstance(Invoice.class);
        var um = context.createUnmarshaller();

        return (Invoice) um.unmarshal(new StringReader(response));
    }
}
