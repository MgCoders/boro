package uy.mgcoders.boro.util;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uy.mgcoders.boro.R;
import uy.mgcoders.boro.objects.Issue;

/**
 * Created by r on 24/04/15.
 */
public class IssueAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Issue> mIssues = Collections.emptyList();
    private boolean issuesOrderedByPriority = true;
    private Comparator<Issue> mIssueComparator = new Comparator<Issue>() {
        @Override
        public int compare(Issue lhs, Issue rhs) {
            if (issuesOrderedByPriority)
                return lhs.getPriority().compareToIgnoreCase(rhs.getPriority());
            else if (lhs.getProjectShortName().compareToIgnoreCase(rhs.getProjectShortName()) == 0)
                return lhs.getNumberInProject().compareToIgnoreCase(rhs.getNumberInProject());
            else
                return lhs.getProjectShortName().compareToIgnoreCase(rhs.getProjectShortName());
        }

    };

    public IssueAdapter(List<Issue> mIssues) {
        this.mIssues = mIssues;
        //Sort first time by priority.
        sortIssuesToogle();
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

    public void sortIssuesToogle() {
        Collections.sort(mIssues, mIssueComparator);
        issuesOrderedByPriority = !issuesOrderedByPriority;
        notifyDataSetChanged();
    }

    public Issue getItem(int position) {
        return mIssues.get(position);
    }

    @Override
    public int getItemCount() {
        return mIssues.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.issue_item, viewGroup, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        Issue i = mIssues.get(position);
        viewHolder.name.setText(i.getProjectShortName() + "-" + i.getNumberInProject());
        viewHolder.summary.setText(i.getSummary());
        viewHolder.state.setText(i.getState());
        viewHolder.priority.setText(i.getPriority());
        viewHolder.priority.setTextColor(Color.parseColor(i.getColor().getFg()));

    }


}
