package ua.karatnyk;

import ua.karatnyk.impl.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestCurrencyConvertor {
    OfflineJsonWorker manager = new OfflineJsonWorker();
    CurrencyConversion conversion = manager.parser();
    CurrencyConvertor convertor;

    public void BlackBoxTest() {
        String[] currencies = new String[]{"CAD", "USD", "GBP", "EUR", "CHF", "INR", "AUD"};
        try {

            for (String fromCurr :
                    currencies) {
                for (String toCurr :
                        currencies) {
                    for (double i = 0; i <= 10000; i++) {
                        double result = convertor.convert(i, fromCurr, toCurr, conversion);
                        if (i == 0 || i == 10000)
                            System.out.println(i + " " + fromCurr + " = " + result + " " + toCurr);
                    }
                    convertor.convert(-1, fromCurr, toCurr, conversion);
                    convertor.convert(10001, fromCurr, toCurr, conversion);
                    convertor.convert(100.60, fromCurr, toCurr, conversion);
                }
                convertor.convert(1, fromCurr, "bidon", conversion);
                convertor.convert(1, "bidon", fromCurr, conversion);
            }
        } catch (ParseException PE) {
            PE.printStackTrace();
        }
    }
}
