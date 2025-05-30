DROP TABLE IF EXISTS "battles";

CREATE TABLE "battles" (
    "id" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "battleyear" INTEGER
);

INSERT INTO "battles" ("name", "battleyear") VALUES
('TEST Battle of Helm''s Deep', 3019),
('TEST Battle of Pelennor Fields', 3019),
('TEST Battle of Five Armies', 2941),
('TEST battle', 1234);