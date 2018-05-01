package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.DAO.ItemDAO;
import com.example.model.Patka;
import com.model.Item;

@Controller
public class ItemController  {
    
 
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public String getItem(Model model, @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws SQLException {
		

		
		ItemDAO dao = ItemDAO.getInstance();
		Item item = dao.getItem(id);
		
		model.addAttribute(item);
		
		
		
		return "item";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/addItem{id}")
	public String addItem(Model model, @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws SQLException {
		
		HttpSession session = request.getSession();
		Cookie c = new Cookie((session.getAttribute("firstName")) + id.toString() , id.toString());
		c.setMaxAge(1000);
		
		response.addCookie(c);	
		
		return "redirect:cart";
	}

	@RequestMapping(method=RequestMethod.GET, value="/search/{text}")
	public String searchItems(Model model, @PathVariable("text") String text) throws SQLException {
		model.addAttribute("newItem", new Item());
		
		ItemDAO dao = ItemDAO.getInstance();
		List<Item> items = dao.getItemsByName(text);
		
		model.addAttribute("items",items);
		
		return "search";
	}

}
