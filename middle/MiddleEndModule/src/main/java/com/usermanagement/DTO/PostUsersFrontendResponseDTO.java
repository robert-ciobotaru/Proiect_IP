package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class PostUsersFrontendResponseDTO {
	public Integer id;
    public String country;
    public String city;
    public Boolean newsCrawler;
    public Boolean hazzardCrawler;
    public Boolean weatherCrawler;
    public String email;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Boolean isNewsCrawler() {
		return newsCrawler;
	}
	public void setNewsCrawler(boolean newsCrawler) {
		this.newsCrawler = newsCrawler;
	}
	public Boolean isHazzardCrawler() {
		return hazzardCrawler;
	}
	public void setHazzardCrawler(boolean hazzardCrawler) {
		this.hazzardCrawler = hazzardCrawler;
	}
	public Boolean isWeatherCrawler() {
		return weatherCrawler;
	}
	public void setWeatherCrawler(boolean weatherCrawler) {
		this.weatherCrawler = weatherCrawler;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
}
