package io.github.cgh;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;

@Path("config")
@RequestScoped
public class ConfigResource {

    @Inject
    @ConfigProperty(name = "message", defaultValue = "This is the default message!")
    String message;

    @Inject
    @ConfigProperty(name = "config1")
    String configFromDatabase;

    @Inject
    @ConfigProperty(name = "duke")
    Duke duke;

    @Inject
    Config config;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response config() {
        JsonArray sources = StreamSupport.stream(config.getConfigSources().spliterator(), false)
                .map(source -> Json.createObjectBuilder()
                        .add("name", source.getName())
                        .add("ordinal", source.getOrdinal()).build()
                ).collect(Collector.of(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)).build();

        JsonObject result = Json.createObjectBuilder()
                .add("injectedConfigProperty", message)
                .add("configFromDb", configFromDatabase)
                .add("convertedProperty", duke.toString())
                .add("sources", sources)
                .build();
        return Response.ok(result).build();
    }

}