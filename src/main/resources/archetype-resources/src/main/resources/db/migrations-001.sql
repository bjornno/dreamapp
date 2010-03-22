
drop table if exists resources;

create table resources (
  id varchar,
  text varchar,
  file_content BINARY,
  status integer default 0
);

drop table if exists dbwork;

create table dbwork (
  id varchar,
  workid varchar,
  queuename varchar,
  status integer default 0
);

