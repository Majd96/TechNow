package com.majd.technow.adapters;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.majd.technow.R;
import com.majd.technow.database.CheckClass;
import com.majd.technow.models.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by majd on 11/11/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private ArrayList<Article> articles;
    private Context mContext;
    private OnArticleClickListener listener;


    public ArticleAdapter(ArrayList<Article> articles, Context mContext, OnArticleClickListener listener) {
        this.articles = articles;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, final int position) {

        String publishedDate = articles.get(position).getPublishedDate();
        holder.author.setText(articles.get(position).getAuthor());
        holder.title.setText(articles.get(position).getTitle());
        holder.desc.setText(articles.get(position).getDescription());
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articles.get(position).getUrl()));
                if (browserIntent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(browserIntent);
                }
            }
        });
        try {
            holder.date.setText(publishedDate.substring(0, 10));
        } catch (StringIndexOutOfBoundsException e) {

        }

        Picasso.with(mContext).load(articles.get(position).getImageUrl()).into(holder.imageView);

        Log.d("$%$%$%", CheckClass.isSaved(mContext, articles.get(position).getDescription()) + "  " + position + "  " + articles.get(position).getAuthor());
        if (CheckClass.isSaved(mContext, articles.get(position).getDescription()) > 0) {
            holder.imageButton.setBackgroundResource(R.drawable.saved_article);
            holder.imageButton.setTag(R.drawable.saved_article);
        } else {
            holder.imageButton.setBackgroundResource(R.drawable.unsaved_article);
            holder.imageButton.setTag(R.drawable.unsaved_article);

        }


    }

    @Override
    public int getItemCount() {
        if (articles == null) return 0;
        else return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView author;
        public TextView title;
        public TextView desc;
        public TextView link;
        public TextView date;
        private ImageButton imageButton;


        public ArticleViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.articleImage);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.description);
            link = itemView.findViewById(R.id.link);
            date = itemView.findViewById(R.id.date);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onArticleClick(getAdapterPosition(), imageButton);

        }
    }


    public interface OnArticleClickListener {
        void onArticleClick(int clickedItemIndex, View view);
    }
}
