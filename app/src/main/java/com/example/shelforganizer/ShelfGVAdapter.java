package com.example.shelforganizer;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ShelfGVAdapter extends ArrayAdapter<ShelfModel> {

    public ShelfGVAdapter(@NonNull ShelfGrid context, ArrayList<ShelfModel> shelfModelArrayList) {
        super(context, 0, shelfModelArrayList);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_items, parent, false);

        }
        ShelfModel shelfModel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.idTVCourse);
        ImageView courseIV = listitemView.findViewById(R.id.idIVcourse);
        listitemView.setOnClickListener(v-> getContext()
                .startActivity(new Intent(this.getContext(),ExampleActivity.class)));

        assert shelfModel != null;
        courseTV.setText(shelfModel.getShelf_name());
        courseIV.setImageResource(shelfModel.getImageID());
        return listitemView;
    }
}
