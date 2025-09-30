package HotelManagement;

import java.util.Scanner;

public class Processor {

    public static void main(String[] args) {
        RoomList roomList = new RoomList();
        Scanner sc = new Scanner(System.in);

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
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    VipRoom vr = new VipRoom();
                    vr.addRoom();
                    roomList.addRoom(vr);
                    break;
                case 2:
                    NormalRoom nr = new NormalRoom();
                    nr.addRoom();
                    roomList.addRoom(nr);
                    break;
                case 3:
                    System.out.print("Enter Room ID to update: ");
                    String uid = sc.nextLine();
                    if (!roomList.updateRoomById(uid)) {
                        System.out.println("Room not found!");
                    }
                    break;
                case 4:
                    System.out.print("Enter Room ID to delete: ");
                    String did = sc.nextLine();
                    if (!roomList.deleteRoomById(did)) {
                        System.out.println("Room not found!");
                    }
                    break;
                case 5:
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
                    roomList.displayAllRoom();
                    break;
                case 7:
                    Room expensive = roomList.findMostExpensiveRoom();
                    if (expensive != null) {
                        System.out.println("Most Expensive Room:");
                        expensive.displayDetails();
                    }
                    break;
                case 8:
                    roomList.countRoom();
                    break;
                case 9:
                    System.exit(0);
            }
        }
    }
}
