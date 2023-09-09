package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class KonwerterJednostekMiary {
    private static final String NAZWA_PLIKU = "konwersje.txt";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Program do konwersji jednostek miary");
            System.out.println("1. Konwertuj jednostki");
            System.out.println("2. Wyszukaj konwersje");
            System.out.println("3. Odczytaj zapisane konwersje");
            System.out.println("4. Wyjdz");

            System.out.print("Wybierz opcje: ");
            int wybor = input.nextInt();

            if (wybor == 1) {
                System.out.print("Podaj wartosc w metrach: ");
                double wartosc = input.nextDouble();
                List<String> konwersje = konwertujJednostki(wartosc);
                for (String konwersja : konwersje) {
                    System.out.println(konwersja);
                }
            } else if (wybor == 2) {
                System.out.print("Podaj rodzaj miary (np. cm, mm, km, ft, in): ");
                String rodzajMiary = input.next();
                wyszukajKonwersje(rodzajMiary);
            } else if (wybor == 3) {
                odczytajZPliku();
            } else if (wybor == 4) {
                break;
            } else {
                System.out.println("Niepoprawny wybor. Sprobuj ponownie.");
            }

            System.out.println();
        }
    }

    public static void odczytajZPliku() {
        try (Scanner scanner = new Scanner(new File(NAZWA_PLIKU))) {
            List<String> konwersje = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String konwersja = scanner.nextLine();
                konwersje.add(konwersja);
            }
            if (konwersje.isEmpty()) {
                System.out.println("Brak zapisanych konwersji.");
            } else {
                System.out.println("Zapisane konwersje:");
                for (String konwersja : konwersje) {
                    System.out.println(konwersja);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak zapisanych konwersji.");
        }
    }

    public static List<String> konwertujJednostki(double wartosc) {
        List<String> konwersje = new ArrayList<>();
        konwersje.add(wartosc + " m = " + (wartosc * 100) + " cm");
        konwersje.add(wartosc + " m = " + (wartosc * 1000) + " mm");
        konwersje.add(wartosc + " m = " + (wartosc / 1000) + " km");
        konwersje.add(wartosc + " m = " + (wartosc * 3.281) + " ft");
        konwersje.add(wartosc + " m = " + (wartosc * 39.37) + " in");

        zapiszDoPliku();
        return konwersje;
    }

    private static void zapiszDoPliku() {
    }

    public static void wyszukajKonwersje(String rodzajMiary) {
        try (Scanner scanner = new Scanner(new File(NAZWA_PLIKU))) {
            boolean znaleziono = false;
            while (scanner.hasNextLine()) {
                String konwersja = scanner.nextLine();
                if (konwersja.contains(rodzajMiary)) {
                    znaleziono = true;
                    System.out.println(konwersja);
                }
            }
            if (!znaleziono) {
                System.out.println("Brak zapisanych konwersji dla jednostki " + rodzajMiary + ".");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak zapisanych konwersji.");
        }
    }
}
