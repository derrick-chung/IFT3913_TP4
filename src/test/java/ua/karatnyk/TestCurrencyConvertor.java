package ua.karatnyk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.karatnyk.impl.CurrencyConversion;
import ua.karatnyk.impl.CurrencyConvertor;
import ua.karatnyk.impl.OfflineJsonWorker;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class TestCurrencyConvertor {
    private static boolean setUpIsDone = false;
    OfflineJsonWorker manager;
    CurrencyConversion conversion;
    CurrencyConvertor convertor;
    String[] currencies;

    @BeforeEach
    void setUp() {
        if (setUpIsDone) {
            return;
        }
        currencies = new String[]{"CAD", "USD", "GBP", "EUR", "CHF", "INR", "AUD"};
        manager = new OfflineJsonWorker();
        conversion = manager.parser();
        setUpIsDone = true;
    }

    @Test
    void BlackBoxTest() {
      convertAcceptedAmountDomainTest();
      convertRejectedAmountDomainTest();
      convertRejectedCurrencyDomainTest();
    }

    @Test
    void convertAcceptedAmountDomainTest() {
        boolean pass = true;
        try {
            for (String fromCurr :
                    currencies) {
                for (String toCurr :
                        currencies) {
                    for (double i = 0; i <= 10000; i += 0.01) {
                        convertor.convert(i, fromCurr, toCurr, conversion);
                    }
                }
            }
        } catch (ParseException PE) {
            pass = false;
        }
        assertTrue(pass);
    }

    @Test
    void convertRejectedAmountDomainTest() {
        boolean pass = true;
        String resultMessage="";
        try {
            for (String fromCurr :
                    currencies) {
                for (String toCurr :
                        currencies) {
                    double resultUnder = convertor.convert(-1, fromCurr, toCurr, conversion);
                    double resultOver = convertor.convert(10001, fromCurr, toCurr, conversion);
                    resultMessage += "-1 "+fromCurr+" = "+Double.toString(resultUnder)+" "+toCurr+
                                   "; 10001 "+fromCurr+" = "+Double.toString(resultOver)+" "+toCurr+"\n";
                }
            }
        } catch (ParseException PE) {
            pass = false;
        }
        assertFalse(pass,resultMessage);
    }

    @Test
    void convertRejectedCurrencyDomainTest() {
        boolean pass = true;
        try {
            for (String currency :
                    currencies) {
                convertor.convert(1, currency, "bidon", conversion);
                convertor.convert(1, "bidon", currency, conversion);
            }
        } catch (ParseException PE) {
            pass = false;
        }
        assertFalse(pass);
    }
}