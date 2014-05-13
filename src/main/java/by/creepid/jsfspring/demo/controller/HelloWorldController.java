package by.creepid.jsfspring.demo.controller;

import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.creepid.jsfspring.demo.faces.FacesServletHelper;
import by.creepid.jsfspring.demo.model.HelloWorld;

@Controller
public class HelloWorldController {

	private static final Logger logger = Logger
			.getLogger(HelloWorldController.class);

	public HelloWorldController() {
		logger.info("HelloWorldController created");
	}

	@Autowired
	private HelloWorld helloWorld;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
	public void helloWorld() {
		helloWorld.setMessage("Spring MVC + JSF");
	}

	// BindingResult is supported only after @ModelAttribute arguments
	@RequestMapping(value = "/helloWorld", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	public String helloWorldPost(@Valid @ModelAttribute HelloWorld helloWorld,
			BindingResult results, ModelMap map, Locale locale,
			HttpServletRequest request, HttpServletResponse response) {

		FacesContext context = FacesServletHelper.getFacesContext(request,
				response);

		String message = null;
		if (results.hasErrors()) {
			message = messageSource.getMessage("helloWorld.errors", null,
					locale);
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, null));

			List<FieldError> errors = results.getFieldErrors();
			for (FieldError fieldError : errors) {
				message = messageSource.getMessage(fieldError, locale);

				context.addMessage(fieldError.getField(), new FacesMessage(
						FacesMessage.SEVERITY_ERROR, message, null));
			}

		} else {
			this.helloWorld = helloWorld;

			message = messageSource.getMessage("helloWorld.info", null, locale);
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, message, null));
		}

		return "helloWorld";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() {
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}