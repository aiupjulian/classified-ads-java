package entity;

import java.util.ArrayList;
import java.io.Serializable;

public class State implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private ArrayList<City> cities;

  public State() {}

  public State(Integer id, String name, ArrayList<City> cities) {
    this.setId(id);
    this.setName(name);
    this.setCities(cities);
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof State) && (((State)obj).getId().equals(this.getId()));
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

  public ArrayList<City> getCities() {
    return cities;
  }

  public void setCities(ArrayList<City> cities) {
    this.cities = cities;
  }
}