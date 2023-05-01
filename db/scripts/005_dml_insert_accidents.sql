INSERT INTO types (name) VALUES ('Две машины'), ('Машина и человек'), ('Машина и велосипед');
INSERT INTO rules (name) VALUES ('Статья. 1'), ('Статья. 2'), ('Статья. 3');
INSERT INTO accidents (name,  address, type_id) VALUES
    ('Сиволобов Игорь', 'Ул. Энтузиастов, 17/12', 1),
    ('Иванов Иван Иванонович', 'Пр. Боголюбова, 13', 2),
    ('Владимиров Владимир Владимирович', 'Новое шоссе', 3);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (1, 1), (1, 2), (1, 3),
                                                          (2, 1), (2, 2), (2, 3),
                                                          (3, 1), (3, 2), (3, 3);
