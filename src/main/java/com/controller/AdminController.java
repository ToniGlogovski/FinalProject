package com.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DAO.ItemDAO;

import com.DAO.UserDAO;

import org.springframework.web.bind.annotation.ResponseBody;

import com.DAO.UserDAOImpl;
import com.exceptions.UserException;
import com.model.Item;
import com.model.User;

@Controller
public class AdminController {

	@Autowired
	private ItemDAO itemDao;

	@Autowired
	private UserDAO userDAO;

	@RequestMapping(method = RequestMethod.GET, value = "/allUsers")
	public String getAllUsers(Model model) {

		try {

			model.addAttribute("newUser", new User());

			UserDAOImpl dao;

			HashMap<String, Boolean> users = (HashMap<String, Boolean>) userDAO.getAllUsers();
			model.addAttribute("users", users);
		} catch (Exception e) {
			return "error";
		}
		return "allUsers";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	public String deleteUser(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		try {
			String email = request.getParameter("email");
			UserDAOImpl.getInstance().deleteUser(email);
			User user = UserDAOImpl.getInstance().getUserByEmail(email);
			user.setDeleted(true);
			model.addAttribute("deleted", user.isDeleted());
			redirectAttributes.addFlashAttribute("deleted", user.isDeleted());
		} catch (Exception e) {
			return "error";
		}
		return "redirect:allUsers";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/newItem")
	public String login(Model model, HttpServletRequest request) {

		return "newItem";
	}

	@RequestMapping(value = "/newItem", method = RequestMethod.POST)
	public String addProduct(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) {
		String UPLOAD_FOLDER = "C:\\items-images\\";
		String brand = request.getParameter("brand");
		String category = request.getParameter("category");

		long brandId = itemDao.getIDbyBrandName(brand);
		long categoryId = itemDao.getIDbyCategoryName(category);
		String name = request.getParameter("name");
		float price = Float.parseFloat(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String description = request.getParameter("description");

		if (!file.isEmpty()) {
			try {

				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(UPLOAD_FOLDER + file.getOriginalFilename())));
				stream.write(bytes);
				stream.close();
				String filePath = "img/" + file.getOriginalFilename();

				if (!file.getOriginalFilename().endsWith("jpg") && !file.getOriginalFilename().endsWith("jpeg")
						&& !file.getOriginalFilename().endsWith("png")) {
					return "error";

				}

				Item item = new Item(name, brandId, price, quantity, categoryId, description, filePath);
				item.setName(name);
				item.setBrandId(brandId);
				item.setPrice(price);
				item.setQuantity(quantity);
				item.setCategoryId(categoryId);
				item.setDescription(description);
				item.setPictureUrl(filePath);

				itemDao.addItem(item);
				return "redirect:index";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		} else
			return "error";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{id}")
	public String editItem(Model model, @PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			// if (Boolean.valueOf(session.getAttribute("isAdmin").toString())) {
			// } else {
			// return "error";
			// }
			String name = request.getParameter("item_name");
			float price = Float.parseFloat(request.getParameter("item_price"));
			String description = request.getParameter("item_description");
			int quantity = Integer.parseInt(request.getParameter("item_quantity"));

			itemDao.editProduct(id, name, price, description, quantity);

		} catch (Exception e) {
			return "error";
		}
		return "redirect:/" + id;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/changePic/{id}")

	public String editPicture(@RequestParam("file") MultipartFile file, @PathVariable Integer id,
			HttpServletRequest request, Model model) {
		String UPLOAD_FOLDER = "C:\\items-images\\";
		if (!file.isEmpty()) {
			try {

				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(UPLOAD_FOLDER + file.getOriginalFilename())));
				stream.write(bytes);
				stream.close();
				String filePath = "img/" + file.getOriginalFilename();
				if (!file.getOriginalFilename().endsWith("jpg") && !file.getOriginalFilename().endsWith("jpeg")
						&& !file.getOriginalFilename().endsWith("png")) {
					return "error";
				}
				itemDao.updatePicture(id, filePath);
				return "redirect:/" + id;
			} catch (Exception e) {
				return "error";
			}
		}
		return "error";
	}

}
