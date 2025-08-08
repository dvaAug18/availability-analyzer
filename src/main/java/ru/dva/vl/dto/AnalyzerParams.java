package ru.dva.vl.dto;

import java.time.Duration;

public record AnalyzerParams(double availability, Duration time, boolean debug) {
}
