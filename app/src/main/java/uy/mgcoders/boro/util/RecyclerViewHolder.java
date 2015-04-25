package uy.mgcoders.boro.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import uy.mgcoders.boro.R;

/**
 * Created by r on 24/04/15.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView name;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.task_name);

    }
}
