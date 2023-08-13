package io.spring.batchworkshop.entity;

import java.time.ZonedDateTime;

public record WeatherData(ZonedDateTime weatherTime, Double prediction, Double rainMeasured) {
}
