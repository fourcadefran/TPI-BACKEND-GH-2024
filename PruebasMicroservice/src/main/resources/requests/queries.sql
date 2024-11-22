INSERT INTO Marcas (NOMBRE) VALUES
                                ('Toyota'),
                                ('Ford'),
                                ('Chevrolet'),
                                ('Renault'),
                                ('Volkswagen');

INSERT INTO Modelos (DESCRIPCION, ID_MARCA) VALUES
                                                ('Corolla', 1),
                                                ('Fiesta', 2),
                                                ('Cruze', 3),
                                                ('Clio', 4),
                                                ('Golf', 5);
INSERT INTO Vehiculos (PATENTE, ID_MODELO) VALUES
                                               ('ABC123', 1),
                                               ('DEF456', 2),
                                               ('GHI789', 3),
                                               ('JKL012', 4),
                                               ('MNO345', 5);

INSERT INTO Interesados (TIPO_DOCUMENTO, DOCUMENTO, NOMBRE, APELLIDO, RESTRINGIDO, NRO_LICENCIA, FECHA_VENCIMIENTO_LICENCIA) VALUES
                                                                                                                                 ('DNI', '12345678', 'Juan', 'Perez', 0, 12345, '2025-11-22T09:00:00'),
                                                                                                                                 ('DNI', '87654321', 'Maria', 'Gomez', 1, 67890, '2025-11-22T09:00:00'),
                                                                                                                                 ('DNI', '11223344', 'Pedro', 'Lopez', 0, 11223, '2025-11-22T09:00:00'),
                                                                                                                                 ('DNI', '44332211', 'Lucia', 'Martinez', 1, 44556, '2025-11-22T09:00:00'),
                                                                                                                                 ('DNI', '99887766', 'Sofia', 'Gutierrez', 0, 99887, '2025-11-22T09:00:00');

INSERT INTO Pruebas (ID_VEHICULO, ID_INTERESADO, ID_EMPLEADO, FECHA_HORA_INICIO, FECHA_HORA_FIN, COMENTARIOS) VALUES
                                                                                                                  (1, 1, 1, '2024-08-22T09:00:00', '2025-11-22T09:00:00', 'Prueba inicial'),
                                                                                                                  (2, 2, 2, '2024-09-22T09:00:00', '2025-11-22T09:00:00', 'Revisión técnica'),
                                                                                                                  (3, 3, 3, '2024-10-22T09:00:00', NULL, 'Prueba avanzada');

INSERT INTO Posiciones (ID_VEHICULO, FECHA_HORA, LATITUD, LONGITUD) VALUES
                                                                        (1, '2024-10-22T09:00:00', -31.4201, -64.1888),
                                                                        (2, '2024-10-22T09:00:00', -34.6037, -58.3816),
                                                                        (3, '2024-10-22T09:00:00', -32.8895, -68.8458);


select * from pruebas;
select * from interesados;
select * from vehiculos;
select * from posiciones;
--delete from pruebas where id = 3;
select * from notificaciones;
select * from agencia


