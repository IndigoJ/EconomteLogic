package com.economte.sales;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

enum ListNames {
	sales, categories, advertisers, cities
}

public class Get_Info extends AsyncTask<Boolean, Void, Void> {
	final private static String XML_CATEGORIES = "http://economte.com/sale_info/cats.xml";
	final private static String XML_CITIES = "http://economte.com/sale_info/cities.xml";
	final private static String XML_ADVERTISERS = "http://economte.com/sale_info/advertisers.xml";

	private Context context;
	private final static String cachepath = Environment
			.getExternalStorageDirectory().toString()
			+ "/data/com.economte.sales/" + "cache";

	Get_Info(Context context) {
		this.context = context;
	}

	@Override
	protected Void doInBackground(Boolean... arg0) {
		try {
			if (arg0[0]) {
				MainMenuActivity.city_list = getCities(true);
				MainMenuActivity.category_list = getCategories(true);
				MainMenuActivity.advertiser_list = getAdvertisers(true);
				Log.d("lists", "recieved");
			} else {
				MainMenuActivity.city_list = getCities(false);
				MainMenuActivity.category_list = getCategories(false);
				MainMenuActivity.advertiser_list = getAdvertisers(false);
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(Void arg0) {
		Log.d("onPostExecute", "started");
		Intent intent = new Intent();
		intent.setClass(context, MainMenuActivity.class);
		context.startActivity(intent);
	}

	public static ArrayList<Sale> getSales(String url, String city,
			String category, boolean isOnline) throws SAXException,
			IOException, ParserConfigurationException {

		ArrayList<Sale> sales = new ArrayList<Sale>();

		if (isOnline) {

			sales = XML_Parser.getSales(url + city + "/" + category);
			cacheSalesList(city, category, sales);

		} else {
			sales = getSalesListFromCache(city, category);
		}

		return sales;
	}

	public static ArrayList<Category> getCategories(boolean isOnline)
			throws SAXException, IOException, ParserConfigurationException {
		ArrayList<Category> categories = new ArrayList<Category>();

		if (isOnline) {
			categories = XML_Parser.getCategories(XML_CATEGORIES);
			cacheList(ListNames.categories, categories);
			cacheCategoryPics(categories);
		} else {
			categories = getListFromCache(ListNames.categories);
		}
		return categories;
	}

	public static ArrayList<String> getCities(boolean isOnline)
			throws SAXException, IOException, ParserConfigurationException {
		ArrayList<String> cities = new ArrayList<String>();

		if (isOnline) {
			cities = XML_Parser.getCities(XML_CITIES);
			cacheList(ListNames.cities, cities);
		} else {
			cities = getListFromCache(ListNames.cities);
		}
		return cities;
	}

	public static ArrayList<Advertiser> getAdvertisers(boolean isOnline)
			throws SAXException, IOException, ParserConfigurationException {
		ArrayList<Advertiser> advertisers = new ArrayList<Advertiser>();

		if (isOnline) {
			advertisers = XML_Parser.getAdvertisers(XML_ADVERTISERS);
			cacheList(ListNames.advertisers, advertisers);

			cacheAdvertiserPics(advertisers);
		} else {
			advertisers = getListFromCache(ListNames.advertisers);
		}
		return advertisers;
	}

	private static void cacheList(ListNames name, ArrayList list) {
		try {
			Log.d("cache " + name.toString(), "started");
			File fld = new File(cachepath);
			fld.mkdirs();

			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(cachepath + "/" + name.toString()
							+ ".cch"));
			for (int i = 0; i < list.size(); i++) {
				oos.writeObject(list.get(i));
			}
			oos.close();
			Log.d("cache " + name.toString(), "finished");
		} catch (FileNotFoundException e) {
			Log.e("exception", "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("exception", "IOException");
			e.printStackTrace();
		}
	}

	private static void cacheSalesList(String city, String category,
			ArrayList list) {
		try {
			Log.d("cache sales " + city + " " + category, "started");
			File fld = new File(cachepath + "/sales/" + city);
			fld.mkdirs();

			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(cachepath + "/sales/" + city + "/"
							+ category + ".cch"));
			Log.d("path", cachepath + "/sales/" + city + "/" + category
					+ ".cch");
			for (int i = 0; i < list.size(); i++) {
				oos.writeObject(list.get(i));
			}
			oos.close();
			Log.d("cache sales " + city + " " + category, "finished");
		} catch (FileNotFoundException e) {
			Log.e("exception", "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("exception", "IOException");
			e.printStackTrace();
		}
	}

	private static ArrayList<Sale> getSalesListFromCache(String city,
			String category) {

		ArrayList<Sale> res = new ArrayList<Sale>();
		Object obj = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					cachepath + "/sales/" + city + "/" + category + ".cch"));
			while ((obj = ois.readObject()) != null) {
				res.add((Sale) obj);
			}
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return res;
	}

	private static ArrayList getListFromCache(ListNames name) {

		ArrayList res = new ArrayList();
		Object obj = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					cachepath + "/" + name.toString() + ".cch"));
			while ((obj = ois.readObject()) != null) {
				switch (name) {
				case cities:
					res.add((String) obj);
					break;
				case categories:
					res.add((Category) obj);
					break;
				case advertisers:
					res.add((Advertiser) obj);
					break;
				}
			}
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return res;
	}

	private static void cacheCategoryPics(ArrayList<Category> list) {

		File fld = new File(cachepath + "/cat_logos/");
		Log.d("folder", fld.getAbsolutePath());
		fld.mkdirs();

		for (Category c : list) {
			String id = c.getCategory_logo();
			File file = new File(cachepath + "/cat_logos/" + id + ".cch");
			if (!file.exists() && id.length() > 1) {
				savePicFromServer("cat_logos/" + id, cachepath + "/cat_logos/"
						+ id + ".cch");
			}
		}
	}

	private static void cacheAdvertiserPics(ArrayList<Advertiser> list) {

		File fld = new File(cachepath + "/logos/");
		Log.d("folder", fld.getAbsolutePath());
		fld.mkdirs();

		for (Advertiser a : list) {
			String id = a.getLogo();
			File file = new File(cachepath + "/logos/" + id + ".cch");
			Log.d("file", file.getAbsolutePath());

			if (!file.exists() && id.length() > 1) {
				Log.d("cache adv pic", a.getLogo());
				savePicFromServer("logos/" + id, cachepath + "/logos/" + id
						+ ".cch");
			}
		}
	}

	private static void savePicFromServer(String id, String path) {
		InputStream i = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream out = null;
		try {
			URL url = new URL("http://economte.com/" + id);
			Log.d("getting pic", "http://economte.com/" + id);
			i = (InputStream) url.getContent();
			bis = new BufferedInputStream(i, 1024 * 8);
			out = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = bis.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.close();
			bis.close();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			byte[] data = out.toByteArray();
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

			Log.d("try to write to file ", path);
			FileOutputStream fos = new FileOutputStream(path);
			Log.d("writed to ", path);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
			Log.d("bitmap " + id, "compressed");
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			Log.e("logo", "seems not found");
			e.printStackTrace();
		}
	}

}
