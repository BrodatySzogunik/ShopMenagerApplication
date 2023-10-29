package Structures;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("adresat")
    public String addressee;
    public String terc;
    public String simc;
    public String ulic;
}

