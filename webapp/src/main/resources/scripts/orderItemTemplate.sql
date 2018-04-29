INSERT INTO pizzaitem (id, quantity, ingredient_id)
VALUES (1, 1, 20),
(2, 1, 13),
(3, 1, 16),
(4, 1, 14),
(5, 1, 21),
(6, 1, 23),
(7, 1, 20),
(8, 1, 13),
(9, 1, 16),
(10, 1, 14),
(11, 1, 21),
(12, 1, 23);

INSERT INTO pizzaside (id, name) VALUES
(1, 'The Veggie'),
(2, 'The Veggie');

INSERT INTO pizzaside_pizzaitem (pizzaside_id, pizzaitems_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12);

INSERT INTO pizza (id, bakestyle_id, crust_id, cutstyle_id, size_id, left_pizzaside_id, right_pizzaside_id)
VALUES (1, 1, 1, 1, 1, 1, 2);

INSERT INTO orderitem (id, quantity, pizza_id) VALUES (1, 1, 1);
INSERT INTO orderitemtemplate (id, orderitem_id, imageFileName) VALUES (1, 1, 'the_veggie.jpg');