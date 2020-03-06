package com.cost.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.procedure.ProcedureCall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cost.model.Record;
import com.cost.model.SmallRecord;
import com.cost.model.AllSmallRecord;
import com.cost.model.MediumRecord;
import com.cost.model.MonthlySum;

public interface RecordRepository extends CrudRepository<Record, Integer> {
	
	// from Role where category = :category
	List<Record> findByCategory(String category);
	
	//Record findyById(int id);
	
	List<Record> findByUserId(int userId);
	
	List<Record> findByUserIdOrderByDateDesc(int userId);
	
	List<Record> findByUserIdAndCategory(int userId, String category);
	
	List<Record> findByUserIdAndRecordDateAfterAndRecordDateBefore(int userId, LocalDate start, LocalDate end);
	
	Page<Record> findAllByUserId(Pageable pageable, int userId); 
	
	@Query("select NEW com.cost.model.MediumRecord(r.category, sum(r.cost), r.recordDate) from Record r where "
			+ "r.userId = :userId and r.recordDate between :start "
			+ "and :end group by r.category, r.recordDate order by sum(r.cost) desc")
	List<MediumRecord> getBetweenRecordDateByCategoryAndRecordDate(@Param("userId") int userId, @Param("start") LocalDate start, 
			@Param("end") LocalDate end);
	
	@Query("select NEW com.cost.model.SmallRecord(r.category, sum(r.cost)) from Record r where "
			+ "r.userId = :userId and r.recordDate between :start "
			+ "and :end group by r.category order by sum(r.cost) desc")
	List<SmallRecord> getBetweenRecordDateByCategory(@Param("userId") int userId, @Param("start") LocalDate start, 
			@Param("end") LocalDate end);
	
	@Query("select NEW com.cost.model.AllSmallRecord(r.category, sum(r.cost), r.userId) from Record r where "
			+ "r.recordDate between :start and :end group by r.category, r.userId order by sum(r.cost) desc")
	List<AllSmallRecord> getAllBetweenRecordDateByCategoryAndUserId(@Param("start") LocalDate start, 
			@Param("end") LocalDate end);
	
	// for mysql only
	@Transactional
	@Procedure(procedureName = "refresh_mv_now")
	int updateMV(@Param("dummy") int dummyInt);

	// for psql only
//	@Transactional
//	@Procedure(procedureName = "refresh_mv_now")
//	void updatePsqlMV();
	
//	@Query(nativeQuery = true, value = "call refresh_mv_now()")
//	void updateMV();
	
//	@Query("from Record r where r.record_date between :start and :end")
//	List<Record> getAllBetweenDates(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
