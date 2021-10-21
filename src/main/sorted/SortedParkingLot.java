package main.sorted;

import main.ParkingLot;
import main.exception.ParkingLotException;
import main.model.Car;
import main.model.Carport;
import main.model.ParkingTicket;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SortedParkingLot implements ParkingLot {

    private final String name;
    private final Map<Integer, Carport> parkingLot = new HashMap<>();

    public SortedParkingLot(int capacity, String name) {
        this.name = name;
        for (int i = 0; i < capacity; i++) {
            parkingLot.put(i + 1, null);
        }
    }

    @Override
    public ParkingTicket park(Car car) {
        Integer position = getFirstAvailablePosition();
        if (Objects.isNull(car)) {
            throw new ParkingLotException("You are parking nothing.");
        }
        if (isHasParked(car)) {
            throw new ParkingLotException("The car has parked.");
        }

        ParkingTicket parkingTicket = new ParkingTicket(position, this.name);
        parkingLot.put(position, new Carport(parkingTicket, car));
        return parkingTicket;
    }

    @Override
    public Car fetch(ParkingTicket ticket) {
        if (Objects.isNull(ticket)) {
            throw new ParkingLotException("No ticket is provided.");
        }
        if (isValidTicket(ticket)) {
            throw new ParkingLotException("Invalid ticket.");
        }
        Car car = parkingLot.get(ticket.getPosition()).getCar();
        parkingLot.put(ticket.getPosition(), null);
        return car;
    }

    @Override
    public long getAvailableParkingCapacity() {
        return parkingLot.entrySet().stream().filter(l -> Objects.isNull(l.getValue())).count();
    }

    protected boolean isHasParked(Car car) {
        return parkingLot.values().stream().filter(Objects::nonNull).anyMatch(p -> p.getCar().equals(car));
    }

    private Integer getFirstAvailablePosition() {
        return parkingLot.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .filter(l -> Objects.isNull(l.getValue()))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("The parking lot is full."))
                .getKey();
    }

    private boolean isValidTicket(ParkingTicket ticket) {
        return parkingLot.values().stream().filter(Objects::nonNull).noneMatch(p -> p.getTicket().equals(ticket));
    }

    public String getName() {
        return name;
    }
}
