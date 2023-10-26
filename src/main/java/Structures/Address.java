package Structures;

public class Address {
    private String street;
    private String building;
    private String postCode;
    private String city;

    public Address(String street, String building, String postCode, String city) {
        this.street = street;
        this.building = building;
        this.postCode = postCode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString(){
        return this.street+" "+this.building+" "+this.postCode+" "+this.city;
    }
}
