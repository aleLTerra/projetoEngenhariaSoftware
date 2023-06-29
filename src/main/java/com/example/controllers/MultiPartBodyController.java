package com.example.controllers;

import com.example.exceptions.StorageException;
import com.example.models.MultiPartBody;
import com.example.models.Payload;
import com.example.models.Status;
import com.example.models.dto.PayloadDTO;
import com.example.services.MessageService;
import com.example.services.PayloadService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api/v1/convert")
public class MultiPartBodyController {
    @Inject
    Logger logger;
    @Inject
    PayloadService payloadService;
    @Inject
    MessageService messageService;

    @POST
    @Transactional
    @RolesAllowed({"manager"})
    public void convertMp4ToMp3(@Valid @MultipartForm MultiPartBody data) throws Exception {
        logger.info("Starting converting mp4 to mp3 - " + data);
        var payloadToPersist = Payload.multiPartToPayload(data);

        payloadToPersist.setStatus(Status.converting);
        PayloadDTO payloadDTO = payloadService.sendObjectToStorage(payloadToPersist, data.getFile());
        messageService.send(payloadDTO);
        payloadToPersist.setBucketName(payloadDTO.getBucketName());
        payloadToPersist.persist();
        logger.info("Payload created " + payloadToPersist);
    }

    @GET
    public Response getAllPayload() {
        logger.info("Getting all payload ");
        return Response.ok(Payload.listAll()).build();
    }

}
