package de.berlin.fu.imp.sakai.direct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.annotations.ApiIgnore;


@Controller
@RequestMapping("/hello")
@ApiIgnore
public class HelloJSPController {

	// @Setter
	// @Resource(name = "messageSource")
	// private MessageSource msgs;

	@RequestMapping(value = "/world", method = RequestMethod.GET)
	public String showHelloWorld() {	
		return "helloworld";
	}

}
