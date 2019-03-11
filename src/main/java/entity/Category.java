package entity;

import java.io.Serializable;

public class Category implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private Subcategory[] subcategories;

  public Category() {
  }

  public Category(Integer id, String name, Subcategory[] subcategories) {
    this.setId(id);
    this.setName(name);
    this.setSubcategories(subcategories);
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Category) && (((Category)obj).getId().equals(this.getId()));
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

  public Subcategory[] getSubcategories() {
    return subcategories;
  }

  public void setSubcategories(Subcategory[] subcategories) {
    this.subcategories = subcategories;
  }
}