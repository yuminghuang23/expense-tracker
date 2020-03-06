package com.cost.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cost.model.MonthlySum;
import com.cost.repository.MonthlySumRepository;


@Service
@Transactional
public class MonthlySumServiceImpl implements MonthlySumService{
	
	@Autowired
	MonthlySumRepository monthlySumRepository;
	
	@Override
	public List<MonthlySum> findByUserIdOrderByMonthYear(int userId) {
		return monthlySumRepository.findByUserIdOrderByMonthYear(userId);
	}

//	@Override
//	public void callPsqlProcedure() {
//		monthlySumRepository.callPsqlProcedure();
//	}

}
