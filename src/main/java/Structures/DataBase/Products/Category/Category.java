package Structures.DataBase.Products.Category;

import Structures.DataBase.Products.Product.Product;
import Structures.DataBase.Products.Property.Property;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="CATEGORY")
public class Category {
    @Id
    @Column(
            name = "category_id",
            nullable = false,
            updatable = false
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    private String categoryName;


    @OneToMany(mappedBy = "categoryId")
    private List<Product> products;

    @OneToMany(mappedBy = "categoryId")
    private List<Property> properties;

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
