package HotelManagement;

import java.util.Date;
import java.util.Scanner;

public class NormalRoom extends Room {

    private int floorNumber;    // Tầng của phòng
    private boolean hasWindow;  // Có cửa sổ hay không

    public NormalRoom() {
    }

    public NormalRoom(String id, String name, double baseCost, Date checkin, Date checkout,
            int floorNumber, boolean hasWindow) {
        super(id, name, baseCost, checkin, checkout);
        this.floorNumber = floorNumber;
        this.hasWindow = hasWindow;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isHasWindow() {
        return hasWindow;
    }

    public void setHasWindow(boolean hasWindow) {
        this.hasWindow = hasWindow;
    }

    @Override
    public void addRoom() {
        super.addRoom();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Floor Number: ");
        setFloorNumber(sc.nextInt());
        System.out.print("Does this room have a window (true/false)? ");
        setHasWindow(sc.nextBoolean());
    }

    @Override
    public boolean updateRoom() {
        super.updateRoom();
        Scanner sc = new Scanner(System.in);
        System.out.print("Update Floor Number: ");
        setFloorNumber(sc.nextInt());
        System.out.print("Update Has Window (true/false): ");
        setHasWindow(sc.nextBoolean());
        return true;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Floor Number: " + floorNumber);
        System.out.println("Has Window: " + (hasWindow ? "Yes" : "No"));
        System.out.println("Total Cost: " + calculateCost());
    }

    @Override
    public double calculateCost() {
        double cost = getBaseCost() * calculateDaysOfStay();
        if (hasWindow) {
            cost += 50;      // thêm phí nếu có cửa sổ view đẹp
        }
        if (floorNumber > 5) {
            cost -= 20; // giảm giá nhẹ nếu ở tầng cao
        }
        return cost;
    }
}
