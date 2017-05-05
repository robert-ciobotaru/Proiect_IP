package com.usermanagement.DTO;

public class UserCreateDto {
    public String country;
    public String city;
    public boolean newsCrawler;
    public boolean hazzardCrawler;
    public boolean weatherCrawler;
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
	public boolean isNewsCrawler() {
		return newsCrawler;
	}
	public void setNewsCrawler(boolean newsCrawler) {
		this.newsCrawler = newsCrawler;
	}
	public boolean isHazzardCrawler() {
		return hazzardCrawler;
	}
	public void setHazzardCrawler(boolean hazzardCrawler) {
		this.hazzardCrawler = hazzardCrawler;
	}
	public boolean isWeatherCrawler() {
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
