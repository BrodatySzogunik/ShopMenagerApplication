package Structures.DataBase.Products.OemToProduct;

import Structures.DataBase.Products.Product.Product;
import jakarta.persistence.*;

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
    private Long id;
    @Column(
            name="oem",
            nullable = false
    )
    private String oem;

    @ManyToOne
    @JoinColumn(
            name="productId",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Product productId;

}
