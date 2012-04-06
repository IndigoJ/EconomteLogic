package com.economte.sales;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.net.*;

public class XML_Parser {

	private static ArrayList<Sale> list_of_sales = new ArrayList<Sale>();

	private static ArrayList<Category> list_of_categories = new ArrayList<Category>();

	private static ArrayList<String> list_of_cities = new ArrayList<String>();

	private static ArrayList<Advertiser> list_of_advertisers = new ArrayList<Advertiser>();

	private static Sale sale_buffer = null;

	private static Category category_buffer = null;
	
	private static Advertiser advertiser_buffer = null;

	public static ArrayList<Sale> getSales(String s) throws SAXException,
			IOException, ParserConfigurationException {

		list_of_sales.clear();

		Document xml_doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(s);

		visitSales(xml_doc, 0);
		return list_of_sales;

	}

	public static void visitSales(Node _node, int _lvl) {

		NodeList list_of_nodes = _node.getChildNodes();
		String parrent;
		String value;

		for (int i = 0; i < list_of_nodes.getLength(); i++) {

			if (list_of_nodes.item(i).getNodeType() == Node.TEXT_NODE) {

				parrent = list_of_nodes.item(i).getParentNode().getNodeName();
				value = list_of_nodes.item(i).getNodeValue();

				if (parrent.equals("company_name")) {
					sale_buffer.setCompany_name(value);
				}
				if (parrent.equals("type")) {
					sale_buffer.setType(value);
				}
				if (parrent.equals("city")) {
					sale_buffer.setCity(value);
				}
				if (parrent.equals("sale_name")) {
					sale_buffer.setSale_name(value);
				}
				if (parrent.equals("start_date")) {
					sale_buffer.setStart_date(value);
				}
				if (parrent.equals("end_date")) {
					sale_buffer.setEnd_date(value);
				}
				if (parrent.equals("description")) {
					sale_buffer.setDescription(value);
				}

			} else {

				if (list_of_nodes.item(i).getNodeName().equals("sale")) {

					sale_buffer = new Sale();
					list_of_sales.add(sale_buffer);

				}
			}
			visitSales(list_of_nodes.item(i), _lvl + 1);
		}
	}

	public static ArrayList<Category> getCategories(String s)
			throws SAXException, IOException, ParserConfigurationException {

		list_of_categories.clear();

		Document xml_doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(s);

		visitCategories(xml_doc, 0);

		return list_of_categories;
	}

	private static void visitCategories(Node _node, int _lvl) {
		NodeList list_of_nodes = _node.getChildNodes();
		String parrent;
		String value;

		for (int i = 0; i < list_of_nodes.getLength(); i++) {

			if (list_of_nodes.item(i).getNodeType() == Node.TEXT_NODE) {

				parrent = list_of_nodes.item(i).getParentNode().getNodeName();
				value = list_of_nodes.item(i).getNodeValue();

				if (parrent.equals("name")) {
					category_buffer.setCategory_name(value);
				}

				if (parrent.equals("logo")) {
					category_buffer.setCategory_logo(value);
				}

				if (parrent.equals("xml_adress")) {
					category_buffer.setCategory_xml(value);
				}

			} else {
				if (list_of_nodes.item(i).getNodeName().equals("category")) {

					category_buffer = new Category();
					list_of_categories.add(category_buffer);

				}
			}
			visitCategories(list_of_nodes.item(i), _lvl + 1);
		}
	}

	public static ArrayList<String> getCities(String s) throws SAXException,
			IOException, ParserConfigurationException {

		list_of_cities.clear();

		Document xml_doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(s);

		visitCities(xml_doc, 0);

		System.out.println(list_of_cities);

		return list_of_cities;
	}

	public static void visitCities(Node _node, int _lvl) {
		NodeList list_of_nodes = _node.getChildNodes();
		String parrent;
		String value;

		for (int i = 0; i < list_of_nodes.getLength(); i++) {

			if (list_of_nodes.item(i).getNodeType() == Node.TEXT_NODE) {

				parrent = list_of_nodes.item(i).getParentNode().getNodeName();
				value = list_of_nodes.item(i).getNodeValue();

				if (parrent.equals("city") && !list_of_cities.contains(value)) {
					list_of_cities.add(value);
				}

			}
			visitCities(list_of_nodes.item(i), _lvl + 1);
		}
	}

	public static ArrayList<Advertiser> getAdvertisers(String s)
			throws SAXException, IOException, ParserConfigurationException {

		list_of_advertisers.clear();

		Document xml_doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().parse(s);

		visitAdvertisers(xml_doc, 0);

		return list_of_advertisers;
	}

	private static void visitAdvertisers(Node _node, int _lvl) {
		NodeList list_of_nodes = _node.getChildNodes();
		String parrent;
		String value;

		for (int i = 0; i < list_of_nodes.getLength(); i++) {

			if (list_of_nodes.item(i).getNodeType() == Node.TEXT_NODE) {

				parrent = list_of_nodes.item(i).getParentNode().getNodeName();
				value = list_of_nodes.item(i).getNodeValue();

				if (parrent.equals("company_name")) {
					advertiser_buffer.setName(value);
				}

				if (parrent.equals("logo")) {
					advertiser_buffer.setLogo(value);
				}

				if (parrent.equals("adress")) {
					advertiser_buffer.setAdress(value);
				}
				
				if (parrent.equals("description")) {
					advertiser_buffer.setDescription(value);
				}

			} else {
				if (list_of_nodes.item(i).getNodeName().equals("owner")) {

					advertiser_buffer = new Advertiser();
					list_of_advertisers.add(advertiser_buffer);

				}
			}
			visitAdvertisers(list_of_nodes.item(i), _lvl + 1);
		}
	}

	public static String translit(String s) {
		s = s.replaceAll("А", "A");
		s = s.replaceAll("Б", "B");
		s = s.replaceAll("В", "V");
		s = s.replaceAll("Г", "G");
		s = s.replaceAll("Д", "D");
		s = s.replaceAll("Е", "E");
		s = s.replaceAll("Ё", "E");
		s = s.replaceAll("Ж", "J");
		s = s.replaceAll("З", "Z");
		s = s.replaceAll("И", "I");
		s = s.replaceAll("Й", "Y");
		s = s.replaceAll("К", "K");
		s = s.replaceAll("Л", "L");
		s = s.replaceAll("М", "M");
		s = s.replaceAll("Н", "N");
		s = s.replaceAll("О", "O");
		s = s.replaceAll("П", "P");
		s = s.replaceAll("Р", "R");
		s = s.replaceAll("С", "S");
		s = s.replaceAll("Т", "T");
		s = s.replaceAll("У", "U");
		s = s.replaceAll("Ф", "F");
		s = s.replaceAll("Х", "H");
		s = s.replaceAll("Ц", "TS");
		s = s.replaceAll("Ч", "CH");
		s = s.replaceAll("Ш", "SH");
		s = s.replaceAll("Щ", "SCH");
		s = s.replaceAll("Ъ", "Y");
		s = s.replaceAll("Ы", "YI");
		s = s.replaceAll("Ь", "");
		s = s.replaceAll("Э", "E");
		s = s.replaceAll("Ю", "YU");
		s = s.replaceAll("Я", "YA");
		s = s.replaceAll("а", "a");
		s = s.replaceAll("б", "b");
		s = s.replaceAll("в", "v");
		s = s.replaceAll("г", "g");
		s = s.replaceAll("д", "d");
		s = s.replaceAll("е", "e");
		s = s.replaceAll("ё", "e");
		s = s.replaceAll("ж", "j");
		s = s.replaceAll("з", "z");
		s = s.replaceAll("и", "i");
		s = s.replaceAll("й", "y");
		s = s.replaceAll("к", "k");
		s = s.replaceAll("л", "l");
		s = s.replaceAll("м", "m");
		s = s.replaceAll("н", "n");
		s = s.replaceAll("о", "o");
		s = s.replaceAll("п", "p");
		s = s.replaceAll("р", "r");
		s = s.replaceAll("с", "s");
		s = s.replaceAll("т", "t");
		s = s.replaceAll("у", "u");
		s = s.replaceAll("ф", "f");
		s = s.replaceAll("х", "h");
		s = s.replaceAll("ц", "ts");
		s = s.replaceAll("ч", "ch");
		s = s.replaceAll("ш", "sh");
		s = s.replaceAll("щ", "sch");
		s = s.replaceAll("ъ", "y");
		s = s.replaceAll("ы", "yi");
		s = s.replaceAll("ь", "");
		s = s.replaceAll("э", "t");
		s = s.replaceAll("ю", "yu");
		s = s.replaceAll("я", "ya");
		s = s.replaceAll(" ", "_");
		return s;
	}

}
