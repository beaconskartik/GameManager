package com.xseed.gamemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xseed.gameFetchServices.GMDownloadImageAsyncTask;
import com.xseed.gameFetchServices.GMGameEntry;
import com.xseed.gameListLoader.GMDemoGraphicAdapter;

public class GameDetailsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_page);

		mGameEntry = GameManagerFragment.sEntry;

		mGameDesc = (TextView) findViewById(R.id.GameDesc);
		mGameTitle = (TextView) findViewById(R.id.GameTitle);
		mGamePhoto = (ImageView) findViewById(R.id.GamePhoto);
		mGateRating = (RatingBar) findViewById(R.id.GameRating);
		mGameRatingText = (TextView) findViewById(R.id.GameRatingText);
		mCountryList = (ListView) findViewById(R.id.country_list);
		
		

		// loading 
		new GMDownloadImageAsyncTask(mGamePhoto).execute(mGameEntry.getImageURL());

		Log.d("GameManager", mGameEntry.getName());

		mShareDetails = "Application Name : " + mGameEntry.getName() +  "Application Price : " + mGameEntry.getPrice()
				+ " Application Rating : " + mGameEntry.getRating() + "Application Des : " + mGameEntry.getDescription();
		mCountryList.setAdapter(new GMDemoGraphicAdapter(this, mGameEntry.getDemoGraphic()));
		mGameTitle.setText(mGameEntry.getName());
		mGameDesc.setText(mGameEntry.getDescription());
		mGameRatingText.setText(""+mGameEntry.getRating());
		mGateRating.setRating((float) mGameEntry.getRating());
	}


	// sharing information via SMS
	public void Sms(View v)
	{
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.putExtra("sms_body", mShareDetails); 
		sendIntent.setType("vnd.android-dir/mms-sms");
		startActivity(sendIntent);
	}

	//General share
	public void Share(View v)
	{
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, mShareDetails);
		sendIntent.setType("text/plain");

		if (sendIntent.resolveActivity(getPackageManager()) != null)
		{
			startActivity(Intent.createChooser(sendIntent, "Share Details"));
		}
		else
		{
			Toast.makeText(this, "No Supported Application", Toast.LENGTH_SHORT).show();
		}
	}

	
	//back button
	public void Back(View v)
	{
		finish();	
	}

	// web 
	public void App(View v)
	{
		Intent webIntent = new Intent(this, GameManagerOpenPlayStore.class);
		webIntent.putExtra(GameManagerOpenPlayStore.URL, mGameEntry.getURL()); //Optional parameters
		webIntent.putExtra(GameManagerOpenPlayStore.TITLE, mGameEntry.getName());
		startActivity(webIntent);
	}
	
	//members variable
	private String mShareDetails;
	private ImageView mGamePhoto;
	private TextView mGameTitle;
	private TextView mGameDesc;
	private RatingBar mGateRating;
	private TextView mGameRatingText;
	private GMGameEntry mGameEntry;
	private ListView mCountryList;


	//	public static final String NAME_KEY = "name_key";
	//	public static final String IMAGE_KEY = "image_url_key";
	//	public static final String URL_KEY = "url_key";
	//	public static final String PRICE_KEY = "price_key";
	//	public static final String RATING_KEY = "rating_key";
	//	public static final String DESCRIPTION_KEY = "description_key";
	//	public static final String DEMOGRAPHIC_KEY = "demographic_key";
	//	public static final String COUNTRY_KEY = "country_key";
	//	public static final String PERCENTAGE_KEY = "percentage_key";

}
