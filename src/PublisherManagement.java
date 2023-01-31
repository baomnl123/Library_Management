import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PublisherManagement {
    private ArrayList<Publisher> pList = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private String fName = "Publisher.txt";

    // Load Publisher from file
    public PublisherManagement() {
        pList = (ArrayList<Publisher>) getPublihsersFromFile(fName);
    }

    private List<Publisher> getPublihsersFromFile(String fName) {
        List<Publisher> pubList = new ArrayList<>();
        List<String> tmp = Performance.readLineFromFile(fName);

        for (String x : tmp) {
            StringTokenizer stk = new StringTokenizer(x, ";");
            String id = stk.nextToken();
            String name = stk.nextToken();
            String phoneNumber = stk.nextToken();

            pubList.add(new Publisher(id, name, phoneNumber));
        }
        return pubList;
    }

    // FUNCTION 1
    public void addPublisher() {
        String id, name, phoneNumber;
        Publisher check;

        do {
            id = Performance.getID("publisher", "(PXXXXX)", "^P\\d{5}$");
            check = searchPublisher(id);
            if (check != null) {
                System.out.println("The publisher has already existed!");
            }
        } while (check != null);
        do {
            System.out.print("Enter publisher's name: ");
            name = sc.nextLine();
        } while (!name.matches("^[a-zA-Z ]{5,30}$"));
        do {
            System.out.print("Enter phone number: ");
            phoneNumber = sc.nextLine();
        } while (!phoneNumber.matches("\\d{10,12}$"));

        pList.add(new Publisher(id, name, phoneNumber));
        System.out.println("Add succeeded!");

        // Write to file
        saveToFile();
    }

    private Publisher searchPublisher(String id) {
        if (pList.isEmpty()) {
            return null;
        }
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).getId().equalsIgnoreCase(id)) {
                return pList.get(i);
            }
        }
        return null;
    }

    // FUNCTION 2
    public void deletePublisher() {
        String id;
        Publisher check;

        id = Performance.getID("publisher", "(PXXXXX)", "^P\\d{5}$");
        check = searchPublisher(id);

        if (check == null) {
            System.out.println("Publisher does not exist!");
        } else {
            System.out.println("The publisher before deleted: ");
            printHeader();
            check.showPublisher1();
            String choice;

            do {
                System.out.println("Do you want to delete publisher? [Y/N]");
                choice = sc.nextLine().toUpperCase();
            } while (!choice.matches("^[YN]$"));

            if (choice.matches("Y")) {
                System.out.println("The publisher is deleted!");
                pList.remove(check);
                saveToFile();
            } else {
                System.out.println("The publisher is not deleted!");
            }
        }
        // Write to File
        saveToFile();
    }

    private void printHeader() {
        String header = String.format("|%-8s|%-25s|%-12s|", "ID", "Name", "Phone Number");
        System.out.println(header);
    }

    // FUNCTION 3
    public void displayPublisherList() {
        if (pList.isEmpty())
            System.out.println("The list is empty!");
        else {
            printHeader();
            Collections.sort(pList);
            for (Publisher x : pList) {
                x.showPublisher1();
            }
        }

    }

    // FUNCTION 4
    public void saveToFile() {
        List<String> tmp = new ArrayList<>();

        for (Publisher x : pList) {
            tmp.add(x.getId() + ";" + x.getName() + ";" + x.getPhoneNumber());
        }

        Performance.writeListToFile(fName, tmp);
    }
}
