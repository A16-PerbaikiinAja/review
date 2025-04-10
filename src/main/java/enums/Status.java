package enums;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String value;
    private Status(String value){
        this.value = value;
    }
}