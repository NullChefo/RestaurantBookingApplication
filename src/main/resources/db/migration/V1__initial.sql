CREATE SEQUENCE IF NOT EXISTS booking_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS cuisine_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS delivery_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS donation_recipient_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS donation_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS driver_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS location_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS menu_item_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS menu_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS notification_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS order_item_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS orders_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS payment_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS promotion_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS restaurant_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS review_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS schedule_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE booking
(
    id                   BIGINT   NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE,
    updated_at           TIMESTAMP WITHOUT TIME ZONE,
    restaurant_id        BIGINT,
    user_id              BIGINT,
    customer_information VARCHAR(255),
    date                 TIMESTAMP WITHOUT TIME ZONE,
    pre_order_meal_id    BIGINT,
    reserved_seats       SMALLINT NOT NULL,
    notes                VARCHAR(255),
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE TABLE cuisine
(
    id            BIGINT NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    restaurant_id BIGINT,
    name          VARCHAR(255),
    description   VARCHAR(255),
    CONSTRAINT pk_cuisine PRIMARY KEY (id)
);

CREATE TABLE customer_favorite_restaurants
(
    customer_id   BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL
);

CREATE TABLE delivery
(
    id              BIGINT NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    order_id        BIGINT,
    driver_id       BIGINT,
    location        VARCHAR(255),
    notes           VARCHAR(255),
    delivery_status SMALLINT,
    CONSTRAINT pk_delivery PRIMARY KEY (id)
);

CREATE TABLE donation
(
    id                       BIGINT  NOT NULL,
    created_at               TIMESTAMP WITHOUT TIME ZONE,
    updated_at               TIMESTAMP WITHOUT TIME ZONE,
    donation_time            TIMESTAMP WITHOUT TIME ZONE,
    donated_food_description VARCHAR(255),
    quantity                 INTEGER NOT NULL,
    restaurant_id            BIGINT,
    donation_recipient_id    BIGINT,
    CONSTRAINT pk_donation PRIMARY KEY (id)
);

CREATE TABLE donation_recipient
(
    id          BIGINT NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    name        VARCHAR(255),
    donation_id BIGINT,
    CONSTRAINT pk_donationrecipient PRIMARY KEY (id)
);

CREATE TABLE driver
(
    id                  BIGINT NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE,
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    name                VARCHAR(255),
    contact_information VARCHAR(255),
    vehicle_details     VARCHAR(255),
    CONSTRAINT pk_driver PRIMARY KEY (id)
);

CREATE TABLE driver_deliveries
(
    driver_id     BIGINT NOT NULL,
    deliveries_id BIGINT NOT NULL
);

CREATE TABLE location
(
    id            BIGINT NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    restaurant_id BIGINT,
    address       VARCHAR(255),
    latitude      VARCHAR(255),
    longitude     VARCHAR(255),
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE menu
(
    id            BIGINT NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    restaurant_id BIGINT,
    name          VARCHAR(255),
    description   VARCHAR(255),
    CONSTRAINT pk_menu PRIMARY KEY (id)
);

CREATE TABLE menu_item
(
    id          BIGINT NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    menu_id     BIGINT,
    name        VARCHAR(255),
    description VARCHAR(255),
    ingredients VARCHAR(255),
    price       FLOAT  NOT NULL,
    CONSTRAINT pk_menuitem PRIMARY KEY (id)
);

CREATE TABLE notification
(
    id                BIGINT NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    customer_id       BIGINT,
    content           VARCHAR(255),
    notification_type SMALLINT,
    date              TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

CREATE TABLE order_item
(
    id               BIGINT   NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    order_id         BIGINT,
    menu_item_id     BIGINT,
    note             VARCHAR(255),
    price_withoutvat DECIMAL,
    vatpercentage    SMALLINT NOT NULL,
    CONSTRAINT pk_orderitem PRIMARY KEY (id)
);

CREATE TABLE order_menu_items
(
    menu_item_id BIGINT NOT NULL,
    order_id     BIGINT NOT NULL
);

CREATE TABLE orders
(
    id            BIGINT NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    customer_id   BIGINT,
    restaurant_id BIGINT,
    ordered_at    TIMESTAMP WITHOUT TIME ZONE,
    total         DECIMAL,
    order_status  SMALLINT,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id             BIGINT NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    customer_id    BIGINT,
    payment_status SMALLINT,
    payment_method SMALLINT,
    total          DECIMAL,
    date_time      TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_payment PRIMARY KEY (id)
);

CREATE TABLE promotion
(
    id              BIGINT NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    restaurant_id   BIGINT,
    discount        VARCHAR(255),
    expiration_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_promotion PRIMARY KEY (id)
);

CREATE TABLE restaurant
(
    id          BIGINT NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    name        VARCHAR(255),
    location_id BIGINT,
    CONSTRAINT pk_restaurant PRIMARY KEY (id)
);

CREATE TABLE review
(
    id            BIGINT   NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    restaurant_id BIGINT,
    customer_id   BIGINT,
    rating        SMALLINT NOT NULL,
    date          TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE schedule
(
    id            BIGINT NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    restaurant_id BIGINT,
    CONSTRAINT pk_schedule PRIMARY KEY (id)
);

CREATE TABLE users
(
    id             BIGINT       NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    locations      VARCHAR(255),
    name           VARCHAR(256) NOT NULL,
    password       VARCHAR(60)  NOT NULL,
    email          VARCHAR(256) NOT NULL,
    is_active      BOOLEAN,
    last_logged_at TIMESTAMP WITHOUT TIME ZONE,
    phone          VARCHAR(30),
    address        VARCHAR(256),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_PREORDERMEAL FOREIGN KEY (pre_order_meal_id) REFERENCES orders (id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE cuisine
    ADD CONSTRAINT FK_CUISINE_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE delivery
    ADD CONSTRAINT FK_DELIVERY_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

ALTER TABLE delivery
    ADD CONSTRAINT FK_DELIVERY_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE donation_recipient
    ADD CONSTRAINT FK_DONATIONRECIPIENT_ON_DONATION FOREIGN KEY (donation_id) REFERENCES donation (id);

ALTER TABLE donation
    ADD CONSTRAINT FK_DONATION_ON_DONATIONRECIPIENT FOREIGN KEY (donation_recipient_id) REFERENCES donation_recipient (id);

ALTER TABLE donation
    ADD CONSTRAINT FK_DONATION_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE location
    ADD CONSTRAINT FK_LOCATION_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE menu_item
    ADD CONSTRAINT FK_MENUITEM_ON_MENU FOREIGN KEY (menu_id) REFERENCES menu (id);

ALTER TABLE menu
    ADD CONSTRAINT FK_MENU_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES users (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_MENU_ITEM FOREIGN KEY (menu_item_id) REFERENCES menu_item (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDERITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES users (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE payment
    ADD CONSTRAINT FK_PAYMENT_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES users (id);

ALTER TABLE promotion
    ADD CONSTRAINT FK_PROMOTION_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE restaurant
    ADD CONSTRAINT FK_RESTAURANT_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES users (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE customer_favorite_restaurants
    ADD CONSTRAINT fk_cusfavres_on_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE customer_favorite_restaurants
    ADD CONSTRAINT fk_cusfavres_on_user FOREIGN KEY (customer_id) REFERENCES users (id);

ALTER TABLE driver_deliveries
    ADD CONSTRAINT fk_dridel_on_delivery FOREIGN KEY (deliveries_id) REFERENCES delivery (id);

ALTER TABLE driver_deliveries
    ADD CONSTRAINT fk_dridel_on_driver FOREIGN KEY (driver_id) REFERENCES driver (id);

ALTER TABLE order_menu_items
    ADD CONSTRAINT fk_ordmenite_on_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item (id);

ALTER TABLE order_menu_items
    ADD CONSTRAINT fk_ordmenite_on_order FOREIGN KEY (order_id) REFERENCES orders (id);
