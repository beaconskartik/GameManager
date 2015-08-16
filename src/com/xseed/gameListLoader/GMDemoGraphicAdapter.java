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
import com.xseed.gameFetchServices.GMGameEntry.Demographic;
import com.xseed.gamemanager.R;

public class GMDemoGraphicAdapter extends BaseAdapter 
{ 
    public GMDemoGraphicAdapter(Context context, List<Demographic> items) 
    {
        this.mDemographicList = items;     
        this.mContext = context;
    }
 
    @Override
    public int getCount() 
    {
        return mDemographicList.size();
    }
 
    @Override
    public Object getItem(int position)
    {
        return mDemographicList.get(position);
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
            convertView = View.inflate(mContext, R.layout.adapter_demographic_row, null);
            holder = new GameListViewHolder();
            holder.countryName = (TextView) convertView.findViewById(R.id.countryName);
            holder.percentageUse = (TextView) convertView.findViewById(R.id.percentageUses);
            convertView.setTag(holder);
        } 
        else
        {
            holder = (GameListViewHolder) convertView.getTag();
        }
        holder.countryName.setText(mDemographicList.get(position).getCountryName());
        
        int per = mDemographicList.get(position).getPercentage();
       
        	holder.percentageUse.setText(per+"%");

       
        return convertView;
    }
 
    private static class GameListViewHolder 
    {
        TextView countryName;
        TextView percentageUse;
    }
    
    //members variable
    private final List<Demographic> mDemographicList;
    private final Context mContext;
}