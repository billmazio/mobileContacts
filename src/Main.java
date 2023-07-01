import dao.MobileContactDAOImpl;

import dto.MobileContactDTO;
import dto.UserDetailsDTO;
import model.MobileContact;
import service.IMobileContactService;
import service.MobileContactServiceImpl;
import service.exceptions.MobileContactNotFoundException;
import service.exceptions.PhoneNumberAlreadyExistsException;
import service.exceptions.UserIdAlreadyExistsException;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final IMobileContactService mobileContactService = new MobileContactServiceImpl(new MobileContactDAOImpl());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Add Mobile Contact");
            System.out.println("2. Update Mobile Contact");
            System.out.println("3. Delete Mobile Contact");
            System.out.println("4. Get Mobile Contact");
            System.out.println("5. Get All Mobile Contacts");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addMobileContact();
                        break;
                    case 2:
                        updateMobileContact();
                        break;
                    case 3:
                        deleteMobileContact();
                        break;
                    case 4:
                        getMobileContact();
                        break;
                    case 5:
                        getAllMobileContacts();
                        break;
                    case 6:
                        exit = true;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Consume the invalid input
            }

            System.out.println();
        }
    }

    private static void addMobileContact() {
        System.out.println("Enter Mobile Contact details:");

        System.out.print("ID: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();

        MobileContactDTO contactDTO = new MobileContactDTO();
        contactDTO.setId(id);
        contactDTO.setPhoneNumber(phoneNumber);

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setId(id);
        userDetailsDTO.setFirstname(firstName);
        userDetailsDTO.setLastname(lastName);

        contactDTO.setUserDetails(userDetailsDTO);

        try {
            mobileContactService.insertMobileContact(contactDTO);
            System.out.println("Mobile Contact added successfully!");
        } catch (PhoneNumberAlreadyExistsException e) {
            System.out.println("Failed to add Mobile Contact: Phone number already exists.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }

    private static void updateMobileContact() {
        System.out.print("Enter the ID of the Mobile Contact to update: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter updated Mobile Contact details:");

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();

        MobileContactDTO contactDTO = new MobileContactDTO();
        contactDTO.setId(id);
        contactDTO.setPhoneNumber(phoneNumber);

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setId(id);
        userDetailsDTO.setFirstname(firstName);
        userDetailsDTO.setLastname(lastName);

        contactDTO.setUserDetails(userDetailsDTO);

        try {
            mobileContactService.updateMobileContact(id, contactDTO);
            System.out.println("Mobile Contact updated successfully!");
        } catch (MobileContactNotFoundException | PhoneNumberAlreadyExistsException | UserIdAlreadyExistsException e) {
            System.out.println("Failed to update Mobile Contact: " + e.getMessage());
        }
    }

      /**
       *  delete the contact with the id
       */
/*      private static void deleteMobileContact() {
        System.out.print("Enter the ID of the Mobile Contact to delete: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        try {
            mobileContactService.deleteMobileContact(id);
            System.out.println("Mobile Contact deleted successfully!");
        } catch (MobileContactNotFoundException e) {
            System.out.println("Failed to delete Mobile Contact: " + e.getMessage());
        }
    }*/

    /**
     * delete the contact with the number
     */
    private static void deleteMobileContact() {
        System.out.print("Enter the number of the Mobile Contact to delete: ");
        String phoneNumber = scanner.nextLine();
        // scanner.nextLine(); // Consume the newline character

        try {
            mobileContactService.deleteMobileContact(phoneNumber);
            System.out.println("Mobile Contact deleted successfully!");
        } catch (MobileContactNotFoundException e) {
            System.out.println("Failed to delete Mobile Contact: " + e.getMessage());
        }
    }


    private static void getMobileContact() {
        System.out.print("Enter the ID of the Mobile Contact to retrieve: ");
        long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        try {
            MobileContact mobileContact = mobileContactService.getMobileContact(id);
            if (mobileContact != null) {
                System.out.println("Mobile Contact:");
                System.out.println("ID: " + mobileContact.getId());
                System.out.println("First Name: " + mobileContact.getUserDetails().getFirstname());
                System.out.println("Last Name: " + mobileContact.getUserDetails().getLastname());
                System.out.println("Phone Number: " + mobileContact.getPhoneNumber());
            } else {
                System.out.println("Mobile Contact not found.");
            }
        } catch (MobileContactNotFoundException e) {
            System.out.println("Failed to retrieve Mobile Contact: " + e.getMessage());
        }
    }

    private static void getAllMobileContacts() {
        System.out.println("Mobile Contacts:");
        List<MobileContact> mobileContacts = mobileContactService.getAllMobileContacts();
        if (mobileContacts.isEmpty()) {
            System.out.println("No Mobile Contacts found.");
        } else {
            for (MobileContact mobileContact : mobileContacts) {
                System.out.println("ID: " + mobileContact.getId());
                System.out.println("First Name: " + mobileContact.getUserDetails().getFirstname());
                System.out.println("Last Name: " + mobileContact.getUserDetails().getLastname());
                System.out.println("Phone Number: " + mobileContact.getPhoneNumber());
                System.out.println();
            }
        }
    }
}