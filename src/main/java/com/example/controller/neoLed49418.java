package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.DAO.ItemDAO;
import com.example.model.Patka;
import com.model.Item;

/**
 * Servlet implementation class neoLed49418
 */
//@WebServlet("/neo-led-49418-uhd-sw-09162977")
@Controller
public class neoLed49418  {
    
    
	@RequestMapping(method=RequestMethod.GET, value="/neo-led-49418-uhd-sw-09162977")
	public Item item(Model model) throws SQLException {
		
		model.addAttribute("newItem", new Item());
		
		ItemDAO dao = ItemDAO.getInstance();
		Item item = dao.getItem(2);
		model.addAttribute(item);
			
		// name of view deto shte gi pokazva
		return item;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	public String viewPatka(Model model, @PathVariable Integer id) throws SQLException {
		ItemDAO dao = ItemDAO.getInstance();
		Item item = dao.getItem(id);
		
		model.addAttribute(item);
		
		return "neo-led-49418-uhd-sw-09162977";
	}



}
