import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PricePerHour[] priceList = new PricePerHour[24];
        fillPriceList(priceList);

        Menu.displayMenu();
        Menu.menu(scanner, priceList);

    }

    private static void fillPriceList(PricePerHour[] priceList) {
        for (int i = 0; i < 24; i++) {
            priceList[i] = new PricePerHour(PricePerHour.getHourString(i), 0);
        }
    }

}
