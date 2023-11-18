-- -----------------------------------------------------
-- Table `medioambiente`.`imagen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`imagen`
(
    `id`        VARCHAR(255) NOT NULL,
    `contenido` LONGBLOB     NULL DEFAULT NULL,
    `mime`      VARCHAR(255) NULL DEFAULT NULL,
    `nombre`    VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`rol`
(
    `id`     VARCHAR(255) NOT NULL,
    `nombre` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`usuario`
(
    `id`        VARCHAR(255) NOT NULL,
    `email`     VARCHAR(255) NULL DEFAULT NULL,
    `nombre`    VARCHAR(255) NULL DEFAULT NULL,
    `password`  VARCHAR(255) NULL DEFAULT NULL,
    `imagen_id` VARCHAR(255) NULL DEFAULT NULL,
    `rol_id`    VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKawrm93te4ld1amk7ss7tfa2b2` (`imagen_id` ASC) VISIBLE,
    INDEX `FKshkwj12wg6vkm6iuwhvcfpct8` (`rol_id` ASC) VISIBLE,
    CONSTRAINT `FKawrm93te4ld1amk7ss7tfa2b2`
        FOREIGN KEY (`imagen_id`)
            REFERENCES `medioambiente`.`imagen` (`id`),
    CONSTRAINT `FKshkwj12wg6vkm6iuwhvcfpct8`
        FOREIGN KEY (`rol_id`)
            REFERENCES `medioambiente`.`rol` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`empresa`
(
    `id`         VARCHAR(255) NOT NULL,
    `usuario_id` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKs12udhh8f7taklesp1phv0ikg` (`usuario_id` ASC) VISIBLE,
    CONSTRAINT `FKs12udhh8f7taklesp1phv0ikg`
        FOREIGN KEY (`usuario_id`)
            REFERENCES `medioambiente`.`usuario` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`campana`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`campana`
(
    `id`             VARCHAR(255) NOT NULL,
    `desafio`        VARCHAR(255) NULL DEFAULT NULL,
    `descripcion`    VARCHAR(255) NULL DEFAULT NULL,
    `estado`         BIT(1)       NULL DEFAULT NULL,
    `titulo`         VARCHAR(255) NULL DEFAULT NULL,
    `imagen_id`      VARCHAR(255) NULL DEFAULT NULL,
    `fecha_creacion` DATE         NULL DEFAULT NULL,
    `fecha_final`    DATE         NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKluvpv2iiq6rt9519y1hesw24g` (`imagen_id` ASC) VISIBLE,
    CONSTRAINT `FKluvpv2iiq6rt9519y1hesw24g`
        FOREIGN KEY (`imagen_id`)
            REFERENCES `medioambiente`.`imagen` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`auspiciante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`auspiciante`
(
    `id`         VARCHAR(255) NOT NULL,
    `campana_id` VARCHAR(255) NULL DEFAULT NULL,
    `empresa_id` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKtfyy5714n4cy1ld8hf66qjh5e` (`campana_id` ASC) VISIBLE,
    INDEX `FKfdedr01vnn67jpqhcwbdocuek` (`empresa_id` ASC) VISIBLE,
    CONSTRAINT `FKfdedr01vnn67jpqhcwbdocuek`
        FOREIGN KEY (`empresa_id`)
            REFERENCES `medioambiente`.`empresa` (`id`),
    CONSTRAINT `FKtfyy5714n4cy1ld8hf66qjh5e`
        FOREIGN KEY (`campana_id`)
            REFERENCES `medioambiente`.`campana` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`subscripcion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`subscripcion`
(
    `id`             VARCHAR(255) NOT NULL,
    `fecha_creacion` DATE         NULL DEFAULT NULL,
    `campana_id`     VARCHAR(255) NULL DEFAULT NULL,
    `usuario_id`     VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKfffl9y69gte4y5nrj3q2qsy37` (`campana_id` ASC) VISIBLE,
    INDEX `FK39ghu1iytf0cy8nf7u7imrbkv` (`usuario_id` ASC) VISIBLE,
    CONSTRAINT `FK39ghu1iytf0cy8nf7u7imrbkv`
        FOREIGN KEY (`usuario_id`)
            REFERENCES `medioambiente`.`usuario` (`id`),
    CONSTRAINT `FKfffl9y69gte4y5nrj3q2qsy37`
        FOREIGN KEY (`campana_id`)
            REFERENCES `medioambiente`.`campana` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`publicacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`publicacion`
(
    `id`              VARCHAR(255) NOT NULL,
    `contenido`       VARCHAR(255) NULL DEFAULT NULL,
    `titulo`          VARCHAR(255) NULL DEFAULT NULL,
    `imagen_id`       VARCHAR(255) NULL DEFAULT NULL,
    `subscripcion_id` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKc6gdoqf1x55fgisrfq6k4oo1h` (`imagen_id` ASC) VISIBLE,
    INDEX `FK8ynq7hs76e5kyi6c6vp8290nw` (`subscripcion_id` ASC) VISIBLE,
    CONSTRAINT `FK8ynq7hs76e5kyi6c6vp8290nw`
        FOREIGN KEY (`subscripcion_id`)
            REFERENCES `medioambiente`.`subscripcion` (`id`),
    CONSTRAINT `FKc6gdoqf1x55fgisrfq6k4oo1h`
        FOREIGN KEY (`imagen_id`)
            REFERENCES `medioambiente`.`imagen` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`comentario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`comentario`
(
    `id`             VARCHAR(255) NOT NULL,
    `contenido`      VARCHAR(255) NULL DEFAULT NULL,
    `fecha_creacion` DATE         NULL DEFAULT NULL,
    `publicacion_id` VARCHAR(255) NULL DEFAULT NULL,
    `usuario_id`     VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FK3gh68jx5umvb7fm8uuv5chiwu` (`publicacion_id` ASC) VISIBLE,
    INDEX `FKixspmid2pb85o8ypsd20jakxg` (`usuario_id` ASC) VISIBLE,
    CONSTRAINT `FK3gh68jx5umvb7fm8uuv5chiwu`
        FOREIGN KEY (`publicacion_id`)
            REFERENCES `medioambiente`.`publicacion` (`id`),
    CONSTRAINT `FKixspmid2pb85o8ypsd20jakxg`
        FOREIGN KEY (`usuario_id`)
            REFERENCES `medioambiente`.`usuario` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`ganador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`ganador`
(
    `id`              VARCHAR(255) NOT NULL,
    `fecha_creacion`  DATE         NULL DEFAULT NULL,
    `empresa_id`      VARCHAR(255) NULL DEFAULT NULL,
    `subscripcion_id` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKb8xvntlbl4bbhgp0n6jhfxth1` (`empresa_id` ASC) VISIBLE,
    INDEX `FK39oa3tuk06h4gjonj084spnw6` (`subscripcion_id` ASC) VISIBLE,
    CONSTRAINT `FK39oa3tuk06h4gjonj084spnw6`
        FOREIGN KEY (`subscripcion_id`)
            REFERENCES `medioambiente`.`subscripcion` (`id`),
    CONSTRAINT `FKb8xvntlbl4bbhgp0n6jhfxth1`
        FOREIGN KEY (`empresa_id`)
            REFERENCES `medioambiente`.`empresa` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`noticia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`noticia`
(
    `id`             VARCHAR(255) NOT NULL,
    `contenido`      VARCHAR(255) NULL DEFAULT NULL,
    `fecha_creacion` DATE         NULL DEFAULT NULL,
    `titulo`         VARCHAR(255) NULL DEFAULT NULL,
    `imagen_id`      VARCHAR(255) NULL DEFAULT NULL,
    `usuario_id`     VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKq4o50dd5xw0641hmqor0roqqu` (`imagen_id` ASC) VISIBLE,
    INDEX `FK51s0hrvyltinu4gignfdoen34` (`usuario_id` ASC) VISIBLE,
    CONSTRAINT `FK51s0hrvyltinu4gignfdoen34`
        FOREIGN KEY (`usuario_id`)
            REFERENCES `medioambiente`.`usuario` (`id`),
    CONSTRAINT `FKq4o50dd5xw0641hmqor0roqqu`
        FOREIGN KEY (`imagen_id`)
            REFERENCES `medioambiente`.`imagen` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `medioambiente`.`voto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `medioambiente`.`voto`
(
    `id`             VARCHAR(255) NOT NULL,
    `fecha_creacion` DATE         NULL DEFAULT NULL,
    `publicacion_id` VARCHAR(255) NULL DEFAULT NULL,
    `usuario_id`     VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `FKkd1xj3w4ofue4qhsfnr73jpn3` (`publicacion_id` ASC) VISIBLE,
    INDEX `FKi9ulyrisn6f8ccd2lwpsbk382` (`usuario_id` ASC) VISIBLE,
    CONSTRAINT `FKi9ulyrisn6f8ccd2lwpsbk382`
        FOREIGN KEY (`usuario_id`)
            REFERENCES `medioambiente`.`usuario` (`id`),
    CONSTRAINT `FKkd1xj3w4ofue4qhsfnr73jpn3`
        FOREIGN KEY (`publicacion_id`)
            REFERENCES `medioambiente`.`publicacion` (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
