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
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        ParkingTicket ticket = parkingBoy.park(new Car());

        assertNotNull(ticket);
    }

    @Test
    void should_park_fail_when_boy_manage_a_parking_lot_and_car_is_parking_twice() {
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();
        parkingBoy.park(car);

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingBoy.park(car));

        assertEquals("The car has parked.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_boy_manage_a_parking_lot_and_parking_nothing() {
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingBoy.park(null));

        assertEquals("You are parking nothing.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_boy_manage_a_parking_lot_and_not_exists_enough_capacity() {
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        for (int i = 0; i < 10; i++) {
            parkingBoy.park(new Car());
        }

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingBoy.park(new Car()));

        assertEquals("The parking lot is full.", parkingLotException.getMessage());
    }
}