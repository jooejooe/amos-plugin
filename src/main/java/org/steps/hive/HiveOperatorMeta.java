package org.steps.hive;

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
import org.steps.entity.HiveBean;
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
        id = "HiveOperatorStep",
        name = "HiveOperator.Name",
        description = "HiveOperator.TooltipDesc",
        image = "org/steps/hive/resources/demo.svg",
        categoryDescription = "i18n:org.pentaho.di.trans.step:BaseStep.Category.Transform",
        i18nPackageName = "org.pentaho.di.sdk.steps.hive",
        documentationUrl = "HiveOperator.DocumentationURL",
        casesUrl = "HiveOperator.CasesURL",
        forumUrl = "HiveOperator.ForumURL"
)
@InjectionSupported( localizationPrefix = "HiveOperatorMeta.Injection." )
public class HiveOperatorMeta extends BaseStepMeta implements StepMetaInterface {
    private static final Class<?> PKG = HiveOperatorMeta.class; // for i18n purposes
    @Injection( name = "OUTPUT_FIELD" )
    private HiveBean hiveBean;
    public HiveBean getHiveBean() {
        return hiveBean;
    }
    public void setHiveBean(HiveBean hiveBean) {
        this.hiveBean = hiveBean;
    }

    public HiveOperatorMeta() {
        super();
    }
    public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name ) {
        return new HiveOperatorDialog( shell, meta, transMeta, name );
    }
    @Override
    public StepInterface getStep( StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta,
                                  Trans disp ) {
        return new HiveOperatorStep( stepMeta, stepDataInterface, cnr, transMeta, disp );
    }



    @Override
    public StepDataInterface getStepData() {
        return new HiveStepData();
    }
    @Override
    public void setDefault() {
        if(null==hiveBean){
            hiveBean=new HiveBean();
        }
        hiveBean.setUri(ConstAttr.URI);
        hiveBean.setOutput(ConstAttr.OUTPUT);
        hiveBean.setSql(ConstAttr.SQL);
        hiveBean.setUser(ConstAttr.USER);
        hiveBean.setPassword(ConstAttr.PASSWORD);
        hiveBean.setJdbc(ConstAttr.JDBC);
    }
    public Object clone() {
        Object retval = super.clone();
        return retval;
    }

    public String getXML() throws KettleValueException {
        StringBuilder xml = new StringBuilder();
        xml.append( XMLHandler.addTagValue( ConstAttr.URI, hiveBean.getUri()) );
        xml.append( XMLHandler.addTagValue( ConstAttr.OUTPUT, hiveBean.getOutput()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.SQL, hiveBean.getSql()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.USER, hiveBean.getUser()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.PASSWORD, hiveBean.getPassword()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.JDBC, hiveBean.getJdbc()) ) ;
        return xml.toString();
    }
    public void loadXML(Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore ) throws KettleXMLException {
        try {
            if(null==hiveBean){
                hiveBean=new HiveBean();
            }
            hiveBean.setUri( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.URI) ) );
            hiveBean.setOutput( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.OUTPUT ) ) );
            hiveBean.setSql( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.SQL) ) );
            hiveBean.setUser( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.USER) ) );
            hiveBean.setPassword( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.PASSWORD) ) );
            hiveBean.setJdbc( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.JDBC) ) );
        } catch ( Exception e ) {
            throw new KettleXMLException( "HadoopMkdir plugin unable to read step info from XML node", e );
        }
    }
    public void saveRep(Repository rep, IMetaStore metaStore, ObjectId id_transformation, ObjectId id_step )
            throws KettleException {
        try {
            if(null==hiveBean){
                hiveBean=new HiveBean();
            }
            rep.saveStepAttribute( id_transformation, id_step, ConstAttr.URI, hiveBean.getUri() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.OUTPUT, hiveBean.getOutput() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.SQL, hiveBean.getSql() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.USER, hiveBean.getUser() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.PASSWORD, hiveBean.getPassword() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.JDBC, hiveBean.getJdbc() );
        } catch ( Exception e ) {
            throw new KettleException( "Unable to save step into repository: " + id_step, e );
        }
    }
    public void readRep( Repository rep, IMetaStore metaStore, ObjectId id_step, List<DatabaseMeta> databases )
            throws KettleException {
        try {
            if(null==hiveBean){
                hiveBean=new HiveBean();
            }
            hiveBean.setUri(rep.getStepAttributeString( id_step,  ConstAttr.URI));
            hiveBean.setOutput(rep.getStepAttributeString( id_step,  ConstAttr.OUTPUT));
            hiveBean.setSql(rep.getStepAttributeString( id_step,  ConstAttr.SQL));
            hiveBean.setUser(rep.getStepAttributeString( id_step,  ConstAttr.USER));
            hiveBean.setPassword(rep.getStepAttributeString( id_step,  ConstAttr.PASSWORD));
            hiveBean.setJdbc(rep.getStepAttributeString( id_step,  ConstAttr.JDBC));
        } catch ( Exception e ) {
            throw new KettleException( "Unable to load step from repository", e );
        }
    }

}