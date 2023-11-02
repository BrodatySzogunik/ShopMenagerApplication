package Structures.CEIDG;

public class CompanyInfo {
    private String companyName;
    private Address companyAddress;
    private String companyNip;
    private String companyRegon;


    public CompanyInfo(String companyName, Address companyAddress, Nip companyNip) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyNip = companyNip.nip;
    }
    public CompanyInfo(String companyName, Address companyAddress, Nip companyNip,Regon companyRegon) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyNip = companyNip.nip;
        this.companyRegon = companyRegon.regon;
    }

    public CompanyInfo(String companyName, Address companyAddress, Regon companyRegon) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyRegon = companyRegon.regon;
    }



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Address getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Address companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyNip() {
        return companyNip;
    }

    public void setCompanyNip(String companyNip) {
        this.companyNip = companyNip;
    }

    public String getCompanyRegon() {
        return companyRegon;
    }

    public void setCompanyRegon(String companyRegon) {
        this.companyRegon = companyRegon;
    }

    @Override
    public String toString(){
        return this.companyName+" "+this.companyAddress.toString()+" "+this.companyNip+" "+this.companyRegon;
    }

}
