package com.xseed.gamemanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class GameManagerTouchDisabledLinearLayout extends LinearLayout
{
	public GameManagerTouchDisabledLinearLayout(Context context)
	{
		super(context);
	}
	
	public GameManagerTouchDisabledLinearLayout(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}
	
	public GameManagerTouchDisabledLinearLayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		return true;
	}
}