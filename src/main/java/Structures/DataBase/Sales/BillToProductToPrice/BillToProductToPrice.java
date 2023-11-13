package Structures.DataBase.Sales.BillToProductToPrice;

import Structures.DataBase.Products.Product.Product;
import Structures.DataBase.Sales.Bill.Bill;
import jakarta.persistence.*;

@Entity
@Table(name ="BILL_PRODUCT_PRICE")
public class BillToProductToPrice {
    @Id
    @Column(
            name="id",
            nullable = false,
            insertable = false,
            updatable = false
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name="bill_id",
            updatable=false)
    private Bill billId;

    @ManyToOne
    @JoinColumn(
            name="product_id",
            updatable=false)
    private Product productId;

    @Column(
            name = "amount"
    )
    private Integer amount;

    @Column(
            name = "sale_price"
    )
    private double salePrice;

    @Column(
            name = "buy_price"
    )
    private double buyPrice;

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Bill getBillId() {
        return billId;
    }

    public void setBillId(Bill billId) {
        this.billId = billId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }
}
