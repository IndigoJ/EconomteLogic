package com.economte.sales;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Advertiser implements Parcelable, Serializable {

	private String name = "";
	private String logo = "";
	private String adress = "";
	private String description = "";

	public void setDescription(String _description) {
		description = _description;
	}

	public void setName(String _name) {
		name = _name;
	}

	public void setLogo(String _logo) {
		logo = _logo;
	}

	public void setAdress(String _adress) {
		adress = _adress;
	}

	public String getName() {
		return name;
	}

	public String getAdress() {
		return adress;
	}

	public String getLogo() {
		return logo;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		return name + " " + logo + " " + adress + " " + description + "\n";
	}

	public Advertiser(){
		
	}
	private Advertiser(Parcel in) {
		this.name = in.readString();
		this.logo = in.readString();
		this.adress = in.readString();
		this.description = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Advertiser> CREATOR = new Parcelable.Creator<Advertiser>() {

		public Advertiser createFromParcel(Parcel in) {
			return new Advertiser(in);
		}

		public Advertiser[] newArray(int size) {
			return new Advertiser[size];
		}
	};

	@Override
	public void writeToParcel(Parcel parcel, int flag) {
		parcel.writeString(name);
		parcel.writeString(logo);
		parcel.writeString(adress);
		parcel.writeString(description);

	}

}
