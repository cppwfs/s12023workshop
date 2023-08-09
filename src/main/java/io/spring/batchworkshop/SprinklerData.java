package io.spring.batchworkshop;

import java.time.LocalDateTime;

public class SprinklerData {

    LocalDateTime startTime;
    String reason;

    String sprinklerId;

    public LocalDateTime getStartTime() {
        System.out.println("+++++++ " + startTime);

        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        System.out.println(">>>>>>> " + startTime);
        this.startTime = startTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSprinklerId() {
        return sprinklerId;
    }

    public void setSprinklerId(String sprinklerId) {
        this.sprinklerId = sprinklerId;
    }
}
