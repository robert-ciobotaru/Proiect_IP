package com.usermanagement.DTO;

import com.usermanagement.Sanitizer;

public class NewsNotificationsDTO {

	public String author;
	public String title;
	public String description;
	public String  url;
	public String urlToImage;
	public String publishedAt;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = Sanitizer.escapeSql(author);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = Sanitizer.escapeSql(title);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = Sanitizer.escapeSql(description);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = Sanitizer.escapeSql(url);
	}
	public String getUrlToImage() {
		return urlToImage;
	}
	public void setUrlToImage(String urlToImage) {
		this.urlToImage = Sanitizer.escapeSql(urlToImage);
	}
	public String getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(String publishedAt) {
		this.publishedAt = Sanitizer.escapeSql(publishedAt);
	}
	public boolean validate(){
		if(this.author == null || this.title == null || this.description == null || this.url == null || this.urlToImage == null || this.publishedAt == null)
			return false;
		return true;
	}
	
	
}
