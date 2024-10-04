package be.vinci.ipl.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {

  private static final Map<Integer, ProductModel> products = new HashMap<>();

  private final ProductService productService;

  public ProductController(ProductService productService){
    this.productService = productService;
  }

  static {

    products.put(0, new ProductModel("Tartine", "nourriture", 20));
    products.put(1, new ProductModel("Pizza", "nourriture", 10.50));
    products.put(2, new ProductModel("Ordinateur", "tech", 500));
  }

  @GetMapping("/products")
  public Iterable<ProductModel> readAll(){
    return productService.getAll();
  }

  @PostMapping("/products")
  public ResponseEntity<Void> createOne(@RequestBody ProductModel product){
    if(product.invalid()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    boolean created = productService.createOne(product);
    if (!created){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<Void> updateOne(@PathVariable int id, @RequestBody ProductModel product){
    if(product.getId() != id || product.invalid() || !exist(id)){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    ProductModel updatedProduct = products.get(id);
    updatedProduct.setCategory(product.getCategory());
    updatedProduct.setName(product.getName());
    updatedProduct.setPrice(product.getPrice());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/products/{id}")
  public void deleteOne(@PathVariable int id, @RequestBody ProductModel product){

    productService.deleteOne(product);
  }

  public boolean exist(int id){
    return products.containsKey(id);
  }

}
