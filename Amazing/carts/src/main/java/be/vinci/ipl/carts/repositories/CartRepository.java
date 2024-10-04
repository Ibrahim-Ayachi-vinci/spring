package be.vinci.ipl.carts.repositories;

import be.vinci.ipl.carts.models.CartModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<CartModel, Integer> {

  boolean existsByProduct_idAndPseudo_user(int product_id, String pseudo_user);

}
