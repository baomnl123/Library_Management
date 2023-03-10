import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BookManagement {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<Book> bList = new ArrayList<>();
    private String fName = "Book.txt";
    private List<Publisher> pList = getPublisherFromFile();

    // Load Publisher from file;
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

    // Load Book from file;
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
        String id, name, status, checkStatus;
        String publisherID;
        double price;
        int quantity;
        Book check;

        do {
            id = Performance.getID("book", "(BXXXXX)", "^B\\d{5}$");
            check = searchBookByID(id);
            if (check != null) {
                System.out.println("The book has already existed!");
            }
        } while (check != null);
        do {
            System.out.print("Enter book's name: ");
            name = sc.nextLine().toUpperCase();
        } while (!name.matches("^[a-zA-Z ]{5,30}$"));
        do {
            System.out.print("Enter book's status [Y = Available, N = Not Available]: ");
            checkStatus = sc.nextLine().toUpperCase();
            if (checkStatus.matches("Y")) {
                status = "Available";
            } else {
                status = "Not Available";
            }
        } while (!checkStatus.matches("^[YN]$"));
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
                bList.add(new Book(id, name, price, quantity, status, publisherID));
                System.out.println("A new book is added.");
                saveToFile();
                return;
            }
        }
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
        List<Book> x = null;

        System.out.print("Enter Book's name: ");
        String enterName = sc.nextLine();
        System.out.print("Enter Book's ID: ");
        String id = sc.nextLine();
        x = Search(enterName, id);
        showBook(x);
    }

    private List<Book> Search(String enterName, String id) {
        List<Book> x;

        if (!id.isEmpty()) {
            x = searchBooksByPublishersID(id, enterName);

        } else {
            x = searchBookByName(enterName);
        }
        return x;
    }

    private void showBook(List<Book> x) {
        if (x == null) {
            System.out.println("Book not found!");
        } else {
            printHeader();
            for (Book tmp : x) {
                tmp.showBook1();
            }
        }
    }

    private void printHeader() {
        String header = String.format("|%-8s|%-30s|%-10s|%-10s|%-20s|%-20s|", "ID", "Name", "Price", "Quantity",
                "Status", "Publisher ID");
        System.out.println(header);
    }

    private List<Book> searchBooksByPublishersID(String publisherID, String name) {
        List<Publisher> checkList = getPublisherFromFile();
        List<Book> tmp = new ArrayList<>();

        if (bList.isEmpty()) {
            return null;
        }

        for (int i = 0; i < bList.size(); i++) {
            if (bList.get(i).getPublisherID().equalsIgnoreCase(publisherID) && (bList.get(i).getName().contains(name)))
                tmp.add(bList.get(i));
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

        for (int i = 0; i < bList.size(); i++) {
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
        Book x, findBook;

        id = Performance.getID("book", "(BXXXXX)", "^B\\d{5}$");
        findBook = searchBookByID(id);
        x = searchBookByID(id);

        if (findBook == null) {
            System.out.println("Book's name doesn't exist!");
        } else {
            System.out.println("Here is the book before updated the name");
            printHeader();
            x.showBook1();
            String choice;

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
                x.setName(newName);
                saveToFile();
            } else {
                System.out.println("The book's name is not updated!");
            }
        }
    }

    // FUNCTION 4
    public void deleteBook() {
        String id, choice;
        Book x, findBook;

        id = Performance.getID("book", "(BXXXXX)", "^B\\d{5}$");
        findBook = searchBookByID(id);
        x = searchBookByID(id);

        if (findBook != null) {
            System.out.println("Here is the book before deleted");
            printHeader();
            x.showBook1();

            do {
                System.out.print("Do you want to delete the book [Y = Yes, N = No]: ");
                choice = sc.nextLine().toUpperCase();
            } while (!choice.matches("^[YN]$"));
            if (choice.matches("Y")) {
                bList.remove(x);
                System.out.println("The book is deleted successfully!");
                saveToFile();
            } else {
                System.out.println("The book is not deleted!");
            }
        } else {
            System.out.println("The book's name doesn't exist!");
        }

        saveToFile();
    }

    public void saveToFile() {
        List<String> tmp = new ArrayList<>();

        for (Book x : bList) {
            tmp.add(x.getId() + ";" + x.getName() + ";" + x.getPrice() + ";" + x.getQuantity() + ";" + x.getStatus()
                    + ";" + x.getPublisherID());
        }

        Performance.writeListToFile(fName, tmp);
    }

    // FUNCTION 5
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
