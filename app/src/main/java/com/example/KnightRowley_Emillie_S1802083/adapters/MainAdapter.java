/**
 * @author Knight-Rowley_Emillie_S1802083
 */
package com.example.KnightRowley_Emillie_S1802083.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.KnightRowley_Emillie_S1802083.R;

import java.util.List;
import java.util.Map;

public class MainAdapter extends BaseExpandableListAdapter {

    public Context context;
    private Map<String, List<String>> disruptionCollection;
    private List<String> groupList;

    public MainAdapter(Context context, List<String> groupList,
                       Map<String, List<String>> disruptionCollection){
        this.context = context;
        this.disruptionCollection = disruptionCollection;
        this.groupList = groupList;


    }

    @Override
    public int getGroupCount() {
        return disruptionCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return disruptionCollection.get(groupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return disruptionCollection.get(groupList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String mobileName = getGroup(i).toString();
        String[] parts = mobileName.split("#!SPLIT_LOL!#");
        mobileName = parts[0];

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_item, null);
        }
        TextView item = view.findViewById(R.id.incident);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(mobileName);

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        String model = getChild(i, i1).toString();
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child_item, null);
        }
        TextView item = view.findViewById(R.id.model);
        item.setText(model);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}