package Structures;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Citizenship{
    public String symbol;
    @JsonProperty("kraj")
    public String country;
}

