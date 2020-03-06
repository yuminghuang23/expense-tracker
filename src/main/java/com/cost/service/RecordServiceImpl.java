package com.cost.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cost.model.Record;
import com.cost.model.SmallRecord;
import com.cost.model.AllSmallRecord;
import com.cost.model.MediumRecord;
import com.cost.repository.RecordRepository;

@Service
@Transactional
public class RecordServiceImpl implements RecordService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private RecordRepository recordRepository;

	@Override
	public Record save(Record record) {
		return recordRepository.save(record);
	}

	@Override
	public Boolean delete(int id) {
		boolean deleted = false; 
		if (recordRepository.existsById(id)) {
			recordRepository.deleteById(id);
			deleted = true;
		}
		return deleted; 
	}

	@Override
	public Record update(Record record) {
		return recordRepository.save(record);
	}
	
	@Override
	public Record findById(int id) {
		return recordRepository.findById(id).get();
	}
	
	@Override
	public List<Record> findByUserId(int userId) {
		return (List<Record>) recordRepository.findByUserId(userId);
	}
	
	@Override
	public List<Record> findByUserIdOrderByDateDesc(int userId) {
		return (List<Record>) recordRepository.findByUserIdOrderByDateDesc(userId);
	}
	
	@Override
	public List<Record> findAll() {
		return (List<Record>) recordRepository.findAll();
	}

	@Override
	public List<Record> findByCategory(String category) {
		return recordRepository.findByCategory(category);
	}

	@Override
	public List<Record> findByUserIdCategory(int userId, String category) {
		return recordRepository.findByUserIdAndCategory(userId, category);
	}
	
	@Override
	public List<Record> findByUserIdAndRecordDateAfterAndRecordDateBefore(int userId, LocalDate start, LocalDate end) {
		return recordRepository.findByUserIdAndRecordDateAfterAndRecordDateBefore(userId, start, end);
	}

	@Override
	public Page<Record> findAllByUserId(Pageable pageable, int userId) {
		return recordRepository.findAllByUserId(pageable, userId);
	}

	@Override
	public List<MediumRecord> getBetweenRecordDateByCategoryAndRecordDate(int userId, LocalDate start, LocalDate end) {
		return recordRepository.getBetweenRecordDateByCategoryAndRecordDate(userId, start, end);
	}
	
	@Override
	public List<SmallRecord> getBetweenRecordDateByCategory(int userId, LocalDate start, LocalDate end) {
		return recordRepository.getBetweenRecordDateByCategory(userId, start, end);
	}

	@Override
	public List<AllSmallRecord> getAllBetweenRecordDateByCategoryAndUserId(LocalDate start, LocalDate end) {
		return recordRepository.getAllBetweenRecordDateByCategoryAndUserId(start, end);
	}

	@Override
	public int updateMV(int dummyInt) {
		return recordRepository.updateMV(dummyInt);
	}

//	@Override
//	public void updatePsqlMV() {
//		recordRepository.updatePsqlMV();
//		
//	}

}
