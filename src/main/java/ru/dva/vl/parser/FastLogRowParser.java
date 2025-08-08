package ru.dva.vl.parser;

import ru.dva.vl.dto.LogRow;
import ru.dva.vl.exception.RowParsingException;

import java.time.LocalDateTime;

public class FastLogRowParser extends LogRowParser {

    @Override
    public LogRow parse(String line) {
        try {
            String dateTimeStr = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
            LocalDateTime time = LocalDateTime.parse(dateTimeStr, formatter);
            String s = line.substring(line.indexOf('"') + 1);
            s = s.substring(s.indexOf('"') + 1);
            String s9_11 = s.substring(1, s.indexOf('"') - 1);
            char s9 = s9_11.charAt(0); //т.к. проверяем на любой 500й код достаточно первого символа
            double s11 = Double.parseDouble(s9_11.substring(s9_11.lastIndexOf(' ') + 1));
            return new LogRow(time, s9, s11);
        } catch (Exception e) {
            throw new RowParsingException(line, e);
        }
    }
}
