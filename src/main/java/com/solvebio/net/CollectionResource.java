package com.solvebio.net;

import java.util.List;

public abstract class CollectionResource<T> extends APIResource {
	List<T> data;
	String url;
	Integer total;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
