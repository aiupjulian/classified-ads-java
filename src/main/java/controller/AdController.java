package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import data.DataAd;
import entity.*;
import util.ApplicationException;

public class AdController {
	private data.DataAd dataAd;

	public AdController() {
		dataAd = new DataAd();
	}

	public ArrayList<Ad> getAllByUser(Integer userId) throws SQLException, ApplicationException {
		return dataAd.getAllByUser(userId);
	}

	public void delete(Integer adId, User user) throws SQLException, ApplicationException {
		if (user.getAdmin()) {
			dataAd.delete(adId);
		} else {
			Ad ad = dataAd.getById(adId);
			if (ad.getUser().getId() == user.getId()) {
				dataAd.delete(adId);
			} else {
				throw new ApplicationException("No tiene permisos para eliminar ese aviso.");
			}
		}
	}

	public void markAdAsSold(Integer adId, Integer userId) throws SQLException, ApplicationException {
		dataAd.markAsSold(adId, userId);
	}
}