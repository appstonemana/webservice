package com.example.webservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Manabendu on 2019-09-18.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {


    private List<Article> lists;
    private Context context;

    public NewsAdapter(List<Article> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_news, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Article article = lists.get(position);
        holder.mDesc.setText(article.getDescription());
        holder.mTitle.setText(article.getTitle());

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {

        TextView mTitle, mDesc;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tv_title);
            mDesc = itemView.findViewById(R.id.tv_desc);
        }
    }
}
