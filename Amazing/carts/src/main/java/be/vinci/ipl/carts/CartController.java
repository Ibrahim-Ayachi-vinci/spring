package be.vinci.ipl.carts;

import be.vinci.ipl.carts.models.CartModel;
import be.vinci.ipl.carts.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @DeleteMapping("carts/users/{pseudo}/products/{productId}")
  public void deleteOneProduct(@PathVariable String pseudo, @PathVariable int productId){
      boolean found = service.deleteOneProduct(pseudo , productId);
      if (!found) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }
    }

    @GetMapping("/carts/users/{pseudo}")
  public Iterable<Product> readAllFromUser(@PathVariable String pseudo){
      return service.readAllFromuser(pseudo);
    }

    @DeleteMapping("carts/users/{pseudo}")
  public void deleteCartUser(@PathVariable String pseudo){
      boolean found = service.deleteCartUser(pseudo);

      if (!found){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("carts/products/{productId}")
  public void deleteProductsOfAll(@PathVariable int productId){
      boolean found = service.deleteProductsOfAll(productId);

      if (!found){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }

    }



}
