#!/bin/sh
#-x
#Usage:
#scriptfile dump_location DB_Username password sid schema_name
export EXPDIR=$1
export DBUSERNAME=$2
export DBPASSWORD=$3
export ORACLE_SID=$4
export SCHEMA_NAME=$6
export EXP_TYPE=$5
export TFILE=`echo /tmp/nohup.$$.tmp`
export STARTTIME=`date`
export DATEFORMAT=`date +%Y%m%d_%Hh%Mm%Ss`
#export ORACLE_HOME=`cat /etc/oratab|grep ^${ORACLE_SID}:|cut -d':' -f2`
export EXPLOG=expdp_`echo $ORACLE_SID`_`echo $DATEFORMAT`.log
export PATH=$PATH:$ORACLE_HOME/bin
DUMPFILE=""
echo $PATH
if [[ $# -lt 6 ]] ; then
 echo "Wrong number of arguments..."
 echo "Usage:"
 echo "./export.sh dump_location DB_Username DB_Password DB_SID Schema_Name EXP_TYPE"
 exit 0
fi
if [[ ! -d "${EXPDIR}" ]]; then
mkdir -p ${EXPDIR}
echo -e "`date` :${EXPDIR} Directory Created."
else
echo -e "`date` :${EXPDIR} directory found on system."
fi
if [ "$?" != 0 ]; then
echo "`date` :Command Failed To check ${EXPDIR} Properly"
exit 1
fi
echo "debut de creation....."
echo "user :: " $DBUSERNAME
echo "pass :: " $DBPASSWORD
echo "sid  :: " $ORACLE_SID
sqlplus -L  $DBUSERNAME/$DBPASSWORD@$ORACLE_SID 2> createDir.log  <<EOF
CREATE OR REPLACE DIRECTORY exp_dir AS '$EXPDIR';
#grant read,write on DIRECTORY exp_dir to $SCHEMA_NAME;
EOF
echo "fin  de creation....."

#    ================== SCHEMA EXPORT ==================
 if [ $EXP_TYPE = "Schema" ]
        then
echo "export shema"
DUMPFILE=expdpSHEMA_`echo $ORACLE_SID`_`echo $DATEFORMAT`.dmp
nohup expdp $DBUSERNAME/$DBPASSWORD@$ORACLE_SID schemas=$SCHEMA_NAME DIRECTORY=exp_dir DUMPFILE=expdpSHEMA_`echo $ORACLE_SID`_`echo $DATEFORMAT`.dmp LOGFILE=$EXPLOG > ${TFILE} 2>&1 &
       
 elif [ $EXP_TYPE = "t" ] 
       then
echo "export table"
DUMPFILE=expdpTABLE_`echo $ORACLE_SID`_`echo $DATEFORMAT`.dmp

#    ================== Table EXPORT ==================
nohup expdp $DBUSERNAME/$DBPASSWORD@$ORACLE_SID tables=$SCHEMA_NAME DIRECTORY=exp_dir DUMPFILE=expdpTABLE_`echo $ORACLE_SID`_`echo $DATEFORMAT`.dmp LOGFILE=$EXPLOG > ${TFILE} 2>&1 &

  elif [ $EXP_TYPE = "f" ]
       then
echo "export ALL"
DUMPFILE=expdpFULL_`echo $ORACLE_SID`_`echo $DATEFORMAT`.dmp
#    ================== FULL EXPORT ==================
nohup expdp $DBUSERNAME/$DBPASSWORD@$ORACLE_SID DIRECTORY=exp_dir DUMPFILE=expdpFULL_`echo $ORACLE_SID`_`echo $DATEFORMAT`.dmp LOGFILE=$EXPLOG > ${TFILE} 2>&1 &
  
  else
         echo"erreur"
  fi

  #    ====================================
sleep 4s;
#if [[ -f "${EXPDIR}/${LOGFILE}" && -f "${EXPDIR}/${DUMPFILE}" ]]; then
if [[ -f ${TFILE} ]]; then
echo "`date` : export started..";
 count=0;
while [ $count -lt 3000 ]; do
status=`cat ${TFILE} | grep -E 'avec succ' | wc -l`
erreur=`cat ${TFILE} | grep -E 'ORA-' | wc -l`
kre=`cat ${TFILE} | grep ORA-`
if [ "$status" == "1" ]; then
echo "`date` :$SCHEMA_NAME  export completed successfully.";
rm -f ${TFILE};
echo "nom du dump :: " $DUMPFILE
echo "chemin complet du dump :: " $EXPDIR/$DUMPFILE
#zip  dump.zip  "$EXPDIR/$DUMPFILE"
exit 0;
break
elif [ $erreur -ge 1 ];then
echo "Erreur des informations...."
echo $kre
exit 0;
break
else
echo "`date` : Export Still in progress...";
a=`expr $a + 1`
(( count++ ))
fi
sleep 1s;
done
fi

