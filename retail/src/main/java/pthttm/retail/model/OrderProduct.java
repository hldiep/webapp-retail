package pthttm.retail.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Entity
@Table(name = "Order_product")
public class OrderProduct {

    @Id
    @Column(name="id")
    private String id;

   // @Column(name="user_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name="total_cost", columnDefinition = "money", nullable = false)
    private Long totalCost;

    @Column(name="address")
    private String address;

    @Column(name="pay_status", nullable = false)
    private String payStatus;

    @Column(name="ship_status", nullable = false)
    private String shipStatus;

    @CreationTimestamp
    @Column(name="created_at",updatable = false, nullable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "last_update",nullable =false)
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @Column(name="note")
    private String note;

    @Column(name = "flag", nullable = false)
    private boolean flag;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<OrderItem> items;

    public String getFormattedCreateAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return createAt.format(formatter);
    }

    public String getFormattedLastUpdate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm đ/MM/yyyy");
        return lastUpdate.format(formatter);
    }


    public String getFormattedShipStatus(){
        switch(shipStatus){
            case "CB":
                return "Đang chuẩn bị";
            case "HT":
                return "Đã hoàn tất";
            case "DG":
                return "Đang giao hàng";
            default:
                return shipStatus;
        }
    }

    public String getFormattedPayStatus(){
        switch(payStatus){
            case "HT":
                return "Đã thanh toán";
            case "CH":
                return "Chưa thanh toán";
            default:
                return payStatus;
        }
    }

    public int getQuantity(){
        return items.size();
    }


    public OrderProduct() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
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

    public Collection<OrderItem> getItems() {
        return items;
    }

    public void setItems(Collection<OrderItem> items) {
        this.items = items;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id='" + id + '\'' +
                ", customer=" + customer +
                ", totalCost=" + totalCost +
                ", address='" + address + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", shipStatus='" + shipStatus + '\'' +
                ", createAt=" + createAt +
                ", lastUpdate=" + lastUpdate +
                ", employee=" + employee +
                ", note='" + note + '\'' +
                ", flag=" + flag +
                ", items=" + items +
                '}';
    }
}
