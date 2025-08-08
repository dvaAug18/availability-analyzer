package ru.dva.vl.parser;

import ru.dva.vl.dto.LogRow;
import ru.dva.vl.exception.RowParsingException;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpLogRowParser extends LogRowParser {

    final String regex = "^(\\S+) (\\S+) (\\S+) " +
            "\\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)" +
            " (\\S+)\\s*(\\S+)?\\s*\" (\\d{3}) (\\d) (\\S+)";

    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    @Override
    public LogRow parse(String line) {
        try {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            String dateTimeStr = matcher.group(4);
            LocalDateTime time = LocalDateTime.parse(dateTimeStr, formatter);
            char s9 = matcher.group(8).charAt(0);
            double s11 = Double.parseDouble(matcher.group(10));
            return new LogRow(time, s9, s11);
        } catch (Exception e) {
            throw new RowParsingException(line, e);
        }
    }
}
