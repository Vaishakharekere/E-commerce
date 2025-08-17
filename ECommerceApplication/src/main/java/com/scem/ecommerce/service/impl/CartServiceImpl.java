package com.scem.ecommerce.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scem.ecommerce.dao.CartItemRepository;
import com.scem.ecommerce.dao.CartRepository;
import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Cart;
import com.scem.ecommerce.entity.CartItem;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.exception.ProductNotFoundException;
import com.scem.ecommerce.exception.UserNotFoundException;
import com.scem.ecommerce.service.CartService;
import com.scem.ecommerce.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
@Service
public class CartServiceImpl  implements CartService{
	
	 @Autowired
	private CartRepository cartRepo;
	 @Autowired
    private UserRepository userRepo;
	 @Autowired
    private ProductRepository productRepo;
	 @Autowired
    private CartItemRepository cartItemRepo;
	 @Autowired
    private final DiscountService discountService;
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    
    
   
	public CartServiceImpl(CartRepository cartRepo, UserRepository userRepo, ProductRepository productRepo,
			CartItemRepository cartItemRepo,DiscountService discountService) {
		this.cartRepo = cartRepo;
		this.userRepo = userRepo;
		this.productRepo = productRepo;
		this.cartItemRepo = cartItemRepo;
		this.discountService = discountService;
	}

	@Override
	public Cart getCartByUserId(Long userId) {
		User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
       
		return cartRepo.findByUser(user)
                .orElseGet(() -> { 
                	Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });
    }
	

	@Override
	public Cart addProductToCart(Long userId, Long productId, int quantity) {
	    
	    Cart cart = getCartByUserId(userId);
	    Product product = productRepo.findById(productId)
	            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

	    
	    double discountedPrice = discountService.computeEffectivePrice(productId, product.getPrice());

	    CartItem existingCartItem = null;
	    for (CartItem item : cart.getCartItems()) {
	        if (item.getProduct().getProductId().equals(productId)) {
	            existingCartItem = item;
	            break; 
	        }
	    }

	    if (existingCartItem != null) {
	        existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
	        existingCartItem.setProductPrice(discountedPrice); // Update price with discount
	    } else {
	        CartItem newCartItem = new CartItem(cart, product, quantity, discountedPrice);
	        cart.getCartItems().add(newCartItem);
	    }

	    updateCartTotal(cart);
	    return cartRepo.save(cart);
	}

	
	private void updateCartTotal(Cart cart) {
	    double total = 0.0;
	    for (CartItem item : cart.getCartItems()) {
	        total += item.getQuantity() * item.getProductPrice();
	    }
	    cart.setTotalPrice(total);
	}
	
	
	@Override
	public Cart removeProductFromCart(Long userId, Long productId) {
		Cart cart = getCartByUserId(userId);
		cart.getCartItems().removeIf(item -> item.getProduct().getProductId().equals(productId));
        updateCartTotal(cart);
        return cartRepo.save(cart);
    }  
		

	@Override
	public Cart updateProductQuantity(Long userId, Long productId, int quantity) {
		Cart cart = getCartByUserId(userId);
        cart.getCartItems().forEach(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
            }
        });
        updateCartTotal(cart);
        return cartRepo.save(cart);
    }
		


	@Override
	public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getCartItems().clear();
        updateCartTotal(cart);
        cartRepo.save(cart);
    }

	@Override
	public List<CartItem> getCartItems(Long userId) {
		return getCartByUserId(userId).getCartItems();
    }

}

