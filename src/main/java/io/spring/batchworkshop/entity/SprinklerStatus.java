package io.spring.batchworkshop.entity;

import java.time.ZonedDateTime;

public record SprinklerStatus(ZonedDateTime statusDate, String state) {
}
