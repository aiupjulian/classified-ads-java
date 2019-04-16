package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import data.DataComment;
import entity.*;
import util.*;

public class CommentController {
	private DataComment dataComment;

	public CommentController() {
		dataComment = new DataComment();
	}

	// public Ad createAd(Ad ad) throws ApplicationException {
	// 	return dataComment.createAd(ad);
	// }

	public ArrayList<Comment> getAllByAdId(Integer adId) throws SQLException, ApplicationException {
		return dataComment.getAllByAdId(adId);
	}
}