package org.example.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class GeneralConfiguration {

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, typeOfSrc, context) ->
                new JsonPrimitive(date.getTime()));

        gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer) (json, typeOfT, context) ->
                new Date(json.getAsLong())
        );
        return gsonBuilder.create();
    }
}
