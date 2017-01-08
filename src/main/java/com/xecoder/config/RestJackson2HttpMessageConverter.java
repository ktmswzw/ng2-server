package com.xecoder.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.io.IOException;
import java.util.LinkedHashMap;

public class RestJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    public RestJackson2HttpMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().build());
    }

    public RestJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, new MediaType("application", "json", DEFAULT_CHARSET),
                new MediaType("application", "*+json", DEFAULT_CHARSET));
    }

    @Override
    protected void writePrefix(JsonGenerator generator, Object object) throws IOException {
        int status = 1;
        if (((LinkedHashMap) object).get("status") == null || ((LinkedHashMap) object).get("status").equals(404))
            status = 0;
        String prefix = "{\"apistatus\":" + status + ",\"result\":";
        generator.writeRaw(prefix);
        String jsonpFunction =
                (object instanceof MappingJacksonValue ? ((MappingJacksonValue) object).getJsonpFunction() : null);
        if (jsonpFunction != null) {
            generator.writeRaw(jsonpFunction + "(");
        }
    }

    @Override
    protected void writeSuffix(JsonGenerator generator, Object object) throws IOException {
        String jsonpFunction =
                (object instanceof MappingJacksonValue ? ((MappingJacksonValue) object).getJsonpFunction() : null);
        if (jsonpFunction != null) {
            generator.writeRaw(");");
        }
        String suffix = "}";
        generator.writeRaw(suffix);
    }
}
