package pthttm.retail.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name="Nutrient")
public class Nutrient {
    @Id
    @Column(name="id",nullable = false)
    private String id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="detail")
    private String detail;

    @Column(name="flag",nullable = false)
    private boolean flag;

    @OneToMany(mappedBy = "nutrient",fetch = FetchType.EAGER)
    private Collection<ProductNutrient> products;

    public Nutrient() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Collection<ProductNutrient> getProducts() {
        return products;
    }

    public void setProducts(Collection<ProductNutrient> products) {
        this.products = products;
    }
}
