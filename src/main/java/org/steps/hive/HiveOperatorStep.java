package org.steps.hive;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.steps.util.HiveService;
import org.steps.util.LoadExt;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-17 11:26
 **/
public class HiveOperatorStep extends BaseStep implements StepInterface {
    private static final Class<?> PKG = HiveOperatorStep.class; // for i18n purposes
    private static Integer uriIndex=-1;
    private static Integer outputIndex=-1;
    private static Integer sqlIndex=-1;
    private static Integer userIndex=-1;
    private static Integer passwordIndex=-1;
    private static Integer jdbcndex=-1;

    public HiveOperatorStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
    }
    public boolean init(StepMetaInterface smi, StepDataInterface sdi ) {
        String str= System.getProperty("KETTLE_HOME");
        if(null==str||"".equals(str)){
            log.logError( BaseMessages.getString( PKG, "Hive.Error.KETTLE_HOME" ) );
        }
        List<String> list=new ArrayList<>();
        list.add(str+"/libext");
        LoadExt.loadExtLib(list);
        HiveOperatorMeta meta = (HiveOperatorMeta) smi;
        HiveStepData data = (HiveStepData) sdi;
        if ( !super.init( meta, data ) ) {
            return false;
        }
        return true;
    }
    public boolean processRow( StepMetaInterface smi, StepDataInterface sdi ) throws KettleException {
        HiveOperatorMeta meta = (HiveOperatorMeta) smi;
        HiveStepData data = (HiveStepData) sdi;
        Object[] r = getRow();
        if ( r == null ) {
            setOutputDone();
            return false;
        }
        if ( first ) {
            first = false;
            data.outputRowMeta = (RowMetaInterface) getInputRowMeta().clone();
            meta.getFields( data.outputRowMeta, getStepname(), null, null, this, null, null );
            uriIndex = data.outputRowMeta.indexOfValue( meta.getHiveBean().getUri());
            outputIndex = data.outputRowMeta.indexOfValue( meta.getHiveBean().getOutput());
            sqlIndex = data.outputRowMeta.indexOfValue( meta.getHiveBean().getSql());
            userIndex = data.outputRowMeta.indexOfValue( meta.getHiveBean().getUser());
            passwordIndex = data.outputRowMeta.indexOfValue( meta.getHiveBean().getPassword());
            jdbcndex = data.outputRowMeta.indexOfValue( meta.getHiveBean().getJdbc());
            if (uriIndex < 0 ||outputIndex<0||sqlIndex<0||userIndex<0||passwordIndex<0||jdbcndex<0) {
                log.logError( BaseMessages.getString( PKG, "HiveStep.Error.NoOutputField" ) );
                setErrors( 1L );
                setOutputDone();
                return false;
            }
        }
        Object[] outputRow = RowDataUtil.resizeArray( r, data.outputRowMeta.size() );
        try{
            String uri= (String)outputRow[uriIndex];
            String sql= (String)outputRow[sqlIndex];
            String user= (String)outputRow[userIndex];
            String password= (String)outputRow[passwordIndex];
            String jdbc= (String)outputRow[jdbcndex];
            String str= HiveService.getHiveService(uri,jdbc,user,password,sql);
            outputRow[outputIndex] =str;
            putRow( data.outputRowMeta, outputRow );
        }catch (Exception e){
            log.logError(e.getMessage());
        }
        if ( checkFeedback( getLinesRead() ) ) {
            logBasic( BaseMessages.getString( PKG, "HiveStep.Linenr", getLinesRead() ) ); // Some basic logging
        }
        return true;
    }

}