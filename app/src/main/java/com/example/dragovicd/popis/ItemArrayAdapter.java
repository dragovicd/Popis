package com.example.dragovicd.popis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<String[]> {

    private List<String[]> scoreList = new ArrayList<String[]>();;

    static class ItemViewHolder {
        TextView sifra;
        TextView naziv;
        TextView lokacija;
    }

    public ItemArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

	@Override
	public void add(String[] object) {
		scoreList.add(object);
		super.add(object);
	}

    @Override
	public int getCount() {
		return this.scoreList.size();
	}

    @Override
	public String[] getItem(int index) {
		return this.scoreList.get(index);
	}

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        ItemViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.item_layout, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.sifra = (TextView) row.findViewById(R.id.sifra);
            viewHolder.naziv = (TextView) row.findViewById(R.id.naziv);
            viewHolder.lokacija = (TextView) row.findViewById(R.id.lokacija);
            row.setTag(viewHolder);
		} else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        String[] stat = getItem(position);
        viewHolder.sifra.setText(stat[0]);
        viewHolder.naziv.setText(stat[1]);
        viewHolder.lokacija.setText(stat[2]);
		return row;
	}
}
