package net.eryingzhang.trend.pojo;

import java.io.Serializable;

public class Index implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String code;
	String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
