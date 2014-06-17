@echo off
setlocal ENABLEDELAYEDEXPANSION
cls

set _date=%date%
set _time=%time: =0%
set _time=%_time:~0,2%.%_time:~3,2%.%_time:~6,2%
echo %_date% %_time% >> %~dp0%~1SQLUpdate.log
set _isBackedUp=false

for /F "usebackq  tokens=1,2,3 skip=1 delims= " %%r in (`sqlcmd -W -E -S "localhost" -h-1 -Q "SET NOCOUNT ON;use [GMOGA];select top 1 cast(MajorVersion as int), cast(MinorVersion as int), cast(FileNumber as int) from MigrationHistory order by DateApplied desc"`) do (
  set /A _dbVersion=%%r
  set /A _dbSubversion=%%s
  set /A _dbFileName=%%t
  echo dbVersion: !_dbVersion!.!_dbSubversion!.!_dbFileName! >> %~dp0%~1SQLUpdate.log
  set /A _dbFileName = !_dbFileName! + 1
:applyNextVersion
call:versionToText
  :applyNextSubversion
  call:subversionToText
    :applyNextFileName
    call:filenameToText
    if exist %~dp0%~1!_dbVersion!.X.X\!_dbVersionString!.!_dbSubversionString!.!_dbFileNameString!.sql (
      if %_isBackedUp% EQU false (
        echo|set /p = "Backing up database....." >> %~dp0%~1SQLUpdate.log
        sqlcmd -E -S "localhost" -Q "backup database [GMOGA] to disk='%~dp0%~1%_date%_%_time%.bak'" -r0 2>> %~dp0%~1SQLUpdate.log 1> NUL
        if ERRORLEVEL 1 (
          goto addNewLine
        )
        echo Success >> %~dp0%~1SQLUpdate.log
        set _isBackedUp=true
      )      
      echo|set /p ="Applying !_dbVersion!.X.X\!_dbVersionString!.!_dbSubversionString!.!_dbFileNameString!.sql....." >> %~dp0%~1SQLUpdate.log
      sqlcmd -E -i .\!_dbVersion!.X.X\!_dbVersionString!.!_dbSubversionString!.!_dbFileNameString!.sql -b -r0 2>> %~dp0%~1SQLUpdate.log 1> NUL
      if ERRORLEVEL 1 goto dbError
      echo Success >> %~dp0%~1SQLUpdate.log
      set /A _dbFileName = !_dbFileName! + 1
      goto applyNextFileName
    )
    if !_dbSubversion! EQU 0 (
      if !_dbFileName! EQU 0 (
        echo Database is up-date >> %~dp0%~1SQLUpdate.log
        goto addNewLine
      )
    )
    if !_dbFileName! EQU 0 (
      set /A _dbSubversion = 0
      set /A _dbVersion = !_dbVersion! + 1
      goto applyNextVersion
    )
    set /A _dbFileName = 0
    set /A _dbSubversion = !_dbSubversion! + 1
    goto applyNextSubversion
)

:dbError
echo|set /p = "Restoring up database....." >> %~dp0%~1SQLUpdate.log
sqlcmd -E -S "localhost" -Q "restore database [GMOGA] from disk='%~dp0%~1%_date%_%_time%.bak'" -r0 2>> %~dp0%~1SQLUpdate.log 1> NUL
if ERRORLEVEL 1 (
  goto addNewLine
)
echo Success >> %~dp0%~1SQLUpdate.log
goto addNewLine

:addNewLine
echo. >> %~dp0%~1SQLUpdate.log
goto:eof

::-------
::-- Function to calculate version or subversion
::-------
:versionToText
if !_dbVersion! LSS 10 set _dbVersionString=0!_dbVersion!
if !_dbVersion! EQU 0 set _dbVersionString=00
goto:eof

:subversionToText
if !_dbSubversion! LSS 10 set _dbSubversionString=0!_dbSubversion!
if !_dbSubversion! EQU 0 set _dbSubversionString=00
goto:eof

:filenameToText
if !_dbFileName! LSS 1000 set _dbFileNameString=0!_dbFileName!
if !_dbFileName! LSS 100 set _dbFileNameString=00!_dbFileName!
if !_dbFileName! LSS 10 set _dbFileNameString=000!_dbFileName!
if !_dbFileName! EQU 0 set _dbFileNameString=0000
goto:eof