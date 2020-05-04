package org.hyperion.cache;

public class Request {
	
private int cache;
private int id;

	public Request(int cache, int id) {
		this.cache = cache;
		this.id = id;
	}

	public int getCache() {
		return cache;
	}

	public int getId() {
		return id;
	}
}