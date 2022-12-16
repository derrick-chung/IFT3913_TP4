package ua.karatnyk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.karatnyk.impl.CurrencyConversion;
import ua.karatnyk.impl.CurrencyConvertor;
import ua.karatnyk.impl.OfflineJsonWorker;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class TestCurrencyConvertor {
    OfflineJsonWorker manager;
    CurrencyConversion conversion;
    CurrencyConvertor convertor;
    String[] currencies;

    @BeforeEach
    void setUp() {
        currencies = new String[]{"CAD", "USD", "GBP", "EUR", "CHF", "INR", "AUD"};
        manager = new OfflineJsonWorker();
        conversion = manager.parser();
    }
    //Blackbox
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
    //Blackbox
    @Test
    void convertRejectedAmountDomainTest() {
        boolean pass = true;
        String resultMessage = "";
        try {
            for (String fromCurr :
                    currencies) {
                for (String toCurr :
                        currencies) {
                    double resultUnder = convertor.convert(-1, fromCurr, toCurr, conversion);
                    double resultOver = convertor.convert(10001, fromCurr, toCurr, conversion);
                    resultMessage += "-1 " + fromCurr + " = " + Double.toString(resultUnder) + " " + toCurr +
                            "; 10001 " + fromCurr + " = " + Double.toString(resultOver) + " " + toCurr + "\n";
                }
            }
        } catch (ParseException PE) {
            pass = false;
        }
        assertFalse(pass, resultMessage);
    }
    //Blackbox
    @Test
    void convertRejectedCurrencyDomainTest1() {
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
    //Blackbox
    @Test
    void convertRejectedCurrencyDomainTest2() {
        boolean pass = true;
        String resultMessage = "";
        try {
            for (String currency :
                    currencies) {
                double resultTo = convertor.convert(1, currency, "FJD", conversion);
                double resultFrom = convertor.convert(1, "FJD", currency, conversion);
                resultMessage += "1 " + currency + " = " + Double.toString(resultTo) + " FJD" +
                        "; 1 FJD = " + Double.toString(resultFrom) + " " + currency + "\n";
            }
        } catch (ParseException PE) {
            pass = false;
        }
        assertFalse(pass, resultMessage);
    }

    @Test
    void whiteBoxTest() {
        //White Box Tests
        assertAll(
                //Fail premiere condition
                () -> assertThrows(ParseException.class, () -> convertor.convert(1, "USD", "pouet", conversion)),
                //Fail deuxieme condition
                () -> assertThrows(ParseException.class, () -> convertor.convert(1, "pouet", "CAD", conversion)),
                //Success AKA normal path
                () -> assertDoesNotThrow(() -> convertor.convert(0, "USD", "CAD", conversion))
        );
    }

}
