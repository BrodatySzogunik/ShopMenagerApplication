package Structures.DataBase.Sales.Bill;

import Structures.DataBase.Sales.BillToProductToPrice.BillToProductToPrice;
import Structures.DataBase.Sales.CustomerComp.CustomerComp;
import Structures.DataBase.Sales.CustomerPriv.CustomerPriv;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name ="BILL")
public class Bill {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name="bill_id"
    )
    private Long id;

    @Column(nullable = false)
    private LocalDate saleDate;

    @ManyToOne
    @JoinColumn(
            name="customer_priv_id",
            insertable=false,
            updatable=false)
    private CustomerPriv customerPriv;


    @ManyToOne
    @JoinColumn(
            name="customer_comp_id",
            insertable=false,
            updatable=false)
    private CustomerComp customerComp;

    @OneToMany(mappedBy = "billId")
    private List<BillToProductToPrice> salePrices;


    public Bill(LocalDate saleDate, Long customerId) {
        this.saleDate = saleDate;
    }

    public Bill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }
}
