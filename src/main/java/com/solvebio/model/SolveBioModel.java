package com.solvebio.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.solvebio.net.APIResource;

public abstract class SolveBioModel extends APIResource {

	protected final String className = this.getClass().getSimpleName();
	
	@SerializedName("id")
	protected int id;

	@SerializedName("name")
	protected String name;

	@SerializedName("full_name")
	protected String fullName;

	@SerializedName("title")
	protected String title;

	@SerializedName("description")
	protected String description;

	@SerializedName("url")
	protected String url;

	@SerializedName("created_at")
	protected Date createdAt;

	@SerializedName("updated_at")
	protected Date updatedAt;
	
	public int getId() {
		return -1;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getClassName() {
		return className;
	}
	
	
}
