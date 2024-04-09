create or replace function now_utc() returns timestamp as
'
    select now() at time zone ''utc'';
' language sql;

-- Рандомное число int от min до max
CREATE OR REPLACE FUNCTION gen_random_int(min integer, max integer) RETURNS integer AS
'
    SELECT round(random() * (max - min) + min)::integer;
' LANGUAGE SQL;

create or replace function gen_random_mac() returns text as
'
    begin
        return substr(md5(random()::text), 0, 16);
    end;
' language plpgsql;

create or replace function gen_test_devices(device_count integer, user_count integer) returns void as
'
    declare
        user_id record;
    begin
        if (user_count = 0) then
            return;
        end if;
        for j in 1..user_count
            loop
                select gen_random_uuid() into user_id;
                for i in 1..device_count
                    loop
                        insert
                        into devices
                        select gen_random_uuid() uuid,
                               gen_random_mac()  mac,
                               null              plant,
                               user_id           user_id,
                               now_utc()         created_date;
                    end loop;
            end loop;
    end;
' language plpgsql;

create or replace function gen_test_sensor_readings(reading_count integer) returns void as
'
    declare
        device_count int;
        device       record;
        start_time   timestamp;
        cur_time     timestamp;
    begin
        select count(*)
        from devices
        into device_count;

        if (device_count = 0) then
            return;
        end if;

        start_time = now_utc();
        for device in select *
                      from devices
            loop
                cur_time = start_time;
                for i in 1..reading_count
                    loop
                        insert into air_humidity_readings
                        select gen_random_uuid()      uuid,
                               gen_random_int(40, 60) value,
                               cur_time               timestamp,
                               device.uuid            device;

                        insert into air_temp_readings
                        select gen_random_uuid()      uuid,
                               gen_random_int(20, 25) value,
                               cur_time               timestamp,
                               device.uuid            device;

                        insert into battery_charge_readings
                        select gen_random_uuid()       uuid,
                               gen_random_int(20, 100) value,
                               cur_time                timestamp,
                               device.uuid             device;

                        insert into light_lux_readings
                        select gen_random_uuid()         uuid,
                               gen_random_int(500, 6000) value,
                               cur_time                  timestamp,
                               device.uuid               device;

                        insert
                        into soil_moisture_readings
                        select gen_random_uuid()       uuid,
                               gen_random_int(70, 100) value,
                               cur_time                timestamp,
                               device.uuid             device;

                        insert into water_reserve_readings
                        select gen_random_uuid() uuid,
                               true              enough_water,
                               cur_time          timestamp,
                               device.uuid       device;

                        cur_time = cur_time - ''30 minutes''::interval;
                    end loop;
            end loop;
    end;
' language plpgsql;

create or replace function gen_test_plants() returns void as
'
    declare
        device            record;
        plant_id          uuid;
        air_temp_min      int;
        air_temp_max      int;
        air_humidity_min  int;
        air_humidity_max  int;
        soil_moisture_min int;
        soil_moisture_max int;
        light_lux_min     int;
        light_lux_max     int;
    begin
        for device in select *
                      from devices
            loop
                select gen_random_uuid()
                into plant_id;

                select gen_random_int(0, 20)
                into air_temp_min;
                select gen_random_int(air_temp_min, 20)
                into air_temp_max;

                select gen_random_int(20, 80)
                into air_humidity_min;
                select gen_random_int(air_humidity_min, 80)
                into air_humidity_max;

                select gen_random_int(40, 100)
                into soil_moisture_min;
                select gen_random_int(soil_moisture_min, 100)
                into soil_moisture_max;

                select gen_random_int(400, 10000)
                into light_lux_min;
                select gen_random_int(light_lux_min, 10000)
                into light_lux_max;

                insert
                into plants
                values (plant_id,
                        (select gen_random_uuid()::text),
                        air_temp_min,
                        air_temp_max,
                        air_humidity_min,
                        air_humidity_max,
                        soil_moisture_min,
                        soil_moisture_max,
                        light_lux_min,
                        light_lux_max,
                        (select now_utc()),
                        null);

                insert into users_plants
                values (device.user_id, plant_id);

                update devices
                set plant = plant_id
                where uuid = device.uuid;
            end loop;
    end;
' language plpgsql;

create or replace function gen_test_data(reading_count integer = 100, user_count integer = 1,
                                         device_count integer = 1) returns void as
'
    select gen_test_devices(device_count := device_count, user_count := user_count);
    select gen_test_sensor_readings(reading_count);
    select gen_test_plants();
' language sql;
