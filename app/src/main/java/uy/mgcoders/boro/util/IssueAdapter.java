package uy.mgcoders.boro.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import uy.mgcoders.boro.R;
import uy.mgcoders.boro.objects.Issue;

/**
 * Created by r on 24/04/15.
 */
public class IssueAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Issue> mIssues = Collections.emptyList();

    public IssueAdapter(List<Issue> mIssues) {
        this.mIssues = mIssues;
    }

    public void updateList(List<Issue> data) {
        mIssues = data;
        notifyDataSetChanged();
    }

    public void addItem(int position, Issue data) {
        mIssues.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mIssues.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mIssues.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.task_item, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        viewHolder.name.setText(mIssues.get(position).getNombre());

    }
}
