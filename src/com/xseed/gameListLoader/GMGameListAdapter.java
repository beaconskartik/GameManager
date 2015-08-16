package com.xseed.gameListLoader;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xseed.gameFetchServices.GMGameEntry;
import com.xseed.gamemanager.R;

public class GMGameListAdapter extends BaseAdapter 
{ 
    public GMGameListAdapter(Context context, List<GMGameEntry> items) 
    {
        this.mGameList = items;     
        this.mContext = context;
    }
 
    @Override
    public int getCount() 
    {
        return mGameList.size();
    }
 
    @Override
    public Object getItem(int position)
    {
        return mGameList.get(position);
    }
 
    @Override
    public long getItemId(int id) {
        return id;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        GameListViewHolder holder;
        if (convertView == null) 
        {
            convertView = View.inflate(mContext, R.layout.adapter_row_game_display, null);
            holder = new GameListViewHolder();
            holder.gameName = (TextView) convertView.findViewById(R.id.GameTitle);
            holder.clickToMoreInfo = (ImageView) convertView.findViewById(R.id.GameclicktoExpand);
            holder.gamePrice = (TextView) convertView.findViewById(R.id.GamePrice);
            convertView.setTag(holder);
        } 
        else
        {
            holder = (GameListViewHolder) convertView.getTag();
        }
        holder.gameName.setText(mGameList.get(position).getName());
        
        String price = mGameList.get(position).getPrice();
        if (price.contentEquals("0"))
        {
        	holder.gamePrice.setText("free");
        }
        else
        {
        	holder.gamePrice.setText("$"+price);
        }
        holder.clickToMoreInfo.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				
				
			}
		});
       
        return convertView;
    }
 
    private static class GameListViewHolder 
    {
        TextView gameName;
        TextView gamePrice;
        ImageView clickToMoreInfo;
    }
    
    //members variable
    private final List<GMGameEntry> mGameList;
    private final Context mContext;
}