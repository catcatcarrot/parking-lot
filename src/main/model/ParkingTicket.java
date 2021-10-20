package main.model;

public class ParkingTicket {

    private final int position;
    private final String parkingLotName;

    public ParkingTicket(int position, String parkingLotName) {
        this.position = position;
        this.parkingLotName = parkingLotName;
    }

    public int getPosition() {
        return position;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }
}
