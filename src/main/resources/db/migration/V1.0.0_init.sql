CREATE TABLE hibernate_sequence (next_val BIGINT) engine = InnoDB;

INSERT INTO hibernate_sequence values (1);

CREATE TABLE Payload (
    id BIGINT NOT NULL,
    bucketName VARCHAR(255),
    contentType VARCHAR(255),
    createdAt DATETIME(6),
    email VARCHAR(255),
    fileName VARCHAR(255),
    notificationType INTEGER,
    phone VARCHAR(255),
    status INTEGER,
    updateAt DATETIME(6),
    username VARCHAR(255),
    PRIMARY KEY (id)
) engine = InnoDB;