package sample.be;

public class Category {
    //id will be set in DB
    private int id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    /**
     * Constructor used in DB when retriving categories
     * from DB
     * @param id
     * @param name
     */
    public Category(int id, String name) {
        this.name = name;
        this.id = id;
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

    @Override
    public String toString() {
        return name;
    }
}
