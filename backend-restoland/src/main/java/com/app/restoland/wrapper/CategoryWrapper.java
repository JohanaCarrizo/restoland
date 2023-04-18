package com.app.restoland.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryWrapper {

    private Long id;
    private String name;

    public CategoryWrapper(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
