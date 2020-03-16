package org.steps.sqoop;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.steps.entity.SqoopBean;
import org.steps.util.LoadExt;
import org.steps.util.SqoopService;
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
public class SqoopImportStep extends BaseStep implements StepInterface {
    private static final Class<?> PKG = SqoopImportStep.class; // for i18n purposes
    private static Integer uriIndex=-1;
    private static Integer outputIndex=-1;
    private static Integer argumentIndex=-1;
    private static Integer methodIndex=-1;
    private static Integer yarnIndex=-1;

    public SqoopImportStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
    }
    public boolean init(StepMetaInterface smi, StepDataInterface sdi ) {
        String str= System.getProperty("KETTLE_HOME");
        if(null==str||"".equals(str)){
            log.logError( BaseMessages.getString( PKG, "Sqoop.Error.KETTLE_HOME" ) );
        }
        List<String> list=new ArrayList<>();
        list.add(str+"/libext");
        LoadExt.loadExtLib(list);
        SqoopImportMeta meta = (SqoopImportMeta) smi;
        SqoopStepData data = (SqoopStepData) sdi;
        if ( !super.init( meta, data ) ) {
            return false;
        }
        return true;
    }
    public boolean processRow( StepMetaInterface smi, StepDataInterface sdi ) throws KettleException {
        SqoopImportMeta meta = (SqoopImportMeta) smi;
        SqoopStepData data = (SqoopStepData) sdi;
        Object[] r = getRow();
        if ( r == null ) {
            setOutputDone();
            return false;
        }
        if ( first ) {
            first = false;
            data.outputRowMeta = (RowMetaInterface) getInputRowMeta().clone();
            meta.getFields( data.outputRowMeta, getStepname(), null, null, this, null, null );
            uriIndex = data.outputRowMeta.indexOfValue( meta.getSqoopBean().getUri());
            outputIndex = data.outputRowMeta.indexOfValue( meta.getSqoopBean().getOutput());
            argumentIndex = data.outputRowMeta.indexOfValue( meta.getSqoopBean().getArgument());
            methodIndex = data.outputRowMeta.indexOfValue( meta.getSqoopBean().getMethod());
            yarnIndex = data.outputRowMeta.indexOfValue( meta.getSqoopBean().getYarn());
            if (uriIndex < 0 ||outputIndex<0||argumentIndex<0||methodIndex<0||yarnIndex<0) {
                log.logError( BaseMessages.getString( PKG, "SqoopStep.Error.NoOutputField" ) );
                setErrors( 1L );
                setOutputDone();
                return false;
            }
        }
        Object[] outputRow = RowDataUtil.resizeArray( r, data.outputRowMeta.size() );
        try{
            String uri= (String)outputRow[uriIndex];
            String arg= (String)outputRow[argumentIndex];
            String method= (String)outputRow[methodIndex];
            String yarn= (String)outputRow[yarnIndex];
            String[] args=arg.split(",");
            SqoopBean bean =new SqoopBean();
            bean.setUri(uri);
            bean.setMethod(method);
            bean.setYarn(yarn);
            String str= SqoopService.sqoopService(bean,args);
            outputRow[outputIndex] =str;
            putRow( data.outputRowMeta, outputRow );
        }catch (Exception e){
            log.logError(e.getMessage());
        }
        if ( checkFeedback( getLinesRead() ) ) {
            logBasic( BaseMessages.getString( PKG, "SqoopStep.Linenr", getLinesRead() ) ); // Some basic logging
        }
        return true;
    }

}