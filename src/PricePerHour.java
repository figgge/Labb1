import java.util.Random;
import java.util.Scanner;


public class PricePerHour {
    private String hour;
    private int price;

    public PricePerHour(String hour, int price) {
        this.hour = hour;
        this.price = price;
    }

    public static final String[] hoursArray = {"00:00 - 01:00", "01:00 - 02:00", "02:00 - 03:00", "03:00 - 04:00", "04:00 - 05:00", "05:00 - 06:00", "06:00 - 07:00", "07:00 - 08:00", "08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00", "19:00 - 20:00", "20:00 - 21:00", "21:00 - 22:00", "22:00 - 23:00", "23:00 - 00:00"};

    public String getHour() {
        return hour;
    }

    public int getPrice() {
        return price;
    }


    public static void inputPrices(PricePerHour[] priceList,  Scanner scanner) {
        System.out.println("Lägg in elpriser: ");
        for (int i = 0; i < 24; i++) {
            System.out.print(hoursArray[i] + ": ");
            priceList[i] = new PricePerHour(hoursArray[i], Main.checkIfInteger(scanner));
        }
    }
    public static void inputRandomPrices(PricePerHour[] priceList) {
        Random random = new Random();
        System.out.println("Lägg in elpriser: ");
        for (int i = 0; i < 24; i++) {
            priceList[i] = new PricePerHour(hoursArray[i], random.nextInt(1,5000));
        }
    }

    public static void printMinMaxAverage(PricePerHour[] priceList, int minIndex, int maxIndex) {
        System.out.println("Lägsta pris: Kl " + priceList[minIndex].getHour() + ": " + priceList[minIndex].getPrice() + " öre");
        System.out.println("Högsta pris: Kl " + priceList[maxIndex].getHour() + ": " + priceList[maxIndex].getPrice() + " öre");
        System.out.println("Medelpris: " + averagePrice(priceList) + " öre");
    }

    public static int minPriceIndex(PricePerHour[] priceList) {
        int minIndex =  0;
        for (int i = 0; i < priceList.length; i++) {
            if (priceList[i].getPrice() < priceList[minIndex].getPrice())
                minIndex = i;
        }
        return minIndex;
    }

    public static int maxPriceIndex(PricePerHour[] priceList) {
        int maxIndex = 0;
        for (int i = 0; i < priceList.length; i++) {
            if (priceList[i].getPrice() > priceList[maxIndex].getPrice())
                maxIndex = i;
        }
        return maxIndex;
    }

    public static int averagePrice(PricePerHour[] priceList) {
        double sum = 0;
        for (PricePerHour pricePerHour : priceList) {
            sum += pricePerHour.getPrice();
        }
        return (int) Math.round(sum / priceList.length);
    }

    public static void printPriceList(PricePerHour[] priceList) {
        for (PricePerHour list : priceList) {
            System.out.println(list.getHour() + ": " + list.getPrice() + " öre");
        }
    }

    public static void sortPriceList(PricePerHour[] priceList) {
        boolean isSwapped = true;
        while(isSwapped) {
            isSwapped = false;
            for (int i = 0; i < priceList.length - 1; i++) {
                if(priceList[i].getPrice() > priceList[i + 1].getPrice()) {
                    PricePerHour temp = new PricePerHour(priceList[i].getHour(), priceList[i].getPrice());
                    priceList[i] = priceList[i+1];
                    priceList[i + 1] = temp;
                    isSwapped = true;
                }
            }
        }
        printPriceList(priceList);
    }

    public static void cheapestChargingHours(PricePerHour[] priceList) {
        int[] fourHourPrice = new int[21];
        for (int i = 0; i < priceList.length -3; i++) {
            int fourHourSum = 0;
            int hour = 0;
            for (int j = 0; j < 4; j++) {
                fourHourSum += priceList[i + hour].getPrice();
                hour += 1;
            }
            fourHourPrice[i] = fourHourSum;
        }
        int min = 0;
        for (int i = 0; i < fourHourPrice.length - 1; i++) {
            if (fourHourPrice[i] < fourHourPrice[min])
                min = i;
        }
        System.out.println("Det är billigast att ladda bilen kl. " + priceList[min].getHour().substring(0,5) + " - " + priceList[min + 4].getHour().substring(0,5));
        System.out.println("Medelpris per kw/h: " + (Math.round(fourHourPrice[min] / 4) + " öre"));
    }

//    public static void visualization(PricePerHour[] priceList, int minPriceIndex, int maxPriceIndex){
//        int maxPriceLength = String.valueOf(priceList[maxPriceIndex].getPrice()).length();
//        int minPriceLength = String.valueOf(priceList[minPriceIndex].getPrice()).length();
//
//        for (int y = 0; y < 12; y++) {
//            System.out.println();
//            for (int x = 0; x < 77; x++) {
//                if (y == 0 && x == 0)
//                    System.out.print(priceList[maxPriceIndex].getPrice() + Main.addSpaces( 5 - maxPriceLength)); // ändra till border
//                else if (y == 9 && x == 0)
//                    System.out.print(priceList[minPriceIndex].getPrice() + Main.addSpaces( 5 - minPriceLength));
//                if (x == 5 && y != 0 && y != 9 && y != 11)
//                    System.out.print("     |");
//                else if (x == 5)
//                    System.out.print("|");
//                else if (y == 10 & x > 5)
//                    System.out.print("-");
//                else if (y == 11 & x > 5 && x - 6 < priceList.length)
//                    System.out.print(priceList[x-6].getHour().substring(0,2) + " ");
//                else if (y == 11)
//                    System.out.print(" ");
//                if (x > 5 && x - 6 < priceList.length && y < 10 && checkPriceRange(priceList, x - 6) <= y)
//                    System.out.print(" * ");
//                else if (x > 5 && x - 6 < priceList.length && y < 10)
//                    System.out.print("   ");
//            }
//        }
//        System.out.println();
//    }
    public static void visualization(PricePerHour[] priceList, int minPriceIndex, int maxPriceIndex){
        int maxPriceLength = String.valueOf(priceList[maxPriceIndex].getPrice()).length();
        int minPriceLength = String.valueOf(priceList[minPriceIndex].getPrice()).length();
        int distanceToOutline = Math.max(maxPriceLength, 5);

        for (int y = 0; y < 12; y++) {
            System.out.println();
            for (int x = 0; x < 77; x++) {
                if (y == 0 && x == 0)
                    System.out.print(priceList[maxPriceIndex].getPrice() + Main.addSpaces( distanceToOutline - maxPriceLength) + "|");
                else if (y == 9 && x == 0)
                    System.out.print(priceList[minPriceIndex].getPrice() + Main.addSpaces( distanceToOutline - minPriceLength) + "|");
                if (x == distanceToOutline && y != 0 && y != 9 && y != 11)
                    System.out.print(Main.addSpaces(distanceToOutline) + "|");
                else if (y == 10 & x > distanceToOutline)
                    System.out.print("-");
                else if (y == 11 & x > distanceToOutline && x - (distanceToOutline + 1) < priceList.length)
                    System.out.print(priceList[x - (distanceToOutline + 1)].getHour().substring(0,2) + " ");
                else if (y == 11)
                    System.out.print(" ");
                if (x > distanceToOutline && x - (distanceToOutline + 1) < priceList.length && y < 10 && checkPriceRange(priceList, x - (distanceToOutline + 1)) <= y)
                    System.out.print(" * ");
                else if (x > distanceToOutline && x - (distanceToOutline + 1) < priceList.length && y < 10)
                    System.out.print("   ");
            }
        }
        System.out.println();
    }

    public static int checkPriceRange(PricePerHour[] priceList, int index) {
        double price = priceList[index].getPrice();
        double maxPrice = priceList[maxPriceIndex(priceList)].getPrice();
        if (price / maxPrice * 10 < 0)
            return 0;
        return (int) (10 - (price / maxPrice) * 10);
    }





}


