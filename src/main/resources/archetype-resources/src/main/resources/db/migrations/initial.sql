
drop table if exists resources;

create table resources (
  id varchar,
  text varchar,
  file_content BINARY,
  status integer default 0
);

drop table if exists application_properties;

create table application_properties (
  key varchar,
  value varchar
);

