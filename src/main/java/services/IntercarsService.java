package services;

import Structures.CEIDG.Root;
import Structures.Intercars.Invoice.Invoice;
import Structures.Intercars.InvoicesList.InvoicesList;
import Structures.Intercars.InvoicesList.nag;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IntercarsService {
    private static IntercarsService instance;
    private OkHttpClient client;

    private IntercarsService(){
        this.client = new OkHttpClient().newBuilder().build();
    };


    public static IntercarsService getInstance(){
        if(instance==null){
            instance = new IntercarsService();
        }
        return instance;
    }



    private List<nag> getInvoicesFromChosenPeriod (Date dateFrom, Date dateTo) throws IOException, JAXBException {
        SimpleDateFormat dateCompressed = new SimpleDateFormat("yyyyMMdd");

        Request request = new Request.Builder()
                .url("https://katalog.intercars.com.pl/api/v2/External/GetInvoices?from="+dateCompressed.format(dateFrom)+"&to="+dateCompressed.format(dateTo))
                .method("GET",null)
                .addHeader("kh_kod", "99O306")
                .addHeader("token", "5b8f39fb-c408-4ce1-a20c-49978efa628a")
                .build();

        String response = this.client.newCall(request).execute().body().string();

        var context = JAXBContext.newInstance(InvoicesList.class);
        var um = context.createUnmarshaller();

        var invoicesListRoot = (InvoicesList) um.unmarshal(new StringReader(response));
        return invoicesListRoot.nag;
    }

    private Invoice getInvoiceById (String id) throws IOException, JAXBException {
        SimpleDateFormat dateCompressed = new SimpleDateFormat("yyyyMMdd");

        Request request = new Request.Builder()
                .url("https://katalog.intercars.com.pl/api/v2/External/GetInvoice?id="+id)
                .method("GET",null)
                .addHeader("kh_kod", "99O306")
                .addHeader("token", "5b8f39fb-c408-4ce1-a20c-49978efa628a")
                .build();

        String response = this.client.newCall(request).execute().body().string();
        System.out.println(response );

        var context = JAXBContext.newInstance(Invoice.class);
        var um = context.createUnmarshaller();

        return (Invoice) um.unmarshal(new StringReader(response));
    }

    public static void main(String[] args) throws IOException, JAXBException {
        System.out.println(IntercarsService.getInstance().getInvoiceById("243079506").poz.get(0).nazwa);
    }
}
