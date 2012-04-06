package com.economte.sales;

import java.io.Serializable;

public class Category implements Serializable{

	private String caterogy_name;
	private String caterogy_logo;
	private String caterogy_xml;

	public void setCategory_name(String _category_name) {
		caterogy_name = _category_name;
	}

	public void setCategory_logo(String _category_logo) {
		caterogy_logo = _category_logo;
	}

	public void setCategory_xml(String _category_xml) {
		caterogy_xml = _category_xml;
	}

	public String getCategory_name() {
		return caterogy_name;
	}

	public String getCategory_logo() {
		return caterogy_logo;
	}

	public String getCategory_xml() {
		return caterogy_xml;
	}

	public String toString() {
		return caterogy_name + " " + caterogy_logo + " " + caterogy_xml + "\n";
	}

}
