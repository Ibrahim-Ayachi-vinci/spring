package be.vinci.ipl.carts.repositories;

import be.vinci.ipl.carts.models.CartModel;
import be.vinci.ipl.carts.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<CartModel, Integer> {

  boolean existsByProductIdAndPseudoUser(int productId, String pseudoUser);


  Optional<CartModel> findCartModelByProductIdAndPseudoUser(int productId, String pseudoUser);


  List<CartModel> findCartModelsByPseudoUser(String pseudo);

  CartModel getByPseudoUser(String pseudoUser);

  boolean existsByPseudoUser(String pseudoUser);

  List<CartModel> findCartModelsByProductId(int productId);


}
