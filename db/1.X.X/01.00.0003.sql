USE [GMOGA];

insert into separators (SEPARATORS, PRIORITY)
values (',', 1);

insert into MigrationHistory (MajorVersion, MinorVersion, FileNumber, Comment, DateApplied)
values ('01', '00', '0003', 'Added comma separator.', now());