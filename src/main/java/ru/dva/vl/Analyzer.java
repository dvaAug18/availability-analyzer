package ru.dva.vl;

import lombok.RequiredArgsConstructor;
import ru.dva.vl.dto.AnalyzerParams;
import ru.dva.vl.dto.LogRow;
import ru.dva.vl.exception.RowParsingException;
import ru.dva.vl.parser.LogRowParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@RequiredArgsConstructor
public class Analyzer {

    private final AnalyzerParams params;
    private final LogRowParser parser;

    private final Duration windowDuration = Duration.of(1, ChronoUnit.SECONDS);

    private AvailabilityInterval unavailable = new AvailabilityInterval();
    private AvailabilityInterval window = new AvailabilityInterval();

    public void process(BufferedReader reader) throws IOException {
        long t = System.currentTimeMillis();

        String line;
        while ((line = reader.readLine()) != null) {
            try {
                LogRow row = parser.parse(line);
                window.initIntervalIfNecessary(row.time(), windowDuration);
                if (!window.end.isAfter(row.time())) {
                    if (window.calc() < params.availability()) {
                        unavailable.merge(window);
                    } else {
                        unavailable.printIfInit();
                        unavailable = new AvailabilityInterval();
                    }
                    window = new AvailabilityInterval();
                    window.initIntervalIfNecessary(row.time(), windowDuration);
                }
                window.check(row);
            } catch (RowParsingException ex) {
                if (params.debug()) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        if (params.debug()) {
            System.out.printf("Total time: %s ms%n", System.currentTimeMillis() - t);
        }
    }

    private class AvailabilityInterval {
        private final static DateTimeFormatter formater = DateTimeFormatter.ofPattern("HH:mm:ss");

        Long success = 0L;
        Long total = 0L;
        LocalDateTime begin = null;
        LocalDateTime end = null;

        void check(LogRow row) {
            total++;
            if (row.httpStatusStartWith() != '5' && row.duration() <= params.timeout()) {
                success++;
            }
        }

        double calc() {
            return  100.0 * success / total;
        }

        void merge(AvailabilityInterval other) {
            if (begin == null) {
                begin = other.begin;
            }
            end = other.end;
            success += other.success;
            total += other.total;
        }

        void printIfInit() {
            if (begin != null) {
                System.out.format("%s %s %.1f\n", formater.format(begin), formater.format(end), unavailable.calc());
            }
        }

        public void initIntervalIfNecessary(LocalDateTime begin, Duration duration) {
            if (this.begin == null) {
                this.begin = begin;
                end = begin.plus(duration);
            }
        }
    }
}
