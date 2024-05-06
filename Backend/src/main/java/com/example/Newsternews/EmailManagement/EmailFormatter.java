package com.example.Newsternews.EmailManagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class EmailFormatter
{

    private static String getFormattedDate()
    {
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d");

        return currentDate.format(formatter);
    }

    public static String getNewsEmailSubject(EmailTemplate emailTemplate)
    {
        Random random = new Random();

        int random_int = random.nextInt(3);

        String sampleTitle = switch (random_int) {
            case 0 -> emailTemplate.getName1();
            case 1 -> emailTemplate.getName2();
            case 2 -> emailTemplate.getName3();
            default -> "";
        };

        if(sampleTitle.length() > 15)
        {
            sampleTitle = sampleTitle.substring(0, 15);
        }

        return "News for " + getFormattedDate() + ": " + sampleTitle + "..." + "and more";
    }
}
