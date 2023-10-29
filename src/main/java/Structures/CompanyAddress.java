package Structures;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    public String ulic;

    public String getFirstAddressLane(){
        if(this.street == null || this.street.length() < 1){
            return this.city+" "+this.building;
        }
        return this.street+" "+this.building;
    }

    public String getSecondAddressLane(){
        return this.postCode+" "+community;
    }
}


