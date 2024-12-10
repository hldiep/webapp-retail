package pthttm.retail.model;

import jakarta.persistence.*;

@Entity
@Table(name="Product_nutrient")
public class ProductNutrient {

    @Id
    @Column(name="id")
    private String id;

    //@Column(name = "product_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    //@Column(name = "nutrient_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "nutrient_id")
    private Nutrient nutrient;

    @Column(name="flag", nullable = false)
    private boolean flag;

    public ProductNutrient() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Nutrient getNutrient() {
        return nutrient;
    }

    public void setNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
