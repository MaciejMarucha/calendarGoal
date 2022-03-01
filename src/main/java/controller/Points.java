package controller;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Points {
    private int level = 1;
    private int pointsNumber = 0;
    private int[] a = new int[101];

    public Points() throws IOException {
        a[0] = 0;
        a[1] = 50;
        int lp = 50;
        int sap = 50;
        int poz = 1;
        int pod = 50;
        for (int i = 1; i < a.length - 1; i++) {
            if (lp >= sap) {
                pod = (int) Math.ceil(1.05 * pod);
                sap = sap + pod;
                poz++;
                a[poz] = sap;
                lp = a[poz];
            }
        }
        //wczytanie liczby punktow z pliku
        try (Scanner sc = new Scanner(new FileReader("liczbapunktow.txt"))) {
            pointsNumber = Integer.parseInt(sc.nextLine());
            level = Integer.parseInt(sc.nextLine());
        }
    }

    public int getLevel() {
        return level;
    }

    public int getPointsNumber() {
        return pointsNumber;
    }

    public void addPoints(int points) throws IOException {
        pointsNumber += points;
        if (pointsNumber >= a[level]) {
            level++;
        }
        String content = pointsNumber + "\r\n" + level;
        Files.write(Paths.get("liczbapunktow.txt"), content.getBytes(StandardCharsets.UTF_8));
    }

    public void subtractPoints(int points) throws IOException {
        pointsNumber -= points;
        if (pointsNumber < 0) pointsNumber = 0;
        if (pointsNumber < a[level - 1]) {
            level--;
            if (level < 1) {
                level = 1;
            }
        }
        String content = pointsNumber + "\r\n" + level;
        Files.write(Paths.get("liczbapunktow.txt"), content.getBytes(StandardCharsets.UTF_8));
    }

    public int[] getA() {
        return a;
    }
}