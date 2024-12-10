package pthttm.retail.model;

import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Entity
@Table(name="Product")
public class Product {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="detail")
    private String detail;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", columnDefinition = "money", nullable = false)
    private Long price;

   // @Column(name = "category_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    //@Column(name = "unit_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    //@Column(name = "brand_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

   /* @Temporal(TemporalType.TIMESTAMP)*/
    @CreationTimestamp
    @Column(name="created_at",updatable = false, nullable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name="last_update",nullable = false)
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name ="employee_id")
    private Employee employee;

    @Column(name="flag",nullable = false)
    private boolean flag;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Collection<OrderItem> orders;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Collection<ProductNutrient> nutrients;

    public Product( String id){
        this.id=id;
    }

    public String getFormattedOfCreateAt(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return createAt.format(formatter);
    }

    public String getFormattedOfLastUpdate(){
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return lastUpdate.format(formatter);
    }

    public Product(){
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Collection<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderItem> orders) {
        this.orders = orders;
    }

    public Collection<ProductNutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(Collection<ProductNutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", category=" + category +
                ", unit=" + unit +
                ", brand=" + brand +
                ", createAt=" + createAt +
                ", lastUpdate=" + lastUpdate +
                ", flag=" + flag +
                '}';
    }
}
