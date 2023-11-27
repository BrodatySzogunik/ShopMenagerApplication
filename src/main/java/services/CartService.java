package services;

import Structures.Cart.CartItem;
import Structures.DataBase.Products.Product.Product;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class CartService {
    private static CartService instance;
    private List<CartItem> productList;

    private PropertyChangeSupport propertyChangeSupport=new PropertyChangeSupport(this);

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


        Optional<CartItem> productInCart =this.productList.stream()
                .filter(cartItem -> Objects.equals(cartItem.getProduct().getId(), product.getId())).findFirst();

        Optional<CartItem> productAvailableInAmount = this.productList.stream()
                .filter(cartItem -> productInCart.isPresent() &&
                        productInCart.get().getAmount()+1 <= product.getQuantity() &&
                        cartItem.getProduct().getId() == productInCart.get().getProduct().getId()).findFirst();

        if(productAvailableInAmount.isPresent()){
            this.increaseProductAmount(this.productList.indexOf(productAvailableInAmount.get()));
        }else {
            if(productInCart.isEmpty() && product.getQuantity() > 0){
                this.productList.add(new CartItem(product,1 ));
                this.propertyChangeSupport.firePropertyChange("sumOfItems",1,2);
            }
        }
    }

    public void removeProductFromCart(int index){
        this.productList.remove(index);
        this.propertyChangeSupport.firePropertyChange("removeItem",1,2);
    }

    public void removeProductFromCart(CartItem cartItem){
        this.productList.remove(cartItem);
        this.propertyChangeSupport.firePropertyChange("removeItem",1,2);
    }

    public void increaseProductAmount(int index){
        this.productList.get(index).increaseAmount();
        this.propertyChangeSupport.firePropertyChange("sumOfItems",1,2);
    }
    public void decreaseProductAmount(int index){
        var cartItem = this.productList.get(index);
        if(cartItem.getAmount()>1){
            cartItem.decreaseAmount();
        }
        this.propertyChangeSupport.firePropertyChange("sumOfItems",2,1);
    }

    public Double getProductValue(){
        Double productsValue = 0d;
        for(CartItem item : this.productList){
            productsValue += item.getProduct().getSellPrice() * item.getAmount();
        }
        return productsValue;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        System.out.println("property listener added");
        propertyChangeSupport.addPropertyChangeListener(listener);
        System.out.println(propertyChangeSupport.getPropertyChangeListeners()[0]);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
