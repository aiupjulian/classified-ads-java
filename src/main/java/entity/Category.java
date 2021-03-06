package entity;

import java.util.ArrayList;
import java.io.Serializable;

public class Category implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private ArrayList<Subcategory> subcategories;

  public Category() {}

  public Category(Integer id, String name) {
    this.setId(id);
    this.setName(name);
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

  public ArrayList<Subcategory> getSubcategories() {
    return subcategories;
  }

  public void setSubcategories(ArrayList<Subcategory> subcategories) {
    this.subcategories = subcategories;
  }
}