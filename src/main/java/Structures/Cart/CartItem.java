package Structures.Cart;

import Structures.DataBase.Products.Product.Product;
import lombok.Getter;

@Getter
public class CartItem {
    private Product product;
    private Integer amount;

    public CartItem(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void increaseAmount(){
        if(this.getAmount()+ 1 <= this.getProduct().getQuantity()) this.amount++;
    }

    public void decreaseAmount(){
        if(this.amount > 0) this.amount--;
    }
}
