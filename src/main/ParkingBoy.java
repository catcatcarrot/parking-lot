package main;

import main.model.Car;
import main.model.ParkingTicket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParkingBoy {

    private final List<ParkingLot> parkingLots = new ArrayList<>();

    public ParkingBoy(ParkingLot... parkingLot) {
        parkingLots.addAll(Arrays.asList(parkingLot));
    }

    public ParkingTicket park(Car car) {
        return parkingLots.get(0).park(car);
    }
}
