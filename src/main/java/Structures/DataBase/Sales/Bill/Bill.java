package Structures.DataBase.Sales.Bill;

import Structures.DataBase.Sales.BillToProductToPrice.BillToProductToPrice;
//import Structures.DataBase.Sales.CustomerComp.CustomerComp;
//import Structures.DataBase.Sales.CustomerPriv.CustomerPriv;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="BILL")
public class Bill {

    @Id
    @Column(
            name="bill_id",
            updatable=false
    )
    private String id;

    @Column(nullable = false)
    private Date saleDate;

    @Column(
            name="nip",
            updatable = false
    )
    private String nip;

    @OneToMany(mappedBy = "billId")
    private List<BillToProductToPrice> salePrices;

    public Bill() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}
