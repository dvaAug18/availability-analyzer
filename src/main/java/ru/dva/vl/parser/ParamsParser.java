package ru.dva.vl.parser;

import ru.dva.vl.exception.ParamFormatException;
import ru.dva.vl.exception.ParamRequiredException;
import ru.dva.vl.dto.AnalyzerParams;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class ParamsParser {

    public static AnalyzerParams parse(String[] args) {
        double availability;
        double timeout;
        List<String> argsList = Arrays.asList(args);

        String uValue = getRequiredValueByKey("-u", argsList);
        try {
            availability = Double.parseDouble(uValue);
        } catch (Exception e) {
            throw new ParamFormatException("-u", e);
        }

        String tValue = getRequiredValueByKey("-t", argsList);
        try {
            timeout = Double.parseDouble(tValue);
        } catch (Exception e) {
            throw new ParamFormatException("-t", e);
        }

        return new AnalyzerParams(availability, timeout, argsList.contains("-d"));
    }

    private static String getRequiredValueByKey(String key, List<String> argsList) {
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
