INSERT INTO usuario (mail, pass, fecha_alta, fecha_modif, estado) VALUES ('AMAIKU@gmail.com', '$2a$10$4/sCxADTNXxoXEb0OM7YEuda46COxATMlFJxATKVsNinraFsd6756', '2023-01-03', '2023-01-03', 'ACTIVO');
INSERT INTO usuario (mail, pass, fecha_alta, fecha_modif, estado) VALUES ('ADMIN_CUENTA@gmail.com', '$2a$10$4/sCxADTNXxoXEb0OM7YEuda46COxATMlFJxATKVsNinraFsd6756', '2023-01-03', '2023-01-03', 'ACTIVO');
INSERT INTO usuario (mail, pass, fecha_alta, fecha_modif, estado) VALUES ('PROFESIONAL@gmail.com', '$2a$10$4/sCxADTNXxoXEb0OM7YEuda46COxATMlFJxATKVsNinraFsd6756', '2023-01-03', '2023-01-03', 'ACTIVO');

INSERT INTO public.Cuenta(estado, email, fecha_alta, nombre, subdomino, telefono) VALUES ('ACTIVO', 'AMAIKU@gmail.com', '2025-01-03', 'AMAIKU', 'AMAIKU', '1551040420');

INSERT INTO public.rol(nombre) VALUES ('AMAIKU');
INSERT INTO public.rol(nombre) VALUES ('SECRETARIA');
INSERT INTO public.rol(nombre) VALUES ('PROFESIONAL');
INSERT INTO public.rol(nombre) VALUES ('PACIENTE');
INSERT INTO public.rol(nombre) VALUES ('ADMIN_CUENTA');

INSERT INTO usuario_cuenta_rol (id_usuario, id_cuenta, id_rol, estado, fecha_alta, fecha_modificacion) VALUES (1, 1, 1, 'ACTIVO', '2025-04-23', '2025-04-23');
INSERT INTO usuario_cuenta_rol (id_usuario, id_cuenta, id_rol, estado, fecha_alta, fecha_modificacion) VALUES (2, 1, 5, 'ACTIVO', '2025-04-23', '2025-04-23');
INSERT INTO usuario_cuenta_rol (id_usuario, id_cuenta, id_rol, estado, fecha_alta, fecha_modificacion) VALUES (3, 1, 3, 'ACTIVO', '2025-04-23', '2025-04-23');

