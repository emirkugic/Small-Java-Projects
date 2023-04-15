package Small-Java-Projects;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class PhoneBook {
    private Map<String, String[]> phoneNumbers;
    private Map<String, String[]> addresses;

    public PhoneBook() {
        phoneNumbers = new HashMap<>();
        addresses = new HashMap<>();
    }

    public static void main(String[] args) {
        PhoneBook directory = new PhoneBook();
        Scanner scanner = new Scanner(System.in);

        // Print available commands
        System.out.println("available operations: ");
        System.out.println("1 add a number");
        System.out.println("2 search for a number");
        System.out.println("3 search for a person by phone number");
        System.out.println("4 add an address");
        System.out.println("5 search for personal information");
        System.out.println("6 delete personal information");
        System.out.println("7 filtered listing");
        System.out.println("x quit");

        while (true) {
            System.out.print("command: ");
            String command = scanner.nextLine();
            if (command.equals("x")) {
                break;
            }

            switch (command) {
                case "1":
                    // Add a phone number
                    System.out.print("whose number: ");
                    String name = scanner.nextLine();
                    System.out.print("number: ");
                    String number = scanner.nextLine();
                    directory.addPhoneNumber(name, number);
                    break;
                case "2":
                    // Search for a phone number
                    System.out.print("whose number: ");
                    name = scanner.nextLine();
                    String[] numbers = directory.searchPhoneNumbers(name);
                    if (numbers == null) {
                        System.out.println(name + " not found");
                    } else {
                        for (String n : numbers) {
                            System.out.println(n);
                        }
                    }
                    break;
                case "3":
                    // Search for a name by phone number
                    System.out.print("number: ");
                    number = scanner.nextLine();
                    name = directory.searchNameByPhoneNumber(number);
                    if (name == null) {
                        System.out.println(number + " not found");
                    } else {
                        System.out.println(name);
                    }
                    break;
                case "4":
                    // Add an address
                    System.out.print("whose address: ");
                    name = scanner.nextLine();
                    System.out.print("street: ");
                    String street = scanner.nextLine();
                    System.out.print("city: ");
                    String city = scanner.nextLine();
                    directory.addAddress(name, street, city);
                    break;
                case "5":
                    // Search for personal information
                    System.out.print("whose information: ");
                    name = scanner.nextLine();
                    String[] phones = directory.searchPhoneNumbers(name);
                    String[] addrs = directory.searchAddresses(name);
                    if (phones == null && addrs == null) {
                        System.out.println(name + " not found");
                    } else {
                        if (phones != null) {
                            System.out.println("phone numbers: ");
                            for (String p : phones) {
                                System.out.println(p);
                            }
                        }
                        if (addrs != null) {
                            System.out.println("address: ");
                            for (String a : addrs) {
                                System.out.println(a);
                            }
                        }
                    }
                    break;
                case "6":
                    // Delete personal information
                    System.out.print("whose information: ");
                    name = scanner.nextLine();
                    directory.deleteInformation(name);
                    break;
                case "7":
                    // Filtered listing
                    System.out.print("keyword (if empty, all listed): ");
                    String keyword = scanner.nextLine();
                    SortedMap<String, String[]> filteredListing = directory.filteredListing(keyword);
                    for (Map.Entry<String, String[]> entry : filteredListing.entrySet()) {
                        System.out.println(entry.getKey());
                        if (entry.getValue()[0] != null) {
                            System.out.println("phone numbers: ");
                            for (String p : entry.getValue()[0].split(",")) {
                                System.out.println(p);
                            }
                        }
                        if (entry.getValue()[1] != null) {
                            System.out.println("address: ");
                            for (String a : entry.getValue()[1].split(",")) {
                                System.out.println(a);
                            }
                        }
                    }
                    break;
                default:
                    System.out.println("invalid command");
                    break;
            }
        }
    }

    public void addPhoneNumber(String name, String number) {
        String[] numbers = phoneNumbers.get(name);
        if (numbers == null) {
            numbers = new String[] {number};
        } else {
            String[] newNumbers = new String[numbers.length + 1];
            for (int i = 0; i < numbers.length; i++) {
                newNumbers[i] = numbers[i];
            }
            newNumbers[newNumbers.length - 1] = number;
            numbers = newNumbers;
        }
        phoneNumbers.put(name, numbers);
    }

    public String[] searchPhoneNumbers(String name) {
        return phoneNumbers.get(name);
    }

    public String searchNameByPhoneNumber(String number) {
        for (Map.Entry<String, String[]> entry : phoneNumbers.entrySet()) {
            for (String n : entry.getValue()) {
                if (n.equals(number)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public void addAddress(String name, String street, String city) {
        String[] addresses = this.addresses.get(name);
        if (addresses == null) {
            addresses = new String[] {street + " " + city};
        } else {
            String[] newAddresses = new String[addresses.length + 1];
            for (int i = 0; i < addresses.length; i++) {
                newAddresses[i] = addresses[i];
            }
            newAddresses[newAddresses.length - 1] = street + " " + city;
            addresses = newAddresses;
        }
        this.addresses.put(name, addresses);
    }

    public String[] searchAddresses(String name) {
        return addresses.get(name);
    }

    public void deleteInformation(String name) {
        phoneNumbers.remove(name);
        addresses.remove(name);
    }

    public SortedMap<String, String[]> filteredListing(String keyword) {
        SortedMap<String, String[]> sortedListing = new TreeMap<>();
        for (Map.Entry<String, String[]> entry : phoneNumbers.entrySet()) {
            String name = entry.getKey();
            if (keyword.isEmpty() || name.contains(keyword) || addresses.get(name)[0].contains(keyword)) {
                String[] phoneNumberList = entry.getValue();
                String[] addressList = addresses.get(name);
                String[] combined = new String[] {concatenate(phoneNumberList), concatenate(addressList)};
                sortedListing.put(name, combined);
            }
        }
        return sortedListing;
    }

    private String concatenate(String[] arr) {
        if (arr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
