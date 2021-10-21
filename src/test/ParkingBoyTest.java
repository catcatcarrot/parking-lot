package test;

import main.exception.ParkingLotException;
import main.model.Car;
import main.model.ParkingTicket;
import main.sorted.ParkingBoy;
import main.sorted.SortedParkingLot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingBoyTest {

    @Test
    void should_return_a_ticket_when_park_car_and_parking_boy_manage_a_parking_lot() {
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));

        ParkingTicket ticket = parkingBoy.park(new Car());

        assertNotNull(ticket);
        assertEquals(1, ticket.getPosition());
    }

    @Test
    void should_park_fail_when_boy_manage_a_parking_lot_and_car_is_parking_twice() {
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));
        Car car = new Car();
        parkingBoy.park(car);

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class, () -> parkingBoy.park(car)
        );

        assertEquals("The car has parked.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_boy_manage_a_parking_lot_and_parking_nothing() {
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class, () -> parkingBoy.park(null)
        );

        assertEquals("You are parking nothing.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_boy_manage_a_parking_lot_and_not_exists_enough_capacity() {
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));

        for (int i = 0; i < 10; i++) {
            parkingBoy.park(new Car());
        }

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class, () -> parkingBoy.park(new Car())
        );

        assertEquals("All parking lots are full.", parkingLotException.getMessage());
    }

    @Test
    void should_return_car_when_boy_manage_a_parking_lot_and_use_ticket() {
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));
        parkingBoy.park(new Car());
        ParkingTicket parkingTicket = parkingBoy.park(new Car());
        parkingBoy.park(new Car());
        parkingBoy.fetch(parkingTicket);

        Car car = new Car();
        ParkingTicket ticket = parkingBoy.park(car);

        Car fetched = parkingBoy.fetch(ticket);

        assertEquals(car, fetched);
        assertEquals(2, ticket.getPosition());
    }

    @Test
    void should_fetch_fail_if_boy_manage_a_parking_lot_and_ticket_has_been_used() {
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));
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
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));
        parkingBoy.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingBoy.fetch(new ParkingTicket(1, "parking-lot"))
        );

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_boy_manage_a_parking_lot_and_use_nothing() {
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));
        parkingBoy.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingBoy.fetch(null)
        );

        assertEquals("No ticket is provided.", parkingLotException.getMessage());
    }

    @Test
    void should_update_capacity_on_parking_success_and_boy_manage_a_parking_lot() {
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));
        long initialCapacity = parkingBoy.getAvailableParkingCapacity();

        parkingBoy.park(new Car());

        assertEquals(initialCapacity, parkingBoy.getAvailableParkingCapacity() + 1);
    }

    @Test
    void should_update_capacity_on_fetching_success_and_boy_manage_a_parking_lot() {
        ParkingBoy parkingBoy = new ParkingBoy(new SortedParkingLot(10, "parking-lot"));
        ParkingTicket ticket = parkingBoy.park(new Car());
        long initialCapacity = parkingBoy.getAvailableParkingCapacity();

        parkingBoy.fetch(ticket);

        assertEquals(initialCapacity, parkingBoy.getAvailableParkingCapacity() - 1);
    }

    @Test
    void should_return_ticket_when_parking_boy_manage_multiple_parking_lots() {
        ParkingBoy parkingBoy = getThreeParkingLots();

        ParkingTicket ticket = parkingBoy.park(new Car());

        assertEquals(1, ticket.getPosition());
        assertEquals("parking-lot-1", ticket.getParkingLotName());
    }

    @Test
    void should_return_ticket_when_boy_manage_multiple_parking_lots_and_the_first_parking_lot_is_full() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        for (int i = 0; i < 10; i++) {
            parkingBoy.park(new Car());
        }
        Car car = new Car();
        ParkingTicket ticket = parkingBoy.park(car);

        Car fetched = parkingBoy.fetch(ticket);

        assertEquals(car, fetched);
        assertEquals(1, ticket.getPosition());
        assertEquals("parking-lot-2", ticket.getParkingLotName());
    }

    @Test
    void should_return_ticket_when_boy_manage_multiple_parking_lots_and_the_first_and_second_parking_lots__are_full() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        for (int i = 0; i < 25; i++) {
            parkingBoy.park(new Car());
        }
        Car car = new Car();
        ParkingTicket ticket = parkingBoy.park(car);

        Car fetched = parkingBoy.fetch(ticket);

        assertEquals(car, fetched);
        assertEquals(6, ticket.getPosition());
        assertEquals("parking-lot-3", ticket.getParkingLotName());
    }

    @Test
    void should_park_fail_when_boy_manage_multiple_parking_lots_and_car_is_parking_twice() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        for (int i = 0; i < 10; i++) {
            parkingBoy.park(new Car());
        }
        Car car = new Car();
        parkingBoy.park(car);

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class, () -> parkingBoy.park(car)
        );

        assertEquals("The car has parked.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_boy_manage_multiple_parking_lots_and_parking_nothing() {
        ParkingBoy parkingBoy = getThreeParkingLots();

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class, () -> parkingBoy.park(null)
        );

        assertEquals("You are parking nothing.", parkingLotException.getMessage());
    }

    @Test
    void should_park_fail_when_boy_manage_multiple_parking_lots_and_not_exists_enough_capacity() {
        ParkingBoy parkingBoy = getThreeParkingLots();

        for (int i = 0; i < 30; i++) {
            parkingBoy.park(new Car());
        }

        ParkingLotException parkingLotException = assertThrows(ParkingLotException.class, () -> parkingBoy.park(new Car()));

        assertEquals("All parking lots are full.", parkingLotException.getMessage());
    }

    @Test
    void should_return_car_when_boy_manage_multiple_parking_lots_and_use_ticket_to_fetch_car_by_first_parking_lot() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        Car car = new Car();
        ParkingTicket ticket = parkingBoy.park(car);

        Car fetched = parkingBoy.fetch(ticket);

        assertEquals(car, fetched);
    }

    @Test
    void should_return_car_when_boy_manage_multiple_parking_lots_and_use_ticket_to_fetch_car_by_second_parking_lot() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        for (int i = 0; i < 10; i++) {
            parkingBoy.park(new Car());
        }
        Car car = new Car();
        ParkingTicket ticket = parkingBoy.park(car);

        Car fetched = parkingBoy.fetch(ticket);

        assertEquals(car, fetched);
    }

    @Test
    void should_return_car_when_boy_manage_multiple_parking_lots_and_use_ticket_to_fetch_car_by_third_parking_lot() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        for (int i = 0; i < 25; i++) {
            parkingBoy.park(new Car());
        }
        Car car = new Car();
        ParkingTicket ticket = parkingBoy.park(car);

        Car fetched = parkingBoy.fetch(ticket);

        assertEquals(car, fetched);
    }

    @Test
    void should_fetch_fail_if_boy_manage_multiple_parking_lots_and_ticket_has_been_used() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        ParkingTicket ticket = parkingBoy.park(new Car());
        parkingBoy.fetch(ticket);

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingBoy.fetch(ticket)
        );

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_boy_manage_multiple_parking_lots_and_use_nonexistent_ticket() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        parkingBoy.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingBoy.fetch(new ParkingTicket(1, "parking-lot-1"))
        );

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_boy_manage_multiple_parking_lots_and_use_nonexistent_name_ticket() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        parkingBoy.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingBoy.fetch(new ParkingTicket(1, "parking-lot-6"))
        );

        assertEquals("Invalid ticket.", parkingLotException.getMessage());
    }

    @Test
    void should_fetch_fail_if_boy_manage_multiple_parking_lots_and_use_nothing() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        parkingBoy.park(new Car());

        ParkingLotException parkingLotException = assertThrows(
                ParkingLotException.class,
                () -> parkingBoy.fetch(null)
        );

        assertEquals("No ticket is provided.", parkingLotException.getMessage());
    }

    @Test
    void should_update_capacity_on_parking_success_and_boy_manage_multiple_parking_lots() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        long initialCapacity = parkingBoy.getAvailableParkingCapacity();

        parkingBoy.park(new Car());

        assertEquals(initialCapacity, parkingBoy.getAvailableParkingCapacity() + 1);
    }

    @Test
    void should_update_capacity_on_fetching_success_and_boy_manage_multiple_parking_lots() {
        ParkingBoy parkingBoy = getThreeParkingLots();
        ParkingTicket ticket = parkingBoy.park(new Car());
        long initialCapacity = parkingBoy.getAvailableParkingCapacity();

        parkingBoy.fetch(ticket);

        assertEquals(initialCapacity, parkingBoy.getAvailableParkingCapacity() - 1);
    }

    private ParkingBoy getThreeParkingLots() {
        return new ParkingBoy(
                new SortedParkingLot(10, "parking-lot-1"),
                new SortedParkingLot(10, "parking-lot-2"),
                new SortedParkingLot(10, "parking-lot-3")
        );
    }
}