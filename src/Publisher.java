public class Publisher implements Comparable<Publisher> {

    private String id, name, phoneNumber;

    public Publisher(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    Publisher() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return id + "; " + name + "; " + phoneNumber;
    }

    public void showPublisher1() {
        System.out.printf("|%-8s|%-25s|%-12s|%n", id, name, phoneNumber);
    }

    public void showPublisher2() {
        System.out.printf("|%-25s|", name);
    }

    @Override
    public int compareTo(Publisher that) {
        return this.name.compareToIgnoreCase(that.name);
    }

}
