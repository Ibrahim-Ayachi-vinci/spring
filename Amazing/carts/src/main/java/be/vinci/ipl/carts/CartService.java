package be.vinci.ipl.carts;

import be.vinci.ipl.carts.models.CartModel;
import be.vinci.ipl.carts.models.Product;
import be.vinci.ipl.carts.models.User;
import be.vinci.ipl.carts.repositories.CartRepository;
import be.vinci.ipl.carts.repositories.ProductsProxy;
import be.vinci.ipl.carts.repositories.UsersProxy;
import feign.FeignException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

  public boolean addOne(CartModel cart) {
    if (cartRepository.existsByProductIdAndPseudoUser(cart.getId(), cart.getPseudoUser())){
      return false;
    }
    cartRepository.save(cart);
    return true;
  }

  public boolean deleteOneProduct(String pseudo, int productId){
    Optional<CartModel> cartModel = cartRepository.findCartModelByProductIdAndPseudoUser(productId, pseudo);

    if (cartModel.isEmpty()){
      return false;
    }
    cartRepository.deleteById(cartModel.get().getId());
    return true;
  }



  public Iterable<Product> readAllFromuser(String pseudo){
    List<CartModel> cartModelList = cartRepository.findCartModelsByPseudoUser(pseudo);
    
    List<Integer> listId = cartModelList.stream().map(CartModel::getProductId).toList();
    
    List<Product> productList = new ArrayList<>();

    for (Integer i : listId) {
      Product product = productsProxy.readOne(i);
      productList.add(product);
    }

    return productList;

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


  public boolean deleteCartUser(String pseudo) {

    if (!cartRepository.existsByPseudoUser(pseudo)){
      return false;
    }

    CartModel cartModel = cartRepository.getByPseudoUser(pseudo);

    cartRepository.delete(cartModel);
    return true;
  }

  public boolean deleteProductsOfAll(int productId) {

    if (productsProxy.readOne(productId) == null){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    List<CartModel> cartModelList = cartRepository.findCartModelsByProductId(productId);

    cartRepository.deleteAll(cartModelList);

    return true;

  }
}

