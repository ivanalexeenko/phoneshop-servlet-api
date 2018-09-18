package com.es.phoneshop.model.classes;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.interfaces.CartServiceInterface;
import com.es.phoneshop.model.interfaces.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class CartService implements CartServiceInterface {

    public static final String CART_ATTRIBUTE_NAME = "cart";

    private static class CartServiceHelper {
        private static final CartService INSTANCE = new CartService();
    }
    public static CartService getInstance() {
        return CartServiceHelper.INSTANCE;
    }

    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CartService.CART_ATTRIBUTE_NAME);
        if(cart == null) {
            cart = new Cart();
            session.setAttribute(CartService.CART_ATTRIBUTE_NAME,cart);
        }
        if(session.isNew()) {
            return new Cart();
        }
        return cart;
    }

    public void add(Cart cart,Product product,Integer quantity) throws CommonException {
        addOrUpdate(cart,product,quantity,true);
    }

    public void update(Cart cart,Product product,Integer quantity) throws CommonException {
        addOrUpdate(cart,product,quantity,false);
    }

    private void addOrUpdate(Cart cart, Product product, Integer quantity, boolean add) throws CommonException {
        if(product.getStock() < quantity) {
            throw new CommonException(ApplicationMessage.NOT_ENOUGH);
        }
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream().filter(cartItem -> cartItem.getProduct().equals(product)).findAny();
        if(!optionalCartItem.isPresent()) {
            product.setStock(product.getStock() - quantity);
            cart.getCartItems().add(new CartItem(product,quantity));
        }
        else {
            CartItem currentItem = optionalCartItem.get();

            int newQuantity = add ? (currentItem.getQuantity() + quantity) : quantity;

            if(add) {
                product.setStock(product.getStock() - quantity);
            }
            else {
                if(quantity < currentItem.getQuantity()) {
                    product.setStock(product.getStock() + (currentItem.getQuantity() - quantity));
                }
                if(quantity > currentItem.getQuantity()) {
                    product.setStock(product.getStock() - (quantity - currentItem.getQuantity()));
                }
            }
            currentItem.setQuantity(newQuantity);
            currentItem.setProduct(product);
        }
    }

    public void remove(Cart cart,Long productId) throws CommonException {
        ProductDao productDao = ArrayListProductDao.getInstance();
        Product product = productDao.getProduct(productId);
        List<CartItem> itemList = cart.getCartItems();
        Optional<CartItem> itemToRemove = itemList.stream().filter(cartItem -> cartItem.getProduct().equals(product)).findAny();
        if(!itemToRemove.isPresent()) {
            throw new CommonException(ApplicationMessage.NOT_FOUND);
        }
        product.setStock(product.getStock() + itemToRemove.get().getQuantity());
        itemToRemove.ifPresent(itemList::remove);
    }

}
