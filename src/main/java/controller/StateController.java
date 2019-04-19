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

	public ArrayList<State> getAllWithCities() throws ApplicationException {
		return dataState.getAllWithCities();
	}

	public State getById(Integer stateId) throws ApplicationException {
		return dataState.getById(stateId);
	}

	public void create(State state) throws ApplicationException {
		dataState.create(state);
	}

	public void update(State state) throws ApplicationException {
		dataState.update(state);
	}

	public void delete(Integer stateId) throws ApplicationException {
		dataState.delete(stateId);
	}
}