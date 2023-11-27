package services;

import Structures.CEIDG.Root;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class CEIDGService {
    private OkHttpClient client;

    public CEIDGService(){
        this.client = new OkHttpClient().newBuilder().build();
    };

    private Request generateCompanyDataRequest(String params){
        Request request = new Request.Builder()
                .url("https://dane.biznes.gov.pl/api/ceidg/v2/firma?"+params)
                .method("GET",null)
                .addHeader("Authorization", "Bearer eyJraWQiOiJjZWlkZyIsImFsZyI6IkhTNTEyIn0.eyJnaXZlbl9uYW1lIjoiSmFrdWIiLCJwZXNlbCI6IjAwMzEwMzEwMDM1IiwiaWF0IjoxNjk3NDY4MzIyLCJmYW1pbHlfbmFtZSI6Ik1vcmRhbHNraSIsImNsaWVudF9pZCI6IlVTRVItMDAzMTAzMTAwMzUtSkFLVUItTU9SREFMU0tJIn0.FuafX30-Rgu51FzbfZr-_9dpyS2lwj4qtRmiXfZ7tzuT_T5T6xx0tOJhRz0cG09t_HE9TPyAD_duFtOHa54fkg")
                .addHeader("Cookie", "cookiesession1=678B2877LNPRTV1357898901234ACF0F")
                .build();
        return request;
    }


    public Root getCompanyDataByNip(String nip) throws IOException {
        Request request = this.generateCompanyDataRequest("nip="+nip);

        return getRoot(request);
    }

    public Root getCompanyDataByRegon(String regon) throws IOException {
        Request request = this.generateCompanyDataRequest("regon="+regon);

        return getRoot(request);
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
}

