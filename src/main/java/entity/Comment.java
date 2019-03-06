package entity;

import java.io.Serializable;

public class Comment implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private Ad ad;
  private User user;
  private String text;
  private String date;

  public Comment() {
  }

  public Comment(Integer id, Ad ad, User user, String text, String date) {
    this.setId(id);
    this.setAd(ad);
    this.setUser(user);
    this.setText(text);
    this.setDate(date);
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Comment) && (((Comment)obj).getId().equals(this.getId()));
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Ad getAd() {
    return ad;
  }

  public void setAd(Ad ad) {
    this.ad = ad;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}