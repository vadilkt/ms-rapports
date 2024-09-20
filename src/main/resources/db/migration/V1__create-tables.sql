create table bordereaux
(
    id          int auto_increment primary key,
    numero      varchar(255),
    provenance  varchar(255),
    destination varchar(255),
    emission    timestamp DEFAULT CURRENT_TIMESTAMP,
    creation    timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE courriers
(
    id                   int auto_increment primary key,
    numero_courrier      varchar(50),
    num_expediteur       varchar(20),
    expediteur           varchar(100) NOT NULL,
    date_sign_courrier   datetime     NOT NULL,
    objet_courrier       text      DEFAULT NULL,
    date_enreg_courrier  datetime     NOT NULL,
    date_enreg_cot       datetime     NOT NULL,
    num_coteur           varchar(20),
    nom_coteur           varchar(100) NOT NULL,
    date_cotation        datetime  DEFAULT NULL,
    instructions         varchar(255),
    date_butoir          datetime  DEFAULT NULL,
    niveau_trt_coteur    varchar(20),
    code_nature_courrier varchar(5),
    nature_courrier      varchar(30)  NOT NULL,
    num_cote             varchar(20),
    nom_cote             varchar(100) NOT NULL,
    niveau_trt_cote      varchar(50),
    observation          varchar(50),
    creation             timestamp DEFAULT CURRENT_TIMESTAMP,
    bordereau_id         int,
    constraint bordereaux_courriers_fk foreign key (bordereau_id) references bordereaux (id)
);

create table pieces_jointes
(
    id          int auto_increment primary key,
    libelle     varchar(255),
    dossier     varchar(255),
    creation    timestamp DEFAULT CURRENT_TIMESTAMP,
    courrier_id int,
    constraint pieces_jointes_courriers_fk foreign key (courrier_id) references courriers (id)
);



create table contacts
(
    id           int auto_increment primary key,
    phone_index  varchar(4),
    phone        varchar(10),
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    creation     timestamp DEFAULT CURRENT_TIMESTAMP,
    bordereau_id int,
    constraint bordereaux_contacts_fk foreign key (bordereau_id) references bordereaux (id)
);
