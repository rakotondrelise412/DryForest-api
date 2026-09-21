package org.ong.dryforest.modules.blacklist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "blacklisted_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlacklistedToken extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime blacklistedAt;
}
