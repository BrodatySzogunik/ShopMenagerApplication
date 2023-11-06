package Structures.DataBase.Products.OemToProduct;

import Structures.DataBase.Products.Product.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="OEM_PRODUCT")
public class OemToProduct {
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
    @Getter
    @Column(
            name="oem",
            nullable = false
    )
    private String oem;

    @Getter
    @ManyToOne
    @JoinColumn(
            name="productId",
            nullable = false,
            updatable = false
    )
    private Product productId;

    public void setOem(String oem) {
        this.oem = oem;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }
}
