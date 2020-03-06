package com.cost.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.cost.utils.Categories;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Record")
public class Record implements Comparable<Record> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "record_id", unique = true)
	private int recordId;
	
	@Column(name = "cost")
	private double cost;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private Categories category;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "date")
	private LocalDateTime date;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "record_date")
	private LocalDate recordDate;

	@Column(name = "user_id")
	private int userId;
	
	public Record(Categories category, double cost, LocalDate recordDate) {
		super();
		this.category = category;
		this.cost = cost;
		this.recordDate = recordDate;
	}
	
	public Record(int recordId, double cost, Categories category, LocalDateTime date, LocalDate recordDate, int userId) {
		super();
		this.recordId = recordId;
		this.cost = cost;
		this.category = category;
		this.date = date;
		this.recordDate = recordDate;
		this.userId = userId;
	}
	
	public Record() {}

	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public Categories getCategory() {
		return category;
	}
	public void setCategory(Categories category) {
		this.category = category;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public LocalDate getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(LocalDate recordDate) {
		this.recordDate = recordDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "[" + recordId + ", " + cost + ", " + category + ", " + date
				+ ", " + recordDate + ", " + userId + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Record record = (Record) o;
        return recordId == record.recordId &&
        		cost == record.cost &&
        		Objects.equals(category, record.category) &&
        		//Objects.equals(date, record.date) &&
        		Objects.equals(recordDate, record.recordDate) &&
        		userId == record.userId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(recordId, cost, category, recordDate, userId);
	}

	@Override
	public int compareTo(Record r) {
		if (getDate() == null || r.getDate() == null) {
			return 0;
		}
		return getDate().compareTo(r.getDate());
	}
}
