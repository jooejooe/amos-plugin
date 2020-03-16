package org.steps.hbase;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.steps.entity.HbaseBean;
import org.steps.util.HbaseService;
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
public class HbaseCreateTableStep extends BaseStep implements StepInterface {
    private static final Class<?> PKG = HbaseCreateTableStep.class; // for i18n purposes
    private static Integer zoopeckerHostIndex=-1;
    private static Integer outputIndex=-1;
    private static Integer zoopeckerPortIndex=-1;
    private static Integer tableIndex=-1;
    private static Integer masterIndex=-1;
    private static Integer famillyndex=-1;

    public HbaseCreateTableStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
    }
    public boolean init(StepMetaInterface smi, StepDataInterface sdi ) {
        String str= System.getProperty("KETTLE_HOME");
        if(null==str||"".equals(str)){
            log.logError( BaseMessages.getString( PKG, "Hbase.Error.KETTLE_HOME" ) );
        }
        List<String> list=new ArrayList<>();
        list.add(str+"/libext");
        LoadExt.loadExtLib(list);
        HbaseCreateTableMeta meta = (HbaseCreateTableMeta) smi;
        HbaseStepData data = (HbaseStepData) sdi;
        if ( !super.init( meta, data ) ) {
            return false;
        }
        return true;
    }
    public boolean processRow( StepMetaInterface smi, StepDataInterface sdi ) throws KettleException {
        HbaseCreateTableMeta meta = (HbaseCreateTableMeta) smi;
        HbaseStepData data = (HbaseStepData) sdi;
        Object[] r = getRow();
        if ( r == null ) {
            setOutputDone();
            return false;
        }
        if ( first ) {
            first = false;
            data.outputRowMeta = (RowMetaInterface) getInputRowMeta().clone();
            meta.getFields( data.outputRowMeta, getStepname(), null, null, this, null, null );
            zoopeckerHostIndex = data.outputRowMeta.indexOfValue( meta.getHbaseBean().getZoopeckerHost());
            outputIndex = data.outputRowMeta.indexOfValue( meta.getHbaseBean().getOutput());
            zoopeckerPortIndex = data.outputRowMeta.indexOfValue( meta.getHbaseBean().getZoopeckerPort());
            tableIndex = data.outputRowMeta.indexOfValue( meta.getHbaseBean().getTable());
            masterIndex = data.outputRowMeta.indexOfValue( meta.getHbaseBean().getMaster());
            famillyndex = data.outputRowMeta.indexOfValue( meta.getHbaseBean().getFamilly());
            if (zoopeckerHostIndex < 0 ||outputIndex<0||zoopeckerPortIndex<0||tableIndex<0||masterIndex<0||famillyndex<0) {
                log.logError( BaseMessages.getString( PKG, "HbaseStep.Error.NoOutputField" ) );
                setErrors( 1L );
                setOutputDone();
                return false;
            }
        }
        Object[] outputRow = RowDataUtil.resizeArray( r, data.outputRowMeta.size() );
        try{
            String zoopeckerHost= (String)outputRow[zoopeckerHostIndex];
            String zoopeckerPort= (String)outputRow[zoopeckerPortIndex];
            String table= (String)outputRow[tableIndex];
            String master= (String)outputRow[masterIndex];
            String familly= (String)outputRow[famillyndex];
            HbaseBean bean=new HbaseBean();
            bean.setTable(table);
            bean.setFamilly(familly);
            bean.setTable(table);
            bean.setZoopeckerHost(zoopeckerHost);
            bean.setZoopeckerPort(zoopeckerPort);
            bean.setMaster(master);
            String str= HbaseService.createTable(bean);
            outputRow[outputIndex] =str;
            putRow( data.outputRowMeta, outputRow );
        }catch (Exception e){
            log.logError(e.getMessage());
        }
        if ( checkFeedback( getLinesRead() ) ) {
            logBasic( BaseMessages.getString( PKG, "HbaseStep.Linenr", getLinesRead() ) ); // Some basic logging
        }
        return true;
    }

}