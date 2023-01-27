import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PublisherManagement {
    private ArrayList<Publisher> pList = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private String fName = "Publisher.txt";

    // Load data from file
    public PublisherManagement() {
        pList = (ArrayList<Publisher>) getPublihsersFromFile(fName);
    }

    // FUNCTION 1
    public void addPublisher() {
        String id, name, phoneNumber;
        boolean check;
        List<String> tmp = new ArrayList<>();

        do {
            id = Performance.getID("publisher", "(PXXXXX)", "^P\\d{5}$");
            check = searchPublisherID(id);
            if (check == true) {
                System.out.println("The publisher has already existed!");
            }
        } while (check == true);
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

    private boolean searchPublisherID(String id) {
        if (pList.isEmpty()) {
            return false;
        }
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
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
        Publisher x;

        id = Performance.getID("publisher", "(PXXXXX)", "^P\\d{5}$");
        x = searchPublisher(id);

        if (x == null) {
            System.out.println("Publisher does not exist!");
        } else {
            System.out.println("The publisher before deleted: ");
            x.showPublisher1();
            String choice;
            do {
                System.out.println("Do you want to delete publisher? [Y/N]");
                choice = sc.nextLine().toUpperCase();
            } while (!choice.matches("^[YN]$"));
            if (choice.matches("Y")) {
                System.out.println("The publisher is deleted!");
                pList.remove(x);
            } else {
                System.out.println("The publisher is not deleted!");
            }

        }
    }

    // FUNCTION 3
    public void displayPublisherList() {
        if (pList.isEmpty())
            System.out.println("The list is empty!");
        for (Publisher x : pList) {
            x.showPublisher1();
        }
    }

    // FUNCTION 4
    public void saveToFile() {
        List<String> tmp = new ArrayList<>();

        if (pList.isEmpty()) {
            System.out.println("The publisher's list is empty");
            return;
        }

        for (Publisher x : pList) {
            tmp.add(x.getId() + "; " + x.getName() + "; " + x.getPhoneNumber());
        }
        Performance.writeListToFile(fName, tmp);
        System.out.println("Save Succeeded!");
    }

    private List<Publisher> getPublihsersFromFile(String fName) {
        List<Publisher> pubList = new ArrayList<>();
        List<String> tmp = Performance.readLineFromFile(fName);

        for (String x : tmp) {
            StringTokenizer stk = new StringTokenizer(x, "; ");
            String id = stk.nextToken();
            String name = stk.nextToken();
            String phoneNumber = stk.nextToken();

            pubList.add(new Publisher(id, name, phoneNumber));
        }
        return pubList;
    }

    // FUNCTION 5
    public void loadPublisherFromFile() {
        List<Publisher> pubList = getPublihsersFromFile(fName);
        Collections.sort(pubList);
        System.out.println("Here is the publisher list: ");
        String header = String.format("|%-8s|%-25s|%-12s|", "ID", "Name", "PhoneNumber");
        System.out.println(header);
        for (int i = 0; i < pubList.size(); i++) {
            pubList.get(i).showPublisher1();
        }
    }
}
