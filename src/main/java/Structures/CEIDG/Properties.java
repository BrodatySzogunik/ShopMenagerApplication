package Structures.CEIDG;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Properties{
    @JsonProperty("dc:title")
    public String title;
    @JsonProperty("dc:description")
    public String description;
    @JsonProperty("dc:language")
    public String language;
    @JsonProperty("schema:provider")
    public String provider;
    @JsonProperty("schema:datePublished")
    public String datePublished;
}