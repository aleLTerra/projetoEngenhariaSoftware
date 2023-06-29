package com.example.models;

import lombok.Data;
import org.jboss.resteasy.reactive.PartType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.File;

@Data
public class MultiPartBody {
    @FormParam("file")
    @PartType((MediaType.APPLICATION_OCTET_STREAM))
    @NotNull(message = "file must not be provided")
    private File file;

    @FormParam("userName")
    @PartType(MediaType.TEXT_PLAIN)
    @NotNull(message = "Username must not be provided")
    private String userName;

    @FormParam("notificationType")
    @PartType(MediaType.TEXT_PLAIN)
    @NotNull(message = "Notification must not be provided")
    private NotificationType notificationType;

    @FormParam("phone")
    @PartType(MediaType.TEXT_PLAIN)
    @NotNull(message = "Phone number must not be provided")
    // @Pattern(regexp = "'/^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$/'")
    private String phone;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    @NotNull(message = "filename must not be provided")
    private String fileName;

    @FormParam("email")
    @PartType(MediaType.TEXT_PLAIN)
    @Email(message = "Email must be a valid one")
    @NotNull(message = "Email must not be provided")
    private String email;

}
