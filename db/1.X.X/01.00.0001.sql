USE [GMOGA];

alter table operations
add column PRIORITY int after OPERATION;

alter table separators
add column PRIORITY int after SEPARATORS;

update separators
set PRIORITY = 0
where ID = 1;

update separators
set PRIORITY = 1
where ID = 2;

update operations
set PRIORITY = 5
where ID > 5;

update operations
set PRIORITY = 6
where ID = 1 or ID = 2;

update operations
set PRIORITY = 7
where ID = 3 or ID = 4;

update operations
set PRIORITY = 8
where ID = 5;

insert into MigrationHistory (MajorVersion, MinorVersion, FileNumber, Comment, DateApplied)
values ('01', '00', '0001', 'Added priority for all operations.', now());