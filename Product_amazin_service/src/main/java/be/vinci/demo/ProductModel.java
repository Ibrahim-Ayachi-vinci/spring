package be.vinci.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Entity(name = "products")
public class ProductModel {
  @Id @Column(nullable = false)
  private int id;
  @Setter @Column(nullable = false)
  private String name;
  @Setter @Column(nullable = false)
  private String category;
  @Setter @Column(nullable = false)
  private double price;


  public ProductModel(String name, String category, double price) {
    this.name = name;
    this.category = category;
    this.price = price;
    }


  public boolean invalid() {
    return name == null || name.isBlank() || category == null || category.isBlank() || price <= 0;
  }
}
