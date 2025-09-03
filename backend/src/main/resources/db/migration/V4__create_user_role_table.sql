create table user_role (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users(id) on delete set null,
    foreign key (role_id) references roles(id) on delete set null
    );