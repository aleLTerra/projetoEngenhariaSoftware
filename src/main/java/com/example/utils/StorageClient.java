package com.example.utils;

import io.minio.MinioClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StorageClient {


    @ConfigProperty(name = "minio.secretkey")
    private String secretKey;


    @ConfigProperty(name = "minio.username")
    private String rootname;


    @ConfigProperty(name = "minio.endpoint")
    private String endpoint;

    public MinioClient getMinioClient() {
        endpoint = "http://localhost:9000";
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(rootname, secretKey)
                .build();

        return minioClient;
    }
}
