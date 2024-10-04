package be.vinci.ipl.carts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private CartService service;

    public CartController (CartService service){
      this.service = service;
    }

    @PostMapping("/carts/users/{pseudo}/products/{productId}")
  public ResponseEntity<Void> addProduct(@PathVariable String pseudo, @PathVariable int productId){

    }

}
