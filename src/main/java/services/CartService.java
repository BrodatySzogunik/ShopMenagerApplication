package services;

import Structures.Cart.CartItem;
import Structures.DataBase.Products.Product.Product;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    private static CartService instance;
    private List<CartItem>  productList;

    private CartService(){
        this.productList = new ArrayList<>();
    }

    public static CartService getInstance(){
        if(instance == null){
            instance = new CartService();
        }
        return instance;
    }


    public List<CartItem> getProductList() {
        return productList;
    }

    public void clearCart(){
        this.productList.clear();
    }

    public void addProductToCart(Product product){
        this.productList.add(new CartItem(product,1 ));
    }

    public void removeProductFromCart(int index){
        this.productList.remove(index);
    }
    public void removeProductFromCart(CartItem cartItem){
        this.productList.remove(cartItem);
    }

    public void increaseProductAmount(int index){
        this.productList.get(index).increaseAmount();
    }
    public void decreaseProductAmount(int index){
        this.productList.get(index).decreaseAmount();
    }

}
