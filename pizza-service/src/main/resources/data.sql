INSERT INTO bake_style (id,name) VALUES (1,'Regular');
INSERT INTO bake_style (id,name) VALUES (2,'Bake Light');
INSERT INTO bake_style (id,name) VALUES (3,'Bake Extra Well');
INSERT INTO bake_style (id,name) VALUES (4,'Bake Well');

INSERT INTO crust (id,name,price) VALUES (1,'Thin','USD 0.00');
INSERT INTO crust (id,name,price) VALUES (2,'Classic','USD 0.00');
INSERT INTO crust (id,name,price) VALUES (3,'Thick','USD 0.00');

INSERT INTO cut_style (id,name) VALUES (1,'Regular');
INSERT INTO cut_style (id,name) VALUES (2,'Cut Square');
INSERT INTO cut_style (id,name) VALUES (3,'Cut in 8');
INSERT INTO cut_style (id,name) VALUES (4,'Cut in 10');
INSERT INTO cut_style (id,name) VALUES (5,'Cut in 12');

INSERT INTO pizza_size (id,name,price,diameter,ingredient_cost_factor) VALUES (1,'Small', 'USD 7.00', 10, 1.00);
INSERT INTO pizza_size (id,name,price,diameter,ingredient_cost_factor) VALUES (2,'Medium', 'USD 10.00', 12, 1.44);
INSERT INTO pizza_size (id,name,price,diameter,ingredient_cost_factor) VALUES (3,'Large', 'USD 12.00', 14, 1.96);
INSERT INTO pizza_size (id,name,price,diameter,ingredient_cost_factor) VALUES (4,'X-Large', 'USD 14.00', 16, 2.56);

INSERT INTO ingredient_type (name,id) VALUES ('Cheese',2);
INSERT INTO ingredient_type (name,id) VALUES ('Meat',1);
INSERT INTO ingredient_type (name,id) VALUES ('Sauce',4);
INSERT INTO ingredient_type (name,id) VALUES ('Veggies',3);

INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (1,'Anchovies','USD 1.00',1, 'anchovies.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (2,'Sausage','USD 1.00',1,'sausage.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (3,'Pepperoni','USD 1.00',1,'pepperoni.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (5,'Chicken','USD 1.00',1,'chicken.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (6,'Meatballs','USD 1.00',1,'meatballs.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (7,'Ham','USD 1.00',1,'ham.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (8,'Steak','USD 1.00',1,'steak.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (9,'Bacon','USD 1.00',1,'bacon.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (10,'Beef','USD 1.00',1,'beef.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (11,'Salami','USD 1.00',1,'salami.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (12,'Spinach','USD 1.00',3,'spinach.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (13,'Mushrooms','USD 1.00',3,'mushrooms.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (14,'Red Onion','USD 1.00',3,'red_onion.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (15,'Onion','USD 1.00',3,'onion.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (16,'Banana Peppers','USD 1.00',3,'banana_peppers.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (17,'Pineapple','USD 1.00',3,'pineapple.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (18,'Jalapeno','USD 1.00',3,'jalapeno.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (19,'Black Olive','USD 1.00',3,'black_olive.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (20,'Green Pepper','USD 1.00',3,'green_pepper.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (21,'Tomatoes','USD 1.00',3,'tomato.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (22,'White Sauce','USD 0.25',4,'white_sauce.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (23,'Classic Red','USD 0.25',4,'classic_red_sauce.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (24,'Sweet Red','USD 0.25',4,'sweet_red_sauce.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (25,'Spicy Red','USD 0.25',4,'spicy_red_sauce.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (26,'Roasted Garlic','USD 1.00',3,'garlic.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (27,'Feta Cheese','USD 1.00',2,'feta.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (28,'Cheddar Cheese','USD 1.00',2,'cheddar.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (29,'Shaved Parmesan','USD 1.00',2,'shaved_parmesan.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (30,'Parmesan','USD 1.00',2,'parmesan.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (31,'Mozzarella','USD 1.00',2,'mozzarella.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (32,'Taco','USD 0.25',4,'taco_sauce.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (33,'Buffalo','USD 0.25',4,'buffalo.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (34,'Pesto','USD 0.25',4,'pesto.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (35,'BBQ','USD 0.25',4,'bbq_sauce.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (36,'Goat','USD 1.00',2,'goat.gif');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (37,'Ricotta','USD 1.00',2,'ricotta.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (38,'Blue','USD 1.00',2,'blue.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (39,'Artichoke','USD 1.00',3,'artichoke.png');
INSERT INTO ingredient (id,name,price,ingredient_type_id,image_file_name) VALUES (40,'Basil','USD 1.00',3,'basil.png');

-- TEMPLATES

-- the veggie
INSERT INTO pizza_item (id, quantity, ingredient_id)
VALUES
(1, 1, 20),
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

INSERT INTO pizza_side (id, name) VALUES
(1, 'The Veggie'),
(2, 'The Veggie');

INSERT INTO pizza_side_pizza_items (pizza_side_id, pizza_items_id) VALUES
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

INSERT INTO pizza (id, bake_style_id, crust_id, cut_style_id, size_id, left_pizzaside_id, right_pizzaside_id)
VALUES (1, 1, 1, 1, 1, 1, 2);

-- four cheese
INSERT INTO pizza_item (id, quantity, ingredient_id) VALUES
(21, 1, 31),
(22, 1, 28),
(23, 1, 30),
(24, 1, 37),
(25, 1, 23),
(26, 1, 31),
(27, 1, 28),
(28, 1, 30),
(29, 1, 37),
(30, 1, 23);

INSERT INTO pizza_side (id, name) VALUES
(3, 'Four Cheese'),
(4, 'Four Cheese');

INSERT INTO pizza_side_pizza_items (pizza_side_id, pizza_items_id) VALUES
(3, 21),
(3, 22),
(3, 23),
(3, 24),
(3, 25),
(4, 26),
(4, 27),
(4, 28),
(4, 29),
(4, 30);

INSERT INTO pizza (id, bake_style_id, crust_id, cut_style_id, size_id, left_pizzaside_id, right_pizzaside_id)
VALUES (2, 1, 1, 1, 1, 3, 4);

-- pepperoni classic
INSERT INTO pizza_item (id, quantity, ingredient_id) VALUES
(31, 2, 3),
(32, 1, 31),
(33, 1, 23),
(34, 1, 3),
(35, 1, 31),
(36, 1, 23);

INSERT INTO pizza_side (id, name) VALUES
(5, 'Pepperoni Classic'),
(6, 'Pepperoni Classic');

INSERT INTO pizza_side_pizza_items (pizza_side_id, pizza_items_id) VALUES
(5, 31),
(5, 32),
(5, 33),
(6, 34),
(6, 35),
(6, 36);

INSERT INTO pizza (id, bake_style_id, crust_id, cut_style_id, size_id, left_pizzaside_id, right_pizzaside_id)
VALUES (3, 1, 1, 1, 1, 5, 6);

-- White Garden
INSERT INTO pizza_item (id, quantity, ingredient_id) VALUES
(41, 1, 20),
(42, 1, 21),
(43, 1, 15),
(44, 1, 19),
(45, 1, 31),
(46, 1, 30),
(47, 1, 20),
(48, 1, 21),
(49, 1, 15),
(50, 1, 19),
(51, 1, 31),
(52, 1, 30);

INSERT INTO pizza_side (id, name) VALUES
(7, 'White Garden'),
(8, 'White Garden');

INSERT INTO pizza_side_pizza_items (pizza_side_id, pizza_items_id) VALUES
(7, 41),
(7, 42),
(7, 43),
(7, 44),
(7, 45),
(7, 46),
(8, 47),
(8, 48),
(8, 49),
(8, 50),
(8, 51),
(8, 52);

INSERT INTO pizza (id, bake_style_id, crust_id, cut_style_id, size_id, left_pizzaside_id, right_pizzaside_id)
VALUES (4, 1, 1, 1, 1, 7, 8);

-- BBQ Chicken
INSERT INTO pizza_item (id, quantity, ingredient_id) VALUES
(61, 1, 5),
(62, 1, 9),
(63, 1, 15),
(64, 1, 35),
(65, 1, 28),
(66, 1, 30),
(67, 1, 31),
(68, 1, 5),
(69, 1, 9),
(70, 1, 15),
(71, 1, 35),
(72, 1, 28),
(73, 1, 30),
(74, 1, 31);

INSERT INTO pizza_side (id, name) VALUES
(9, 'BBQ Chicken'),
(10, 'BBQ Chicken');

INSERT INTO pizza_side_pizza_items (pizza_side_id, pizza_items_id) VALUES
(9, 61),
(9, 62),
(9, 63),
(9, 64),
(9, 65),
(9, 66),
(9, 67),
(10, 68),
(10, 69),
(10, 70),
(10, 71),
(10, 72),
(10, 73),
(10, 74);

INSERT INTO pizza (id, bake_style_id, crust_id, cut_style_id, size_id, left_pizzaside_id, right_pizzaside_id)
VALUES (5, 1, 1, 1, 1, 9, 10);

-- Hawaiian
INSERT INTO pizza_item (id, quantity, ingredient_id) VALUES
(81, 1, 7),
(82, 1, 5),
(83, 1, 9),
(84, 1, 17),
(85, 2, 31),
(86, 1, 23),
(87, 1, 7),
(88, 1, 5),
(89, 1, 9),
(90, 1, 17),
(91, 2, 31),
(92, 1, 23);

INSERT INTO pizza_side (id, name) VALUES
(11, 'Hawaiian'),
(12, 'Hawaiian');

INSERT INTO pizza_side_pizza_items (pizza_side_id, pizza_items_id) VALUES
(11, 81),
(11, 82),
(11, 83),
(11, 84),
(11, 85),
(11, 86),
(12, 87),
(12, 88),
(12, 89),
(12, 90),
(12, 91),
(12, 92);

INSERT INTO pizza (id, bake_style_id, crust_id, cut_style_id, size_id, left_pizzaside_id, right_pizzaside_id)
VALUES (6, 1, 1, 1, 1, 11, 12);

INSERT INTO pizza_template (id, name, pizza_id, image_file_name) VALUES (1, 'The Veggie', 1, 'the_veggie.jpg');

INSERT INTO pizza_template (id, name, pizza_id, image_file_name) VALUES (2, 'Four Cheese', 2, 'four_cheese.jpg');

INSERT INTO pizza_template (id, name, pizza_id, image_file_name) VALUES (3, 'Pepperoni Classic', 3, 'pepperoni_classic.jpg');

INSERT INTO pizza_template (id, name, pizza_id, image_file_name) VALUES (4, 'White Garden', 4, 'white_garden.jpg');

INSERT INTO pizza_template (id, name, pizza_id, image_file_name) VALUES (5, 'BBQ Chicken', 5, 'bbq_chicken.jpg');

INSERT INTO pizza_template (id, name, pizza_id, image_file_name) VALUES (6, 'Hawaiian', 6, 'hawaiian.jpg');
