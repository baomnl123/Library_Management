
public class Book implements Comparable<Book> {

    private String id, name, status, publisherID;
    private double price;
    private int quantity;

    public Book(String id, String name, double price, int quantity, String status, String publisherID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.publisherID = publisherID;
    }

    Book() {
    }

    public String getId() {
        return id;
    }

    // public void setId(String id) {
    // this.id = id;
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double calcSubTotal() {
        return (price * quantity);
    }

    @Override
    public String toString() {
        return id + "; " + name + "; " + price + "; " + quantity + "; " + status + "; " + publisherID;
    }

    public void showBook1() {
        System.out.printf("|%-8s|%-30s|%-10.1f|%-10d|%-20s|%-20s|\n", id, name, price, quantity, status, publisherID);
    }

    public void showBook2() {
        System.out.printf("|%-8s|%-25s|%-6.1f|%-8d|%-8.1f", id, name, price, quantity, calcSubTotal());
    }

    public void showBook3() {
        System.out.printf("%-15s|%n", status);
    }

    @Override
    public int compareTo(Book that) {
        if (this.quantity > that.quantity) {
            return -1;
        } else if (this.quantity < that.quantity) {
            return 1;
        } else {
            if (this.price > that.getPrice()) {
                return -1;
            } else if (this.price < that.getPrice()) {
                return 1;
            } else {
                return this.name.compareTo(that.getName());
            }
        }
    }
}
