package cloud.javacoder.demo1;

public class Book {
    long id;
    String name;
    String author;
    private int numOfSides;

    public Book(long id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id =" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public int getNumberOfSides() {
        return numOfSides;
    }
}
