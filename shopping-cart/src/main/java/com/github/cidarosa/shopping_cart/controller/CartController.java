package com.github.cidarosa.shopping_cart.controller;

import com.github.cidarosa.shopping_cart.model.Cart;
import com.github.cidarosa.shopping_cart.model.Item;
import com.github.cidarosa.shopping_cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Cart addItem(@PathVariable Long id, @RequestBody Item item){
        Optional <Cart> savedCart = cartRepository.findById(id);
        Cart cart;

//        if (savedCart.isPresent()) {
//            cart = savedCart.get();
//        } else {
//            cart = new Cart(id);
//        }

        cart = savedCart.orElseGet(() -> new Cart(id));


        cart.getItems().add(item);


        return cartRepository.save(cart);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Cart> findById(@PathVariable Long id){
        return cartRepository.findById(id);
    }

    @RequestMapping( method = RequestMethod.GET)
    public List<Cart> findAll(){
        return (List<Cart>) cartRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void clear(@PathVariable Long id){
        cartRepository.deleteById(id);
    }

}
