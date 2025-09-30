package HotelManagement;


import java.util.ArrayList;

public class RoomList {
    ArrayList<Room> rl = new ArrayList<>();

    public void addRoom(Room room) {
        rl.add(room);
    }

    public boolean updateRoomById(String id) {
        for (Room room : rl) {
            if (room.getId().equals(id)) {
                return room.updateRoom();
            }
        }
        return false;
    }

    public boolean deleteRoomById(String id) {
        return rl.removeIf(room -> room.getId().equals(id));
    }

    public Room findRoomById(String id) {
        for (Room room : rl) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    public void displayAllRoom() {
        for (Room room : rl) {
            room.displayDetails();
            System.out.println("-----------------");
        }
    }

    public Room findMostExpensiveRoom() {
        if (rl.isEmpty()) return null;
        Room maxRoom = rl.get(0);
        for (Room room : rl) {
            if (room.calculateCost() > maxRoom.calculateCost()) {
                maxRoom = room;
            }
        }
        return maxRoom;
    }

    public void countRoom() {
        int vip = 0, normal = 0;
        for (Room room : rl) {
            if (room instanceof VipRoom) vip++;
            else if (room instanceof NormalRoom) normal++;
        }
        System.out.println("VIP Rooms: " + vip);
        System.out.println("Normal Rooms: " + normal);
    }
}

