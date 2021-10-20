package main;

import main.exception.ParkingLotException;
import main.model.Car;
import main.model.Carport;
import main.model.ParkingTicket;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParkingLot {

    private final Map<Integer, Carport> parkingLot = new HashMap<>();

    public ParkingLot(int capacity) {
        for (int i = 0; i < capacity; i++) {
            parkingLot.put(i + 1, null);
        }
    }

    public ParkingTicket park(Car car) {
        Integer position = parkingLot.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .filter(l -> Objects.isNull(l.getValue()))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("The parking lot is full."))
                .getKey();
        if (Objects.isNull(car)) {
            throw new ParkingLotException("You are parking nothing.");
        }
        if (parkingLot.values().stream().filter(Objects::nonNull).anyMatch(p -> p.getCar().equals(car))) {
            throw new ParkingLotException("The car has parked.");
        }

        ParkingTicket parkingTicket = new ParkingTicket(position);
        parkingLot.put(position, new Carport(parkingTicket, car));
        return parkingTicket;
    }

    public Car fetch(ParkingTicket ticket) {
        if (Objects.isNull(ticket)) {
            throw new ParkingLotException("No ticket is provided.");
        }
        if (parkingLot.values().stream().filter(Objects::nonNull).noneMatch(p -> p.getTicket().equals(ticket))) {
            throw new ParkingLotException("Invalid ticket.");
        }
        Car car = parkingLot.get(ticket.getPosition()).getCar();
        parkingLot.put(ticket.getPosition(), null);
        return car;
    }

    public long getAvailableParkingCapacity() {
        return parkingLot.entrySet().stream().filter(l -> Objects.isNull(l.getValue())).count();
    }
}
