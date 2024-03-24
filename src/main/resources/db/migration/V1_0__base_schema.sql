CREATE TABLE "plants"
(
    "uuid"              uuid PRIMARY KEY,
    "name"              text      NOT NULL,
    "air_temp_min"      real,
    "air_temp_max"      real,
    "air_humidity_min"  real,
    "air_humidity_max"  real,
    "soil_moisture_min" real,
    "soil_moisture_max" real,
    "light_lux_min"     int,
    "light_lux_max"     int,
    "created_date"      timestamp NOT NULL DEFAULT (now())
);

CREATE TABLE "users_plants"
(
    "user_id" text NOT NULL,
    "plant"   uuid NOT NULL,
    PRIMARY KEY ("user_id", "plant")
);

CREATE TABLE "devices"
(
    "uuid"         uuid PRIMARY KEY,
    "mac"          varchar(17) NOT NULL,
    "plant"        uuid,
    "user_id"      text        NOT NULL,
    "created_date" timestamp   NOT NULL DEFAULT (now())
);

CREATE TABLE "air_humidity_readings"
(
    "uuid"      uuid PRIMARY KEY,
    "reading"   real      NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL
);

CREATE TABLE "air_temp_readings"
(
    "uuid"      uuid PRIMARY KEY,
    "reading"   real      NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL
);

CREATE TABLE "battery_charge_readings"
(
    "uuid"      uuid PRIMARY KEY,
    "reading"   real      NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL
);

CREATE TABLE "light_lux_readings"
(
    "uuid"      uuid PRIMARY KEY,
    "reading"   int       NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL
);

CREATE TABLE "soil_moisture_readings"
(
    "uuid"      uuid PRIMARY KEY,
    "reading"   real      NOT NULL,
    "timestamp" timestamp NOT NULL DEFAULT (now()),
    "device"    uuid      NOT NULL
);

CREATE TABLE "water_reserve_readings"
(
    "uuid"         uuid PRIMARY KEY,
    "enough_water" bool      NOT NULL,
    "timestamp"    timestamp NOT NULL DEFAULT (now()),
    "device"       uuid      NOT NULL
);

CREATE UNIQUE INDEX ON "devices" ("plant");

CREATE UNIQUE INDEX ON "devices" ("mac", "user_id");

CREATE INDEX ON "air_humidity_readings" ("timestamp");

CREATE INDEX ON "air_temp_readings" ("timestamp");

CREATE INDEX ON "battery_charge_readings" ("timestamp");

CREATE INDEX ON "light_lux_readings" ("timestamp");

CREATE INDEX ON "soil_moisture_readings" ("timestamp");

CREATE INDEX ON "water_reserve_readings" ("timestamp");

ALTER TABLE "users_plants"
    ADD FOREIGN KEY ("plant") REFERENCES "plants" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "devices"
    ADD FOREIGN KEY ("plant") REFERENCES "plants" ("uuid") ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE "air_humidity_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "air_temp_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "battery_charge_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "light_lux_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "soil_moisture_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "water_reserve_readings"
    ADD FOREIGN KEY ("device") REFERENCES "devices" ("uuid") ON DELETE CASCADE ON UPDATE CASCADE;
