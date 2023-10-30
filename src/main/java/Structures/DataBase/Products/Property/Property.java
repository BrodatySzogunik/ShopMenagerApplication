package Structures.DataBase.Products.Property;

import Structures.DataBase.Products.Category.Category;
import jakarta.persistence.*;

@Entity
@Table(name = "PROPERTY")
public class Property {
    @Id
    @Column(
            name="property_id",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Long id;
    @Column(
            name="property_name"
    )
    private String propertyName;

    @ManyToOne
    @JoinColumn(
            name = "category_id",
            insertable = false,
            updatable = false
    )
    private Category categoryId;
}
