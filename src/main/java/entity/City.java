package entity;

import java.io.Serializable;

public class City implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private State state;

  public City() {
  }

  public City(Integer id, String name, State state) {
    this.setId(id);
    this.setName(name);
    this.setState(state);
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof City) && (((City)obj).getId().equals(this.getId()));
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

  public State getState(){
		return state;
	}
	
	public void setState(State state){
		this.state=state;
	}
}