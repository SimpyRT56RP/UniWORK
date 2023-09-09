package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Java extends {
    private static final String NAZWA_PLIKU = "konwersje.txt";

    private TextArea outputTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Konwerter Jednostek Miary");

        Button konwertujButton = new Button("Konwertuj jednostki");
        Button wyszukajButton = new Button("Wyszukaj konwersje");
        Button odczytajButton = new Button("Odczytaj zapisane konwersje");

        Label wartoscLabel = new Label("Podaj wartosc w metrach:");
        TextField wartoscTextField = new TextField();

        Label rodzajMiaryLabel = new Label("Podaj rodzaj miary (np. cm, mm, km, ft, in):");
        TextField rodzajMiaryTextField = new TextField();

        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);

        konwertujButton.setOnAction(e -> {
            double wartosc = Double.parseDouble(wartoscTextField.getText());
            List<String> konwersje = konwertujJednostki(wartosc);
            updateOutputText(konwersje);
        });

        wyszukajButton.setOnAction(e -> {
            String rodzajMiary = rodzajMiaryTextField.getText();
            wyszukajKonwersje(rodzajMiary);
        });

        odczytajButton.setOnAction(e -> odczytajZPliku());
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
                wartoscLabel, wartoscTextField,
                konwertujButton,
                rodzajMiaryLabel, rodzajMiaryTextField,
                wyszukajButton,
                odczytajButton,
                outputTextArea
        );
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateOutputText(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        outputTextArea.setText(sb.toString());
    }

    public static void odczytajZPliku() {
        try (Scanner scanner = new Scanner(new File(NAZWA_PLIKU))) {
            System.out.println("Zapisane konwersje:");
            while (scanner.hasNextLine()) {
                String konwersja = scanner.nextLine();
                System.out.println(konwersja);
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

        zapiszDoPliku(konwersje);
        return konwersje;
    }

    public static void wyszukajKonwersje(String rodzajMiary) {
        try (Scanner scanner = new Scanner(new File(NAZWA_PLIKU))) {
            System.out.println("Wyszukane konwersje dla jednostki " + rodzajMiary + ":");
            while (scanner.hasNextLine()) {
                String konwersja = scanner.nextLine();
                if (konwersja.contains(rodzajMiary)) {
                    System.out.println(konwersja);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak zapisanych konwersji.");
        }
    }

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
}