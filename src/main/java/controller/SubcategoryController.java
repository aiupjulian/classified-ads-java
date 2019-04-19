package controller;

import java.util.ArrayList;

import data.DataSubcategory;
import entity.*;

public class SubcategoryController {
	private data.DataSubcategory dataSubcategory;

	public SubcategoryController() {
		dataSubcategory = new DataSubcategory();
	}

	public ArrayList<Subcategory> getAll() throws Exception {
		return dataSubcategory.getAll();
	}

	public Subcategory getById(Integer subcategoryId) throws Exception {
		return dataSubcategory.getById(subcategoryId);
	}

	public void create(Subcategory subcategory) throws Exception {
		dataSubcategory.create(subcategory);
	}

	public void update(Subcategory subcategory) throws Exception {
		dataSubcategory.update(subcategory);
	}

	public void delete(Integer subcategoryId) throws Exception {
		dataSubcategory.delete(subcategoryId);
	}
}