package be.vinci.ipl.products;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository){
    this.productRepository = productRepository;
  }


  public boolean createOne(ProductModel productModel){
    if (productRepository.existsById(String.valueOf(productModel.getId()))) return false;
    productRepository.save(productModel);
    return true;
  }


  public Iterable<ProductModel> getAll() {
    return productRepository.findAll();
  }

  public boolean deleteOne(ProductModel productModel){
    if (!productRepository.existsById(String.valueOf(productModel.getId()))){
      return false;
    }
    productRepository.delete(productModel);
    return true;
  }
}
