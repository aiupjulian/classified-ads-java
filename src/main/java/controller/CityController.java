package controller;

import java.util.ArrayList;

import data.DataCity;
import entity.*;
import util.ApplicationException;

public class CityController {
	private data.DataCity dataCity;

	public CityController() {
		dataCity = new DataCity();
	}

	public ArrayList<City> getAll() throws ApplicationException {
		return dataCity.getAll();
	}

	public City getById(Integer cityId) throws ApplicationException {
		return dataCity.getById(cityId);
	}

	public void create(City city) throws ApplicationException {
		dataCity.create(city);
	}

	public void update(City city) throws ApplicationException {
		dataCity.update(city);
	}

	public void delete(Integer cityId) throws ApplicationException {
		dataCity.delete(cityId);
	}
}