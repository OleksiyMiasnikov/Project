USE `cash_register`;

INSERT INTO `role` (`id`, `name`)
VALUES
(1, 'admin'),
(2, 'cashier'),
(3, 'senior cashier'),
(4, 'commodity expert');


INSERT INTO `user` (`id`, `login`, `email`, `password`, `role_id`)
VALUES
(1, 'admin', 'admin@admin', 'admin', 1),
(2, 'Alex', 'alex@alex', '1111', 1),
(3, 'Bob', 'b@b', '2222', 2),
(4, 'Clode', 'c@c', '3333', 3),
(5, 'Den', 'd@d', '4444', 4);

INSERT INTO `unit` (`name`)
VALUES
('KG'),
('PCS');

INSERT INTO `in_out` (`value`)
VALUES
('IN'),
('OUT');


INSERT INTO `product` (`id`, `name`, `unit_name`, `price`)
VALUES
(1, 'мінеральна вода 1л','PCS', 15.50),
(2, 'чай Greenfield','PCS', 160.00),
(3, 'цукор','KG', 25.00),
(4, 'сосиски', 'KG', 120.00),
(5, 'молоко 1л', 'PCS', 35.80),
(6,'cellphone', 'PCS', 1023.56),
(7,'glasses', 'PCS', 25.38),
(8,'кілька в томаті 240гр', 'PCS', 60.0),
(9,'сир Cheddar 1.033', 'PCS', 400.51),
(10,'Кава в зернах Lavazza', 'PCS', 959.0),
(11,'pencil', 'PCS', 15.45),
(12,'пиво', 'PCS', 31.0),
(13,'сіль', 'KG', 20.0),
(14,'олія Олейна 1,0 л', 'PCS', 60.35),
(15,'шампунь', 'PCS', 45.88);

INSERT INTO `warehouse` (`id`, `quantity`, `product_id`)
VALUES
(1, 30, 1),
(2, 5, 2),
(3, 40, 3),
(4, 10, 4),
(5, 20, 5),
(6, 30, 6),
(7, 5, 7),
(8, 40, 8),
(9, 10, 9),
(10, 20, 10),
(11, 30, 11),
(12, 5, 12),
(13, 40, 13),
(14, 10, 14);

INSERT INTO `order` (`id`, `user_id`, `time`, `totalAmount`, `direction`)
VALUES
( 1, 3, '2022-09-15 13:15:25', 521.5, 'OUT'),
( 2, 3, '2022-09-15 14:20:25', 625, 'OUT'),
( 3, 3, '2022-09-16 11:03:15', 1640.16, 'OUT'),
( 4, 3, '2022-09-17 09:45:50', 10662.57, 'OUT'),
( 5, 3, '2022-09-17 10:33:18', 348.63, 'OUT');

INSERT INTO `order_details` (`id`, `order_id`, `product_id`, `quantity`, `price`)
VALUES
(01, 1, 1, 13, 15.50),
(02, 1, 2,  2, 160.00),
(03, 2, 3, 25, 25.00),
(04, 3, 4,  8, 120.00),
(05, 3, 5, 11, 35.80),
(06, 3, 6,  3, 1023.56),
(07, 3, 7,  2, 25.38),
(08, 4, 8, 23, 300.0),
(09, 4, 9, 7, 400.51),
(10, 4,10, 1, 959.0),
(11, 5,11, 15, 15.45),
(12, 5,12, 1, 31.0),
(13, 5,13, 2, 20.0),
(14, 5,14, 1, 45.88);

INSERT INTO `order` (`id`, `user_id`, `time`, `totalAmount`, `direction`)
VALUES
( 6, 5, '2022-09-15 16:35:25', 5988.8, 'IN'),
( 7, 5, '2022-09-17 13:23:18', 696.44, 'IN');

INSERT INTO `order_details` (`id`, `order_id`, `product_id`, `quantity`, `price`)
VALUES
(15, 6, 8,  4, 200),
(16, 6, 9,  8, 300),
(17, 6,10, 2, 800),
(18, 6, 1,  8, 10),
(19, 6, 2, 11, 100.80),
(20, 7, 3,  3, 18.56),
(21, 7, 4,  2, 90.38),
(22, 7, 5, 23, 20.0);
