package com.milkmatters.honoursproject.milkmatters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.milkmatters.honoursproject.milkmatters.R;

import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends BaseAdapter
{
    private List<Item> items = new ArrayList<Item>();
    private LayoutInflater inflater;

    public GridImageAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);

        items.add(new Item("Breastfeeding", R.drawable.breastfeeding));
        items.add(new Item("Donation", R.drawable.donation));
        items.add(new Item("Increase Milk Supply", R.drawable.increase_milk_supply));
        items.add(new Item("Latching", R.drawable.latching));
        items.add(new Item("Nutrition", R.drawable.nutrition));
        items.add(new Item("Pumping", R.drawable.pumping));
        items.add(new Item("Mastitis", R.drawable.mastitis));
        items.add(new Item("Parenting", R.drawable.parenting));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i)
    {
        return items.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return items.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = view;
        ImageView picture;
        TextView name;

        if(v == null)
        {
            v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);

        Item item = (Item)getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }

    private class Item
    {
        final String name;
        final int drawableId;

        Item(String name, int drawableId)
        {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}