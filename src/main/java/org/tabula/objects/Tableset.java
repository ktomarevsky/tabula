package org.tabula.objects;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tableset implements Serializable {

    private String name;
    private String tables;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTables() {
        return tables;
    }

    public String getFormattedTables() {
        String result = Stream.of(tables.split(" ")).filter(value -> {return !value.isEmpty();}).distinct().collect(Collectors.joining("', '"));
        return "'" + result + "'";
    }

    public void setTables(String tables) {
        this.tables = tables;
    }
}
