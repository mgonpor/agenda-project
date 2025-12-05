INSERT INTO grupo (id_usuario, nombre) VALUES (1, 'DAM1A');
INSERT INTO grupo (id_usuario, nombre) VALUES (1, 'DAM1B');
INSERT INTO grupo (id_usuario, nombre) VALUES (1, 'DAW1A');
INSERT INTO grupo (id_usuario, nombre) VALUES (1, 'DAW1B');
INSERT INTO grupo (id_usuario, nombre) VALUES (2, 'SMR1A');
INSERT INTO grupo (id_usuario, nombre) VALUES (2, 'SMR1B');
INSERT INTO grupo (id_usuario, nombre) VALUES (1, 'ASIR1A');
INSERT INTO grupo (id_usuario, nombre) VALUES (2, 'ASIR1B');
INSERT INTO grupo (id_usuario, nombre) VALUES (1, 'ESO1A');
INSERT INTO grupo (id_usuario, nombre) VALUES (2, 'ESO1B');
INSERT INTO grupo (id_usuario, nombre) VALUES (1, 'BACH1A');
INSERT INTO grupo (id_usuario, nombre) VALUES (2, 'BACH1B');
INSERT INTO grupo (id_usuario, nombre) VALUES (1, 'FPB1A');
INSERT INTO grupo (id_usuario, nombre) VALUES (2, 'FPB1B');
INSERT INTO grupo (id_usuario, nombre) VALUES (1, 'TIC1A');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (1, '2025-01-10', 'Repaso de programación básica.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (1, '2025-01-17', 'Introducción a POO y ejercicios.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (2, '2025-01-11', 'Instalación de entorno de desarrollo.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (3, '2025-01-12', 'Maquetación con HTML y CSS.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (4, '2025-01-13', 'Primer contacto con JavaScript.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (5, '2025-01-14', 'Configuración de red en el aula.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (6, '2025-01-15', 'Práctica con hardware y montaje.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (7, '2025-01-16', 'Introducción a bases de datos relacionales.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (8, '2025-01-18', 'Consultas SQL básicas.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (9, '2025-01-19', 'Presentación del curso y normas.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (10, '2025-01-20', 'Actividad de trabajo en equipo.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (11, '2025-01-21', 'Introducción a programación en Java.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (12, '2025-01-22', 'Resolución de dudas de la unidad 1.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (13, '2025-01-23', 'Explicación de proyecto trimestral.');

INSERT INTO anotacion (id_grupo, fecha, texto)
VALUES (14, '2025-01-24', 'Repaso general antes del examen.');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (1, 'MONDAY', 1, 'Aula 1.1');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (1, 'WEDNESDAY', 2, 'Aula 1.1');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (2, 'TUESDAY', 1, 'Aula 1.2');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (2, 'THURSDAY', 3, 'Aula 1.2');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (3, 'MONDAY', 2, 'Aula 2.1');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (3, 'FRIDAY', 4, 'Aula 2.1');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (4, 'WEDNESDAY', 1, 'Aula 2.2');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (4, 'FRIDAY', 3, 'Aula 2.2');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (5, 'TUESDAY', 2, 'Lab 1');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (6, 'THURSDAY', 2, 'Lab 2');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (7, 'MONDAY', 3, 'Aula 3.1');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (8, 'WEDNESDAY', 4, 'Aula 3.2');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (9, 'TUESDAY', 3, 'Aula 4.1');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (10, 'THURSDAY', 4, 'Aula 4.2');

INSERT INTO clase (id_grupo, dia_semana, tramo, aula)
VALUES (11, 'FRIDAY', 2, 'Salón de actos');
