package services;

import Structures.Address;
import Structures.CompanyInfo;
import Structures.Nip;
import Structures.Regon;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class CEIDGService {
    private static CEIDGService instance;
    private OkHttpClient client;

    private CEIDGService(){
        this.client = new OkHttpClient().newBuilder().build();
    };


    public static CEIDGService getInstance(){
        if(instance==null){
            instance = new CEIDGService();
        }
        return instance;
    }


//    public static void main(String[] args) {
////        CEIDGService.getInstance().getCompanyDataByNip("9111962037");
//    }


    public CompanyInfo getCompanyDataByNip(String nip) throws IOException {
        Request request = new Request.Builder()
                .url("https://dane.biznes.gov.pl/api/ceidg/v2/firma?nip="+nip)
                .method("GET",null)
                .addHeader("Authorization", "Bearer eyJraWQiOiJjZWlkZyIsImFsZyI6IkhTNTEyIn0.eyJnaXZlbl9uYW1lIjoiSmFrdWIiLCJwZXNlbCI6IjAwMzEwMzEwMDM1IiwiaWF0IjoxNjk3NDY4MzIyLCJmYW1pbHlfbmFtZSI6Ik1vcmRhbHNraSIsImNsaWVudF9pZCI6IlVTRVItMDAzMTAzMTAwMzUtSkFLVUItTU9SREFMU0tJIn0.FuafX30-Rgu51FzbfZr-_9dpyS2lwj4qtRmiXfZ7tzuT_T5T6xx0tOJhRz0cG09t_HE9TPyAD_duFtOHa54fkg")
                .addHeader("Cookie", "cookiesession1=678B2877LNPRTV1357898901234ACF0F")
                .build();
        Response response = null;

        response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            String responseBodyString = responseBody.string();
            String companyString= new JSONObject(responseBodyString).get("firma").toString();
            String companyArrayString = new JSONArray(companyString).get(0).toString();
            String companyAddressObjectString = new JSONObject(companyArrayString).get("adresDzialalnosci").toString();
            String companyOwnerObjectString = new JSONObject(companyArrayString).get("wlasciciel").toString();
            JSONObject companyAddressObject = new JSONObject(companyAddressObjectString);
            JSONObject companyOwnerObject = new JSONObject(companyOwnerObjectString);
            String companyStreet = companyAddressObject.get("ulica").toString();
            String companyBuilding =  companyAddressObject.get("budynek").toString();
            String companyPostCode = companyAddressObject.get("kod").toString();
            String companyCity = companyAddressObject.get("miasto").toString();
            String companyName = new JSONObject(companyArrayString).get("nazwa").toString();
            Nip companyNip = new Nip(companyOwnerObject.get("nip").toString());
            Regon companyRegon =new Regon(companyOwnerObject.get("regon").toString());

            Address companyAddress = new Address(companyStreet,companyBuilding,companyPostCode,companyCity);
            CompanyInfo companyInfo = new CompanyInfo(companyName,companyAddress, companyNip, companyRegon);

            response.close();
            return companyInfo;
        }
        return null;

    }
}

public class CompanyAddress {
    @JsonProperty("budynek")
    public String building;
    @JsonProperty("ulica")
    public String street;
    @JsonProperty("miasto")
    public String city;
    @JsonProperty("wojewodztwo")
    public String voivodeship;
    @JsonProperty("powiat")
    public String county;
    @JsonProperty("gmina")
    public String community;
    @JsonProperty("kraj")
    public String country;
    @JsonProperty("kod")
    public String postCode;
    public String terc;
    public String simc;
}

public class CorrespondenceAddress{
    @JsonProperty("budynek")
    public String building;
    @JsonProperty("ulica")
    public String street;
    @JsonProperty("miasto")
    public String city;
    @JsonProperty("wojewodztwo")
    public String voivodeship;
    @JsonProperty("powiat")
    public String county;
    @JsonProperty("gmina")
    public String community;
    @JsonProperty("kraj")
    public String country;
    @JsonProperty("kod")
    public String postCode;
    public String terc;
    public String simc;
}

public class Firma{
    public String id;
    @JsonProperty("nazwa")
    public String name;
    @JsonProperty("adresDzialalnosci")
    public CompanyAddress companyAddress;
    @JsonProperty("adresKorespondencyjny")
    public CorrespondenceAddress correspondenceAddress;
    @JsonProperty("wlasciciel")
    public Owner owner;
    @JsonProperty("obywatelstwa")
    public List<Citizenship> obywatelstwa;
    @JsonProperty("pkd")
    public List<String> pkd;
    @JsonProperty("pkdGlowny")
    public String mainPkd;
    @JsonProperty("dataRozpoczecia")
    public String creationDate;
    public String status;
    @JsonProperty("numerStatusu")
    public int statusNumber;
    @JsonProperty("telefon")
    public String phoneNumber;
    @JsonProperty("wspolnoscMajatkowa")
    public int sharedShiproperty;
    public String link;
}

public class Citizenship{
    public String symbol;
    @JsonProperty("kraj")
    public String country;
}

public class Properties{
    public String dc:title;
    public String dc:description;
    public String dc:language;
    public String schema:provider;
    public String schema:datePublished;
}

public class Root{
    public ArrayList<Firma> firma;
    public Properties properties;
}

public class Owner{
    @JsonProperty("imie")
    public String firstName;
    @JsonProperty("nazwisko")
    public String lastName;
    public String nip;
    public String regon;
}
