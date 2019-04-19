package controller;

import java.util.ArrayList;

import data.DataSubcategory;
import entity.*;
import util.ApplicationException;

public class SubcategoryController {
	private data.DataSubcategory dataSubcategory;

	public SubcategoryController() {
		dataSubcategory = new DataSubcategory();
	}

	public ArrayList<Subcategory> getAll() throws ApplicationException {
		return dataSubcategory.getAll();
	}

	public Subcategory getById(Integer subcategoryId) throws ApplicationException {
		return dataSubcategory.getById(subcategoryId);
	}

	public void create(Subcategory subcategory) throws ApplicationException {
		dataSubcategory.create(subcategory);
	}

	public void update(Subcategory subcategory) throws ApplicationException {
		dataSubcategory.update(subcategory);
	}

	public void delete(Integer subcategoryId) throws ApplicationException {
		dataSubcategory.delete(subcategoryId);
	}
}