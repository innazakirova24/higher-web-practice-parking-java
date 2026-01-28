package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkMateTest {


    @Test
    void normalCanEnterAndNumberAppearsInSet() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1, 0, 0);
        parkingLot.enter("NORMAL", "A001AA");
        assertTrue(parkingLot.getNumbers().contains("A001AA"));
    }

    @Test
    void electricCantEnterWhenZeroElectricSpots() {
        ParkingLot parkingLot = new ParkingLot(5, 0, 0);
        assertThrows(ParkingException.class, () -> parkingLot.enter("ELECTRIC", "E001EE"));
    }

    @Test
    void premiumTakesPremiumSpotIfExists() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2, 0, 1);
        parkingLot.enter("PREMIUM", "P001PP");
        parkingLot.enter("NORMAL", "N001NN");

        assertTrue(parkingLot.getNumbers().contains("P001PP"));
        assertTrue(parkingLot.getNumbers().contains("N001NN"));
    }

    @Test
    void premiumTakesNormalSpotIfZeroPremiumSpots() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2, 0, 1);
        parkingLot.enter("PREMIUM", "P001PP");
        parkingLot.enter("PREMIUM", "P002PP");

        assertThrows(ParkingException.class, () -> parkingLot.enter("NORMAL", "N001NN"));
    }

    @Test
    void enteringAgainWithSameNumber() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1, 0, 0);
        parkingLot.enter("NORMAL", "A001AA");

        assertThrows(ParkingException.class, () -> parkingLot.enter("NORMAL", "A001AA"));
    }

    @Test
    void leavingNotParkedCarThrows() {
        ParkingLot parkingLot = new ParkingLot(1, 0, 0);
        assertThrows(ParkingException.class, () -> parkingLot.leave("A001AA"));
    }


    @Test
    void leaveFreesSpotSoAnotherCarCanEnter() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1, 0, 0);
        parkingLot.enter("NORMAL", "A001AA");
        parkingLot.leave("A001AA");
        parkingLot.enter("NORMAL", "B002BB");

        assertTrue(parkingLot.getNumbers().contains("B002BB"));
    }

}