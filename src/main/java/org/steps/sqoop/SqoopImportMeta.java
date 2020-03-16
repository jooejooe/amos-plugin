package org.steps.sqoop;

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
import org.steps.entity.SqoopBean;
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
        id = "SqoopImportStep",
        name = "SqoopImport.Name",
        description = "SqoopImport.TooltipDesc",
        image = "org/steps/sqoop/resources/demo.svg",
        categoryDescription = "i18n:org.pentaho.di.trans.step:BaseStep.Category.Transform",
        i18nPackageName = "org.pentaho.di.sdk.steps.sqoop",
        documentationUrl = "SqoopImport.DocumentationURL",
        casesUrl = "SqoopImport.CasesURL",
        forumUrl = "SqoopImport.ForumURL"
)
@InjectionSupported( localizationPrefix = "SqoopImportMeta.Injection." )
public class SqoopImportMeta extends BaseStepMeta implements StepMetaInterface {
    private static final Class<?> PKG = SqoopImportMeta.class; // for i18n purposes
    @Injection( name = "OUTPUT_FIELD" )
    private SqoopBean sqoopBean;
    public SqoopBean getSqoopBean() {
        return sqoopBean;
    }

    public void setSqoopBean(SqoopBean sqoopBean) {
        this.sqoopBean = sqoopBean;
    }

    public SqoopImportMeta() {
        super();
    }
    public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name ) {
        return new SqoopImportDialog( shell, meta, transMeta, name );
    }
    @Override
    public StepInterface getStep( StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta,
                                  Trans disp ) {
        return new SqoopImportStep( stepMeta, stepDataInterface, cnr, transMeta, disp );
    }



    @Override
    public StepDataInterface getStepData() {
        return new SqoopStepData();
    }
    @Override
    public void setDefault() {
        if(null==sqoopBean){
            sqoopBean=new SqoopBean();
        }
        sqoopBean.setUri(ConstAttr.URI);
        sqoopBean.setOutput(ConstAttr.OUTPUT);
        sqoopBean.setArgument(ConstAttr.ARGUMENT);
        sqoopBean.setMethod(ConstAttr.METHOD);
        sqoopBean.setYarn(ConstAttr.YARN);
    }
    public Object clone() {
        Object retval = super.clone();
        return retval;
    }

    public String getXML() throws KettleValueException {
        StringBuilder xml = new StringBuilder();
        xml.append( XMLHandler.addTagValue( ConstAttr.ARGUMENT, sqoopBean.getArgument()) );
        xml.append( XMLHandler.addTagValue( ConstAttr.OUTPUT, sqoopBean.getOutput()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.METHOD, sqoopBean.getMethod()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.URI, sqoopBean.getUri()) ) ;
        xml.append( XMLHandler.addTagValue( ConstAttr.YARN, sqoopBean.getYarn()) ) ;
        return xml.toString();
    }
    public void loadXML(Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore ) throws KettleXMLException {
        try {
            if(null==sqoopBean){
                sqoopBean=new SqoopBean();
            }
            sqoopBean.setUri( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.URI) ) );
            sqoopBean.setOutput( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.OUTPUT ) ) );
            sqoopBean.setYarn( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.YARN) ) );
            sqoopBean.setMethod( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.METHOD) ) );
            sqoopBean.setArgument( XMLHandler.getNodeValue( XMLHandler.getSubNode( stepnode, ConstAttr.ARGUMENT) ) );
        } catch ( Exception e ) {
            throw new KettleXMLException( "SqoopBean plugin unable to read step info from XML node", e );
        }
    }
    public void saveRep(Repository rep, IMetaStore metaStore, ObjectId id_transformation, ObjectId id_step )
            throws KettleException {
        try {
            if(null==sqoopBean){
                sqoopBean=new SqoopBean();
            }
            rep.saveStepAttribute( id_transformation, id_step, ConstAttr.URI, sqoopBean.getUri() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.OUTPUT, sqoopBean.getOutput() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.YARN, sqoopBean.getYarn() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.METHOD, sqoopBean.getMethod() );
            rep.saveStepAttribute( id_transformation, id_step,  ConstAttr.ARGUMENT, sqoopBean.getArgument() );
        } catch ( Exception e ) {
            throw new KettleException( "Unable to save step into repository: " + id_step, e );
        }
    }
    public void readRep( Repository rep, IMetaStore metaStore, ObjectId id_step, List<DatabaseMeta> databases )
            throws KettleException {
        try {
            if(null==sqoopBean){
                sqoopBean=new SqoopBean();
            }
            sqoopBean.setUri(rep.getStepAttributeString( id_step,  ConstAttr.URI));
            sqoopBean.setOutput(rep.getStepAttributeString( id_step,  ConstAttr.OUTPUT));
            sqoopBean.setArgument(rep.getStepAttributeString( id_step,  ConstAttr.ARGUMENT));
            sqoopBean.setMethod(rep.getStepAttributeString( id_step,  ConstAttr.METHOD));
            sqoopBean.setYarn(rep.getStepAttributeString( id_step,  ConstAttr.YARN));
        } catch ( Exception e ) {
            throw new KettleException( "Unable to load step from repository", e );
        }
    }

}