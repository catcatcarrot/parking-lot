package main.sorted;

import main.exception.ParkingLotException;
import main.model.Car;
import main.model.ParkingTicket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ParkingBoy {

    private final List<SortedParkingLot> basicParkingLots = new ArrayList<>();

    public ParkingBoy(SortedParkingLot... parkingLot) {
        basicParkingLots.addAll(Arrays.asList(parkingLot));
    }

    public ParkingTicket park(Car car) {
        return basicParkingLots.stream()
                .filter(l -> l.getAvailableParkingCapacity() > 0)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("All parking lots are full."))
                .park(car);
    }

    public Car fetch(ParkingTicket ticket) {
        if (Objects.isNull(ticket)) {
            throw new ParkingLotException("No ticket is provided.");
        }

        return basicParkingLots.stream()
                .filter(l -> l.getName().equals(ticket.getParkingLotName()))
                .map(l -> l.fetch(ticket))
                .findAny()
                .orElseThrow(() -> new ParkingLotException("Invalid ticket."));
    }
}
