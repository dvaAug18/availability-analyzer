package ru.dva.vl.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dva.vl.dto.AnalyzerParams;
import ru.dva.vl.exception.ParamFormatException;
import ru.dva.vl.exception.ParamRequiredException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ParamsParserTest {

    @Test
    @DisplayName("Ожидаемое исключение ParamRequiredException, отсутсвуют оба обязательных параметра")
    void testParamRequiredException_both() {
        ParamRequiredException ex = Assertions.assertThrows(ParamRequiredException.class,
                () -> ParamsParser.parse(new String[0]));
        Assertions.assertEquals("Not found required param [-u]", ex.getMessage());
    }

    @Test
    @DisplayName("Ожидаемое исключение ParamRequiredException, отсутсвует параметр -u")
    void testParamRequiredException_u() {
        ParamRequiredException ex = Assertions.assertThrows(ParamRequiredException.class,
                () -> ParamsParser.parse(new String[]{"-t", "45", "-other"}));
        Assertions.assertEquals("Not found required param [-u]", ex.getMessage());
    }

    @Test
    @DisplayName("Ожидаемое исключение ParamRequiredException, отсутсвует параметр -t")
    void testParamRequiredException_t() {
        ParamRequiredException ex = Assertions.assertThrows(ParamRequiredException.class,
                () -> ParamsParser.parse(new String[]{"-u", "99.99", "-other"}));
        Assertions.assertEquals("Not found required param [-t]", ex.getMessage());
    }

    @Test
    @DisplayName("Ожидаемое исключение ParamFormatException, отсутствует значение параметра -u")
    void testParamFormatException_u() {
        ParamFormatException ex = Assertions.assertThrows(ParamFormatException.class,
                () -> ParamsParser.parse(new String[]{"-u", "-t", "44", "-other"}));
        Assertions.assertEquals("Param [-u] has incorrect format", ex.getMessage());
    }

    @Test
    @DisplayName("Ожидаемое исключение ParamFormatException, отсутствует значение параметра -t")
    void testParamFormatException_t() {
        ParamFormatException ex = Assertions.assertThrows(ParamFormatException.class,
                () -> ParamsParser.parse(new String[]{"-u", "98.01", "-t", "-other"}));
        Assertions.assertEquals("Param [-t] has incorrect format", ex.getMessage());
    }

    @Test
    @DisplayName("Корректное распознавание параметров")
    void testCorrectParam() {
        AnalyzerParams params = ParamsParser.parse(new String[]{"-u", "98.01", "-t", "45", "-other"});

        Assertions.assertEquals(98.01, params.availability());
        Assertions.assertEquals(45, params.timeout());
    }
}
