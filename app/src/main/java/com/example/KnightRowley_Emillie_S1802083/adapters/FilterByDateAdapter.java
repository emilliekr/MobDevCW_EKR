/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.KnightRowley_Emillie_S1802083.R;

import java.util.List;
import java.util.Map;

public class FilterByDateAdapter extends MainAdapter{

    public FilterByDateAdapter(Context context, List<String> groupList, Map<String, List<String>> disruptionCollection) {
        super(context, groupList, disruptionCollection);
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String mobileName = getGroup(i).toString();
        String[] parts = mobileName.split("#!SPLIT_LOL!#");
        mobileName = parts[0];
        int duration = Integer.parseInt(parts[1]);
        String orange = "FFA500";

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_item, null);
        }
        TextView item = view.findViewById(R.id.incident);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(mobileName);

        if(duration <= 1){
            item.setBackgroundColor(Color.parseColor("#0D" + orange));
        } else if (duration <= 7){
            item.setBackgroundColor(Color.parseColor("#40" + orange));
        } else if (duration <= 14){
            item.setBackgroundColor(Color.parseColor("#80" + orange));
        } else if (duration <= 28){
            item.setBackgroundColor(Color.parseColor("#BF" + orange));
        } else {
            item.setBackgroundColor(Color.parseColor("#FF" + orange));
        }


        return view;
    }
}
