package uy.mgcoders.boro.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import uy.mgcoders.boro.objects.Task;
import uy.mgcoders.boro.R;

/**
 * Created by r on 24/04/15.
 */
public class TaskAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Task> mTasks = Collections.emptyList();

    public TaskAdapter(List<Task> mTasks) {
        this.mTasks = mTasks;
    }

    public void updateList(List<Task> data) {
        mTasks = data;
        notifyDataSetChanged();
    }

    public void addItem(int position, Task data) {
        mTasks.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mTasks.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.task_item, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        viewHolder.name.setText(mTasks.get(position).getNombre());

    }
}
