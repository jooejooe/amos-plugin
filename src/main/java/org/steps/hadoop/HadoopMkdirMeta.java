package org.steps.hadoop;

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
import org.steps.entity.HadoopBean;
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
        id = "HadoopMkdirStep",
        name = "HadoopMkdir.Name",
        description = "HadoopMkdir.TooltipDesc",
        image = "org/steps/hadoop/resources/demo5.svg",
        categoryDescription = "i18n:org.pentaho.di.trans.step:BaseStep.Category.Transform",
        i18nPackageName = "org.pentaho.di.sdk.steps.hadoop",
        documentationUrl = "HadoopMkdirStep.DocumentationURL",
        casesUrl = "HadoopMkdirStep.CasesURL",
        forumUrl = "HadoopMkdirStep.ForumURL"
)
@InjectionSupported( localizationPrefix = "HadoopMkdirMeta.Injection." )
public class HadoopMkdirMeta extends BaseStepMeta implements StepMetaInterface {
    private static final Class<?> PKG = HadoopMkdirMeta.class; // for i18n purposes
    @Injection( name = "OUTPUT_FIELD" )
    private HadoopBean hadoopBean;

    public HadoopBean getHadoop() {
        return hadoopBean;
    }

    public void setHadoop(HadoopBean hadoop) {
        this.hadoopBean = hadoop;
    }

    public HadoopMkdirMeta() {
        super();
    }
    public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name ) {
        return new HadoopMkdirDialog( shell, meta, transMeta, name );
    }
    @Override
    public StepInterface getStep( StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta,
                                  Trans disp ) {
        return new HadoopMkdirStep( stepMeta, stepDataInterface, cnr, transMeta, disp );
    }



    @Override
    public StepDataInterface getStepData() {
        return new HadoopStepData();
    }
    @Override
    public void setDefault() {
        if(null==hadoopBean){
            hadoopBean=new HadoopBean();
        }
        hadoopBean.setUser(ConstAttr.USER);
        hadoopBean.setPath(ConstAttr.PATH);
        hadoopBean.setUri("hdfs://localhost:8020/");
        hadoopBean.setOutput("output");
    }
    public Object clone() {
        Object retval = super.clone();
        return retval;
    }

    public String getXML() throws KettleValueException {
        StringBuilder xml = new StringBuilder();
        xml.append( XMLHandler.addTagValue( ConstAttr.USER, hadoopBean.getUser()) );
        xml.append( XMLHandler.addTagValue( ConstAttr.PATH, hadoopBean.getPath()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.URI, hadoopBean.getUri()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.URI, hadoopBean.getOutput()) ) ;
        return xml.toString();
    }
    public void loadXML(Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore ) throws KettleXMLException {
        try {
            if(null==hadoopBean){
                hadoopBean=new HadoopBean();
            }
            hadoopBean.setUser( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.USER) ) );
            hadoopBean.setPath( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.PATH ) ) );
            hadoopBean.setUri( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.URI) ) );
            hadoopBean.setOutput( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.OUTPUT) ) );
        } catch ( Exception e ) {
            throw new KettleXMLException( "HadoopMkdir plugin unable to read step info from XML node", e );
        }
    }
    public void saveRep(Repository rep, IMetaStore metaStore, ObjectId id_transformation, ObjectId id_step )
            throws KettleException {
        try {
            if(null==hadoopBean){
                hadoopBean=new HadoopBean();
            }
            rep.saveStepAttribute( id_transformation, id_step, ConstAttr.USER, hadoopBean.getUser() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.PATH, hadoopBean.getPath() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.URI, hadoopBean.getUri() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.OUTPUT, hadoopBean.getOutput() );
        } catch ( Exception e ) {
            throw new KettleException( "Unable to save step into repository: " + id_step, e );
        }
    }
    public void readRep( Repository rep, IMetaStore metaStore, ObjectId id_step, List<DatabaseMeta> databases )
            throws KettleException {
        try {
            if(null==hadoopBean){
                hadoopBean=new HadoopBean();
            }
            hadoopBean.setUser(rep.getStepAttributeString( id_step,  ConstAttr.USER));
            hadoopBean.setPath(rep.getStepAttributeString( id_step,  ConstAttr.PATH));
            hadoopBean.setUri(rep.getStepAttributeString( id_step,  ConstAttr.URI));
            hadoopBean.setOutput(rep.getStepAttributeString( id_step,  ConstAttr.OUTPUT));
        } catch ( Exception e ) {
            throw new KettleException( "Unable to load step from repository", e );
        }
    }

}