package sample.be;

public class CategoryItem {
    private int id;
    private int CategoryId;
    private int MovieId;
    //I dont set id beacuse it deosnt matter (for me)
    public CategoryItem(int categoryId, int movieId) {
        CategoryId = categoryId;
        MovieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getMovieId() {
        return MovieId;
    }

    public void setMovieId(int movieId) {
        MovieId = movieId;
    }
}
