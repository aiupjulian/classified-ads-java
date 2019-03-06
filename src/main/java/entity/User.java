package entity;

import java.io.Serializable;

public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String username;
  private String password;
  private String phone;
  private String email;
  private String name;
  private Boolean admin;

  public User() {
  }

  public User(Integer id, String username, String password, String phone, String email, String name, Boolean admin) {
    this.setId(id);
    this.setUsername(username);
    this.setPassword(password);
    this.setPhone(phone);
    this.setEmail(email);
    this.setName(name);
    this.setAdmin(admin);
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof User) && (((User)obj).getId().equals(this.getId()));
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }
}