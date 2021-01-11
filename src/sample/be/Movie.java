package sample.be;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int id;
    private String name;
    private int rating;
    private int ratingIMDB;
    private String filelink;
    private Timestamp lastview;
    private List<Category> categoryList;

    //when adding a new movie dont care about id because it will be set up when adding to DB
    //we need it in constructror for retriving it from DB

/*
    public Movie(int id, String name, int rating, int ratingIMDB, String filelink, Timestamp lastview) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.ratingIMDB = ratingIMDB;
        this.filelink = filelink;
        this.lastview = lastview;
        categoryList = new ArrayList<>();
    }

 */

    public Movie(int id, String name, int rating, int ratingIMDB, String filelink, Timestamp lastview, List<Category> categoryList) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.ratingIMDB = ratingIMDB;
        this.filelink = filelink;
        this.lastview = lastview;
       // if( this.categoryList==null) this.categoryList = new ArrayList<>();
        this.categoryList = new ArrayList<>();
        if(categoryList!=null) this.categoryList = categoryList;
    }


    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRatingIMDB() {
        return ratingIMDB;
    }

    public void setRatingIMDB(int ratingIMDB) {
        this.ratingIMDB = ratingIMDB;
    }

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }

    public Timestamp getLastview() {
        return lastview;
    }

    public void setLastview(Timestamp lastview) {
        this.lastview = lastview;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", ratingIMDB=" + ratingIMDB +
                ", filelink='" + filelink + '\'' +
                ", lastview=" + lastview +
                ", categoryList=" + categoryList +
                '}';
    }
}
