package com.xseed.gameFetchServices;

import java.util.ArrayList;

public interface GMGameFetchServiceInterface
{
	void onLoadGameListStarted();
	void onLoadGameListFailed();
	void onLoadGameListSuccess(ArrayList<GMGameEntry> gameList, int nuberOfGames);
}