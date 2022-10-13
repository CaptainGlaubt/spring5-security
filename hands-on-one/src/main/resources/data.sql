INSERT INTO handsonone.springuser (id, username, password, algorithm) VALUES (1, 'john', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');
INSERT INTO handsonone.authority (id, name) VALUES (1, 'READ');
INSERT INTO handsonone.authority (id, name) VALUES (2, 'WRITE');
INSERT INTO handsonone.springuser_authority (id, springuser, authority) VALUES (1, 1, 1);
INSERT INTO handsonone.springuser_authority (id, springuser, authority) VALUES (2, 1, 2);
INSERT INTO handsonone.product (id, name, price, currency) VALUES ('1', 'Chocolate', '10', 'USD');