package HotelManagement;

import java.util.Date;
import java.util.Scanner;

public class VipRoom extends Room {

    private boolean hasJacuzzi;   // có bồn tắm massage không
    private boolean freeBreakfast; // có bao gồm bữa sáng miễn phí không

    public VipRoom() {
    }

    public VipRoom(String id, String name, double baseCost, Date checkin, Date checkout,
            boolean hasJacuzzi, boolean freeBreakfast) {
        super(id, name, baseCost, checkin, checkout);
        this.hasJacuzzi = hasJacuzzi;
        this.freeBreakfast = freeBreakfast;
    }

    public boolean isHasJacuzzi() {
        return hasJacuzzi;
    }

    public void setHasJacuzzi(boolean hasJacuzzi) {
        this.hasJacuzzi = hasJacuzzi;
    }

    public boolean isFreeBreakfast() {
        return freeBreakfast;
    }

    public void setFreeBreakfast(boolean freeBreakfast) {
        this.freeBreakfast = freeBreakfast;
    }

    @Override
    public void addRoom() {
        super.addRoom();
        Scanner sc = new Scanner(System.in);
        System.out.print("Does this VIP room have Jacuzzi (true/false)? ");
        setHasJacuzzi(sc.nextBoolean());
        System.out.print("Does this VIP room include free breakfast (true/false)? ");
        setFreeBreakfast(sc.nextBoolean());
    }

    @Override
    public boolean updateRoom() {
        super.updateRoom();
        Scanner sc = new Scanner(System.in);
        System.out.print("Update Jacuzzi (true/false): ");
        setHasJacuzzi(sc.nextBoolean());
        System.out.print("Update Free Breakfast (true/false): ");
        setFreeBreakfast(sc.nextBoolean());
        return true;
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Jacuzzi: " + (hasJacuzzi ? "Yes" : "No"));
        System.out.println("Free Breakfast: " + (freeBreakfast ? "Yes" : "No"));
        System.out.println("Total Cost: " + calculateCost());
    }

    @Override
    public double calculateCost() {
        double cost = getBaseCost() * calculateDaysOfStay();
        if (hasJacuzzi) {
            cost += 200; // thêm phí dịch vụ jacuzzi
        }
        if (freeBreakfast) {
            cost += 100; // thêm phí ăn sáng
        }
        return cost;
    }

}
