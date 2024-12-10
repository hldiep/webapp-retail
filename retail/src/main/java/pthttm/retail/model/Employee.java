package pthttm.retail.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name="Employee")
public class Employee {
    @Id
    @Column(name ="id")
    private String id;

    @Column(name="last_name")
    private String lastName;//ho va ten dem

    @Column(name="first_name", nullable = false)
    private String firstName;//ten

    @Column(name="phone", nullable = false)
    private String phone;

    @Column(name="email", nullable = false)
    private String email;

    private String password;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name="birthday")
    private LocalDate birthday;

    @Column(name="gender")
    private String gender;

    @CreationTimestamp
    @Column(name="created_at",updatable = false, nullable = false)
    private LocalDateTime createAt;

    @Column(name="flag",nullable = false)
    private boolean flag;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Collection<Product> products;

    @OneToMany(mappedBy= "employee", fetch = FetchType.LAZY)
    private Collection<OrderProduct> orders;

    public String getName(){
        return lastName + " " +  firstName;
    }

    public Employee() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public Collection<OrderProduct> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderProduct> orders) {
        this.orders = orders;
    }
}
