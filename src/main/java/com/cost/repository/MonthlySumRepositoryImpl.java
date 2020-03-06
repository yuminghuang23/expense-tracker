package com.cost.repository;

import javax.persistence.EntityManager;

import org.springframework.data.annotation.Persistent;

public class MonthlySumRepositoryImpl implements MonthlySumRepoistoryCustom{
	
	@Persistent
	private EntityManager entityManager;
	
	@Override
	public int callPsqlProcedure() {
		return this.entityManager.createNativeQuery("INSERT INTO monthly_spending_mv\r\n" + 
				"    SELECT \r\n" + 
				"    TO_CHAR(record_date,'MM-YYYY') as month_year\r\n" + 
				"    , user_id \r\n" + 
				"    , sum(cost)\r\n" + 
				"    FROM record\r\n" + 
				"    GROUP BY user_id, TO_CHAR(record_date, 'MM-YYYY');")
        .executeUpdate();
	}

}
