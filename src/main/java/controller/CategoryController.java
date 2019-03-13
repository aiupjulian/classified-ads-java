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
}