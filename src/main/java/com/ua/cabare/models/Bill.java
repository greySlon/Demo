package com.ua.cabare.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.PayStatus;
import com.ua.cabare.domain.PayType;
import com.ua.cabare.domain.SaleType;
import com.ua.cabare.hibernate.custom.types.MoneyConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bills")
public class Bill extends EntityManager<Long, Bill> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "bill_number")
  private int billNumber;

  @JsonProperty(value = "bill_date")
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  @Column(name = "bill_date", columnDefinition = "date")
  private LocalDateTime billDate;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id")
  private Employee employee;

  @JsonProperty(value = "table_number")
  @Column(name = "table_number")
  private Integer tableNumber;

  @JsonProperty(value = "number_of_persons")
  @Column(name = "number_of_persons")
  private Integer numberOfPersons;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discount_id", nullable = true)
  private Discount discount;

  @Column(name = "sale_type_id")
  @JsonProperty(value = "sale_type")
  @Enumerated(EnumType.ORDINAL)
  private SaleType saleType;

  @Column(name = "pay_type_id")
  @JsonProperty(value = "pay_type")
  @Enumerated(EnumType.ORDINAL)
  private PayType payType;

  @JsonIgnore
  @Column(name = "pay_status_id")
  @Enumerated(EnumType.ORDINAL)
  private PayStatus payStatus;

  @JsonProperty(value = "order_items")
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "bill", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems;

  @JsonProperty(defaultValue = "0")
  @Convert(converter = MoneyConverter.class)
  @Column(name = "money_paid")
  private Money paid;

  @JsonIgnore
  @Column(name = "opened", nullable = false, columnDefinition = "BIT(1) NULL DEFAULT 1")
  private boolean opened;

  public Bill() {
    this.orderItems = new ArrayList<>();
    this.paid = Money.ZERO;
  }

  public Money getPaid() {
    return paid;
  }

  public void setPaid(Money paid) {
    this.paid = paid;
  }

  public boolean isOpened() {
    return opened;
  }

  public void setOpened(boolean opened) {
    this.opened = opened;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public SaleType getSaleType() {
    return saleType;
  }

  public void setSaleType(SaleType saleType) {
    this.saleType = saleType;
  }

  public int getBillNumber() {
    return billNumber;
  }

  public void setBillNumber(int billNumber) {
    this.billNumber = billNumber;
  }

  public LocalDateTime getBillDate() {
    return billDate;
  }

  public void setBillDate(LocalDateTime billDate) {
    this.billDate = billDate;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Integer getTableNumber() {
    return tableNumber;
  }

  public void setTableNumber(Integer tableNumber) {
    this.tableNumber = tableNumber;
  }

  public Integer getNumberOfPersons() {
    return numberOfPersons;
  }

  public void setNumberOfPersons(Integer numberOfPersons) {
    this.numberOfPersons = numberOfPersons;
  }

  public Discount getDiscount() {
    return discount;
  }

  public void setDiscount(Discount discount) {
    this.discount = discount;
  }

  public PayType getPayType() {
    return payType;
  }

  public void setPayType(PayType payType) {
    this.payType = payType;
  }

  public PayStatus getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(PayStatus payStatus) {
    this.payStatus = payStatus;
  }

  public void addPayment(Money payment) {
    this.paid = this.paid.add(payment);
  }
}
