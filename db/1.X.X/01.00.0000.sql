USE [GMOGA];

create table MigrationHistory
(
	Id int not null auto_increment,
	MajorVersion varchar(2),
	MinorVersion varchar(2),
	FileNumber varchar(4),
	Comment varchar(255),
	DateApplied datetime,

	primary key(Id)
);

insert into MigrationHistory (MajorVersion, MinorVersion, FileNumber, Comment, DateApplied)
values ('01', '00', '0000', 'Baseline. Added migration history', now());