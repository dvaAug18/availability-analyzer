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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String line;
            long t = System.currentTimeMillis();
            while ((line = br.readLine()) != null) {
                analyzer.process(line);
            }
            if (params.debug()) {
                System.out.printf("Total time: %s ms%n", System.currentTimeMillis() - t);
            }
        } catch (IOException e) {
            throw new RuntimeException("Issue reading from input stream", e);
        }
    }
}