package pthttm.retail.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name="Unit")
public class Unit {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="flag",nullable = false)
    private boolean flag;

    @OneToMany(mappedBy = "unit",fetch = FetchType.LAZY)
    private Collection<Product> products;

    public Unit() {
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

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}
