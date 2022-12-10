package ru.job4j.models;

import lombok.*;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    @NonNull
    @EqualsAndHashCode.Include
    private String name;

    @Getter
    @Setter
    @NonNull
    private String description;

    @Getter
    @Setter
    @NonNull
    private long created;



    public Item(@NonNull String name) {
        this.name = name;
    }

    public Item(@NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
    }

    public Item(@NonNull String name, @NonNull String description, @NonNull long created) {
        this.name = name;
        this.description = description;
        this.created = created;
    }
}



