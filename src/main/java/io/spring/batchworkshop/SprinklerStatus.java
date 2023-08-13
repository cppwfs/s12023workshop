package io.spring.batchworkshop;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record SprinklerStatus(ZonedDateTime statusDate, String state) {
}
