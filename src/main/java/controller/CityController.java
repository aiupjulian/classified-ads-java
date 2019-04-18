package controller;

import java.util.ArrayList;

import data.DataCity;
import entity.*;

public class CityController {
	private data.DataCity dataCity;

	public CityController() {
		dataCity = new DataCity();
	}

	public ArrayList<City> getAll() throws Exception {
		return dataCity.getAll();
	}

	public City getById(Integer cityId) throws Exception {
		return dataCity.getById(cityId);
	}

	public void create(City city) throws Exception {
		dataCity.create(city);
	}

	public void update(City city) throws Exception {
		dataCity.update(city);
	}

	public void delete(Integer cityId) throws Exception {
		dataCity.delete(cityId);
	}
}