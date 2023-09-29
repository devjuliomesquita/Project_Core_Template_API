package juliomesquita.projectcore.persistence.common.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Audited
@MappedSuperclass
@Data
@NoArgsConstructor
@EntityListeners(AuditedPersistenceListener.class)
public class AuditedBaseEntity {

    @JsonIgnore
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    private LocalDateTime deletedAt;

    @JsonIgnore
    @CreatedBy
    @Column(updatable = false)
    private Long createdByUser;

    @JsonIgnore
    @LastModifiedBy
    private Long updatedByUser;

    @JsonIgnore
    private Long deletedByUser;
}
