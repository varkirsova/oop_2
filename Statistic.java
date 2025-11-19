package oop_2;

import java.util.*;

public class Statistic {
    private Map<String, Map<String, Map<String, Integer>>> addressCount;
    private Map<String, int[]> floorStatistics;

    public Statistic() {
        addressCount = new HashMap<>();
        floorStatistics = new HashMap<>();
    }

    public void addRecord(String city, String street, String house, int floor) {
        updateDuplicates(city, street, house);
        updateFloors(city, floor);
    }

    private void updateDuplicates(String city, String street, String house) {
        if (city.isEmpty() || street.isEmpty() || house.isEmpty()) {
            return;
        }

        Map<String, Map<String, Integer>> streetsMap = addressCount.get(city);
        if (streetsMap == null) {
            streetsMap = new HashMap<>();
            addressCount.put(city, streetsMap);
        }

        Map<String, Integer> housesMap = streetsMap.get(street);
        if (housesMap == null) {
            housesMap = new HashMap<>();
            streetsMap.put(street, housesMap);
        }

        Integer count = housesMap.get(house);
        if (count == null) {
            housesMap.put(house, 1);
        } else {
            housesMap.put(house, count + 1);
        }
    }

    private void updateFloors(String city, int floor) {
        if (city.isEmpty() || floor < 1 || floor > 5) {
            return;
        }

        int[] floors = floorStatistics.get(city);
        if (floors == null) {
            floors = new int[5];
            floorStatistics.put(city, floors);
        }

        floors[floor - 1]++;
    }


    public void printStatistics() {
        printDuplicates();
        printFloorStatistics();
    }

    private void printDuplicates() {
        System.out.println("Дубликаты\n");
        boolean hasDuplicates = false;

        for (String city : addressCount.keySet()) {
            Map<String, Map<String, Integer>> streets = addressCount.get(city);

            for (String street : streets.keySet()) {
                Map<String, Integer> houses = streets.get(street);

                for (String house : houses.keySet()) {
                    int count = houses.get(house);
                    if (count > 1) {
                        System.out.printf("Город: %s, Улица: %s, Дом: %s - %d повторений%n",
                                city, street, house, count);
                        hasDuplicates = true;
                    }
                }
            }
        }

        if (!hasDuplicates) {
            System.out.println("Дубликатов не найдено");
        }
    }

    private void printFloorStatistics() {
        System.out.println("\nСтатистика по этажам");

        if (floorStatistics.isEmpty()) {
            System.out.println("Данных по этажам не найдено");
            return;
        }

        for (String city : floorStatistics.keySet()) {
            int[] floors = floorStatistics.get(city);
            System.out.printf("Город %s:%n", city);

            for (int i = 0; i < floors.length; i++) {
                System.out.printf("  %d-этажных зданий: %d%n", i + 1, floors[i]);
            }
        }
    }
}
