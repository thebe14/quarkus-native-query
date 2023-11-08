insert into users (id, fullname, email)
values (1543, 'John Doe', 'john.doe@test.com');

insert into roles (role, name, version, changedon, changedescription)
values ('process-staff', 'Process Staff', 1, '2021-02-19T19:23:18', 'First version'),

       ('process-owner', 'Process Owner', 1, '2021-02-19T19:23:18', 'First version'),

       ('process-manager', 'Process Manager', 1, '2021-02-19T19:23:18', 'First version'),

       ('report-owner', 'Report Owner', 1, '2021-02-19T19:23:18', 'First version'),

       ('process-developer', 'Process Developer', 1, '2023-09-02T19:23:18', 'First version'),

       ('catalog-owner', 'Service Catalog Owner', 1, '2021-02-19T19:23:18', 'First version'),

       ('sla-owner', 'Service Level Agreement Owner', 1, '2021-02-19T19:23:18', 'First version'),

       ('ola-owner', 'Operational Level Agreement Owner', 1, '2021-02-19T19:23:18', 'First version'),

       ('ua-owner', 'Underpinning Agreement Owner', 1, '2021-02-19T19:23:18', 'First version'),

       ('process-manager', 'Process Manager', 2, '2021-02-19T19:23:18', 'Second version');

insert into role_editor_map (role_id, user_id)
values (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1);