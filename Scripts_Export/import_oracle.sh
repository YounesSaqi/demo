#!/bin/sh
#-x
#Usage:
#scriptfile dump_location DB_Username password sid schema_name
export USERB=$1
export PASSWORD=$2
ORACLE_SID=$3
export DATEFORMAT=`date +%Y%m%d_%Hh%Mm%Ss`
export ORACLE_SID
export ANCIEN_SHCEMA=$4
export ANCIEN_SPACE=$5
export CHEMIN=$6
export EXPLOG=Importdp_`echo $ORACLE_SID`_`echo $DATEFORMAT`.log
export PATH=$PATH:$ORACLE_HOME/bin

sqlplus -L / as sysdba@$ORACLE_SID 2> createDir.log  <<EOF

CREATE USER $USERB   IDENTIFIED BY $PASSWORD   DEFAULT TABLESPACE DATADBS TEMPORARY TABLESPACE TEMP   PROFILE DEFAULT   ACCOUNT UNLOCK;
grant connect,resource,dba to $USERB;


create directory IMPORT${ORACLE_SID}  as ${CHEMIN};
grant read,write on directory IMPORTAWBDE to $USERB;
EOF
echo "fin  de creation....."

nohup impdp $USERB/$PASSWORD directory=IMPORT${ORACLE_SID} content=metadata_only schemas=$ANCIEN_SHCEMA logfile=$EXPLOG dumpfile=awbeprod_de_ano_08032019.dmp  remap_schema=$ANCIEN_SHCEMA:$USERB  REMAP_TABLESPACE=$ANCIEN_SPACE:DATADBS  &
echo "fin  d'import metadata_only trigger et contraintes ..."


sqlplus -L $USERB/$PASSWORD@$ORACLE_SID 2> createDir.log  <<EOF

create table constraints_save as (SELECT  c.CONSTRAINT_NAME FROM user_constraints c, user_tables t WHERE c.table_name = t.table_name AND c.status = 'DISABLED' and CONSTRAINT_TYPE='R');

create table triggers_save as select trigger_name from user_triggers where status='DISABLED';

EOF
echo "fin  de stockage trigger et contraintes ..."

