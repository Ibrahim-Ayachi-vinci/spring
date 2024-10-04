package be.vinci.ipl.products;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {


  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }


  @GetMapping("/products")
  public Iterable<ProductModel> readAll() {
    return productService.getAll();
  }

  @GetMapping("/products/{id}")
  public ProductModel readOne(@PathVariable int id){
    return productService.getOne(id);
  }

  @PostMapping("/products")
  public ResponseEntity<Void> createOne(@RequestBody ProductModel product) {
    if (product.invalid()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    boolean created = productService.createOne(product);
    if (!created) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/products/{id}")
  public void updateOne(@PathVariable int id, @RequestBody ProductModel product) {
    if (!Objects.equals(product.getId(), id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    if (product.invalid()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    boolean found = productService.updateOne(product);

    if (!found)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/products/{id}")
  public void deleteOne(@PathVariable int id, @RequestBody ProductModel product) {

    productService.deleteOne(product);
  }
}
