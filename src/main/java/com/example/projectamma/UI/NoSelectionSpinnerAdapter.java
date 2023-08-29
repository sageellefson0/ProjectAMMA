package com.example.projectamma.UI;

/* Imports */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/** Custom ArrayAdapter for a the spinner that removes the initial selected item from the dropdown. */
public class NoSelectionSpinnerAdapter extends ArrayAdapter<String> {

    private List<String> items;
    private LayoutInflater inflater;

    public NoSelectionSpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.items = items;
        this.inflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(items.get(position));
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position == 0) {
            // Hide the "Select Client" item in the dropdown
            return new View(getContext());
        }
        View view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(items.get(position));
        return view;
    }
}
