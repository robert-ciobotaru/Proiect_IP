package com.usermanagement.DTO;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UserCreateDto {
    
	
	@Size(max=255, message="Invalid Length")
	public String country;
	@Size(max=255, message="Invalid Length")
    public String city;
    public Boolean newsCrawler;
    public Boolean hazzardCrawler;
    public Boolean weatherCrawler;
    @Email
    public String email;
    
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
