package services;

import Structures.CEIDG.Root;
import Structures.Intercars.DocumentElement;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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



    private Request getInvoicesFromChosenPeriod (Date dateFrom, Date dateTo){
        SimpleDateFormat dateCompressed = new SimpleDateFormat("yyyyMMdd");

        Request request = new Request.Builder()
                .url("https://katalog.intercars.com.pl/api/v2/External/GetInvoices?from="+dateCompressed.format(dateFrom)+"&to="+dateCompressed.format(dateTo))
                .method("GET",null)
                .addHeader("kh_kod", "99O306")
                .addHeader("token", "5b8f39fb-c408-4ce1-a20c-49978efa628a")
                .build();
        return request;
    }


    @Nullable
    private Root getRoot(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            String responseBodyString = responseBody.string();
            ObjectMapper mapper = new ObjectMapper();
            Root root = mapper.readValue(responseBodyString, Root.class);
            response.close();
            return root;
        }
        return null;
    }

    public static void main(String[] args) throws IOException, JAXBException {
        Request request = IntercarsService.getInstance().getInvoicesFromChosenPeriod(new GregorianCalendar(2023, 8, 11).getTime(),new Date());
        String response = IntercarsService.getInstance().client.newCall(request).execute().body().string();

        // create JAXB context and unmarshaller
        var context = JAXBContext.newInstance(DocumentElement.class);
        var um = context.createUnmarshaller();

        var data = (DocumentElement) um.unmarshal(new StringReader(response));
        var bookList = data.nag;

        bookList.forEach((book) -> {
            System.out.println(book.id);
        });
    }
}
