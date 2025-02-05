INSERT INTO order_statuses
 (
  id,
  system_name,
  active,
  created_at
 )
VALUES
    (1, 'NEW', true, now()),
    (2, 'COMPLETED', true, now()),
    (3, 'FAILED', true, now()),
    (4, 'DELETED', true, now());


INSERT INTO roles
 (
  id,
  system_name,
  created_at
 )
VALUES
    (1, 'ROLE_USER', now()),
    (2, 'ROLE_ADMIN', now());

INSERT INTO permission_entities
 (
  id,
  name,
  created_at
 )
VALUES
    (1, 'order', now()),
    (2, 'order_item', now());

INSERT INTO permissions
 (
  id,
  system_name,
  created_at
 )
VALUES
    (1, 'CREATE', now()),
    (2, 'UPDATE', now()),
    (3, 'VIEW', now()),
    (4, 'DELETE', now());


INSERT INTO authorities
(
 id, system_name
) VALUES
      (1, 'ORDER_CREATE'),
      (2, 'ORDER_UPDATE'),
      (3, 'ORDER_VIEW'),
      (4, 'ORDER_DELETE'),

      (5, 'ORDER_ITEM_CREATE'),
      (6, 'ORDER_ITEM_UPDATE'),
      (7, 'ORDER_ITEM_VIEW'),
      (8, 'ORDER_ITEM_DELETE');

INSERT INTO permission_entity_role_rules
 (
  permission_entity,
  permission,
  description,
  authority_id,
  role_id
 )
VALUES
    -- Для админа
    (1, 1, 'Создание заказа', 1, 2),
    (1, 2, 'Обновление заказа', 2, 2),
    (1, 3, 'Просмотр заказа', 3, 2),
    (1, 4, 'Удаление заказа', 4, 2),

    (2, 1, 'Создание элемента заказа', 5, 2),
    (2, 2, 'Обновление элемента заказа', 6, 2),
    (2, 3, 'Просмотр элемента заказа', 7, 2),
    (2, 4, 'Удаление элемента заказа', 8, 2),

    -- Юзеру дозволено лишь просматривать заказы
    (1, 3, 'Просмотр заказа', 3, 1),
    (2, 3, 'Просмотр элемента заказа', 7, 1);


INSERT INTO users
 (
  login,
  phone_number,
  password_hash,
  first_name,
  last_name,
  email,
  activated,
  image_url,
  created_by,
  role_id
)
VALUES
    ('admin',
     '998901234567',
     '$2a$10$St7I6im0NmHqzh2.hpKJBeZNkMErEnQtc12.hn5NLTlUavqXj7C6S',
     'Admin',
     'Admin',
     'admin@gmail.com',
     true,
     '/images/photo.png',
     'SYSTEM',
     2);

INSERT INTO user_permissions
(
    user_id,
   authority
) VALUES
      (1, 'ORDER_CREATE'),
      (1, 'ORDER_UPDATE'),
      (1, 'ORDER_VIEW'),
      (1, 'ORDER_DELETE'),

      (1, 'ORDER_ITEM_CREATE'),
      (1, 'ORDER_ITEM_UPDATE'),
      (1, 'ORDER_ITEM_VIEW'),
      (1, 'ORDER_ITEM_DELETE');