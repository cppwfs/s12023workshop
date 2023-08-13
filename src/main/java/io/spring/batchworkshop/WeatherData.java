package io.spring.batchworkshop;

import java.time.ZonedDateTime;

public record WeatherData(ZonedDateTime weatherTime, Double prediction, Double rainMeasured) {
}
