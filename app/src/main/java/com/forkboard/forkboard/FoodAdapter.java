package com.forkboard.forkboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Comparator;

/**
 * Created by Kyle on 6/20/2016.
 */
public class FoodAdapter extends ArrayAdapter<Food> {
    private final Context context;
    private final Food[]  values;

    public FoodAdapter(Context context, Food[] values) {
        super(context, R.layout.food_adapter_layout, values);
        this.context = context;
        this.values = values;
        this.sort(new Comparator<Food>() {
            @Override
            public int compare(Food lhs, Food rhs) {
                return lhs.type().compareTo(rhs.type());
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View     rowView = inflater.inflate(R.layout.food_adapter_layout, parent, false);
        TextView $type   = (TextView) rowView.findViewById(R.id.type);
        TextView $amount = (TextView) rowView.findViewById(R.id.amount);
        $amount  .setText(values[position].quantity() + " " + values[position].units());
        $type    .setText(values[position].type());

        return rowView;
    }
}
