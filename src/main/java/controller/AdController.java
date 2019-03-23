package controller;

import java.util.ArrayList;

import data.DataAd;
import entity.*;
import util.ApplicationException;

public class AdController {
	private data.DataAd dataAd;

	public AdController() {
		dataAd = new DataAd();
	}

	public ArrayList<Ad> getAllByUser(Integer userId) throws Exception, ApplicationException {
		return dataAd.getAllByUser(userId);
	}
}