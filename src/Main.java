import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PricePerHour[] priceList = new PricePerHour[24];
        for (int i = 0; i < 24; i++) {
            priceList[i] = new PricePerHour(PricePerHour.hoursArray[i], 0);
        }

        Menu.displayMenu();
        Menu.menu(scanner, priceList);

    }

    public static String addSpaces(int count) {
        char c = ' ';
        StringBuilder print = new StringBuilder();
        for (int i = 0; i < count; i++) {
            print.append(c);
        }
        return print.toString();
    }

    public static int checkIfInteger(Scanner scanner) {
        int stringToNumber;
        while(true) {
            try {
                stringToNumber = Integer.parseInt(scanner.next());
                return stringToNumber;
            } catch (Exception e) {
                System.out.print("Lägg in pris i hela ören:");
            }
        }
    }

}
