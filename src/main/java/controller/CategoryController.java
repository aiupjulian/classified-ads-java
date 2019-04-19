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

	public ArrayList<Category> getAllWithSubcategories() throws ApplicationException {
		return dataCategory.getAllWithSubcategories();
	}

	public Category getById(Integer categoryId) throws ApplicationException {
		return dataCategory.getById(categoryId);
	}

	public void create(Category category) throws ApplicationException {
		dataCategory.create(category);
	}

	public void update(Category category) throws ApplicationException {
		dataCategory.update(category);
	}

	public void delete(Integer categoryId) throws ApplicationException {
		dataCategory.delete(categoryId);
	}
}