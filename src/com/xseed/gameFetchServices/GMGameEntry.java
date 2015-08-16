package com.xseed.gameFetchServices;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class GMGameEntry
{
	private String mName, mDescription, mPrice ;
	private String mUrl, mImage;
	private double mRating;
	private ArrayList<Demographic> mDemographics = new ArrayList<>();
	public static class Demographic
	{
		private String mCountryName;
		private int mPercent;
		public Demographic(String countryName, int percent) 
		{
			// TODO Auto-generated constructor stub
			mCountryName = countryName;
			mPercent = percent;
		}
		public String getCountryName()
		{
			return mCountryName;
		}
		
		public int getPercentage()
		{
			return mPercent;
		}
	}
	
	
	public GMGameEntry(String name, String image, String url, String price, double rating, String des, ArrayList<Demographic> demographic)
	{
		mName = name;
		mImage = image;
		mUrl = url;
		mPrice = price;
		mRating = rating;
		mDescription = des;
		mDemographics = demographic;
	}
	
	public String getName() 
	{
		return mName;
	}
	
	public String getDescription()
	{
		return mDescription;
	}
	
	public String getURL()
	{
		return mUrl; 
	}
	
	public String getImageURL()
	{
		return mImage;
	}
	
	public String getPrice()
	{
		return mPrice;
	}
	
	public double getRating()
	{
		return mRating;
	}
	
	public ArrayList<Demographic> getDemoGraphic()
	{
		return mDemographics;
	}
}
