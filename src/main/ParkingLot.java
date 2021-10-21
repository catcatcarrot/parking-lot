package main;

import main.model.Car;
import main.model.ParkingTicket;

public interface ParkingLot {

    ParkingTicket park(Car car);

    Car fetch(ParkingTicket ticket);

    long getAvailableParkingCapacity();
}
