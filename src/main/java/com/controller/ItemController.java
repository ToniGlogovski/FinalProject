package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.DAO.IitemDAO;
import com.DAO.ItemDAO;
import com.model.Item;

@Controller
public class ItemController  {
    

	@Autowired
	private IitemDAO itemDAO;
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public String getItem(Model model, @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response){
		
		try{
		
		Item item = itemDAO.getItem(id);
		
		model.addAttribute(item);
		
		} catch (Exception e) {
		}
		
		return "item";
	}
	
	

	@RequestMapping(method=RequestMethod.GET, value="/search/{text}")
	public String searchItems(Model model, @PathVariable("text") String text) throws SQLException {
		model.addAttribute("newItem", new Item());
		
		try{
		
		List<Item> items = itemDAO.getItemsByName(text);
		
		model.addAttribute("items",items);
		
		} catch (Exception e) {
		}
		
		return "search";
	}
	
	
	

}
