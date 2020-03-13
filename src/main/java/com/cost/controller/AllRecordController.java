package com.cost.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cost.model.Record;
import com.cost.service.RecordService;

@Controller
public class AllRecordController {
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private RecordService recordService;
	
	@RequestMapping(value = {"/allRecords"})
    public String allRecords(Model model, HttpServletRequest request) {
    	int page = 0; //default page number is 0 (yes it is weird)
        int size = 10; //default page size is 10
        
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        
    	String username = globalController.getLoginUser().getUsername();
    	int userId = globalController.getLoginUser().getUserId(); 

    	Page<Record> records = recordService.findAllByUserId(PageRequest.of(page, size, Sort.by("recordDate").descending()), userId);
    	
    	model.addAttribute("username", username);
    	model.addAttribute("records", records);
    	return "allRecords";
    }
	
	@RequestMapping(value = "/allRecord/{operation}/{id}", method = RequestMethod.GET)
	public String operationAll(@PathVariable("operation") String operation,
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
		return "redirect:/allRecords";		
	}
}
