package Structures.DataBase.Sales.CustomerPriv;

import Structures.DataBase.Sales.Bill.Bill;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="CUSTOMER_PRIV")
public class CustomerPriv {

    @Id
    @SequenceGenerator(
            name="customer_sequence",
            sequenceName="customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    @Column(
            name ="customer_priv_id",
            nullable = false,
            insertable=false,
            updatable=false
    )
    private Long id;
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;
    @Column(
            name="pesel",
            nullable = false
    )
    private String pesel;

    @OneToMany(mappedBy = "customerPriv")
    private List<Bill> bills;

    public CustomerPriv(String firstName, String lastName, String pesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public CustomerPriv() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }
}
