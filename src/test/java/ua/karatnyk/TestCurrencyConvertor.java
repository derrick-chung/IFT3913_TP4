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
        try {
            convertor.convert(0, "USD", "CAD", conversion);
            convertor.convert(10000, "USD", "CAD", conversion);
            convertor.convert(0, "CAD", "USD", conversion);
            convertor.convert(10000, "USD", "CAD", conversion);
        } catch (ParseException PE) {
            PE.printStackTrace();
        }
    }
}
