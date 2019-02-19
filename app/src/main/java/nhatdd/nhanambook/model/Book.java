package nhatdd.nhanambook.model;

public class Book {
    private String idBook;
    private String idTypeBook;
    private String bookName;
    private String author;
    private String publisher;
    private double price;
    private int amount;

    public Book() {

    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public String getIdTypeBook() {
        return idTypeBook;
    }

    public void setIdTypeBook(String idTypeBook) {
        this.idTypeBook = idTypeBook;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Book(String idBook, String idTypeBook, String bookName, String author, String publisher, double price, int amount) {

        this.idBook = idBook;
        this.idTypeBook = idTypeBook;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return getIdBook();
    }
}
