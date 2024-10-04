package be.vinci.ipl.carts;

import be.vinci.ipl.carts.models.CartModel;
import be.vinci.ipl.carts.repositories.CartRepository;
import be.vinci.ipl.carts.repositories.ProductsProxy;
import be.vinci.ipl.carts.repositories.UsersProxy;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  private final CartRepository cartRepository;
  private final UsersProxy usersProxy;
  private final ProductsProxy productsProxy;

  public CartService(CartRepository cartRepository, UsersProxy usersProxy, ProductsProxy productsProxy){
    this.cartRepository = cartRepository;
    this.usersProxy = usersProxy;
    this.productsProxy = productsProxy;
  }

    public boolean userNotExists(String pseudo){
      try {
        usersProxy.readOne(pseudo);
        return false;
      }catch (FeignException.FeignClientException e){
        if (e.status() == 404) return true;
        else throw e;
      }
    }

    public boolean productNotExists(int id){
      try {
        productsProxy.readOne(id);
        return false;
      }catch (FeignException.FeignClientException e){
        if (e.status() == 404) return true;
        else throw e;
      }
    }

  public boolean addOne(CartModel cart) {
    if (cartRepository.existsByProduct_idAndPseudo_user(cart.getId(), cart.getPseudo_user())){
      return false;
    }
    cartRepository.save(cart);
    return true;
  }

}
