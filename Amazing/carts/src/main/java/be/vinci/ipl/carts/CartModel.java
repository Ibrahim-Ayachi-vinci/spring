package be.vinci.ipl.carts;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Entity(name = "carts")
public class CartModel {

  @Id
  @Column(nullable = false)
  private int id;

  @Column(nullable = false)
  private int product_id;

  @Column(nullable = false)
  private String pseudo_user;

  public CartModel (){

  }


}
