package com.xseed.gamemanager;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.xseed.gameFetchServices.GMAPIHitCountAsyncTask;
import com.xseed.gameFetchServices.GMGameEntry;
import com.xseed.gameFetchServices.GMGameFetchServiceInterface;
import com.xseed.gameFetchServices.GMLoadGameListAsynTask;
import com.xseed.gameListLoader.GMGameListAdapter;


public class GameManagerFragment extends Fragment implements GMGameFetchServiceInterface, OnItemClickListener
{

	public GameManagerFragment() 
	{

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_game_list,
				container, false);
		return rootView;
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		mLoadingScreen = (GameManagerTouchDisabledLinearLayout) view.findViewById(R.id.loading_view);
		mGameListRefresh = (SwipeRefreshLayout) view.findViewById(R.id.game_list_view_swipe_container);
		mGameListView = (ListView) view.findViewById(R.id.game_list_view);
		mNoGameFound = (TextView) view.findViewById(R.id.no_matching_game_found);
		mNoGameTodisplay = (TextView) view.findViewById(R.id.noGameListToDisplay);
		
		View footerView =  ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_view_footer, null, false);
        mGameListView.addFooterView(footerView);
        mNoAPIHits = (TextView) footerView.findViewById(R.id.apihit);
        mNoGames = (TextView) footerView.findViewById(R.id.gamecount);
		
        if (getAvailableNetworkType(getActivity()) != NETWORK_AVAILABILITY_STATUS.NO_NETWORK)
        {
        	new GMLoadGameListAsynTask(this).execute("http://xseed.0x10.info/api/game_data?type=json");
        	new GMAPIHitCountAsyncTask(mNoAPIHits).execute("http://xseed.0x10.info/api/game_hits?type=json");
        }
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onDestroyView() 
	{
		mLoadingScreen = null;
		mGameListView = null;
		mGameListRefresh = null;
		mNoGameFound = null;
		mNoGameTodisplay = null;
		super.onDestroyView();
	}
	
	@Override
	public void onLoadGameListStarted()
	{
		if (mLoadingScreen != null)
		{
			mLoadingScreen.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onLoadGameListFailed() 
	{
		if (mLoadingScreen != null)
		{
			mLoadingScreen.setVisibility(View.GONE);
		}	
	}

	@Override
	public void onLoadGameListSuccess(ArrayList<GMGameEntry> gameList, int numberOfGames) 
	{
		if (mLoadingScreen != null)
		{
			mLoadingScreen.setVisibility(View.GONE);
		}	
		
		if (gameList != null && !gameList.isEmpty())
		{
			mNoGameTodisplay.setVisibility(View.GONE);
			GMGameListAdapter adapter = new GMGameListAdapter(getActivity(), gameList);
			if (mGameListView != null)
			{
				mNoGames.setText("Game Count : "+numberOfGames);
				mGameListView.setAdapter(adapter);
				mGameListView.setOnItemClickListener(this);
			}
		}
		else 
		{
			mNoGameTodisplay.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) 
	{

		if (position < 0 || position > (parent.getCount() - 1))
		{
			// Invalid ID , only valid values can be
			// 0,1,...size-1
			return;
		}

		Object object = parent.getItemAtPosition(position);
		if (object instanceof GMGameEntry)
		{
			GMGameEntry clickedFileEntry = (GMGameEntry) object;
			sEntry = clickedFileEntry;
			Intent openGameDetailsIntent = new Intent(getActivity(), GameDetailsActivity.class);
			getActivity().startActivity(openGameDetailsIntent);
		}
		
	}
	
	public static GMGameEntry getCurrentClickEntry()
	{
		return sEntry;
	}
	
	public static NETWORK_AVAILABILITY_STATUS getAvailableNetworkType(Context context)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

		if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting())
		{
			int type = activeNetworkInfo.getType();
			if (type == ConnectivityManager.TYPE_MOBILE)
			{
				return NETWORK_AVAILABILITY_STATUS.DATA_PLAN;
			}
			else if (type == ConnectivityManager.TYPE_WIFI)
			{
				return NETWORK_AVAILABILITY_STATUS.WIFI;
			}
		}
		
		return NETWORK_AVAILABILITY_STATUS.NO_NETWORK;
	}
	
	//private members
	public enum NETWORK_AVAILABILITY_STATUS
	{
		NO_NETWORK,
		DATA_PLAN, 
		WIFI
	}

	public static GMGameEntry sEntry;
	private GameManagerTouchDisabledLinearLayout mLoadingScreen;
	private ListView mGameListView;
	private SwipeRefreshLayout mGameListRefresh;
	private TextView mNoGameTodisplay;
	private TextView mNoGameFound;
	private TextView mNoAPIHits;
	private TextView mNoGames;
}