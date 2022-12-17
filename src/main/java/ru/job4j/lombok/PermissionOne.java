package ru.job4j.lombok;

import lombok.*;

import java.util.List;

@Data
@Builder(builderMethodName = "of")
@ToString
@Getter
public class PermissionOne {
    private int id;
    private String name;

    @Singular("accessBy")
    private List<String> rules;
}


