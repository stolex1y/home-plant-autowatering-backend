-- Рандомное число int от min до max
CREATE OR REPLACE FUNCTION gen_random_int(min integer = 0, max integer = 100) RETURNS integer AS
'
    SELECT round(random() * (max - min) + min)::integer;
' LANGUAGE SQL;

create or replace function gen_random_mac() returns text as
'
    begin
        return substr(md5(random()::text), 0, 16);
    end;
' language plpgsql;

create or replace function gen_test_devices(device_count integer = 20, user_count integer = 10) returns void as
'
    declare
        user_id         record;
    begin
        if (user_count = 0) then
            return;
        end if;
        for j in 1..user_count
            loop
                select gen_random_uuid() into user_id;
                for i in 1..device_count
                    loop
                        insert into devices
                        select gen_random_uuid() uuid,
                               gen_random_mac()  mac,
                               null              plant,
                               user_id           user_id,
                               now()             created_date;
                    end loop;
            end loop;
    end;
' language plpgsql;

create or replace function gen_test_sensor_readings(reading_count integer = 10000) returns void as
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
        start_time = now();
        for device in
            select *
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

                        insert into soil_moisture_readings
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

create or replace function gen_test_data(reading_count integer = 100, user_count integer = 1,
                                                    device_count integer = 1) returns void as
'
    select gen_test_devices(device_count);
    select gen_test_sensor_readings(reading_count);
' language sql;
