package org.steps.hbase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.core.widget.LabelText;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-17 10:31
 **/
public class HbaseCreateTableDialog extends BaseStepDialog implements StepDialogInterface {
    private static final Class<?> PKG = HbaseCreateTableDialog.class;
    private HbaseCreateTableMeta meta;
    private LabelText zoopeckerHost;
    private LabelText zoopeckerPort;
    private LabelText master;
    private LabelText table;
    private LabelText familly;
    private LabelText output;


    public HbaseCreateTableDialog(Shell parent, Object in, TransMeta transMeta, String sname ) {
        super( parent, (BaseStepMeta) in, transMeta, sname );
        meta = (HbaseCreateTableMeta) in;
    }
    @Override
    public String open() {
        Shell parent = getParent();
        Display display = parent.getDisplay();

        shell = new Shell( parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX );
        props.setLook( shell );
        setShellImage( shell, meta );
        changed = meta.hasChanged();

        ModifyListener lsMod = new ModifyListener() {
            public void modifyText( ModifyEvent e ) {
                meta.setChanged();
            }
        };

        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = Const.FORM_MARGIN;
        formLayout.marginHeight = Const.FORM_MARGIN;
        shell.setLayout( formLayout );
        shell.setText( BaseMessages.getString( PKG, "HBase.CreateTable.Shell.Title" ) );
        int middle = props.getMiddlePct();
        int margin = Const.MARGIN;

        wlStepname = new Label( shell, SWT.RIGHT );
        wlStepname.setText( BaseMessages.getString( PKG, "System.Label.StepName" ) );
        props.setLook( wlStepname );
        fdlStepname = new FormData();
        fdlStepname.left = new FormAttachment( 0, 0 );
        fdlStepname.right = new FormAttachment( middle, -margin );
        fdlStepname.top = new FormAttachment( 0, margin );
        wlStepname.setLayoutData( fdlStepname );

        wStepname = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
        wStepname.setText( stepname );
        props.setLook( wStepname );
        wStepname.addModifyListener( lsMod );
        fdStepname = new FormData();
        fdStepname.left = new FormAttachment( middle, 0 );
        fdStepname.top = new FormAttachment( 0, margin );
        fdStepname.right = new FormAttachment( 100, 0 );
        wStepname.setLayoutData( fdStepname );

        zoopeckerHost = new LabelText( shell, BaseMessages.getString( PKG, "Hbase.Host.Label" ), null );
        props.setLook( zoopeckerHost );
        zoopeckerHost.addModifyListener( lsMod );
        FormData fdValName = new FormData();
        fdValName.left = new FormAttachment( 0, 0 );
        fdValName.right = new FormAttachment( 100, 0 );
        fdValName.top = new FormAttachment( wStepname, 25 );
        zoopeckerHost.setLayoutData( fdValName );

        zoopeckerPort = new LabelText( shell, BaseMessages.getString( PKG, "Hbase.Port.Label" ), null );
        props.setLook( zoopeckerPort );
        zoopeckerPort.addModifyListener( lsMod );
        FormData fdValName1 = new FormData();
        fdValName1.left = new FormAttachment( 0, 0 );
        fdValName1.right = new FormAttachment( 100, 0 );
        fdValName1.top = new FormAttachment( wStepname, 50 );
        zoopeckerPort.setLayoutData( fdValName1 );

        master = new LabelText( shell, BaseMessages.getString( PKG, "Hbase.Master.Label" ), null );
        props.setLook( master );
        master.addModifyListener( lsMod );
        FormData fdValName2 = new FormData();
        fdValName2.left = new FormAttachment( 0, 0 );
        fdValName2.right = new FormAttachment( 100, 0 );
        fdValName2.top = new FormAttachment( wStepname, 75 );
        master.setLayoutData( fdValName2 );

        table = new LabelText( shell, BaseMessages.getString( PKG, "Hbase.Table.Label" ), null );
        props.setLook( table );
        table.addModifyListener( lsMod );
        FormData fdValName3 = new FormData();
        fdValName3.left = new FormAttachment( 0, 0 );
        fdValName3.right = new FormAttachment( 100, 0 );
        fdValName3.top = new FormAttachment( wStepname, 75 );
        table.setLayoutData( fdValName3 );

        familly = new LabelText( shell, BaseMessages.getString( PKG, "Hbase.Familly.Label" ), null );
        props.setLook( familly );
        familly.addModifyListener( lsMod );
        FormData fdValName4 = new FormData();
        fdValName4.left = new FormAttachment( 0, 0 );
        fdValName4.right = new FormAttachment( 100, 0 );
        fdValName4.top = new FormAttachment( wStepname, 100 );
        familly.setLayoutData( fdValName4);

        output = new LabelText( shell, BaseMessages.getString( PKG, "Hbase.Output.Label" ), null );
        props.setLook( output );
        output.addModifyListener( lsMod );
        FormData fdValName5 = new FormData();
        fdValName5.left = new FormAttachment( 0, 0 );
        fdValName5.right = new FormAttachment( 100, 0 );
        fdValName5.top = new FormAttachment( wStepname, 125);
        output.setLayoutData( fdValName5);

        // OK and cancel buttons
        wOK = new Button( shell, SWT.PUSH );
        wOK.setText( BaseMessages.getString( PKG, "System.Button.OK" ) );
        wCancel = new Button( shell, SWT.PUSH );
        wCancel.setText( BaseMessages.getString( PKG, "System.Button.Cancel" ) );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, zoopeckerHost );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, zoopeckerPort );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, master );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, familly );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, table );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, output );

        lsCancel = new Listener() {
            public void handleEvent( Event e ) {
                cancel();
            }
        };
        lsOK = new Listener() {
            public void handleEvent( Event e ) {
                ok();
            }
        };
        wCancel.addListener( SWT.Selection, lsCancel );
        wOK.addListener( SWT.Selection, lsOK );

        lsDef = new SelectionAdapter() {
            public void widgetDefaultSelected( SelectionEvent e ) {
                ok();
            }
        };
        wStepname.addSelectionListener( lsDef );
        zoopeckerHost.addSelectionListener( lsDef );
        zoopeckerPort.addSelectionListener( lsDef );
        master.addSelectionListener( lsDef );
        table.addSelectionListener( lsDef );
        familly.addSelectionListener( lsDef );

        output.addSelectionListener( lsDef );
        wCancel.setText( BaseMessages.getString( PKG, "System.Button.Cancel" ) );
        shell.addShellListener( new ShellAdapter() {
            public void shellClosed( ShellEvent e ) {
                cancel();
            }
        } );
        setSize();
        populateDialog();
        meta.setChanged( changed );
        shell.open();
        while ( !shell.isDisposed() ) {
            if ( !display.readAndDispatch() ) {
                display.sleep();
            }
        }
        return stepname;
    }
    private void populateDialog() {
        wStepname.selectAll();
        zoopeckerHost.setText(null==meta.getHbaseBean().getZoopeckerHost()?"": meta.getHbaseBean().getZoopeckerHost());
        zoopeckerPort.setText(null==meta.getHbaseBean().getZoopeckerPort()?"": meta.getHbaseBean().getZoopeckerPort());
        master.setText(null==meta.getHbaseBean().getMaster()?"": meta.getHbaseBean().getMaster());
        table.setText(null==meta.getHbaseBean().getTable()?"": meta.getHbaseBean().getTable());
        familly.setText(null==meta.getHbaseBean().getFamilly()?"": meta.getHbaseBean().getFamilly());
        output.setText(null==meta.getHbaseBean().getOutput()?"": meta.getHbaseBean().getOutput());
    }


    private void cancel() {
        stepname = null;
        meta.setChanged( changed );
        dispose();
    }

    private void ok() {
        stepname = wStepname.getText();
        meta.getHbaseBean().setZoopeckerHost(zoopeckerHost.getText());
        meta.getHbaseBean().setZoopeckerPort(zoopeckerPort.getText() );
        meta.getHbaseBean().setFamilly(familly.getText() );
        meta.getHbaseBean().setTable(table.getText() );
        meta.getHbaseBean().setMaster(master.getText() );
        meta.getHbaseBean().setOutput(output.getText() );
        dispose();
    }
}