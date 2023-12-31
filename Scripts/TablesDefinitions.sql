if not exists (select * from sys.databases where name = 'WorkingTimeScheduleDB') 
begin
create database WorkingTimeScheduleDB
end

use WorkingTimeScheduleDB;

if exists (select * from WorkingTimeScheduleDB.sys.tables where name = 'DailySchedule')
begin
drop table DailySchedule
end

if exists (select * from WorkingTimeScheduleDB.sys.tables where name = 'ScheduleStats')
begin
drop table ScheduleStats
end

if exists (select * from WorkingTimeScheduleDB.sys.tables where name = 'Records')
begin
drop table Records
end

if exists (select * from WorkingTimeScheduleDB.sys.tables where name = 'Employees')
begin
drop table Employees
end

create table Employees(
EmployeeID uniqueidentifier primary key,
FirstName varchar(max),
LastName varchar(max),
Login varchar(max),
Password varbinary(max),
IsAdmin bit
)

create table Records(
RecordID uniqueidentifier primary key,
EmployeeIDFK uniqueidentifier foreign key references Employees(EmployeeID),
MonthYear Date
)

create table DailySchedule(
RecordID_PK_FK uniqueidentifier foreign key references Records(RecordID),
DayNumberPK int,
StartTime time,
EndTime time,
primary key (RecordID_PK_FK, DayNumberPK)
)

create table ScheduleStats(
RecordID_PK_FK uniqueidentifier foreign key references Records(RecordID),
TotalMinutes int,
NumberOfWorkingDays int,
TotalOvertimeMinutes int,
primary key (RecordID_PK_FK)
)

go