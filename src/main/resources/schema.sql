drop table if exists logic_rule;
drop table if exists hibernate_sequences;
drop table if exists prediction;
drop table if exists model;
drop table if exists chemical;
drop table if exists endpoint;
drop table if exists software;

create table chemical(
    id bigint not null identity(1,1),
    cas varchar(255),
    smile varchar(255),
    primary key (id)
);
create table endpoint(
    id bigint not null identity(1,1),
    name varchar(255) not null unique,
    primary key(id)
);

create table software(
    id bigint not null identity(1,1),
    name varchar(255) not null unique,
    primary key (id)
);
create table model(
    id bigint not null identity(1,1),
    name varchar(255) not null,
    eid bigint not null FOREIGN KEY REFERENCES endpoint(id),
    sid bigint not null FOREIGN KEY REFERENCES software(id),
    primary key (id)
);


create table prediction(
    id bigint not null identity(1,1),
    prediction varchar(255

        ),
    prediction_raw varchar(255),
    reliability varchar(255),
    reliability_raw varchar(255),
    chemical_id bigint FOREIGN KEY REFERENCES chemical(id),
    model_id bigint FOREIGN KEY REFERENCES model(id),
    primary key (id)
);


