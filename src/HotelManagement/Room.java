package HotelManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class Room implements IRoom {

    private String id;
    private String name;
    private double baseCost;
    private Date checkinDate;
    private Date checkoutDate;

    public Room() {
    }

    public Room(String id, String name, double baseCost, Date checkinDate, Date checkoutDate) {
        this.id = id;
        this.name = name;
        this.baseCost = baseCost;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    // Getter Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    // nhập phòng
    @Override
    public void addRoom() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Room ID: ");
        setId(sc.nextLine());
        System.out.print("Enter Room Name: ");
        setName(sc.nextLine());
        System.out.print("Enter Base Cost per Day: ");
        setBaseCost(sc.nextDouble());
        sc.nextLine();

        try {
            System.out.print("Enter Check-in Date (dd/MM/yyyy): ");
            Date checkin = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
            setCheckinDate(checkin);

            System.out.print("Enter Check-out Date (dd/MM/yyyy): ");
            Date checkout = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
            setCheckoutDate(checkout);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // update phòng
    @Override
    public boolean updateRoom() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Update Room Name (" + getName() + "): ");
        setName(sc.nextLine());
        System.out.print("Update Base Cost (" + getBaseCost() + "): ");
        setBaseCost(sc.nextDouble());
        sc.nextLine();
        return true;
    }

    // xuất thông tin
    @Override
    public void displayDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Room ID: " + getId());
        System.out.println("Room Name: " + getName());
        System.out.println("Base Cost per Day: " + getBaseCost());
        System.out.println("Check-in Date: " + (getCheckinDate() != null ? sdf.format(getCheckinDate()) : "N/A"));
        System.out.println("Check-out Date: " + (getCheckoutDate() != null ? sdf.format(getCheckoutDate()) : "N/A"));
    }

    // tính số ngày thuê
    public long calculateDaysOfStay() {
        if (checkinDate == null || checkoutDate == null) {
            return 0;
        }
        long diff = checkoutDate.getTime() - checkinDate.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }

    // abstract cost
    @Override
    public abstract double calculateCost();
}
