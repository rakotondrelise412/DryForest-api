package org.ong.dryforest.shared.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.enums.Locale;

import java.io.Serializable;

@MappedSuperclass
@ToString
@Getter
@Setter
public abstract class AbstractTranslation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected @Nullable Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    protected Locale locale;
}
