package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import data.DataAd;
import entity.*;
import util.*;

public class AdController {
	private data.DataAd dataAd;

	public AdController() {
		dataAd = new DataAd();
	}

	public Ad createAd(Ad ad) throws ApplicationException {
		return dataAd.createAd(ad);
	}

	public Ad updateAd(Ad ad) throws ApplicationException {
		return dataAd.updateAd(ad);
	}

	public ArrayList<Ad> getAllByUser(Integer userId) throws SQLException, ApplicationException {
		return dataAd.getAllByUser(userId);
	}

	public DataAdsPages getAllByQuery(HashMap<String, String> queryMap) throws ApplicationException {
		return dataAd.getAllByQuery(queryMap);
	}

	public Ad getById(Integer adId) throws SQLException, ApplicationException {
		return dataAd.getById(adId);
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