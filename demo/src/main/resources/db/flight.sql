CREATE TABLE flight (
    id SERIAL PRIMARY KEY,
    flight_number VARCHAR(10) NOT NULL,
    airline VARCHAR(50) NOT NULL,
    origin VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('Scheduled', 'On Time', 'Delayed', 'Cancelled', 'Landed')),
    gate VARCHAR(10),
    updated_at DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
);
