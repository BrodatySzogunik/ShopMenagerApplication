package Structures;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Company{
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
    @JsonProperty("dataWznowienia")
    public String resumptionDate;
    public String status;
    @JsonProperty("numerStatusu")
    public int statusNumber;
    @JsonProperty("telefon")
    public String phoneNumber;
    @JsonProperty("wspolnoscMajatkowa")
    public int sharedShiproperty;
    public String link;


    public String getName() {
        return name;
    }

    public CompanyAddress getCompanyAddress() {
        return companyAddress;
    }

    public Owner getOwner() {
        return owner;
    }
}

