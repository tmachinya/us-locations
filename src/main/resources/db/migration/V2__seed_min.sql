-- V2__seed_min.sql

INSERT INTO time_zones (name) VALUES
                                  ('America/New_York'), ('America/Chicago'), ('America/Denver'),
                                  ('America/Los_Angeles'), ('America/Phoenix'), ('America/Anchorage'),
                                  ('Pacific/Honolulu')
    ON CONFLICT DO NOTHING;

INSERT INTO states (state_code, name, fips, postal_abbr, capital, area_sq_mi)
VALUES
    ('NY','New York','36','NY','Albany', 54555.00),
    ('CA','California','06','CA','Sacramento', 163695.00),
    ('TX','Texas','48','TX','Austin', 268596.00)
    ON CONFLICT DO NOTHING;

-- state -> time zones
INSERT INTO state_time_zones(state_id, time_zone_id)
SELECT s.id, tz.id
FROM states s
         JOIN time_zones tz ON tz.name IN (
    CASE s.state_code
        WHEN 'NY' THEN 'America/New_York'
        WHEN 'CA' THEN 'America/Los_Angeles'
        WHEN 'TX' THEN 'America/Chicago'
        END
    )
WHERE tz.name IS NOT NULL;

-- a few counties
INSERT INTO counties (state_id, name, county_fips, seat)
SELECT id, 'Albany County', '36001', 'Albany' FROM states WHERE state_code='NY'
UNION ALL
SELECT id, 'Los Angeles County', '06037', 'Los Angeles' FROM states WHERE state_code='CA'
UNION ALL
SELECT id, 'Travis County', '48453', 'Austin' FROM states WHERE state_code='TX';

--- a few places
INSERT INTO places (state_id, county_id, name, place_type, place_fips, latitude, longitude, elevation_ft)
SELECT s.id, c.id, 'Albany',      'CITY'::place_type, '3601000', 42.6526, -73.7562, 141
FROM states s JOIN counties c ON c.state_id=s.id AND c.name='Albany County' AND s.state_code='NY'
UNION ALL
SELECT s.id, c.id, 'Los Angeles', 'CITY'::place_type, '0644000', 34.0522, -118.2437, 305
FROM states s JOIN counties c ON c.state_id=s.id AND c.name='Los Angeles County' AND s.state_code='CA'
UNION ALL
SELECT s.id, c.id, 'Austin',      'CITY'::place_type, '4805000', 30.2672,  -97.7431, 489
FROM states s JOIN counties c ON c.state_id=s.id AND c.name='Travis County' AND s.state_code='TX';

-- population snapshots
INSERT INTO state_population_stats (state_id, year, population, source)
SELECT id, 2024, 19900000, 'Example' FROM states WHERE state_code='NY'
UNION ALL
SELECT id, 2024, 38900000, 'Example' FROM states WHERE state_code='CA'
UNION ALL
SELECT id, 2024, 30400000, 'Example' FROM states WHERE state_code='TX';

INSERT INTO place_population_stats (place_id, year, population, source)
SELECT id, 2024, 100000, 'Example' FROM places WHERE name='Albany'
UNION ALL
SELECT id, 2024, 3849000, 'Example' FROM places WHERE name='Los Angeles'
UNION ALL
SELECT id, 2024, 980000, 'Example' FROM places WHERE name='Austin';
