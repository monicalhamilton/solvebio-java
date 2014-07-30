package com.solvebio.model;

import com.solvebio.net.APIResource;

public abstract class SolveBioModel extends APIResource {
	protected int id;
	
	public int getId() {
		return -1;
	}

	public void setId(int id) {
		this.id = id;
	}
}
