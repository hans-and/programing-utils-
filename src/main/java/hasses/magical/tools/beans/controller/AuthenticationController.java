package hasses.magical.tools.beans.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hasses.magical.helpers.RandomHelper;
import hasses.magical.tools.model.EmailMessage;
import hasses.magical.tools.model.User;
import hasses.magical.tools.service.EmailSender;
import hasses.magical.tools.service.UserService;

@Controller
public class AuthenticationController {
	private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class);
	@Autowired
	private EmailSender emailSender; 
	@Autowired
	private UserService userService;
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		LOGGER.debug("Login is called /login");
		try {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("login"); // resources/template/login.html
			return modelAndView;
		} catch (Exception e) {
			LOGGER.error("Something went wrong loggin", e);
			return null;
		}

	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		LOGGER.debug("Register is called /register");
		try {
			ModelAndView modelAndView = new ModelAndView();
			User user = new User();
			modelAndView.addObject("user", user);
			modelAndView.setViewName("register"); // resources/template/register.html
			return modelAndView;
		} catch (Exception e) {
			LOGGER.error("Something went wrong loggin", e);
			return null;
		}
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		LOGGER.debug("Home is called /home");
		try {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("message", "Nästan där kolla din e-post");
			modelAndView.setViewName("home"); // resources/template/home.html
			return modelAndView;
		} catch (Exception e) {
			LOGGER.error("Something went wrong loggin", e);
			return null;
		}

	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminHome() {
		LOGGER.debug("Admin is called /admin");
		try {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("admin"); // resources/template/home.html
			return modelAndView;
		} catch (Exception e) {
			LOGGER.error("Something went wrong going for admin", e);
			return null;
		}

	}

	
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public ModelAndView getConfirmation(@RequestParam String email,@RequestParam String confpswrd) {
		LOGGER.debug("confirm is called /confirm param: email="+email+" confpswrd"+confpswrd);
		try {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("home"); // resources/template/home.html
			if(userService.setConfirmation(email,confpswrd)) {
				modelAndView.addObject("message", "Men vad kul, nu börjar det ");
				modelAndView.setViewName("home");
			}else {
				modelAndView.addObject("message", "Nära men kunde inte konfirmera att "+confpswrd+" tillhör "+email);
				modelAndView.setViewName("home");
			}
			return modelAndView;
		} catch (Exception e) {
			LOGGER.error("Something went wrong going for admin", e);
			return null;
		}
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@Valid User user, BindingResult bindingresult, ModelMap modelmap) {
		LOGGER.debug("Register is called /register");
		try {
			ModelAndView modelAndView = new ModelAndView();
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			
			Set<ConstraintViolation<@Valid User>> errors =  validator.validate(user);
			if(errors!=null && errors.size() > 0) {
				List<String> errorsStrings = new ArrayList<String>();
				for (Iterator<ConstraintViolation<User>> iterator = errors.iterator(); iterator.hasNext();) {
					ConstraintViolation<User> constraintViolation = (ConstraintViolation<User>) iterator.next();
					errorsStrings.add(constraintViolation.getMessage());
					LOGGER.debug(constraintViolation.getMessage());	
				}
				modelAndView.addObject("message", "Snälla ordna felen i formuläret hasse");
				LOGGER.error("Invalid user data hej hasse");
				try {
					modelAndView.addObject("errors", errorsStrings);	
				} catch (Exception e) {
					LOGGER.error("Could not add errors ", e);
				}
				
				modelmap.addAttribute("bindingresult", bindingresult);
				
			}if (bindingresult.hasErrors()) {
				List<ObjectError> error =  bindingresult.getAllErrors();
				for (Iterator iterator = error.iterator(); iterator.hasNext();) {
					ObjectError objectError = (ObjectError) iterator.next();
					LOGGER.debug(objectError.toString()+objectError.getDefaultMessage());
				}
				
				LOGGER.error("Invalid user data");
				modelAndView.addObject("message", "Hörru din klåpare ordna felen i formuläret");
				modelmap.addAttribute("bindingresult", bindingresult);
			} else if( userService.isUserAlreadyPrecent(user)){
				
				modelAndView.addObject("message", "Användaren finns sen förut");
			}else {
				
				user.setConfirmation(RandomHelper.getConfirmationWord());
				userService.saveUser(user);				
				modelAndView.addObject("message", "Användare sparad");
				try {
					emailSender.sendConfirmEmail(user.getName()+" "+user.getLastName(), user.getEmail(), user.getConfirmation());	
				} catch (Exception e) {
					LOGGER.error("Could not send email ", e);
				}
				
				
			}
		
			modelAndView.setViewName("register"); // resources/template/register.html
			modelAndView.addObject("user", user);		
			return modelAndView;

		} catch (Exception e) {
			LOGGER.error("Something went wrong loggin", e);
			return null;
		}
	}
}
