package com.economte.sales;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Sale implements Parcelable, Serializable {

	private String company_name;
	private String type;
	private String city;
	private String sale_name;
	private String start_date;
	private String end_date;
	private String description;
	
	public void setCompany_name(String _company_name) {
		company_name = _company_name;
	}

	public void setType(String _type) {
		type = _type;
	}
	
	public void setCity(String _city) {
		city = _city;
	}
	
	public void setSale_name(String _sale_name) {
		sale_name = _sale_name;
	}
	
	public void setStart_date(String _start_date) {
		start_date = _start_date;
	}
	
	public void setEnd_date(String _end_date) {
		end_date = _end_date;
	}
	
	public void setDescription(String _description) {
		description = _description;
	}
	
	public String getCompany_name() {
		return company_name;
	}

	public String getType() {
		return type;
	}

	public String getCity() {
		return city;
	}

	public String getSale_name() {
		return sale_name;
	}

	public String getStart_date() {
		return start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public String getDescription() {
		return description;
	}

        //Метод вычисления времени до начала акции,если она ещё не началась
	//и до конца,если уже началась
	//Если акция ещё не началась,возвращает орицательное значение
	//Если началась - положительное
	public static long days(String begin_date,String end_date)
	{
		try
		{
		//Получение текущей даты
			Date current = Calendar.getInstance().getTime();
			Date begin;
		
			if(((begin = DateFormat.getDateInstance().parse(convert_sql_date(begin_date))).after(current)))
			{
				//Вычисление разницы между датами, если акция ещё не началсь
				//Разница получается в миллисекундах,поэтому полученное значение делится на количество  миллисекунд в дне
				//А именно 86400000
				return ((current.getTime() - begin.getTime())/86400000) - 1;
			}
			else
			{
			//Вычисление разницы, если акция уже началась
				Date end = DateFormat.getDateInstance().parse(convert_sql_date(end_date));
				return ((end.getTime()-current.getTime())/86400000) + 1;
			}
		}
		catch(ParseException exc)
		{
			return 0;
		}
	}
	
	//Метод, который конвертирует дату из формата sql-даты в формат,который понимает java
	public static String convert_sql_date(String sql_date)
	{
		//yyyy-mm-dd - sql формат
		//dd.mm.yyyy - java формат
		String[] parts = sql_date.split("-");
		return parts[2]+"."+parts[1]+"."+parts[0];
	}

	public String toString() {
		return company_name + " " + type + " " + city + " " + sale_name + " " + start_date + " " + end_date + " " + description + "\n";
	}
	
	private Sale(Parcel in)
	{
		this.company_name = in.readString();
		this.type = in.readString();
		this.city = in.readString();
		this.sale_name = in.readString();
		this.start_date = in.readString();
		this.end_date = in.readString();
		this.description = in.readString();
	}
	public Sale() {
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	public static final Parcelable.Creator<Sale> CREATOR = new Parcelable.Creator<Sale>() {
		 
	    public Sale createFromParcel(Parcel in) {
	      return new Sale(in);
	    }
	 
	    public Sale[] newArray(int size) {
	      return new Sale[size];
	    }
	};

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(company_name);
		parcel.writeString(type);
		parcel.writeString(city);
		parcel.writeString(sale_name);
		parcel.writeString(start_date);
		parcel.writeString(end_date);
		parcel.writeString(description);
		
	}
	
}
