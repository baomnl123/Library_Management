import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

// import Performance;

public class BookManagement {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<Book> bList = new ArrayList<>();
    private String fName = "Book.txt";
    private List<Publisher> pList = getPublisherFromFile();

    private List<Publisher> getPublisherFromFile() {
        String fName = "Publisher.txt";
        List<Publisher> pubList = new ArrayList<>();
        List<String> tmp = Performance.readLineFromFile(fName);

        for (String x : tmp) {
            StringTokenizer stk = new StringTokenizer(x, ";");
            String id = stk.nextToken();
            String name = stk.nextToken();
            String phone = stk.nextToken();

            pubList.add(new Publisher(id, name, phone));
        }
        return pubList;
    }

    public BookManagement() {
        bList = (ArrayList<Book>) getBooksFromFile(fName);
    }

    private List<Book> getBooksFromFile(String fName) {
        List<Book> bookList = new ArrayList<>();
        List<String> tmp = Performance.readLineFromFile(fName);

        for (String x : tmp) {
            StringTokenizer stk = new StringTokenizer(x, ";");
            String id = stk.nextToken();
            String name = stk.nextToken();
            double price = Double.parseDouble(stk.nextToken());
            int quantity = Integer.parseInt(stk.nextToken());
            String status = stk.nextToken();
            String pubID = stk.nextToken();

            bookList.add(new Book(id, name, price, quantity, status, pubID));
        }
        return bookList;
    }

    // FUNCTION 1
    public void addBook() {
        String id, name, status, display;
        String publisherID;
        double price;
        int quantity;
        boolean check;

        do {
            id = Performance.getID("book", "(BXXXXX)", "^B\\d{5}$");
            check = isBookExist(id);
            if (check == true) {
                System.out.println("The book has already existed!");
            }
        } while (check == true);
        do {
            System.out.print("Enter book's name: ");
            name = sc.nextLine();
        } while (!name.matches("^[a-zA-Z ]{5,30}$"));
        do {
            System.out.print("Enter book's status [Y = Available, N = Not Available]: ");
            status = sc.nextLine().toUpperCase();
            if (status.matches("Y")) {
                display = "Available";
            } else {
                display = "Not Available";
            }
        } while (!status.matches("^[YN]$"));
        do {
            System.out.print("Enter book's price: ");
            price = Integer.parseInt(sc.nextLine());
        } while (price <= 0);
        do {
            System.out.print("Enter book's quantity: ");
            quantity = Integer.parseInt(sc.nextLine());
        } while (quantity < 1);
        publisherID = Performance.getID("publisher", "(PXXXXX)", "^P\\d{5}$");
        for (int i = 0; i < pList.size(); i++) {
            String tmp = pList.get(i).getId();
            if (tmp.equalsIgnoreCase(publisherID)) {
                bList.add(new Book(id, name, price, quantity, display, publisherID));
                System.out.println("A new book is added.");
                return;
            }
        }
        saveToFile();
        System.out.println("Publisher's id not found. A book is not added.");
    }

    // Check if Book is existed
    public boolean isBookExist(String id) {
        if (bList.isEmpty()) {
            return false;
        }
        for (int i = 0; i < bList.size(); i++) {
            if (bList.get(i).getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    // Find book by ID
    public Book searchBookByID(String id) {
        if (bList.isEmpty()) {
            return null;
        }
        for (int i = 0; i < bList.size(); i++) {
            if (bList.get(i).getId().equalsIgnoreCase(id)) {
                return bList.get(i);
            }
        }
        return null;
    }

    // FUNCTION 2
    public void searchBook() {
        List<Book> x;
        int choice;

        System.out.println("-------------BOOKS SEARCH-------------");
        System.out.println("| 1. Search a book by publisher's ID |");
        System.out.println("| 2. Search a book by book's name    |");
        System.out.println("| 0. Back                            |");
        System.out.println("--------------------------------------");

        do {
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    String publisherID = Performance.getID("publisher", "(PXXXXX)", "^P\\d{5}$");
                    x = searchBooksByPublishersID(publisherID);

                    if (x == null) {
                        System.out.println("Book not found!");
                    } else {
                        for (Book tmp : x) {
                            tmp.showBook1();
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter book's name: ");
                    String bookName = sc.nextLine();
                    x = searchBookByName(bookName);
                    break;
            }
        } while (choice != 0);
    }

    private List<Book> searchBooksByPublishersID(String publisherID) {
        List<Publisher> checkList = getPublisherFromFile();
        List<Book> tmp = new ArrayList<>();

        if (bList.isEmpty()) {
            return null;
        }

        for (int i = 0; i < bList.size(); i++) {
            for (int j = 0; j < checkList.size(); j++) {
                if (bList.get(i).getPublisherID().equalsIgnoreCase(publisherID)) {
                    if (checkList.get(i).getId().equalsIgnoreCase(publisherID)) {
                        tmp.add(bList.get(i));
                    }
                }
            }
        }
        if (tmp.isEmpty()) {
            return null;
        }
        return tmp;
    }

    private List<Book> searchBookByName(String bookName) {
        List<Book> tmp = new ArrayList<>();
        if (bList.isEmpty()) {
            return null;
        }
        for (int i = 0; i > bList.size(); i++) {
            if (bList.get(i).getName().contains(bookName)) {
                tmp.add(bList.get(i));
            }
        }
        if (tmp.isEmpty()) {
            return null;
        }
        return tmp;
    }

    // FUNCTION 3
    public void updateBook() {
        String id, newName;
        boolean findBook;
        Book x;

        id = Performance.getID("book", "(BXXXXX)", "^B\\d{5}$");
        findBook = isBookExist(id);
        x = searchBookByID(id);

        if (findBook == true) {
            System.out.println("Book's name doesn't exist!");
        } else {
            System.out.println("Here is the book before updated the name");
            x.showBook1();
            // boolean check = true;
            String choice;
            if (true) {
                do {
                    System.out.print("Do you want to update [Y = Yes, N = No]: ");
                    choice = sc.nextLine().toUpperCase();
                } while (!choice.matches("^[YN]$"));
                if (choice.matches("Y")) {
                    do {
                        System.out.print("Enter new book's name: ");
                        newName = sc.nextLine();
                    } while (!newName.matches("^[a-zA-Z]{5,30}$"));
                    System.out.println("The new book's name is updated!");
                } else {
                    System.out.println("The book's name is not updated!");
                }
            }
        }
    }

    // FUNCTION 4
    public void deleteBook() {
        String id, choice;
        boolean findBook;
        Book x;

        id = Performance.getID("book", "(BXXXXX)", "^B\\d{5}$");
        findBook = isBookExist(id);
        x = searchBookByID(id);

        if (findBook) {
            System.out.println("Here is the book before deleted");
            x.showBook1();
            do {
                System.out.print("Do you want to delete the book [Y = Yes, N = No]: ");
                choice = sc.nextLine().toUpperCase();
            } while (!choice.matches("^[YN]$"));
            if (choice.matches("Y")) {
                bList.remove(findBook);
                System.out.println("The book is deleted successfully!");
            } else {
                System.out.println("The book is not deleted!");
            }
        } else {
            System.out.println("The book's name doesn't exist!");
        }
    }

    // FUNCTION 5
    public void saveToFile() {
        List<String> tmp = new ArrayList<>();

        if (bList.isEmpty()) {
            System.out.println("The list is empty");
            return;
        }

        for (Book x : bList) {
            tmp.add(x.getId() + ";" + x.getName() + ";" + x.getPrice() + ";" + x.getQuantity() + ";" + x.getStatus()
                    + ";" + x.getPublisherID());
        }

        Performance.writeListToFile(fName, tmp);
    }

    // FUNCTION 6
    public void printBookFromFile() {
        List<Book> bookList = getBooksFromFile(fName);
        // Implement Comparable in Book to use Collections
        Collections.sort(bookList);

        System.out.println("Here is the book list");
        String header = String.format("|%-8s|%-25s|%-6s|%-8s|%-7s|%-25s|%-15s|", "ID", "Name", "Price", "Quantity",
                "SubTotal", "Publisher's name", "Status");
        System.out.println(header);

        for (int i = 0; i < bookList.size(); i++) {
            for (int j = 0; j < pList.size(); j++) {
                if (pList.get(j).getId().equalsIgnoreCase(bookList.get(i).getPublisherID())) {
                    bookList.get(i).showBook2();
                    pList.get(j).showPublisher2();
                    bookList.get(i).showBook3();
                }
            }
        }
    }

}
