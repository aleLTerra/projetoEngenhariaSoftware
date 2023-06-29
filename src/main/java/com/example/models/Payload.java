package com.example.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload extends PanacheEntity {
    public String userName;
    public NotificationType notificationType;
    public String phone;
    public String fileName;
    public String email;
    public String bucketName;
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    public Date createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    public Date updatedAt;
    public Status status;

    public static Payload multiPartToPayload(MultiPartBody multiPartiBody) {
        return Payload.builder()
                .userName(multiPartiBody.getUserName())
                .notificationType(multiPartiBody.getNotificationType())
                .phone(multiPartiBody.getPhone())
                .fileName(multiPartiBody.getFileName())
                .email(multiPartiBody.getEmail())
                .build();
    }

}
