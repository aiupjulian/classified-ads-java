package entity;

import java.io.Serializable;

public class Ad implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private String description;
  private Integer price;
  private Boolean sold;
  private String date;
  private User user;
  private String image;
  private Subcategory subcategory;
  private City city;

  public Ad() {
  }

  public Ad(Integer id, String name, String description, Integer price, Boolean sold, String date, User user, String image, Subcategory subcategory, City city) {
    this.setId(id);
    this.setName(name);
    this.setDescription(description);
    this.setPrice(price);
    this.setSold(sold);
    this.setDate(date);
    this.setUser(user);
    this.setImage(image);
    this.setSubcategory(subcategory);
    this.setCity(city);
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Ad) && (((Ad)obj).getId().equals(this.getId()));
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Boolean getSold() {
    return sold;
  }

  public void setSold(Boolean sold) {
    this.sold = sold;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Subcategory getSubcategory() {
    return subcategory;
  }

  public void setSubcategory(Subcategory subcategory) {
    this.subcategory = subcategory;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }
}