package controller;

import java.util.ArrayList;

import data.DataComment;
import entity.*;
import util.*;

public class CommentController {
	private DataComment dataComment;

	public CommentController() {
		dataComment = new DataComment();
	}

	public Comment createComment(Comment comment) throws ApplicationException {
		return dataComment.createComment(comment);
	}

	public ArrayList<Comment> getAllByAdId(Integer adId) throws ApplicationException {
		return dataComment.getAllByAdId(adId);
	}
}