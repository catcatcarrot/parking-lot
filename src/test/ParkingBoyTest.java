package test;

import main.ParkingBoy;
import main.ParkingLot;
import main.exception.ParkingLotException;
import main.model.Car;
import main.model.ParkingTicket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyTest {

    @Test
    void should_return_a_ticket_when_parking_boy_manage_a_parking_lot() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot(10));

        ParkingTicket ticket = parkingBoy.park(new Car());

        assertNotNull(ticket);
    }

    @Test
    void should_park_fail_when_boy_manage_a_parking_lot_and_car_is_parking_twice() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot(10));
        Car car = new Car();
        parkingBoy.park(car);

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingBoy.park(car));

        assertEquals("The car has parked.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_boy_manage_a_parking_lot_and_parking_nothing() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot(10));

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingBoy.park(null));

        assertEquals("You are parking nothing.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_boy_manage_a_parking_lot_and_not_exists_enough_capacity() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot(10));

        for (int i = 0; i < 10; i++) {
            parkingBoy.park(new Car());
        }

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingBoy.park(new Car()));

        assertEquals("The parking lot is full.", parkingLotException.getMessage());
    }

    @Test
    void should_return_car_when_boy_manage_a_parking_lot_and_use_ticket() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot(10));
        Car car = new Car();
        ParkingTicket ticket = parkingBoy.park(car);

        Car fetched = parkingBoy.fetch(ticket);

        assertEquals(car, fetched);
    }

    @Test
    void should_fetch_fail_if_boy_manage_a_parking_lot_and_ticket_has_been_used() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot(10));
        ParkingTicket ticket = parkingBoy.park(new Car());
        parkingBoy.fetch(ticket);

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingBoy.fetch(ticket)
        );

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_boy_manage_a_parking_lot_and_use_nonexistent_ticket() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot(10));
        parkingBoy.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingBoy.fetch(new ParkingTicket())
        );

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_boy_manage_a_parking_lot_and_use_nothing() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot(10));
        parkingBoy.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingBoy.fetch(null)
        );

        assertEquals("No ticket is provided.", parkingLotException.getMessage());
    }
}