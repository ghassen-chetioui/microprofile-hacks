package io.github.cgh;

import org.eclipse.microprofile.config.spi.Converter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class DukeConverter implements Converter<Duke> {

    @Override
    public Duke convert(String value) {
        try (JsonReader reader = Json.createReader(new StringReader(value))) {
            JsonObject jsonObject = reader.readObject();
            return new Duke(jsonObject.getString("name"), jsonObject.getInt("age"));
        }
    }
}
