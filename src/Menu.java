
import java.util.Arrays;
import java.util.Scanner;

public class Menu {


    public static void menu(Scanner scanner, PricePerHour[] priceList) {
        System.out.print("Menyval: ");
        String menuChoice = scanner.next();

        switch (menuChoice.toLowerCase()) {
            case "1" -> PricePerHour.inputPrices(priceList, scanner);
            case "2" -> PricePerHour.printMinMaxAverage(priceList, PricePerHour.minPriceIndex(priceList), PricePerHour.maxPriceIndex(priceList));
            case "3" -> PricePerHour.sortPriceList(Arrays.copyOf(priceList, priceList.length));
            case "4" -> PricePerHour.cheapestChargingHours(priceList);
            case "5" -> PricePerHour.visualization(priceList, PricePerHour.minPriceIndex(priceList), PricePerHour.maxPriceIndex(priceList));
            case "e" -> System.exit(0);
            // Hidden menu:
            case "p" -> PricePerHour.printPriceList(priceList);
            case "r" -> PricePerHour.inputRandomPrices(priceList);

        }
        System.out.println();
        displayMenu();
        menu(scanner, priceList);

    }

    public static void displayMenu() {
        System.out.println("""
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid
                5. Visualisering
                e. Avsluta""");
        System.out.println();
    }
}
