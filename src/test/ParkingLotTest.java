package test;

import main.Car;
import main.ParkingLot;
import main.ParkingLotException;
import main.ParkingTicket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

    @Test
    void should_return_a_ticket_when_parking_lot_exists_position() {
        ParkingLot parkingLot = new ParkingLot(10);

        ParkingTicket ticket = parkingLot.park(new Car());

        assertNotNull(ticket);
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
                () -> parkingLot.fetch(new ParkingTicket())
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
}