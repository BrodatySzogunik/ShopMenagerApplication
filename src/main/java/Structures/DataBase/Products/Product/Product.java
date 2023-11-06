package Structures.DataBase.Products.Product;

import Structures.DataBase.Products.Category.Category;
import Structures.DataBase.Products.OemToProduct.OemToProduct;
import Structures.DataBase.Sales.BillToProductToPrice.BillToProductToPrice;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="PRODUCT")
public class Product {
    @Id
    @Column(
            name = "product_id",
            nullable = false,
//            insertable = false,
            updatable = false
    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE
//    )
    private String id;

    private int quantity;

    private double buyPrice;

    private double sellPrice;

    private String name;
    private String description;

    @ManyToOne()
    @JoinColumn(
            name="category_id",
            insertable=false,
            updatable=false)
    private Category categoryId;

    @OneToMany(mappedBy = "productId")
    private List<BillToProductToPrice> salePrices;

    @OneToMany(mappedBy = "productId")
    private List<OemToProduct> matchingOemCodes;

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
}
