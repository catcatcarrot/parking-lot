package test;

import main.basic.BasicParkingLot;
import main.exception.ParkingLotException;
import main.model.Car;
import main.model.ParkingTicket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicParkingLotTest {

    @Test
    void should_return_a_ticket_when_parking_lot_exists_position() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");

        ParkingTicket ticket = basicParkingLot.park(new Car());

        assertNotNull(ticket);
        assertEquals(1, ticket.getPosition());
    }

    @Test
    void should_return_ticket_when_parking_lot_exists_position() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        basicParkingLot.park(new Car());

        ParkingTicket ticket = basicParkingLot.park(new Car());

        assertEquals(2, ticket.getPosition());
    }

    @Test
    void should_park_fail_when_car_is_parking_twice() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        Car car = new Car();
        basicParkingLot.park(car);

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> basicParkingLot.park(car));

        assertEquals("The car has parked.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_parking_nothing() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> basicParkingLot.park(null));

        assertEquals("You are parking nothing.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_parking_lot_not_exists_enough_capacity() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        for (int i = 0; i < 10; i++) {
            basicParkingLot.park(new Car());
        }

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> basicParkingLot.park(new Car()));

        assertEquals("The parking lot is full.", parkingLotException.getMessage());
    }

    @Test
    void should_return_car_when_use_ticket() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        Car car = new Car();
        ParkingTicket ticket = basicParkingLot.park(car);

        Car fetched = basicParkingLot.fetch(ticket);

        assertEquals(car, fetched);
    }

    @Test
    void should_fetch_fail_if_ticket_has_been_used() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        ParkingTicket ticket = basicParkingLot.park(new Car());
        basicParkingLot.fetch(ticket);

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> basicParkingLot.fetch(ticket));

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_use_nonexistent_ticket() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        basicParkingLot.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> basicParkingLot.fetch(new ParkingTicket(1, "parking-lot"))
        );

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_use_nothing() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        basicParkingLot.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> basicParkingLot.fetch(null)
        );

        assertEquals("No ticket is provided.", parkingLotException.getMessage());
    }

    @Test
    void should_return_ticket_when_after_multiple_park_and_multiple_fetch() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        basicParkingLot.park(new Car());
        basicParkingLot.park(new Car());
        ParkingTicket ticket = basicParkingLot.park(new Car());
        basicParkingLot.park(new Car());
        basicParkingLot.park(new Car());
        basicParkingLot.fetch(ticket);

        ParkingTicket ticket2 = basicParkingLot.park(new Car());

        assertEquals(3, ticket2.getPosition());
    }

    @Test
    void should_update_capacity_on_parking_success() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        long initialCapacity = basicParkingLot.getAvailableParkingCapacity();

        basicParkingLot.park(new Car());

        assertEquals(initialCapacity, basicParkingLot.getAvailableParkingCapacity() + 1);
    }

    @Test
    void should_update_capacity_on_fetching_success() {
        BasicParkingLot basicParkingLot = new BasicParkingLot(10, "parking-lot");
        ParkingTicket ticket = basicParkingLot.park(new Car());
        long initialCapacity = basicParkingLot.getAvailableParkingCapacity();

        basicParkingLot.fetch(ticket);

        assertEquals(initialCapacity, basicParkingLot.getAvailableParkingCapacity() - 1);
    }
}