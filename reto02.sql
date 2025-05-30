-- Eliminar secuencias existentes

/*drop sequence SEQ_COMERCIANTE;

drop sequence SEQ_ESTABLECIMIENTO;

drop sequence SEQ_USUARIO;*/

-- Secuencia y trigger para COMERCIANTE
/*==============================================================*/
/* Sequence: SEQ_COMERCIANTE                                    */
/*==============================================================*/

create sequence SEQ_COMERCIANTE
start with 1
increment by 1
nocache
nocycle;


/*==============================================================*/
/* Trigger: TRG_COMERCIANTE                                     */
/*==============================================================*/

create or replace trigger TRG_COMERCIANTE before insert or update on COMERCIANTE
for each row
begin
  if INSERTING then
    :new.ID_COMERCIANTE := SEQ_COMERCIANTE.nextval;
    if :new.ID_USUARIO is not null then
      :new.FECHA_ACTUALIZACION := SYSDATE;
	end if;
  elsif UPDATING then
    if :new.ID_USUARIO is not null then
      :new.FECHA_ACTUALIZACION := SYSDATE;
	end if;
  end if;
end;
/

-- Secuencia y trigger para ESTABLECIMIENTO
/*==============================================================*/
/* Sequence: SEQ_ESTABLECIMIENTO                                */
/*==============================================================*/

create sequence SEQ_ESTABLECIMIENTO
start with 1
increment by 1
nocache
nocycle;

/*==============================================================*/
/* Sequence: TRG_ESTABLECIMIENTO                                */
/*==============================================================*/

create or replace trigger TRG_ESTABLECIMIENTO before insert or update on ESTABLECIMIENTO
for each row
begin
  if INSERTING then
    :new.ID_ESTAB := SEQ_ESTABLECIMIENTO.nextval;
    if :new.ID_USUARIO is not null then
      :new.FECHA_ACTUALIZACION := SYSDATE;
	end if;
  elsif UPDATING then
    if :new.ID_USUARIO is not null then
      :new.FECHA_ACTUALIZACION := SYSDATE;
	end if;
  end if;
end;
/

-- Secuencia y trigger para USUARIO
/*==============================================================*/
/* Sequence: SEQ_USUARIO                                        */
/*==============================================================*/

create sequence SEQ_USUARIO
start with 1
increment by 1
nocache
nocycle;


/*==============================================================*/
/* Sequence: TRG_USUARIO                                        */
/*==============================================================*/

create or replace trigger TRG_USUARIO before insert on USUARIO
for each row
begin
  :new.ID_USUARIO := SEQ_USUARIO.nextval;
end;
/

COMMIT;