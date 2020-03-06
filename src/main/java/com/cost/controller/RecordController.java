package com.cost.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cost.model.Record;
import com.cost.service.RecordService;



@Controller
@ComponentScan
public class RecordController {
	
	@Autowired 
	private RecordService recordService;
	
	@Autowired
	private GlobalController globalController;
	
	@RequestMapping(value = {"/record/saveRecord"}, method = RequestMethod.POST)	
	public String saveRecord(@ModelAttribute("reqRecord") Record reqRecord, final RedirectAttributes redirectAttributes) {

		try {
			System.out.println(reqRecord.getRecordDate().getClass());
			System.out.println("---------------------------------------------------------------------------------");
			reqRecord.setCategory(reqRecord.getCategory());
			//reqRecord.setDate(reqRecord.getDate());
			reqRecord.setRecordDate(reqRecord.getRecordDate());
			reqRecord.setUserId(globalController.getLoginUser().getUserId());
			reqRecord.setDate(LocalDateTime.now());
			recordService.save(reqRecord);
			redirectAttributes.addFlashAttribute("mgs","success");
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("mgs","fail");
		}
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/record/{operation}/{id}", method = RequestMethod.GET)
	public String operation(@PathVariable("operation") String operation,
							@PathVariable("id") int recordId, 
							final RedirectAttributes redirectAttributes, Model model) {
		if (operation.equals("delete")) {
			if(recordService.delete(recordId)) {
				redirectAttributes.addFlashAttribute("msg", "del");
				redirectAttributes.addFlashAttribute("msgText", "Record deleted!");
			}
			else {
				redirectAttributes.addFlashAttribute("msg", "del_fail");
				redirectAttributes.addFlashAttribute("msgText", "Could not delete record. Try again later");
			}
		}
		else if (operation.equals("edit")) {
			Record editRecord = recordService.findById(recordId);
			String username = globalController.getLoginUser().getUsername();
			if (editRecord != null) {
				model.addAttribute("editRecord", editRecord);
				model.addAttribute("username", username);
				return "edit";
			}
			else {
				redirectAttributes.addFlashAttribute("msg", "not found");
			}
			
		}
		return "redirect:/home";
		
	}
	
	@RequestMapping(value= {"/record/editRecord"}, method = RequestMethod.POST)
	public String editRecord(@ModelAttribute("editRecord") Record editRecord, Model model) {
		
		try {
			Record record = recordService.findById(editRecord.getRecordId());
			if (!record.equals(editRecord)) {
				editRecord.setDate(LocalDateTime.now());
				recordService.update(editRecord);
				model.addAttribute("msg", "success");
			} 
			else {
				model.addAttribute("msg", "same");
			}
		} 
		catch (Exception e) {
			model.addAttribute("msg", "fail");
		}
		
		model.addAttribute("editRecord", editRecord);
		return "edit";
	}
	
	
}
