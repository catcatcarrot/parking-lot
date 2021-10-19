package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {

    private int capacity;
    private final Map<ParkingTicket, Car> parkingLot = new HashMap<>();

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public ParkingTicket park(Car car) {
        if (capacity <= 0) {
            throw new ParkingLotException("The parking lot is full.");
        }
        if (Objects.isNull(car)) {
            throw new ParkingLotException("You are parking nothing.");
        }
        if (parkingLot.containsValue(car)) {
            throw new ParkingLotException("The car has parked.");
        }

        ParkingTicket ticket = new ParkingTicket();
        parkingLot.put(ticket, car);
        capacity--;
        return ticket;
    }

    public Car fetch(ParkingTicket ticket) {
        if (Objects.isNull(ticket)) {
            throw new ParkingLotException("No ticket is provided.");
        }
        if (!parkingLot.containsKey(ticket)) {
            throw new ParkingLotException("Invalid ticket.");
        }
        capacity++;
        return parkingLot.remove(ticket);
    }

    public int getAvailableParkingCapacity() {
        return this.capacity;
    }
}
