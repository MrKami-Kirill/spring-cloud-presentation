package spring.cloud.config;

import static spring.cloud.util.Constants.BIRTHDAY_DATE_FORMAT;
import static spring.cloud.util.Constants.ISO_DATE_FORMAT;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        JsonSerializer<LocalDate> localDateSerializer = new JsonSerializer<>() {
            @Override
            public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern(BIRTHDAY_DATE_FORMAT)));
            }
        };
        JsonDeserializer<LocalDate> localDateDeserializer = new JsonDeserializer<>() {
            @Override
            public LocalDate deserialize(JsonParser p, DeserializationContext deserializationContext)
                    throws IOException {
                return LocalDate.parse(p.readValueAs(String.class), DateTimeFormatter.ofPattern(BIRTHDAY_DATE_FORMAT));
            }
        };
        JsonSerializer<OffsetDateTime> dateTimeSerializer = new JsonSerializer<>() {
            @Override
            public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(offsetDateTime.format(DateTimeFormatter.ofPattern(ISO_DATE_FORMAT)));
            }
        };
        JsonDeserializer<OffsetDateTime> dateTimeDeserializer = new JsonDeserializer<>() {
            @Override
            public OffsetDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                    throws IOException {
                return OffsetDateTime.parse(p.readValueAs(String.class), DateTimeFormatter.ofPattern(ISO_DATE_FORMAT));
            }
        };
        javaTimeModule.addSerializer(LocalDate.class, localDateSerializer);
        javaTimeModule.addDeserializer(LocalDate.class, localDateDeserializer);
        javaTimeModule.addSerializer(OffsetDateTime.class, dateTimeSerializer);
        javaTimeModule.addDeserializer(OffsetDateTime.class, dateTimeDeserializer);
        return new ObjectMapper().configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(javaTimeModule);
    }

}
