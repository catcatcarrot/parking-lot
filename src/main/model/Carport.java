package main.model;

public class Carport {

    private ParkingTicket ticket;
    private Car car;

    public Carport(ParkingTicket ticket, Car car) {
        this.ticket = ticket;
        this.car = car;
    }

    public ParkingTicket getTicket() {
        return ticket;
    }

    public Car getCar() {
        return car;
    }
}
