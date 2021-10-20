package test;

import main.model.Car;
import main.ParkingLot;
import main.exception.ParkingLotException;
import main.model.ParkingTicket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

    @Test
    void should_return_a_ticket_and_correct_position_when_parking_lot_exists_position() {
        ParkingLot parkingLot = new ParkingLot(10);

        ParkingTicket ticket = parkingLot.park(new Car());

        assertNotNull(ticket);
        assertEquals(1, ticket.getPosition());
    }

    @Test
    void should_return_correct_position_when_parking_lot_exists_position() {
        ParkingLot parkingLot = new ParkingLot(10);
        parkingLot.park(new Car());

        ParkingTicket ticket = parkingLot.park(new Car());

        assertEquals(2, ticket.getPosition());
    }

    @Test
    void should_park_fail_when_car_is_parking_twice() {
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new Car();
        parkingLot.park(car);

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingLot.park(car));

        assertEquals("The car has parked.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_parking_nothing() {
        ParkingLot parkingLot = new ParkingLot(10);

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingLot.park(null));

        assertEquals("You are parking nothing.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_parking_lot_not_exists_enough_capacity() {
        ParkingLot parkingLot = new ParkingLot(10);
        for (int i = 0; i < 10; i++) {
            parkingLot.park(new Car());
        }

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingLot.park(new Car()));

        assertEquals("The parking lot is full.", parkingLotException.getMessage());
    }

    @Test
    void should_return_car_when_use_ticket() {
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new Car();
        ParkingTicket ticket = parkingLot.park(car);

        Car fetched = parkingLot.fetch(ticket);

        assertEquals(car, fetched);
    }

    @Test
    void should_fetch_fail_if_ticket_has_been_used() {
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingTicket ticket = parkingLot.park(new Car());
        parkingLot.fetch(ticket);

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingLot.fetch(ticket));

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_use_nonexistent_ticket() {
        ParkingLot parkingLot = new ParkingLot(10);
        parkingLot.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingLot.fetch(new ParkingTicket(1))
        );

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_use_nothing() {
        ParkingLot parkingLot = new ParkingLot(10);
        parkingLot.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingLot.fetch(null)
        );

        assertEquals("No ticket is provided.", parkingLotException.getMessage());
    }

    @Test
    void should_return_correct_position_when_after_multiple_park_and_multiple_fetch() {
        ParkingLot parkingLot = new ParkingLot(10);
        parkingLot.park(new Car());
        parkingLot.park(new Car());
        ParkingTicket ticket = parkingLot.park(new Car());
        parkingLot.park(new Car());
        parkingLot.park(new Car());
        parkingLot.fetch(ticket);

        ParkingTicket ticket2 = parkingLot.park(new Car());

        assertEquals(3, ticket2.getPosition());
    }

    @Test
    void should_update_capacity_on_parking_success() {
        ParkingLot parkingLot = new ParkingLot(10);
        long initialCapacity = parkingLot.getAvailableParkingCapacity();

        parkingLot.park(new Car());

        assertEquals(initialCapacity, parkingLot.getAvailableParkingCapacity() + 1);
    }

    @Test
    void should_update_capacity_on_fetching_success() {
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingTicket ticket = parkingLot.park(new Car());
        long initialCapacity = parkingLot.getAvailableParkingCapacity();

        parkingLot.fetch(ticket);

        assertEquals(initialCapacity, parkingLot.getAvailableParkingCapacity() - 1);
    }
}