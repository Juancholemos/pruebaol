/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO 
(
   ID_USUARIO           NUMBER               not null,
   NOMBRE               VARCHAR2(100)        not null,
   EMAIL                VARCHAR2(70)         not null,
   PASSWORD             VARCHAR2(128)        not null,
   ROL                  VARCHAR2(20) CHECK (ROL IN ('Administrador', 'Auxiliar')) not null,
   constraint PK_USUARIO primary key (ID_USUARIO)
);

/*==============================================================*/
/* Table: COMERCIANTE                                           */
/*==============================================================*/
create table COMERCIANTE 
(
   ID_COMERCIANTE       NUMBER               not null,
   ID_USUARIO           NUMBER,
   RAZON_SOCIAL         VARCHAR2(150)        not null,
   MUNICIPIO            VARCHAR2(100)        not null,
   TELEFONO             VARCHAR2(30),
   EMAIL                VARCHAR2(70),
   FECHA_REGISTRO       DATE                 not null,
   ESTADO               VARCHAR2(15) CHECK (ESTADO IN ('Activo', 'Inactivo')) not null,
   FECHA_ACTUALIZACION  DATE,
   constraint PK_COMERCIANTE primary key (ID_COMERCIANTE)
);

/*==============================================================*/
/* Index: AUDITA_COMERCIANTE_FK                                 */
/*==============================================================*/
create index AUDITA_COMERCIANTE_FK on COMERCIANTE (
   ID_USUARIO ASC
);

/*==============================================================*/
/* Table: ESTABLECIMIENTO                                       */
/*==============================================================*/
create table ESTABLECIMIENTO 
(
   ID_ESTAB             NUMBER               not null,
   ID_COMERCIANTE       NUMBER               not null,
   ID_USUARIO           NUMBER,
   NOMBRE_ESTAB         VARCHAR2(100)        not null,
   INGRESOS             NUMBER(15,2)         not null,
   NUM_EMPLEADOS        NUMBER               not null,
   FECHA_ACTUALIZACION  DATE,
   constraint PK_ESTABLECIMIENTO primary key (ID_ESTAB)
);

/*==============================================================*/
/* Index: ESTABLECIMIENTO_COMERCIANTE_FK                        */
/*==============================================================*/
create index ESTABLECIMIENTO_COMERCIANTE_FK on ESTABLECIMIENTO (
   ID_COMERCIANTE ASC
);

/*==============================================================*/
/* Index: AUDITA_ESTABLECIMIENTO_FK                             */
/*==============================================================*/
create index AUDITA_ESTABLECIMIENTO_FK on ESTABLECIMIENTO (
   ID_USUARIO ASC
);



alter table COMERCIANTE
   add constraint FK_COMERCIANTE_AUDITOR foreign key (ID_USUARIO)
      references USUARIO (ID_USUARIO);

alter table ESTABLECIMIENTO
   add constraint FK_ESTABLECIMIENTO_AUDITOR foreign key (ID_USUARIO)
      references USUARIO (ID_USUARIO);

alter table ESTABLECIMIENTO
   add constraint FK_ESTABLECIMIENTO_COMERCIANTE foreign key (ID_COMERCIANTE)
      references COMERCIANTE (ID_COMERCIANTE);


COMMIT;