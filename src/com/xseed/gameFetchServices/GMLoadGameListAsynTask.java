package com.xseed.gameFetchServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xseed.gameFetchServices.GMGameEntry.Demographic;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;


public class GMLoadGameListAsynTask extends AsyncTask<String, Void, ArrayList<GMGameEntry>>
{
	public GMLoadGameListAsynTask(GMGameFetchServiceInterface handler)
	{
		mGameFetchInterface = handler;
	}

	@Override
	protected void onPreExecute() 
	{
		mGameFetchInterface.onLoadGameListStarted();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<GMGameEntry> doInBackground(String... url) 
	{
		ArrayList<GMGameEntry> gameList = null;
		try
		{
			String readJSON = getJSON(url[0]);
			Log.d("GameManager", "kartik + -->" + readJSON);

			JSONArray jsonArray = new JSONArray(readJSON);
			gameList = getGameListContentFromJsonObject(jsonArray);
			mIsSuccess = true;
		} 
		catch(Exception e)
		{
			mIsSuccess = false;
			e.printStackTrace();
		}
		return gameList;
	}

	@Override
	protected void onPostExecute(ArrayList<GMGameEntry> result)
	{
		if (mIsSuccess)
		{
			mGameFetchInterface.onLoadGameListSuccess(result, mNumberOfGames);
		}
		else
		{
			mGameFetchInterface.onLoadGameListFailed();
		}
		super.onPostExecute(result);
	}

	private ArrayList<GMGameEntry> getGameListContentFromJsonObject(JSONArray jsObject) throws MalformedURLException, JSONException
	{
		ArrayList<GMGameEntry> gameList = new ArrayList<GMGameEntry>();

		// and iterate through the array
		int listSize = jsObject.length();
		mNumberOfGames = listSize;
		for (int i = 0; i < listSize; ++i)
		{
			// get individual file entry in JSON 
			JSONObject game = jsObject.getJSONObject(i);
			String name = game.has(NAME) ? game.getString(NAME) : null;
			String image = game.has(IMAGE)? game.getString(IMAGE) : null;
			String url = game.has(URL) ? game.getString(URL) : null;
			String price = game.has(PRICE) ? game.getString(PRICE) : null;
			double rating = game.has(RATING) ? game.getDouble(RATING) : 0.0;
			String desc = game.has(DESCRIPTION) ? game.getString(DESCRIPTION) : null;
			JSONArray demographic = game.has(DEMOGRAPHIC) ? game.getJSONArray(DEMOGRAPHIC) : null;

			ArrayList<Demographic> demoGraphic  = new ArrayList<>();
			if (demoGraphic != null)
			{
				int size = demographic.length();
				for (int j = 0; j<size; j++)
				{
					JSONObject demoGraphicObject = demographic.getJSONObject(j);
					demoGraphic.add(new Demographic(demoGraphicObject.has(COUNTRY) ? demoGraphicObject.getString(COUNTRY) : null , 
							demoGraphicObject.has(PERCENTAGE) ? demoGraphicObject.getInt(PERCENTAGE): null));
				}
			}
			gameList.add(new GMGameEntry(name, image, url, price, rating, desc, demoGraphic));
		}		
		return gameList;		
	}

	private String getJSON(String address) throws ClientProtocolException, IOException
	{
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(address);


		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode == 200)
		{
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;
			while((line = reader.readLine()) != null)
			{
				builder.append(line);
			}
		} 
		else 
		{
			Log.e("GameManager","Failed to get JSON object");
		}

		return builder.toString();
	}

	private GMGameFetchServiceInterface mGameFetchInterface;
	private boolean mIsSuccess = false; 
	private int mNumberOfGames = 0;
	//member variable
	public static final String NAME = "name";
	public static final String IMAGE = "image";
	public static final String URL = "url";
	public static final String PRICE = "price";
	public static final String RATING = "rating";
	public static final String DESCRIPTION = "description";
	public static final String DEMOGRAPHIC = "demographic";
	public static final String COUNTRY = "country";
	public static final String PERCENTAGE = "percentage";
}
