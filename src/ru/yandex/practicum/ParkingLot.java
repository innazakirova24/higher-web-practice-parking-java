package ru.yandex.practicum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ParkingLot extends AbstractParkingLot {

    private int freeN;
    private int freeE;
    private int freeP;
    private final HashMap<String, SpotType> parked = new HashMap<>();

    public ParkingLot(int totalSpots, int electricSpots, int premiumSpots) {
        super(totalSpots, electricSpots, premiumSpots);
        int allowedElectricSpots = Math.min(electricSpots, totalSpots);
        int allowedPremiumSpots = Math.min(premiumSpots, totalSpots - allowedElectricSpots);
        int allowedNormalSpots = totalSpots - allowedElectricSpots - allowedPremiumSpots;
        this.freeE = allowedElectricSpots;
        this.freeP = allowedPremiumSpots;
        this.freeN = allowedNormalSpots;

    }

    @Override
    boolean isEmpty() {
        return parked.isEmpty();
    }

    @Override
    public void enter(String carType, String number) throws ParkingException {
          if (parked.containsKey(number)) {
              throw new ParkingException("already parked");
          }

          if (carType.equals("NORMAL") && freeN > 0) {
              freeN--;
              parked.put(number, SpotType.NORMAL);
          } else if (carType.equals("ELECTRIC") && freeE > 0) {
              freeE--;
              parked.put(number, SpotType.ELECTRIC);
          } else if (carType.equals("PREMIUM") && freeP > 0) {
              freeP--;
              parked.put(number, SpotType.PREMIUM);
          } else if (carType.equals("PREMIUM") && freeN > 0) {
              freeN--;
              parked.put(number, SpotType.NORMAL);
          } else if (carType.equals("NORMAL") || carType.equals("ELECTRIC") || carType.equals("PREMIUM")) {
            throw new ParkingException("no free parking spot");
          } else {
            throw new RuntimeException("unknown car type " + carType);
          }

    }

    @Override
    public void leave(String number) throws ParkingException {
        SpotType spot = parked.get(number);
        if (spot == null) throw new ParkingException("not parked");

       if (spot == SpotType.NORMAL) {
           freeN++;
       } else if (spot == SpotType.ELECTRIC) {
           freeE++;
       } else if (spot == SpotType.PREMIUM) {
           freeP++;
       } else {
           throw new RuntimeException("unknown spot type " + spot);
       }
        parked.remove(number);
    }

    @Override
    public Set<String> getNumbers() {
        return new HashSet<>(parked.keySet());
    }
}
