package ru.dva.vl;

import ru.dva.vl.exception.ParamFormatException;
import ru.dva.vl.exception.ParamRequiredException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class ParamsParser {

    public static AnalyzerParams parse(String[] args) {
        double availability;
        long time;
        List<String> argsList = Arrays.asList(args);

        String uValue = getValueByKey("u", argsList);
        try {
            availability = Double.parseDouble(uValue);
        } catch (Exception e) {
            throw new ParamFormatException("u", e);
        }

        String tValue = getValueByKey("t", argsList);
        try {
            time = Long.parseLong(tValue);
        } catch (Exception e) {
            throw new ParamFormatException("t", e);
        }

        return new AnalyzerParams(availability, Duration.of(time, ChronoUnit.MILLIS));
    }

    private static String getValueByKey(String key, List<String> argsList) {
        int keyIndex = argsList.indexOf(key);
        if (keyIndex == -1) {
            throw new ParamRequiredException(key);
        }
        try {
            return argsList.get(keyIndex + 1);
        } catch (IndexOutOfBoundsException e) {
            throw new ParamFormatException(key, e);
        }
    }

}
