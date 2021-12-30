INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(1,'Arcadio','López','arcadiolg2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(2,'Jesus','López','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(3,'Jeesus','Ló2pez','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(4,'Jesus','López','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(5,'Jedssus','Ló2pez','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(6,'Jesus','López','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(7,'Jesus','López','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(8,'J3esus','Ló2pez','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(9,'Jesus','López','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(10,'Jesus','López','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(11,'Jesus','López','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(12,'Jesus','López','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(13,'Jesus','López','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(14,'Jesus','López2','jesus2@gmail.com','2020-08-28','');
INSERT INTO clientes(id,nombre,apellido,email,create_at,foto) values(15,'Jesus2','López','jesus2@gmail.com','2020-08-28','');


INSERT INTO productos(nombre,precio,create_at) values("gansito",10.0,NOW());
INSERT INTO productos(nombre,precio,create_at) values("chocoroll",12.0,NOW());
INSERT INTO productos(nombre,precio,create_at) values("donitas",17.0,NOW());
INSERT INTO productos(nombre,precio,create_at) values("chetos",10.0,NOW());
INSERT INTO productos(nombre,precio,create_at) values("sabritas",13.0,NOW());

INSERT INTO facturas (descripcion,observacion,create_at,cliente_id) values ("factura 1","nada",NOW(),1);
INSERT INTO factura_items(cantidad, producto_id,factura_id) values(2,1,1);
INSERT INTO factura_items(cantidad, producto_id,factura_id) values(3,3,1);
INSERT INTO factura_items(cantidad, producto_id,factura_id) values(5,2,1);

INSERT INTO facturas (descripcion,observacion,create_at,cliente_id) values ("factura 2","nada",NOW(),1);
INSERT INTO factura_items(cantidad, producto_id,factura_id) values(1,3,2);
INSERT INTO factura_items(cantidad, producto_id,factura_id) values(2,2,2);
INSERT INTO factura_items(cantidad, producto_id,factura_id) values(3,1,2);

INSERT INTO facturas (descripcion,observacion,create_at,cliente_id) values ("factura 3","nada",NOW(),2);
INSERT INTO factura_items(cantidad, producto_id,factura_id) values(1,2,3);
INSERT INTO factura_items(cantidad, producto_id,factura_id) values(3,5,3);
INSERT INTO factura_items(cantidad, producto_id,factura_id) values(4,3,3);


