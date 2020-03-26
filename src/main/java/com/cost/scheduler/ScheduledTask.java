package com.cost.scheduler;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cost.model.MonthlySum;
import com.cost.repository.MonthlySumRepository;


@Component
public class ScheduledTask {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
	private static final DateTimeFormatter dateTimeFormmater = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Autowired
	private MonthlySumRepository monthlySumRepository;
	
	@Transactional
	@Scheduled(initialDelay = 2000, fixedDelay = Long.MAX_VALUE)
	public void scheduleTaskWithInitialDelay() {
		logger.info("Fixed rate task :: Execution Time - {}", dateTimeFormmater.format(LocalTime.now()));
		try {
			System.out.println("ASDASDASDASDASDASDASDASDASDASD");
			// this is to simulate procedure
			// first remove all rows
			monthlySumRepository.deleteAll();
			// get the required data
			List<MonthlySum> monthlySumList= monthlySumRepository.findAgg();
			// then insert into table 
			monthlySumRepository.saveAll(monthlySumList);
			logger.info("Fixed rate task procedure completed");
		}
		catch (Exception e) {
			logger.info("psql procedure failed with error: " + e.getStackTrace());
		}
	}
	
	@Transactional
	@Scheduled(cron = "0 0 0 1 * *") // At 00:00:00 am, on the 1st day, every month
	//@Scheduled(fixedRate = 3000)
	public void scheduleTaskFixedCronRate() {
		logger.info("Fixed rate task :: Execution Time - {}", dateTimeFormmater.format(LocalTime.now()));
		
		// for psql, since version 11 procedure does not work in Hibernate+JPA
		try {
			// this is to simulate procedure
			// first remove all rows
			monthlySumRepository.deleteAll();
			// get the required data
			List<MonthlySum> monthlySumList= monthlySumRepository.findAgg();
			// then insert into table 
			monthlySumRepository.saveAll(monthlySumList);
			logger.info("Fixed rate task procedure completed");
		}
		catch (Exception e) {
			logger.info("psql procedure failed with error: " + e.getStackTrace());
		}
	}

}