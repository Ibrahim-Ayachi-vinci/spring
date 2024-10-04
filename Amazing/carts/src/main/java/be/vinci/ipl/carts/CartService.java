package be.vinci.ipl.carts;

import org.springframework.stereotype.Service;

@Service
public class CartService {

  private final CartRepository cartRepository;

  public CartService(CartRepository cartRepository){
    this.cartRepository = cartRepository;
  }

    public boolean createOne(String pseudo, int id_product){

    }

}
