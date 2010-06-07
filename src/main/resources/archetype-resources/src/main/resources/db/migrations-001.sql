
drop table if exists resources;

create table resources (
  id varchar,
  text varchar,
  file_content BINARY,
  status integer default 0
);


