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
	
	public Category[] getAllWithSubcategories() throws ApplicationException {
		return dataCategory.getAllWithSubcategories();
	}
}