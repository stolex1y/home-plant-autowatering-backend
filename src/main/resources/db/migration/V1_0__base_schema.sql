CREATE TABLE "plants"
(
    "uuid"              uuid PRIMARY KEY,
    "name"              text      NOT NULL,
    "air_temp_min"      real,
    "air_temp_max"      real,
    "air_humidity_min"  real,
    "air_humidity_max"  real,
    "soil_moisture_min" real,
    "light_level_min"   int,
    "light_level_max"   int,
    "created_date"      timestamp NOT NULL DEFAULT (now()),
    "updated_date"      timestamp NOT NULL DEFAULT (now()),
    "photo"             uuid
);

CREATE TABLE "users_plants"
(
    "user_id" text NOT NULL,
    "plant"   uuid NOT NULL,
    PRIMARY KEY ("user_id", "plant")
);

CREATE TABLE "devices"
(
    "name"         text        NOT NULL,
    "uuid"         uuid PRIMARY KEY,
    "mac"          varchar(17) NOT NULL,
    "plant"        uuid,
    "user_id"      text        NOT NULL,
    "created_date" timestamp   NOT NULL DEFAULT (now()),
    "updated_date" timestamp   NOT NULL DEFAULT (now()),
    "photo"        uuid
);

CREATE TABLE "photos"
(
    "uuid"         uuid PRIMARY KEY,
    "photo"        bytea     NOT NULL,
    "created_date" timestamp NOT NULL DEFAULT (now()),
    "updated_date" timestamp NOT NULL DEFAULT (now())
);

CREATE TABLE "air_humidity_readings"
(
    "reading"   real      NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL,
    PRIMARY KEY ("device", "timestamp")
);

CREATE TABLE "air_temp_readings"
(
    "reading"   real      NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL,
    PRIMARY KEY ("device", "timestamp")
);

CREATE TABLE "battery_charge_readings"
(
    "reading"   real      NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL,
    PRIMARY KEY ("device", "timestamp")
);

CREATE TABLE "light_level_readings"
(
    "reading"   int       NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL,
    PRIMARY KEY ("device", "timestamp")
);

CREATE TABLE "soil_moisture_readings"
(
    "reading"   real      NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL,
    PRIMARY KEY ("device", "timestamp")
);

CREATE TABLE "water_reserve_readings"
(
    "reading"   real      NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL,
    PRIMARY KEY ("device", "timestamp")
);

CREATE UNIQUE INDEX ON "plants" ("photo");

CREATE INDEX ON "devices" ("plant");

CREATE UNIQUE INDEX ON "devices" ("mac", "user_id");

CREATE UNIQUE INDEX ON "devices" ("photo");

ALTER TABLE "users_plants"
    ADD FOREIGN KEY ("plant") REFERENCES "plants" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "devices"
    ADD FOREIGN KEY ("plant") REFERENCES "plants" ("uuid") ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE "plants"
    ADD FOREIGN KEY ("photo") REFERENCES "photos" ("uuid") ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE "devices"
    ADD FOREIGN KEY ("photo") REFERENCES "photos" ("uuid") ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE "air_humidity_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "air_temp_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "battery_charge_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "light_level_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "soil_moisture_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "water_reserve_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;
