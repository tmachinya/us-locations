-- V1__init.sql

-- reference: time zones (IANA ids)
CREATE TABLE time_zones (
                            id SERIAL PRIMARY KEY,
                            name TEXT UNIQUE NOT NULL  -- e.g., 'America/New_York'
);

-- states
CREATE TABLE states (
                        id SERIAL PRIMARY KEY,
                        state_code CHAR(2) NOT NULL UNIQUE,   -- e.g., 'NY'
                        name CITEXT NOT NULL UNIQUE,          -- 'New York'
                        fips CHAR(2) NOT NULL UNIQUE,         -- '36'
                        postal_abbr CHAR(2) NOT NULL UNIQUE,  -- duplicate of state_code if you like
                        capital CITEXT,                       -- 'Albany'
                        area_sq_mi NUMERIC(12,2),             -- total area
                        population_est BIGINT,                -- latest snapshot (optional)
                        created_at TIMESTAMP NOT NULL DEFAULT now(),
                        updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- states <-> time_zones (many-to-many)
CREATE TABLE state_time_zones (
                                  state_id INT NOT NULL REFERENCES states(id) ON DELETE CASCADE,
                                  time_zone_id INT NOT NULL REFERENCES time_zones(id) ON DELETE CASCADE,
                                  PRIMARY KEY (state_id, time_zone_id)
);

-- counties
CREATE TABLE counties (
                          id SERIAL PRIMARY KEY,
                          state_id INT NOT NULL REFERENCES states(id) ON DELETE CASCADE,
                          name CITEXT NOT NULL,
                          county_fips CHAR(5) NOT NULL UNIQUE,   -- state(2)+county(3)
                          seat CITEXT,                           -- county seat
                          created_at TIMESTAMP NOT NULL DEFAULT now(),
                          updated_at TIMESTAMP NOT NULL DEFAULT now(),
                          UNIQUE (state_id, name)                -- no dup county names within a state
);

-- places (cities/towns/CDPs/boroughs)
CREATE TYPE place_type AS ENUM ('CITY','TOWN','VILLAGE','CDP','BOROUGH','OTHER');

CREATE TABLE places (
                        id SERIAL PRIMARY KEY,
                        state_id INT NOT NULL REFERENCES states(id) ON DELETE CASCADE,
                        county_id INT REFERENCES counties(id) ON DELETE SET NULL,  -- some places span counties; you can create a junction later if needed
                        name CITEXT NOT NULL,
                        place_type place_type NOT NULL,
                        place_fips CHAR(7),                  -- optional but recommended (state(2)+place(5)); may be null for very small entities
                        latitude NUMERIC(9,6),
                        longitude NUMERIC(9,6),
                        elevation_ft INT,
                        created_at TIMESTAMP NOT NULL DEFAULT now(),
                        updated_at TIMESTAMP NOT NULL DEFAULT now(),
                        UNIQUE (state_id, name, place_type)
);

-- population stats (yearly)
CREATE TABLE state_population_stats (
                                        id SERIAL PRIMARY KEY,
                                        state_id INT NOT NULL REFERENCES states(id) ON DELETE CASCADE,
                                        year INT NOT NULL,
                                        population BIGINT NOT NULL,
                                        source TEXT,                               -- e.g., 'Census Bureau'
                                        created_at TIMESTAMP NOT NULL DEFAULT now(),
                                        UNIQUE (state_id, year)
);

CREATE TABLE place_population_stats (
                                        id SERIAL PRIMARY KEY,
                                        place_id INT NOT NULL REFERENCES places(id) ON DELETE CASCADE,
                                        year INT NOT NULL,
                                        population BIGINT NOT NULL,
                                        source TEXT,
                                        created_at TIMESTAMP NOT NULL DEFAULT now(),
                                        UNIQUE (place_id, year)
);

-- climate stats (monthly averages per year or climatology normal)
CREATE TYPE climate_aggregate AS ENUM ('MONTHLY','YEARLY');

CREATE TABLE state_climate_stats (
                                     id SERIAL PRIMARY KEY,
                                     state_id INT NOT NULL REFERENCES states(id) ON DELETE CASCADE,
                                     aggregate climate_aggregate NOT NULL,  -- MONTHLY/YEARLY
                                     year INT,                              -- null if using 30-yr normals
                                     month SMALLINT,                        -- 1..12 required for MONTHLY
                                     avg_temp_f NUMERIC(5,2),
                                     avg_precip_in NUMERIC(6,2),
                                     avg_snow_in NUMERIC(6,2),
                                     source TEXT,
                                     created_at TIMESTAMP NOT NULL DEFAULT now(),
                                     CHECK ((aggregate='MONTHLY' AND month BETWEEN 1 AND 12) OR (aggregate='YEARLY' AND month IS NULL))
);

CREATE TABLE place_climate_stats (
                                     id SERIAL PRIMARY KEY,
                                     place_id INT NOT NULL REFERENCES places(id) ON DELETE CASCADE,
                                     aggregate climate_aggregate NOT NULL,
                                     year INT,
                                     month SMALLINT,
                                     avg_temp_f NUMERIC(5,2),
                                     avg_precip_in NUMERIC(6,2),
                                     avg_snow_in NUMERIC(6,2),
                                     source TEXT,
                                     created_at TIMESTAMP NOT NULL DEFAULT now(),
                                     CHECK ((aggregate='MONTHLY' AND month BETWEEN 1 AND 12) OR (aggregate='YEARLY' AND month IS NULL))
);

-- helpful indexes for fast lookup/search
CREATE INDEX idx_states_name_trgm ON states USING gin (name gin_trgm_ops);
CREATE INDEX idx_counties_name_trgm ON counties USING gin (name gin_trgm_ops);
CREATE INDEX idx_places_name_trgm ON places USING gin (name gin_trgm_ops);
CREATE INDEX idx_places_state ON places(state_id);
CREATE INDEX idx_counties_state ON counties(state_id);

-- triggers to auto-update updated_at
CREATE OR REPLACE FUNCTION set_updated_at() RETURNS trigger AS $$
BEGIN NEW.updated_at = now(); RETURN NEW; END; $$ LANGUAGE plpgsql;

CREATE TRIGGER tg_states_updated  BEFORE UPDATE ON states  FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER tg_counties_updated BEFORE UPDATE ON counties FOR EACH ROW EXECUTE FUNCTION set_updated_at();
CREATE TRIGGER tg_places_updated  BEFORE UPDATE ON places  FOR EACH ROW EXECUTE FUNCTION set_updated_at();
