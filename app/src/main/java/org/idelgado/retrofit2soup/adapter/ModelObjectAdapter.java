package org.idelgado.retrofit2soup.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.idelgado.retrofit2soup.R;

import java.util.List;

public class ModelObjectAdapter<T> extends RecyclerView.Adapter<ModelObjectAdapter.ViewHolder> {
    private List<T> modelObject;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contributorTextView;
        public ViewHolder(TextView v) {
            super(v);
            contributorTextView = v;
        }
    }

    public ModelObjectAdapter(List<T> modelObject) {
        this.modelObject = modelObject;
    }

    @Override
    public ModelObjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_object_text_view, parent, false);

        ViewHolder vh = new ViewHolder((TextView)v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T contributor = modelObject.get(position);
        holder.contributorTextView.setText(contributor.toString());
    }

    @Override
    public int getItemCount() {
        return modelObject.size();
    }
}
