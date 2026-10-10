
package org.ong.dryforest.shared.base;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MadagascarDateTimeSerializer
        extends ValueSerializer<Instant> {

    private static final ZoneId MADAGASCAR_ZONE =
            ZoneId.of("Indian/Antananarivo");

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(
            Instant instant,
            JsonGenerator generator,
            SerializationContext context
    ) throws JacksonException {

        if (instant == null) {
            generator.writeNull();
            return;
        }

        String value = instant
                .atZone(MADAGASCAR_ZONE)
                .format(FORMATTER);

        generator.writeString(value);
    }
}
