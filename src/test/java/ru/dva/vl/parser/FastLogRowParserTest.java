package ru.dva.vl.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dva.vl.dto.LogRow;
import ru.dva.vl.exception.RowParsingException;

import java.time.LocalDateTime;

public class FastLogRowParserTest {

    LogRowParser parser = new FastLogRowParser();

    @Test
    @DisplayName("FastLogRowParser успешный разбор")
    void testCorrectFormat() {
        String line = "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=" +
                "407a047a HTTP/1.1\" 200 2 108.334488 \"-\" \"@list-item-updater\" prio:0";
        LogRow row = parser.parse(line);
        Assertions.assertEquals(LocalDateTime.of(2017, 6, 14, 16, 47, 2), row.time());
        Assertions.assertEquals('2', row.httpStatusStartWith());
        Assertions.assertEquals(108.334488, row.duration());
    }

    @Test
    @DisplayName("FastLogRowParser ошибка при разборе даты")
    void testIncorrectDateTime() {
        String line = "192.168.32.181 - - [14-06-2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=" +
                "407a047a HTTP/1.1\" 200 2 108.334488 \"-\" \"@list-item-updater\" prio:0";
        RowParsingException ex = Assertions.assertThrows(RowParsingException.class,
                () -> parser.parse(line));
        Assertions.assertEquals("Log row [" + line + "] has incorrect format", ex.getMessage());
    }

    @Test
    @DisplayName("FastLogRowParser ошибка при разборе http статуса")
    void testIncorrectHttpStatus() {
        String line = "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=" +
                "407a047a HTTP/1.1\"";
        RowParsingException ex = Assertions.assertThrows(RowParsingException.class,
                () -> parser.parse(line));
        Assertions.assertEquals("Log row [" + line + "] has incorrect format", ex.getMessage());
    }

    @Test
    @DisplayName("FastLogRowParser ошибка при разборе длительности запроса")
    void testIncorrectDuration() {
        String line = "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=" +
                "407a047a HTTP/1.1\" 200 2 108.33.4488 \"-\" \"@list-item-updater\" prio:0";
        RowParsingException ex = Assertions.assertThrows(RowParsingException.class,
                () -> parser.parse(line));
        Assertions.assertEquals("Log row [" + line + "] has incorrect format", ex.getMessage());
    }
}
