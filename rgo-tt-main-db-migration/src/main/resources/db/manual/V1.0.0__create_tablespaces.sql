CREATE TABLESPACE ${tbsTables}
OWNER postgres
LOCATION '${tbsTablesDataFile}';

CREATE TABLESPACE ${tbsIndexes}
OWNER postgres
LOCATION '${tbsIndexesDataFile}';