DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id         UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    user_id    INT,
    product_id INT,
    price      NUMERIC(10, 2),
    status     VARCHAR(50)
);
