
import java.io.IOException;
import java.util.Scanner;

public class Menu {

    // MENU
    private void menu() {
        System.out.println("------------MENU-------------");
        System.out.println("| 1. Publishers' management |");
        System.out.println("| 2. Books management       |");
        System.out.println("| 0. Exit                   |");
        System.out.println("-----------------------------");
    }

    private void menu1() {
        System.out.println("------------------PUBLISHER-------------------");
        System.out.println("| 1. Create a publisher                      |");
        System.out.println("| 2. Delete the publisher                    |");
        System.out.println("| 3. Print publisher list                    |");
        System.out.println("| 4. Save the publishers list to file        |");
        System.out.println("| 5. Print the publishers list from the file |");
        System.out.println("| 0. Back                                    |");
        System.out.println("----------------------------------------------");
    }

    private void menu2() {
        System.out.println("-------------------BOOK------------------");
        System.out.println("| 1. Create a Book                      |");
        System.out.println("| 2. Search the Book                    |");
        System.out.println("| 3. Update a Book                      |");
        System.out.println("| 4. Delete the Book                    |");
        System.out.println("| 5. Save the Books list to file        |");
        System.out.println("| 6. Print the Books list from the file |");
        System.out.println("| 0. Back                               |");
        System.out.println("-----------------------------------------");
    }

    protected void printMenu() throws IOException {
        while (true) {
            menu();
            int choice = getChoice(0, 2);
            // Clear the terminal screen
            System.out.print("\033[H\033[2J");
            performAction(choice);
        }
    }

    private int getChoice(int min, int max) {
        int choice = -1;

        while (choice < min || choice > max) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please try again!");
            }
        }
        return choice;
    }

    // Press key to Continue
    private void pressKeyContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPress any key to continue...");
        scanner.nextLine();
    }

    public void performAction(int choice) throws IOException {
        PublisherManagement pM = new PublisherManagement();
        BookManagement bM = new BookManagement();
        int choice1;

        switch (choice) {
            case 1:
                menu1();
                try {
                    choice1 = getChoice(0, 5);
                    switch (choice1) {
                        case 1:
                            pM.addPublisher();
                            pressKeyContinue();
                            break;
                        case 2:
                            pM.deletePublisher();
                            pressKeyContinue();
                            break;
                        case 3:
                            pM.displayPublisherList();
                        case 4:
                            pM.saveToFile();
                            pressKeyContinue();
                            break;
                        case 5:
                            pM.loadPublisherFromFile();
                            pressKeyContinue();
                            break;
                        case 0:
                            printMenu();
                    }
                } catch (NumberFormatException e) {
                }
                break;
            case 2:
                menu2();
                try {
                    choice1 = getChoice(0, 6);
                    switch (choice1) {
                        case 1:
                            bM.addBook();
                            pressKeyContinue();
                            break;
                        case 2:
                            bM.searchBook();
                            pressKeyContinue();
                            break;
                        case 3:
                            bM.updateBook();
                            pressKeyContinue();
                            break;
                        case 4:
                            bM.deleteBook();
                            pressKeyContinue();
                            break;
                        case 5:
                            bM.saveToFile();
                            pressKeyContinue();
                            break;
                        case 6:
                            bM.printBookFromFile();
                            pressKeyContinue();
                            break;
                        case 0:
                            printMenu();
                    }
                } catch (NumberFormatException e) {
                }
                break;
            case 0:
                System.exit(0);
        }
        System.out.print("\033[H\033[2J");
    }

}
