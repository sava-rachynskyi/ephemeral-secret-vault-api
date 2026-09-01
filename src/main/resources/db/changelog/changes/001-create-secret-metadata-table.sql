--liquibase formatted sql

--changeset savarachynskyi:001-create-secret-metadata-table
CREATE TABLE secret_metadata (
                                 id UUID PRIMARY KEY,
                                 created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                 expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                 is_read BOOLEAN NOT NULL DEFAULT FALSE
);

--rollback DROP TABLE secret_metadata;