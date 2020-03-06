package com.cost.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cost.model.MediumRecord;
import com.cost.model.MonthlySum;
import com.cost.model.SmallRecord;
import com.cost.service.MonthlySumService;
import com.cost.service.RecordService;
import com.cost.utils.Categories;

@Controller
public class ReportController {
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private RecordService recordService;
	
	@Autowired
	private MonthlySumService monthlySumService;
	
	@RequestMapping(value= {"/report"})
    public String report(Model model) {
    	String username = globalController.getLoginUser().getUsername();
    	int userId = globalController.getLoginUser().getUserId();
    	
    	YearMonth month = YearMonth.from(LocalDate.now());
    	month = month.minusMonths(1);
    	LocalDate start = month.atDay(1);
    	LocalDate end   = month.atEndOfMonth();

     	List<MediumRecord> recordsSumGroupedByCatDate = recordService.getBetweenRecordDateByCategoryAndRecordDate(userId, start, end);
    	List<SmallRecord> recordsSumGroupedByCat = recordService.getBetweenRecordDateByCategory(userId, start, end);
    	
    	System.out.println("EMPTY:" + recordsSumGroupedByCat.isEmpty());
    	System.out.println();
    	for (int i = 0; i < recordsSumGroupedByCat.size(); i++) {
    		System.out.println(recordsSumGroupedByCat.get(i));
    	}
    	
    	System.out.println(month + " " + start + " " + end);
    	System.out.println("ASDASDADJASHYDUIJOASKLMDJNBHGYUSAIJODKLMAS< BNDHGASYDUIASJOKDP:L<ASDMK");
    	
    	List<Categories> listEnum = getAllEnum();
    	
    	List<MonthlySum> monthlySumByUser = monthlySumService.findByUserIdOrderByMonthYear(userId);
    	
    	model.addAttribute("enumList", listEnum);
    	model.addAttribute("username", username);
    	model.addAttribute("recordsSumGroupedByCatDate", recordsSumGroupedByCatDate);
    	model.addAttribute("recordsSumGroupedByCat", recordsSumGroupedByCat);
    	model.addAttribute("monthlySumByUser", monthlySumByUser);
    	return "report";
    }
	
	// function to get all enum values into a list
    List<Categories> getAllEnum() {
    	List<Categories> listEnum = new ArrayList<Categories>();
    	Categories[] categories = Categories.values();
		for (Categories category : categories) {
			//System.out.println(category.toString());
			listEnum.add(category);
		}
		return listEnum; 
    }
}
