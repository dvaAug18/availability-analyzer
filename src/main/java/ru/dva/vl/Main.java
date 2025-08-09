package ru.dva.vl;

import ru.dva.vl.dto.AnalyzerParams;
import ru.dva.vl.parser.FastLogRowParser;
import ru.dva.vl.parser.ParamsParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        AnalyzerParams params = ParamsParser.parse(args);
        Analyzer analyzer = new Analyzer(params, new FastLogRowParser());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            analyzer.process(reader);
        } catch (IOException e) {
            throw new RuntimeException("Issue reading from input stream", e);
        }
    }

}