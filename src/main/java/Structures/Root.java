package Structures;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Root{
    @JsonProperty("firma")
    public List<Company> companies;
    public Properties properties;
}
