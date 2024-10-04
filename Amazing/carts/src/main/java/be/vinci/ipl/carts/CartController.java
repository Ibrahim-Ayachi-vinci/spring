package be.vinci.ipl.carts;

import be.vinci.ipl.carts.models.CartModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CartController {

    private CartService service;

    public CartController (CartService service){
      this.service = service;
    }

    @PostMapping("/carts/users/{pseudo}/products/{productId}")
  public ResponseEntity<Void> addProduct(@PathVariable String pseudo, @PathVariable int productId, @RequestBody
        CartModel cart){
      if (service.userNotExists(pseudo)){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }
      if (service.productNotExists(productId)){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }
      boolean added = service.addOne(cart);
      if (!added){
        throw new ResponseStatusException(HttpStatus.CONFLICT);
      }else {
        return new ResponseEntity<>(HttpStatus.CREATED);
      }
    }

}
