package HotelManagement;

import java.util.Scanner;

public class Processor {

    public static void main(String[] args) {
    	// Initialize the room list
        RoomList roomList = new RoomList();
        // Scanner for user input
        Scanner sc = new Scanner(System.in);

        // Infinite loop for menu
        while (true) {
            System.out.println("===== HOTEL MENU =====");
            System.out.println("1. Add a new VIP Room");
            System.out.println("2. Add a new Normal Room");
            System.out.println("3. Update a room by ID");
            System.out.println("4. Delete a room by ID");
            System.out.println("5. Find a room by ID");
            System.out.println("6. Display all rooms");
            System.out.println("7. Find the most expensive room");
            System.out.println("8. Count total rooms");
            System.out.println("9. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); // Read user's choice
            sc.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                	// Create and add a new VIP room
                    VipRoom vr = new VipRoom();
                    vr.addRoom(); // Input room information
                    roomList.addRoom(vr); // Add to list
                    break;
                case 2:
                	// Create and add a new normal room
                    NormalRoom nr = new NormalRoom();
                    nr.addRoom();
                    roomList.addRoom(nr);
                    break;
                case 3:
                	// Update a room by ID
                    System.out.print("Enter Room ID to update: ");
                    String uid = sc.nextLine();
                    if (!roomList.updateRoomById(uid)) {
                        System.out.println("Room not found!");
                    }
                    break;
                case 4:
                	// Delete a room by ID
                    System.out.print("Enter Room ID to delete: ");
                    String did = sc.nextLine();
                    if (!roomList.deleteRoomById(did)) {
                        System.out.println("Room not found!");
                    }
                    break;
                case 5:
                	// Find a room by ID
                    System.out.print("Enter Room ID to find: ");
                    String fid = sc.nextLine();
                    Room found = roomList.findRoomById(fid);
                    if (found != null) {
                        found.displayDetails();
                    } else {
                        System.out.println("Room not found!");
                    }
                    break;
                case 6:
                	// Display all rooms
                    roomList.displayAllRoom();
                    break;
                case 7:
                	// Find the most expensive room
                    Room expensive = roomList.findMostExpensiveRoom();
                    if (expensive != null) {
                        System.out.println("Most Expensive Room:");
                        expensive.displayDetails();
                    }
                    break;
                case 8:
                	// Count total number of rooms
                    roomList.countRoom();
                    break;
                case 9:
                	// Exit the program
                    System.exit(0);
            }
        }
    }
}
