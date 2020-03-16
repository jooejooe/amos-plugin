package org.steps.hadoop;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.steps.entity.HadoopBean;
import org.steps.util.HdfsUtil;
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
public class HadoopMkdirStep extends BaseStep implements StepInterface {
    private static final Class<?> PKG = HadoopMkdirStep.class; // for i18n purposes
    private static Integer userIndex=-1;
    private static Integer pathIndex=-1;
    private static Integer uriIndex=-1;
    private static Integer outputIndex=-1;
    public HadoopMkdirStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
    }
    public boolean init(StepMetaInterface smi, StepDataInterface sdi ) {
        String str= System.getProperty("KETTLE_HOME");
        if(null==str||"".equals(str)){
            log.logError( BaseMessages.getString( PKG, "HadoopMkdirStep.Error.KETTLE_HOME" ) );
        }
        List<String> list=new ArrayList<>();
        list.add(str+"/libext");
        LoadExt.loadExtLib(list);
        HadoopMkdirMeta meta = (HadoopMkdirMeta) smi;
        HadoopStepData data = (HadoopStepData) sdi;
        if ( !super.init( meta, data ) ) {
            return false;
        }
        return true;
    }
    public boolean processRow( StepMetaInterface smi, StepDataInterface sdi ) throws KettleException {
        HadoopMkdirMeta meta = (HadoopMkdirMeta) smi;
        HadoopStepData data = (HadoopStepData) sdi;
        Object[] r = getRow();
        if ( r == null ) {
            setOutputDone();
            return false;
        }
        if ( first ) {
            first = false;
            data.outputRowMeta = (RowMetaInterface) getInputRowMeta().clone();
            meta.getFields( data.outputRowMeta, getStepname(), null, null, this, null, null );
            userIndex = data.outputRowMeta.indexOfValue( meta.getHadoop().getUser());
            pathIndex = data.outputRowMeta.indexOfValue( meta.getHadoop().getPath());
            uriIndex = data.outputRowMeta.indexOfValue( meta.getHadoop().getUri());
            outputIndex = data.outputRowMeta.indexOfValue( meta.getHadoop().getOutput());
            if (userIndex < 0 ||pathIndex<0||uriIndex<0||outputIndex<0) {
                log.logError( BaseMessages.getString( PKG, "HadoopMkdirStep.Error.NoOutputField" ) );
                setErrors( 1L );
                setOutputDone();
                return false;
            }
        }
        Object[] outputRow = RowDataUtil.resizeArray( r, data.outputRowMeta.size() );
        try{
            String user= (String)outputRow[userIndex];
            String uri= (String)outputRow[uriIndex];
            String path= (String)outputRow[pathIndex];
            HadoopBean bean=new HadoopBean();
            bean.setUri(uri);
            bean.setPath(path);
            bean.setUser(user);
            String str=HdfsUtil.mkdir(bean);
            outputRow[outputIndex] =str;
            putRow( data.outputRowMeta, outputRow );
        }catch (Exception e){
            log.logError(e.getMessage());
        }
        if ( checkFeedback( getLinesRead() ) ) {
            logBasic( BaseMessages.getString( PKG, "TikaStep.Linenr", getLinesRead() ) ); // Some basic logging
        }
        return true;
    }
}