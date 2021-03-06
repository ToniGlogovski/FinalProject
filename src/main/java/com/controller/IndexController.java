package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/index")
public class IndexController {

	@RequestMapping(method = RequestMethod.GET)
	public String sayHello(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		try{
		if ((request.getSession(false) != null) && (request.getAttribute("id") != null)) {
			long id = (long) request.getSession().getAttribute("id");
		}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "index";
	}

}
