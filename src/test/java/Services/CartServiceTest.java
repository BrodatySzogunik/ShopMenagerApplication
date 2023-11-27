package Services;

import Structures.DataBase.Products.Product.Product;
import Utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.CartService;

import static org.junit.jupiter.api.Assertions.*;


class CartServiceTest {

    private CartService instance;

    @BeforeEach
    public void init(){

        this.instance = CartService.getInstance();
        this.instance.clearCart();
    }


    @Test
    void getProductList() {
        var product1 =
                new Product("4455aa",2,18.23,
                        20.99,"Product1","Product 1 description");
        var product2 = new Product("5566bb",9,11.23,
                29.99,"Product2","Product 2 description");

        var productList = this.instance.getProductList();
        assertEquals(0 , productList.size());
        this.instance.addProductToCart(product1);
        this.instance.addProductToCart(product2);
        productList = this.instance.getProductList();
        assertEquals(2, productList.size());
        this.instance.removeProductFromCart(0);
        productList = this.instance.getProductList();
        assertEquals(productList.size(), 1);
    }

    @Test
    void clearCart() {
        var product1 =
                new Product("4455aa",2,18.23,
                        20.99,"Product1","Product 1 description");

        var product2 = new Product("5566bb",9,11.23,
                29.99,"Product2","Product 2 description");

        this.instance.addProductToCart(product1);
        this.instance.addProductToCart(product2);
        this.instance.clearCart();

        assertEquals(this.instance.getProductList().size() ,0 );

        this.instance.addProductToCart(product1);
        System.out.println(this.instance.getProductList().get(0));
        this.instance.removeProductFromCart(0);
        this.instance.clearCart();

        assertEquals(this.instance.getProductList().size() ,0 );
        this.instance.addProductToCart(product1);
        this.instance.addProductToCart(product2);
        this.instance.removeProductFromCart(0);

        assertEquals(this.instance.getProductList().size(), 1);
        this.instance.clearCart();

        assertEquals(this.instance.getProductList().size() ,0 );


    }

    @Test
    void addProductToCart() {
        var product1 =
                new Product("4455aa",2,18.23,
                        20.99,"Product1","Product 1 description");

        var product2 = new Product("5566bb",9,11.23,
                29.99,"Product2","Product 2 description");


        //add every product one time
        this.instance.addProductToCart(product1);
        this.instance.addProductToCart(product2);

        // we should have only 2 items at cart and each of them should be at 1 amount
        assertEquals(2, this.instance.getProductList().size());
        assertEquals(1, this.instance.getProductList().get(0).getAmount());
        assertEquals(1, this.instance.getProductList().get(1).getAmount());


        //add product 1 one more time to check if its amount will increase
        this.instance.addProductToCart(product1);

        assertEquals("Product1", this.instance.getProductList().get(0).getProduct().getName());
        assertEquals(2, this.instance.getProductList().get(0).getAmount());

        // adding the same product again should only increase its amount instead of adding another item to cart

        assertEquals(2, this.instance.getProductList().size());

        //add product 1 to check if adding is limited to product quantity as it should be
        this.instance.addProductToCart(product1);

        assertEquals("Product1", this.instance.getProductList().get(0).getProduct().getName());
        assertEquals(2, this.instance.getProductList().get(0).getAmount());

        //add product 2 few times to chek if its amount is increased correctly

        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);

        // adding the same product few more times should only increase its amount instead of adding another item to cart
        assertEquals(2, this.instance.getProductList().size());
        assertEquals("Product2", this.instance.getProductList().get(1).getProduct().getName());
        assertEquals(4, this.instance.getProductList().get(1).getAmount());

    //        znaleziony błąd - przy dodawniu drugiego produktu po raz kolejny, zwiększasię ilość produktu dodanego jako pierwszy
    }

    @Test
    void removeProductFromCart() {
        var product1 =
                new Product("4455aa",2,18.23,
                        20.99,"Product1","Product 1 description");
        var product2 = new Product("5566bb",9,11.23,
                29.99,"Product2","Product 2 description");

        // add every product in maximum amount
        this.instance.addProductToCart(product1);
        this.instance.addProductToCart(product1);
        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);
        this.instance.addProductToCart(product2);

        this.instance.removeProductFromCart(0);

        assertFalse(this.instance.getProductList().stream().filter(item -> product1.getId().equals(item.getProduct().getId())).findAny().isPresent());
        assertTrue(this.instance.getProductList().stream().filter(item -> product2.getId().equals(item.getProduct().getId())).findAny().isPresent());

        this.instance.removeProductFromCart(0);

        assertFalse(this.instance.getProductList().stream().filter(item -> product1.getId().equals(item.getProduct().getId())).findAny().isPresent());
        assertFalse(this.instance.getProductList().stream().filter(item -> product2.getId().equals(item.getProduct().getId())).findAny().isPresent());

    }

    @Test
    void increaseProductAmount() {
        var product1 =
                new Product("4455aa",2,18.23,
                        20.99,"Product1","Product 1 description");

        var product2 = new Product("5566bb",9,11.23,
                29.99,"Product2","Product 2 description");

        //add every product one time
        this.instance.addProductToCart(product1);
        this.instance.addProductToCart(product2);

        assertEquals(1, this.instance.getProductList().get(0).getAmount());
        assertEquals(1, this.instance.getProductList().get(1).getAmount());


        //increase product 1 one more time to check if its amount will increase
        this.instance.increaseProductAmount(0);

        assertEquals("Product1", this.instance.getProductList().get(0).getProduct().getName());
        assertEquals(2, this.instance.getProductList().get(0).getAmount());

        //increase product 1 to check if adding is limited to product quantity as it should be
        this.instance.increaseProductAmount(0);

        assertEquals("Product1", this.instance.getProductList().get(0).getProduct().getName());
        assertEquals(2, this.instance.getProductList().get(0).getAmount());

        //increase product 2 few times to chek if its amount is increased correctly
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);

        assertEquals("Product2", this.instance.getProductList().get(1).getProduct().getName());
        assertEquals(4, this.instance.getProductList().get(1).getAmount());
    }

    @Test
    void decreaseProductAmount() {
        var product1 =
                new Product("4455aa",2,18.23,
                        20.99,"Product1","Product 1 description");
        var product2 = new Product("5566bb",9,11.23,
                29.99,"Product2","Product 2 description");

        //add every product one time
        this.instance.addProductToCart(product1);
        this.instance.addProductToCart(product2);

        assertEquals(1, this.instance.getProductList().get(0).getAmount());
        assertEquals(1, this.instance.getProductList().get(1).getAmount());

        //increase every product to its maximum amount
        this.instance.increaseProductAmount(0);
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);

        //both product should be at their maximum amount
        assertEquals(2, this.instance.getProductList().get(0).getAmount());
        assertEquals(9, this.instance.getProductList().get(1).getAmount());

        //using decrease function should decrease specific item
        this.instance.decreaseProductAmount(0);
        assertEquals(1, this.instance.getProductList().get(0).getAmount());
        this.instance.decreaseProductAmount(0);
        assertEquals(9, this.instance.getProductList().get(1).getAmount());

        //decrease function should not decrease item amount when there is only one in cart - we have remove option to remove it from cart
        this.instance.decreaseProductAmount(0);
        this.instance.decreaseProductAmount(1);
        this.instance.decreaseProductAmount(1);
        this.instance.decreaseProductAmount(1);
        this.instance.decreaseProductAmount(1);
        this.instance.decreaseProductAmount(1);
        this.instance.decreaseProductAmount(1);
        this.instance.decreaseProductAmount(1);
        this.instance.decreaseProductAmount(1);
        assertEquals(1, this.instance.getProductList().get(0).getAmount());
        assertEquals(1, this.instance.getProductList().get(1).getAmount());

        //Try decreasing one more time
        this.instance.decreaseProductAmount(0);
        this.instance.decreaseProductAmount(1);
        assertEquals(1, this.instance.getProductList().get(0).getAmount());
        assertEquals(1, this.instance.getProductList().get(1).getAmount());
    }

    @Test
    void getProductValue() {
        var product1 =
                new Product("4455aa",2,18.23,
                        20.99,"Product1","Product 1 description");
        var product2 = new Product("5566bb",9,11.23,
                29.99,"Product2","Product 2 description");

        this.instance.addProductToCart(product1);
        this.instance.addProductToCart(product2);

        //get product value should return sum of product values  multiplied by their ammounts

        assertEquals( 50.98 , this.instance.getProductValue());

        //increase product amount to see if cart value will also increase

        this.instance.increaseProductAmount(0);
        this.instance.increaseProductAmount(0);
        this.instance.increaseProductAmount(1);
        this.instance.increaseProductAmount(1);

        assertEquals(131.95, this.instance.getProductValue());

        // decrease product amount to see if cart value will also decrease


        this.instance.decreaseProductAmount(1);

        assertEquals(101.96, this.instance.getProductValue());

        //clear cart to see if cart value will fall to 0

        this.instance.clearCart();
        assertEquals(0d, this.instance.getProductValue());

    }
}