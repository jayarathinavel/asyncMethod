CREATE TABLE zomato_restaurants (
    restaurant_id NUMERIC,
    restaurant_name VARCHAR(255),
    city VARCHAR(255),
    address VARCHAR(255),
    rating DOUBLE PRECISION,
    PRIMARY KEY (restaurant_id)
)

CREATE TABLE zomato_restaurants_progress (
    progress_id VARCHAR(255) NOT NULL,
    message VARCHAR(255),
    PRIMARY KEY (progress_id)
)