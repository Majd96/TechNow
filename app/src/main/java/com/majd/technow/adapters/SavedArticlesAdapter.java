package com.majd.technow.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.majd.technow.R;
import com.majd.technow.database.ArticleContract;
import com.squareup.picasso.Picasso;


/**
 * Created by majd on 11/13/17.
 */

public class SavedArticlesAdapter extends RecyclerView.Adapter<SavedArticlesAdapter.SavedArticlesViewHodler> {

    private Context mContext;
    public static Cursor mCursor;
    private OnArticleClickListener listener;

    public SavedArticlesAdapter(Context context, OnArticleClickListener listener) {
        mContext = context;
        this.listener = listener;
    }

    @Override
    public SavedArticlesAdapter.SavedArticlesViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.article_item, parent, false);
        return new SavedArticlesViewHodler(view);

    }

    @Override
    public void onBindViewHolder(SavedArticlesAdapter.SavedArticlesViewHodler holder, int position) {
        mCursor.moveToPosition(position);
        String author = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_AUthor));
        String title = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_TITLE));
        String desc = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_DESCRIPTION));
        String imageUrl = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_IMAGE_URL));
        final String url = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_ARTICLE_URL));
        String date = mCursor.getString(mCursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_PUBLISHED_DATE));

        holder.author.setText(author);
        holder.title.setText(title);
        holder.desc.setText(desc);
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (browserIntent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(browserIntent);
                }
            }
        });
        try {
            holder.date.setText(date.substring(0, 10));
        } catch (StringIndexOutOfBoundsException e) {

        }
        Picasso.with(mContext).load(imageUrl).into(holder.imageView);
        holder.imageButton.setBackgroundResource(R.drawable.saved_article);
        holder.imageButton.setTag(R.drawable.saved_article);


    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        else return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public class SavedArticlesViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView author;
        public TextView title;
        public TextView desc;
        public TextView link;
        public TextView date;
        public ImageButton imageButton;

        public SavedArticlesViewHodler(View itemView) {
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
