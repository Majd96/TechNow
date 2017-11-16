package com.majd.technow.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by majd on 11/10/17.
 */

public class Article implements Parcelable {
    private String author;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String publishedDate;

    public Article() {

    }

    public Article(String author, String title, String description,
                   String url, String imageUrl, String publishedDate) {

        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;

    }

    protected Article(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        url = in.readString();
        imageUrl = in.readString();
        publishedDate = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(url);
        parcel.writeString(imageUrl);
        parcel.writeString(publishedDate);
    }
}
