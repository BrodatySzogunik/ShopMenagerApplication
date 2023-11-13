package Structures.Intercars.Invoice;

import jakarta.xml.bind.annotation.XmlElement;

public class poz {
    public int lp;
    public String tow_kod;
    @XmlElement(name="indeks")
    public String index;
    
    public String kod_kre;
    public String nazwa;
    public String opis;
    public int ilosc;
    public double cena;
    public double cenadet;
    public int vat;
    public Object sww;
    public Object grunaz;
    public String nr_wz;
    public int cn;
    public String gtu;
}
