package org.idelgado.retrofit2soup.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.idelgado.retrofit2soup.R;
import org.idelgado.retrofit2soup.model.Contributor;

import java.util.List;

public class ContributorAdapter extends RecyclerView.Adapter<ContributorAdapter.ViewHolder> {
    private List<Contributor> contributors;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contributorTextView;
        public ViewHolder(TextView v) {
            super(v);
            contributorTextView = v;
        }
    }

    public ContributorAdapter(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    @Override
    public ContributorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contributor_text_view, parent, false);

        ViewHolder vh = new ViewHolder((TextView)v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contributor contributor = contributors.get(position);
        holder.contributorTextView.setText(contributor.toString());
    }

    @Override
    public int getItemCount() {
        return contributors.size();
    }
}
