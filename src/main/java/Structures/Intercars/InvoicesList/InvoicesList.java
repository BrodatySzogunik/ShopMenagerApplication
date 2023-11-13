package Structures.Intercars.InvoicesList;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement(name = "DocumentElement")
public class InvoicesList {
    public ArrayList<nag> nag;
}
