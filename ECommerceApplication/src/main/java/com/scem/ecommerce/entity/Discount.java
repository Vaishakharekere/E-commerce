package com.scem.ecommerce.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "discounts")
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long discountId;
	
	 @Column(nullable = false)
	 private String discountCode;
	 
	 @Column(nullable = false)
	 private double discountValue; 
	 
	 @Column(nullable = false)
	 private boolean isPercentage;
	 
	 private LocalDate startDate;
	 
	 private LocalDate endDate;
	 
	 @ManyToOne
	 @JoinColumn(name = "product_id")
	 private Product product;
	 
	 public Discount() {
		
	}

	 public Discount(String discountCode, double discountValue, boolean isPercentage, LocalDate startDate,
			LocalDate endDate, Product product) {
		super();
		this.discountCode = discountCode;
		this.discountValue = discountValue;
		this.isPercentage = isPercentage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.product = product;
	 }

	 public Long getDiscountId() {
		 return discountId;
	 }

	 public void setDiscountId(Long discountId) {
		 this.discountId = discountId;
	 }

	 public String getDiscountCode() {
		 return discountCode;
	 }

	 public void setDiscountCode(String discountCode) {
		 this.discountCode = discountCode;
	 }

	 public double getDiscountValue() {
		 return discountValue;
	 }

	 public void setDiscountValue(double discountValue) {
		 this.discountValue = discountValue;
	 }

	 public boolean isPercentage() {
		 return isPercentage;
	 }

	 public void setPercentage(boolean isPercentage) {
		 this.isPercentage = isPercentage;
	 }

	 public LocalDate getStartDate() {
		 return startDate;
	 }

	 public void setStartDate(LocalDate startDate) {
		 this.startDate = startDate;
	 }

	 public LocalDate getEndDate() {
		 return endDate;
	 }

	 public void setEndDate(LocalDate endDate) {
		 this.endDate = endDate;
	 }

	 public Product getProduct() {
		 return product;
	 }

	 public void setProduct(Product product) {
		 this.product = product;
	 }

	 @Override
	 public String toString() {
		return "Discount [discountId=" + discountId + ", discountCode=" + discountCode + ", discountValue="
				+ discountValue + ", isPercentage=" + isPercentage + ", startDate=" + startDate + ", endDate=" + endDate
				+ "]";
	 }
	 
	 
}
