package com.cost.service;

import java.util.List;

import com.cost.model.MonthlySum;

public interface MonthlySumService {
	
	List<MonthlySum> findByUserIdOrderByMonthYear(int userId);
	
//	void callPsqlProcedure();
}
