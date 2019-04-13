package util;

import java.io.Serializable;
import java.util.ArrayList;

import entity.Ad;

public class DataAdsPages implements Serializable {
  private static final long serialVersionUID = 1L;
  private ArrayList<Ad> ads;
  private Integer pages;

  public DataAdsPages() {
  }

  public DataAdsPages(ArrayList<Ad> ads, Integer pages) {
    this.setAds(ads);
    this.setPages(pages);
  }

  public ArrayList<Ad> getAds() {
    return ads;
  }

  public void setAds(ArrayList<Ad> ads) {
    this.ads = ads;
  }

  public Integer getPages() {
    return pages;
  }

  public void setPages(Integer pages) {
    this.pages = pages;
  }
}