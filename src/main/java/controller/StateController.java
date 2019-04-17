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

	public ArrayList<State> getAllWithCities() throws Exception {
		return dataState.getAllWithCities();
	}

	public State getById(Integer stateId) throws Exception {
		return dataState.getById(stateId);
	}

	public void create(State state) throws Exception {
		dataState.create(state);
	}

	public void update(State state) throws Exception {
		dataState.update(state);
	}

	public void delete(Integer stateId) throws Exception {
		dataState.delete(stateId);
	}
}