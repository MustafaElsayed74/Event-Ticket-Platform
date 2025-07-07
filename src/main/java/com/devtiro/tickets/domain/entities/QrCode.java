package com.devtiro.tickets.domain.entities;

import com.devtiro.tickets.domain.enums.QrCodeStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "qr_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrCode {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private QrCodeStatusEnum status;

    @Column(name = "value", columnDefinition = "TEXT", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QrCode qrCode = (QrCode) o;
        return Objects.equals(id, qrCode.id) && status == qrCode.status && Objects.equals(value,
                qrCode.value) && Objects.equals(createdAt, qrCode.createdAt) && Objects.equals(updatedAt,
                qrCode.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, value, createdAt, updatedAt);
    }
}
