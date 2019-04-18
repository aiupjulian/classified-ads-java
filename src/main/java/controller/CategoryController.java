package controller;

import java.util.ArrayList;

import data.DataCategory;
import entity.*;
import util.ApplicationException;

public class CategoryController {
	private data.DataCategory dataCategory;

	public CategoryController() {
		dataCategory = new DataCategory();
	}

	public ArrayList<Category> getAllWithSubcategories() throws Exception, ApplicationException {
		return dataCategory.getAllWithSubcategories();
	}

	public Category getById(Integer categoryId) throws Exception {
		return dataState.getById(categoryId);
	}

	public void create(Category category) throws Exception {
		dataState.create(category);
	}

	public void update(Category category) throws Exception {
		dataState.update(category);
	}

	public void delete(Integer categoryId) throws Exception {
		dataState.delete(categoryId);
	}
}