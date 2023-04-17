--country_statistics_data
CREATE TABLE IF NOT EXISTS public.country_statistics_data
(
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL,
    distance int NOT NULL,
    invocations bigint NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL
);
