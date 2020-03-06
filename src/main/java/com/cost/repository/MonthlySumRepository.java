package com.cost.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import com.cost.model.MonthlySum;
import com.cost.model.MonthlySumId;


public interface MonthlySumRepository extends CrudRepository<MonthlySum, MonthlySumId> {
	
	List<MonthlySum> findByUserIdOrderByMonthYear(int userId);
	
	@Query("SELECT new com.cost.model.MonthlySum(\r\n" + 
			"    TO_CHAR(r.recordDate,'MM-YYYY') as month_year\r\n" + 
			"    , r.userId \r\n" + 
			"    , sum(r.cost))\r\n" + 
			"    FROM com.cost.model.Record r\r\n" + 
			"    GROUP BY r.userId, TO_CHAR(r.recordDate, 'MM-YYYY')")
	List<MonthlySum> findAgg();
}
