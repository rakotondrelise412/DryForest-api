package org.ong.dryforest.shared.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@ToString
@Getter
@Setter
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected @Nullable Long id;

    @Column(nullable = false)
    @CreatedDate
    protected Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    protected Instant updatedAt;
}
