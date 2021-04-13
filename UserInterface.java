package Homework6;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    Controller controller = new Controller();

    public void runInterface(){
        Scanner scanner = new Scanner(System.in);

        outer: while (true){
            System.out.println("_______________________");
            System.out.println("Введите название города");
            String city = scanner.nextLine();

            System.out.println("Вам доступны следующие команды:");
            System.out.println("1 - для получения текущей погоды");
            System.out.println("2 - для получения прогноза на 5 дней. Данные будут записаны в БД");
            System.out.println("history - для получения сохраненных прогнозов из БД");
            System.out.println("end - для выхода из программы");
            String command = scanner.nextLine();

            try {
                controller.getWeather(command, city);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
