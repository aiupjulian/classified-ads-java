package controller;

import java.util.ArrayList;

import data.DataState;
import entity.*;
import util.ApplicationException;

public class StateController {
	private data.DataState dataState;

	public StateController() {
		dataState = new DataState();
	}

	public ArrayList<State> getAllWithCities() throws Exception, ApplicationException {
		return dataState.getAllWithCities();
	}
}