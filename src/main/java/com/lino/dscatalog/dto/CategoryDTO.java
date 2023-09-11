package com.lino.dscatalog.dto;

import java.io.Serializable;

import com.lino.dscatalog.entities.Category;

public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;

	public CategoryDTO() {
		// TODO Auto-generated constructor stub
	}

	public CategoryDTO(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
