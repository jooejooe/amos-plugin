package org.steps.hbase;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.injection.Injection;
import org.pentaho.di.core.injection.InjectionSupported;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.steps.entity.HbaseBean;
import org.steps.util.ConstAttr;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;

import java.util.List;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-17 10:32
 **/
@Step(
        id = "HbaseCreateTableStep",
        name = "HbaseCreateTable.Name",
        description = "HbaseCreateTable.TooltipDesc",
        image = "org/steps/hbase/resources/demo.svg",
        categoryDescription = "i18n:org.pentaho.di.trans.step:BaseStep.Category.Transform",
        i18nPackageName = "org.pentaho.di.sdk.steps.hbase",
        documentationUrl = "HbaseCreateTable.DocumentationURL",
        casesUrl = "HbaseCreateTable.CasesURL",
        forumUrl = "HbaseCreateTable.ForumURL"
)
@InjectionSupported( localizationPrefix = "HbaseCreateTableMeta.Injection." )
public class HbaseCreateTableMeta extends BaseStepMeta implements StepMetaInterface {
    private static final Class<?> PKG = HbaseCreateTableMeta.class; // for i18n purposes
    @Injection( name = "OUTPUT_FIELD" )
    private HbaseBean hbaseBean;

    public HbaseBean getHbaseBean() {
        return hbaseBean;
    }

    public void setHbaseBean(HbaseBean hbaseBean) {
        this.hbaseBean = hbaseBean;
    }

    public HbaseCreateTableMeta() {
        super();
    }
    public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name ) {
        return new HbaseCreateTableDialog( shell, meta, transMeta, name );
    }
    @Override
    public StepInterface getStep( StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta,
                                  Trans disp ) {
        return new HbaseCreateTableStep( stepMeta, stepDataInterface, cnr, transMeta, disp );
    }



    @Override
    public StepDataInterface getStepData() {
        return new HbaseStepData();
    }
    @Override
    public void setDefault() {
        if(null==hbaseBean){
            hbaseBean=new HbaseBean();
        }
        hbaseBean.setZoopeckerHost(ConstAttr.ZOOPECKERHOST);
        hbaseBean.setOutput(ConstAttr.OUTPUT);
        hbaseBean.setZoopeckerPort(ConstAttr.ZOOPECKERPORT);
        hbaseBean.setMaster(ConstAttr.MASTER);
        hbaseBean.setFamilly(ConstAttr.FAMILLY);
        hbaseBean.setTable(ConstAttr.TABLE);
    }
    public Object clone() {
        Object retval = super.clone();
        return retval;
    }

    public String getXML() throws KettleValueException {
        StringBuilder xml = new StringBuilder();
        xml.append( XMLHandler.addTagValue( ConstAttr.ZOOPECKERHOST, hbaseBean.getZoopeckerHost()) );
        xml.append( XMLHandler.addTagValue( ConstAttr.OUTPUT, hbaseBean.getOutput()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.ZOOPECKERPORT, hbaseBean.getZoopeckerPort()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.MASTER, hbaseBean.getMaster()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.FAMILLY, hbaseBean.getFamilly()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.TABLE, hbaseBean.getTable()) ) ;
        return xml.toString();
    }
    public void loadXML(Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore ) throws KettleXMLException {
        try {
            if(null==hbaseBean){
                hbaseBean=new HbaseBean();
            }
            hbaseBean.setZoopeckerHost( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.ZOOPECKERHOST) ) );
            hbaseBean.setOutput( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.OUTPUT ) ) );
            hbaseBean.setZoopeckerPort( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.ZOOPECKERPORT) ) );
            hbaseBean.setMaster( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.MASTER) ) );
            hbaseBean.setTable( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.TABLE) ) );
            hbaseBean.setFamilly( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.FAMILLY) ) );
        } catch ( Exception e ) {
            throw new KettleXMLException( "Hbase plugin unable to read step info from XML node", e );
        }
    }
    public void saveRep(Repository rep, IMetaStore metaStore, ObjectId id_transformation, ObjectId id_step )
            throws KettleException {
        try {
            if(null==hbaseBean){
                hbaseBean=new HbaseBean();
            }
            rep.saveStepAttribute( id_transformation, id_step, ConstAttr.ZOOPECKERHOST, hbaseBean.getZoopeckerHost() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.OUTPUT, hbaseBean.getOutput() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.ZOOPECKERPORT, hbaseBean.getZoopeckerPort() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.MASTER, hbaseBean.getMaster() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.TABLE, hbaseBean.getTable() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.FAMILLY, hbaseBean.getFamilly() );
        } catch ( Exception e ) {
            throw new KettleException( "Unable to save step into repository: " + id_step, e );
        }
    }
    public void readRep( Repository rep, IMetaStore metaStore, ObjectId id_step, List<DatabaseMeta> databases )
            throws KettleException {
        try {
            if(null==hbaseBean){
                hbaseBean=new HbaseBean();
            }
            hbaseBean.setZoopeckerHost(rep.getStepAttributeString( id_step,  ConstAttr.ZOOPECKERHOST));
            hbaseBean.setOutput(rep.getStepAttributeString( id_step,  ConstAttr.OUTPUT));
            hbaseBean.setZoopeckerPort(rep.getStepAttributeString( id_step,  ConstAttr.ZOOPECKERPORT));
            hbaseBean.setMaster(rep.getStepAttributeString( id_step,  ConstAttr.MASTER));
            hbaseBean.setFamilly(rep.getStepAttributeString( id_step,  ConstAttr.FAMILLY));
            hbaseBean.setTable(rep.getStepAttributeString( id_step,  ConstAttr.TABLE));
        } catch ( Exception e ) {
            throw new KettleException( "Unable to load step from repository", e );
        }
    }

}