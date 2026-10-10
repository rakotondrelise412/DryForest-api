
package org.ong.dryforest.shared.base;

import tools.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractTimestamps implements Serializable {

    @JsonSerialize(using = MadagascarDateTimeSerializer.class)
    private Instant createdAt;

    @JsonSerialize(using = MadagascarDateTimeSerializer.class)
    private Instant updatedAt;
}
