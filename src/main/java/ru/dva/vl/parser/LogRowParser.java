package ru.dva.vl.parser;

import ru.dva.vl.dto.LogRow;

import java.time.format.DateTimeFormatter;

public abstract class LogRowParser {

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss Z");

    public abstract LogRow parse(String line);

}
