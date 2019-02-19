package nhatdd.nhanambook.model;

public class InfoBill {
    private int idInfoBill;
    private Bill bill;
    private Book book;
    private int amountPay;

    public InfoBill(){

    }

    public int getIdInfoBill() {
        return idInfoBill;
    }

    public void setIdInfoBill(int idInfoBill) {
        this.idInfoBill = idInfoBill;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getAmountPay() {
        return amountPay;
    }

    public void setAmountPay(int amountPay) {
        this.amountPay = amountPay;
    }

    public InfoBill(int idInfoBill, Bill bill, Book book, int amountPay) {

        this.idInfoBill = idInfoBill;
        this.bill = bill;
        this.book = book;
        this.amountPay = amountPay;
    }

    @Override
    public String toString() {
        return  "InfoBill{" +
                "idInfoBill=" + idInfoBill +
                ", bill=" + bill.toString() +
                ", book=" + book.toString() +
                ", amountPay=" + amountPay +
                '}';
    }
}
