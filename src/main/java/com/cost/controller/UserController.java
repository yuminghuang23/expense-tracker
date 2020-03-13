package com.cost.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cost.model.Record;
import com.cost.model.SmallRecord;
import com.cost.model.User;
import com.cost.model.MediumRecord;
import com.cost.model.MonthlySum;
import com.cost.service.MonthlySumService;
import com.cost.service.RecordService;
import com.cost.service.RoleService;
import com.cost.service.UserService;
import com.cost.utils.Categories;
import com.cost.utils.PasswordEncoding;
import com.cost.utils.Roles;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //@Autowired
    private GlobalController globalController;

    //@Autowired
    private RecordService recordService;

    //@Autowired
    private UserService userService;
    
    @Autowired
    public UserController(GlobalController globalController, RecordService recordService, UserService userService) {
		this.globalController = globalController;
		this.recordService = recordService;
		this.userService = userService;
	}

	@RequestMapping("/")
    public String root(Model model) {
		if(globalController.getLoginUser() != null) {
			model.addAttribute("loggedin", "yes");
			model.addAttribute("username", globalController.getLoginUser().getUsername());
    	}
		else {
			model.addAttribute("loggedin", "no");
		}
        model.addAttribute("reqUser", new User());
        logger.info("root");
        return "index";
    }
    
    @RequestMapping("/login")
    public String login(Model model) {
    	if(globalController.getLoginUser() != null) {
    		return "redirect:/";
    	}
        model.addAttribute("reqUser", new User());
        logger.info("login");
        return "login";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
    }
    
    @RequestMapping("/home")
    public String home(Model model) {
        Record record =new Record();
        //List<Record> recordList = recordService.findById(globalController.getLoginUser().getUserId());
//        System.out.println("*****************************************************************");
//        System.out.println(globalController.getLoginUser().getUserId());
        
        int userId = globalController.getLoginUser().getUserId(); 
        String username = globalController.getLoginUser().getUsername();
        
        List<Record> recordList = recordService.findByUserIdOrderByDateDesc(userId);
        LocalDate today = LocalDate.now().plusDays(1);
        LocalDate startDate = today.minusDays(7);
//        System.out.println(startDate);
        List<Record> d3List = recordService.findByUserIdAndRecordDateAfterAndRecordDateBefore(userId, startDate, today);
//        for (int i=0; i<recordList.size(); i++) {
//        	System.out.println(recordList.get(i).getUserId()+" | "+recordList.get(i).getCategory()+" | "+recordList.get(i).getCost()+" | "
//        						+recordList.get(i).getDate()+" | "+recordList.get(i).getRecordDate());
//        }
        for (int i=0; i<d3List.size(); i++) {
        	System.out.println(d3List.get(i));
        }
        
        model.addAttribute("username", username);	
        model.addAttribute("reqRecord", record);
        model.addAttribute("records", recordList);
        model.addAttribute("d3List", d3List);
      
        return "home";
    }

    @RequestMapping("/admin")
    public String helloAdmin() {
        logger.info("admin");
        return "admin";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("reqUser", new User());
        logger.info("register");
        return "register";
    }
    
    @RequestMapping(value = {"/user/register"}, method = RequestMethod.POST)
    public String register(@ModelAttribute("reqUser") User reqUser,
                           final RedirectAttributes redirectAttributes) {
    	
        logger.info("/user/register");
        User user = userService.findByUserName(reqUser.getUsername());
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-name");
            return "redirect:/register";
        }
        user = userService.findByEmail(reqUser.getEmail());
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-email");
            return "redirect:/register";
        }

        reqUser.setPassword(PasswordEncoding.getInstance().passwordEncoder.encode(reqUser.getPassword()));
        reqUser.setRole(Roles.USER.getValue());
        
        if (userService.save(reqUser) != null) {
            redirectAttributes.addFlashAttribute("saveUser", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveUser", "fail");
        }

        return "redirect:/register";
    }
    
    // function to group by category and date
    Map<LocalDate, Map<Categories, Double>> proccessData(List<Record> record) {
    	Map<LocalDate, Map<Categories, Double>> fi = record.stream().collect(
				Collectors.groupingBy(Record::getRecordDate, Collectors.groupingBy(Record::getCategory, 
						Collectors.summingDouble(Record::getCost))));
    	return fi;
    }
    
//    @ResponseBody 
//	@RequestMapping(value = "/recordJson", method = RequestMethod.POST, headers = "Accept=application/json")
//	public List<Record> recordAPI(int userId) {
//		//int userId = globalController.getLoginUser().getUserId();
//	    return recordService.findByUserId(userId);
//	}
}
