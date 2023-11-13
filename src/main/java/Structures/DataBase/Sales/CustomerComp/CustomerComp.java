//package Structures.DataBase.Sales.CustomerComp;
//
//import Structures.DataBase.Sales.Bill.Bill;
//import jakarta.persistence.*;
//import lombok.Getter;
//
//import java.util.List;
//
//
//@Entity
//@Table(name="CUSTOMER_COMP")
//public class CustomerComp {
//    @Getter
//    @Id
//    @SequenceGenerator(
//            name="customer_sequence",
//            sequenceName="customer_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "customer_sequence"
//    )
//    @Column(
//            name ="customer_comp_id",
//            nullable = false,
//            insertable=false,
//            updatable=false
//    )
//    private Long id;
//
//    @Column(
//            name="nip",
//            nullable = false
//    )
//    private String nip;
//    @Column(
//            name="regon",
//            nullable = false
//    )
//    private String regon;
//
//
//    @OneToMany(mappedBy = "customerComp")
//    private List<Bill> bills;
//
//    public CustomerComp() {
//    }
//
//    public CustomerComp(String nip, String regon) {
//        this.nip = nip;
//        this.regon = regon;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getNip() {
//        return nip;
//    }
//
//    public void setNip(String nip) {
//        this.nip = nip;
//    }
//
//    public String getRegon() {
//        return regon;
//    }
//
//    public void setRegon(String regon) {
//        this.regon = regon;
//    }
//}
