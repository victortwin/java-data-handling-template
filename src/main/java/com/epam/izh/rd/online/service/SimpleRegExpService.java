package com.epam.izh.rd.online.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String string = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/sensitive_data.txt"));
            string = reader.readLine();
            reader.close();
            Pattern pattern = Pattern.compile("\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}");
            Matcher matcher = pattern.matcher(string);
            String account;
            while (matcher.find()) {
                account = matcher.group();
                String[] array = account.split(" ");
                array[1] = "****";
                array[2] = "****";
                String accountHide = array[0] + " " + array[1] + " " + array[2]+ " " + array[3];
                string = string.replace(account, accountHide);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String string = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/sensitive_data.txt"));) {
            string = reader.readLine();
            reader.close();
            Pattern pattern = Pattern.compile("\\$\\{.+?}");
            Matcher matcher = pattern.matcher(string);
            String toReplace;
            while (matcher.find()) {
                toReplace = matcher.group();
                if (toReplace.equals("${payment_amount}")) {
                    string = string.replace(toReplace, String.valueOf((int) paymentAmount));
                } else if (toReplace.equals("${balance}")) {
                    string = string.replace(toReplace, String.valueOf((int) balance));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }
}
