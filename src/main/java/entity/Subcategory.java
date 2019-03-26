package entity;

import java.io.Serializable;

public class Subcategory implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private Category category;

  public Subcategory() {}

  public Subcategory(Integer id, String name, Category category) {
    this.setId(id);
    this.setName(name);
    this.setCategory(category);
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Subcategory) && (((Subcategory)obj).getId().equals(this.getId()));
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

  public Category getCategory(){
		return category;
	}
	
	public void setCategory(Category category){
		this.category=category;
	}
}