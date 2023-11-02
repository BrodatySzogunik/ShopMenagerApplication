package Structures.CEIDG;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner{
    @JsonProperty("imie")
    public String firstName;
    @JsonProperty("nazwisko")
    public String lastName;
    public String nip;
    public String regon;

    public String getNip() {
        return nip;
    }

    public String getRegon() {
        return regon;
    }
}
