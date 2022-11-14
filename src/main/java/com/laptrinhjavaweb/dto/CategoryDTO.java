package com.laptrinhjavaweb.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO extends BaseDTO<CategoryDTO> {

	private String name;
	private String code;
	private List<Long> news = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Long> getNews() {
		return news;
	}
	public void setNews(List<Long> news) {
		this.news = news;
	}
}
