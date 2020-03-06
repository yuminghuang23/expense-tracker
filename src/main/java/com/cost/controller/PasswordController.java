package com.cost.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cost.model.ChangePasswordForm;
import com.cost.model.Email;
import com.cost.model.PasswordResetToken;
import com.cost.model.User;
import com.cost.repository.PasswordResetTokenRepository;
import com.cost.service.EmailService;
import com.cost.service.PasswordResetTokenService;
import com.cost.service.UserService;
import com.cost.utils.PasswordEncoding;

@Controller
public class PasswordController {
	
	private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);
	private String tempToken;
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	
	@Autowired 
	private EmailService emailService;
	
	// Forgot Password //
	@RequestMapping("/forgotPassword")
    public String register(Model model) {
		model.addAttribute("reqUser", new User());
        logger.info("forgot-password ");
        return "forgotPassword";
    }
	
	@RequestMapping(value = {"/forgotPassword"}, method = RequestMethod.POST)
	public String forgotPassword(User reqUser,
								HttpServletRequest request,
            					final RedirectAttributes redirectAttributes) {
		logger.info("fogort-password-validation");
		User user = userService.findByEmail(reqUser.getEmail());
		
		// check if email exists 
		if(user == null) {
			logger.info("user-not-exits");
			redirectAttributes.addFlashAttribute("passResetToken", "noEmail");
            return "redirect:/forgotPassword";
		}
		
		// generate password reset token
		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setUser(user);
		resetToken.setToken(UUID.randomUUID().toString());
		resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
		passwordResetTokenService.save(resetToken);
		
//		System.out.println("}{}{}{}{}{}{}}}}{{}{}{}{}{}{}");
//		System.out.println(resetToken.getToken() + " " + resetToken.getId() + " " + resetToken.getExpiryDate() + " " + resetToken.getUser());
//		System.out.println(user.getEmail() + " " + user.getPassword() + " " + user.getRole() + " " + user.getUsername() + " " + user.getUserId());
//		
//		PasswordResetToken pass = passwordResetTokenService.findByToken(resetToken.getToken());
//		System.out.println(pass.getId()+ " " + pass.getToken() + " " + 
//				pass.getExpiryDate() + " " + pass.getUser());
		
		// generate email
		Email emailSend = new Email();
		emailSend.setFrom("no-reply@kane.com");
		emailSend.setTo(user.getEmail());
		emailSend.setSubject("Password Reset Request");

		// model for email
		Map<String, Object> emailModel = new HashMap<>();
		emailModel.put("token", resetToken);
		emailModel.put("user", user);
		emailModel.put("signature", "https://kane.com");
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		emailModel.put("resetUrl", url + "/resetPassword?token=" + resetToken.getToken());
		emailSend.setModel(emailModel);
		
		// send email
		emailService.sendEmail(emailSend);
		
		logger.info("password-reset-token-send");
		
		redirectAttributes.addFlashAttribute("passResetToken", "success");
		return "redirect:/forgotPassword";
	}
	
	// Reset Password //
	@RequestMapping("/resetPassword")	
	public String resetPassword(@RequestParam("token") String token, Model model) {
		// validate token 
		PasswordResetToken checkToken = passwordResetTokenService.findByToken(token);
		if(checkToken == null) {
			model.addAttribute("error", "Could not find the token, please request a new one");
			return "/tokenError";
		}
		else if (checkToken.isExpired()) {
			model.addAttribute("error", "Token has expired, please request a new token");
			return "/tokenError";
		}
		else {
			model.addAttribute("token", checkToken.getToken());
		}
		tempToken = checkToken.getToken();
		model.addAttribute("reqUser", new User());
        logger.info("reset-password");
		return "resetPassword";
	}
	
	@RequestMapping(value = {"/resetPassword"}, method = RequestMethod.POST)
	public String resetPasswordForm(User reqUser, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("reset-password-validation");
		User user = userService.findByEmail(reqUser.getEmail());
		User tokenUser = passwordResetTokenService.findByToken(tempToken).getUser();
		//System.out.println(user.getEmail() + " | " +tokenUser.getEmail());
		
		if(user == null) {
			logger.info("user-not-exits");
			redirectAttributes.addFlashAttribute("validate", "noEmail");
			return "redirect:/resetPassword?token=" + tempToken;
		}
		else if (user != tokenUser) {
			logger.info("user-token-not-match");
			redirectAttributes.addFlashAttribute("validate", "noMatch");
			return "redirect:/resetPassword?token=" + tempToken;
		}

		user.setPassword(PasswordEncoding.getInstance().passwordEncoder.encode(reqUser.getPassword()));
		//System.out.println(user.toString());
		
		logger.info("resetting-password");
		userService.save(user);
		passwordResetTokenService.deleteByToken(tempToken);
		
		//System.out.println(reqUser.getPassword());
		return "redirect:/login?resetPassword";
	
	}
	
	// Change Password //
	@RequestMapping("/changePassword")
	public String changePassword(Model model) {
		model.addAttribute("reqUser", new ChangePasswordForm());
		model.addAttribute("oldPassword", "");
		model.addAttribute("username", globalController.getLoginUser().getUsername());
        logger.info("change-password ");
        return "changePassword";
	}
	
	@RequestMapping(value = {"/changePassword"}, method = RequestMethod.POST)
	public String changePasswordForm(ChangePasswordForm changePasswordForm, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("change-password-validation");
		User user = globalController.getLoginUser();
		
		System.out.println("asddddddddddddddddddddd");
		System.out.println(changePasswordForm.getOldPassword());
		System.out.println(user.getPassword());
		System.out.println(PasswordEncoding.getInstance().match(changePasswordForm.getOldPassword(), user.getPassword()));
		
		// check if passwords matches 
		if(!PasswordEncoding.getInstance().match(changePasswordForm.getOldPassword(), user.getPassword())) {
			logger.info("password-not-match");
			redirectAttributes.addFlashAttribute("validate", "wrongPassword");
			return "redirect:/changePassword";
		}
		
		redirectAttributes.addFlashAttribute("validate", "changed");
		user.setPassword(PasswordEncoding.getInstance().passwordEncoder.encode(changePasswordForm.getPassword()));
		//System.out.println(user.toString());
		
		logger.info("changing-password");
		userService.save(user);

		//System.out.println(reqUser.getPassword());
		return "redirect:/changePassword";
	
	}
}
