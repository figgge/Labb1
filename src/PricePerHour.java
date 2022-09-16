import java.util.Random;
import java.util.Scanner;


public class PricePerHour {
    private final String hour;
    private final int price;

    public PricePerHour(String hour, int price) {
        this.hour = hour;
        this.price = price;
    }

    public String getHour() {
        return hour;
    }

    public int getPrice() {
        return price;
    }


    public static void inputPrices(PricePerHour[] priceList,  Scanner scanner) {
        System.out.println("Lägg in elpriser: ");
        int hour = 0;
        String hourString;
        for (int i = 0; i < 24; i++) {
            hourString = getHourString(hour);
            System.out.print(hourString + ": ");
            priceList[i] = new PricePerHour(hourString, checkIfValidPrice(scanner));
            hour += 1;
        }
    }


    public static String getHourString(int hour) {
        String hourString;
        if (hour == 9)
            hourString = "0" + hour + ":00 - " + (hour + 1) + ":00";
        else if (hour < 10)
            hourString = "0" + hour + ":00 - 0" + (hour + 1) + ":00";
        else
           hourString = hour + ":00 - " + (hour + 1) + ":00";
        return hourString;
    }

    public static void inputRandomPrices(PricePerHour[] priceList) {
        Random random = new Random();
        int hour = 0;
        System.out.println("Lägg in elpriser: ");
        for (int i = 0; i < 24; i++) {
            priceList[i] = new PricePerHour(getHourString(hour), random.nextInt(100,5000));
            hour += 1;
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

    private static int averagePrice(PricePerHour[] priceList) {
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
        double[] fourHourPrice = new double[21];
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
        System.out.println("Det är billigast att ladda bilen med start kl. " + priceList[min].getHour().substring(0,5) + " till kl. " + priceList[min + 4].getHour().substring(0,5));
        System.out.println("Medelpris per kwh: " + ((int) Math.round(fourHourPrice[min] / 4) + " öre"));
    }

    public static void visualization(PricePerHour[] priceList, int minPriceIndex, int maxPriceIndex){
        int maxPriceLength = String.valueOf(priceList[maxPriceIndex].getPrice()).length();
        int minPriceLength = String.valueOf(priceList[minPriceIndex].getPrice()).length();
        int distanceToOutline = Math.max(maxPriceLength, 5);

        for (int y = 0; y < 12; y++) {
            System.out.println();
            for (int x = 0; x < 77; x++) {
                addSpacesBetweenPriceAndOutline(priceList, minPriceIndex, maxPriceIndex, maxPriceLength, minPriceLength, distanceToOutline, y, x);
                printOutlineAndHours(priceList, distanceToOutline, y, x);
                visualizePriceRange(priceList, distanceToOutline, y, x);
            }
        }
        System.out.println();
    }

    private static void visualizePriceRange(PricePerHour[] priceList, int distanceToOutline, int y, int x) {
        if (x > distanceToOutline && x - (distanceToOutline + 1) < priceList.length && y < 10 && checkPriceRange(priceList, x - (distanceToOutline + 1)) <= y)
            System.out.print(" * ");
        else if (x > distanceToOutline && x - (distanceToOutline + 1) < priceList.length && y < 10)
            System.out.print("   ");
    }

    private static void printOutlineAndHours(PricePerHour[] priceList, int distanceToOutline, int y, int x) {
        if (x == distanceToOutline && y != 0 && y != 9 && y != 11)
            System.out.print(addSpaces(distanceToOutline) + "|");
        else if (y == 10 && x > distanceToOutline)
            System.out.print("-");
        else if (y == 11 && x > distanceToOutline && x - (distanceToOutline + 1) < priceList.length)
            System.out.print(priceList[x - (distanceToOutline + 1)].getHour().substring(0,2) + " ");
        else if (y == 11)
            System.out.print(" ");
    }

    private static void addSpacesBetweenPriceAndOutline(PricePerHour[] priceList, int minPriceIndex, int maxPriceIndex, int maxPriceLength, int minPriceLength, int distanceToOutline, int y, int x) {
        if (y == 0 && x == 0)
            System.out.print(priceList[maxPriceIndex].getPrice() + addSpaces( distanceToOutline - maxPriceLength) + "|");
        else if (y == 9 && x == 0)
            System.out.print(priceList[minPriceIndex].getPrice() + addSpaces( distanceToOutline - minPriceLength) + "|");
    }

    private static int checkPriceRange(PricePerHour[] priceList, int index) {
        double price = priceList[index].getPrice();
        double maxPrice = priceList[maxPriceIndex(priceList)].getPrice();
        if (price / maxPrice * 10 < 0)
            return 0;
        return (int) (10 - (price / maxPrice) * 10);
    }
    private static String addSpaces(int count) {
        char c = ' ';
        StringBuilder print = new StringBuilder();
        for (int i = 0; i < count; i++) {
            print.append(c);
        }
        return print.toString();
    }

    private static int checkIfValidPrice(Scanner scanner) {
        int stringToNumber;
        while(true) {
            try {
                stringToNumber = Integer.parseInt(scanner.next());
                if (stringToNumber < 0)
                    throw new IllegalArgumentException();
                return stringToNumber;
            }
            catch (NumberFormatException e) {
                System.out.print("Lägg in pris i hela ören: ");
            }
            catch (IllegalArgumentException exception) {
                System.out.print("Negativa priser ej tillåtna. ");
            }
        }
    }


}
