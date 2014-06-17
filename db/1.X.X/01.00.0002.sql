USE [GMOGA];

delete from separators
where id = 4 or id = 3;

insert into MigrationHistory (MajorVersion, MinorVersion, FileNumber, Comment, DateApplied)
values ('01', '00', '0002', 'Removed unnecessary operations.', now());