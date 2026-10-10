
package org.ong.dryforest.shared.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.enums.Locale;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
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

    @Column(nullable = false, updatable = false)
    @CreatedDate
    protected Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    protected Instant updatedAt;
}
