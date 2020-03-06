package com.cost.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.cost.model.Record;
import com.cost.model.SmallRecord;
import com.cost.model.AllSmallRecord;
import com.cost.model.MediumRecord;
import com.cost.model.MonthlySum;

public interface RecordService {

	Record save(Record record);
	
	Boolean delete(int id);
	
	Record update(Record record);
	
	Record findById(int id);
	
	List<Record> findByUserId(int UserId);
	
	List<Record> findByUserIdOrderByDateDesc(int userId);
	
	List<Record> findAll();
	
	List<Record> findByCategory(String category);
	
	List<Record> findByUserIdCategory(int userId, String category);
	
	List<Record> findByUserIdAndRecordDateAfterAndRecordDateBefore(int userId, LocalDate start, LocalDate end);
	
	Page<Record> findAllByUserId(Pageable pageable, int userId); 
	
	List<MediumRecord> getBetweenRecordDateByCategoryAndRecordDate(int userId, LocalDate start, LocalDate end);
	
	List<SmallRecord> getBetweenRecordDateByCategory(int userId, LocalDate start, LocalDate end);
	
	List<AllSmallRecord> getAllBetweenRecordDateByCategoryAndUserId(LocalDate start, LocalDate end);
	
	// procedure for mysql
	int updateMV(int dummyInt); 
	//List<Record> findBetween(LocalDate start, LocalDate end);
	
//	void updatePsqlMV();
}
