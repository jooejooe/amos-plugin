package org.steps.sqoop;

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
public class SqoopImportDialog extends BaseStepDialog implements StepDialogInterface {
    private static final Class<?> PKG = SqoopImportDialog.class;
    private SqoopImportMeta meta;
    private LabelText argument;
    private LabelText method;
    private LabelText uri;
    private LabelText yarn;
    private LabelText output;

    public SqoopImportDialog(Shell parent, Object in, TransMeta transMeta, String sname ) {
        super( parent, (BaseStepMeta) in, transMeta, sname );
        meta = (SqoopImportMeta) in;
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
        shell.setText( BaseMessages.getString( PKG, "Sqoop.Import.Shell.Title" ) );
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

        argument = new LabelText( shell, BaseMessages.getString( PKG, "Sqoop.Argument.Label" ), null );
        props.setLook( argument );
        argument.addModifyListener( lsMod );
        FormData fdValName = new FormData();
        fdValName.left = new FormAttachment( 0, 0 );
        fdValName.right = new FormAttachment( 100, 0 );
        fdValName.top = new FormAttachment( wStepname, 25 );
        argument.setLayoutData( fdValName );


        method = new LabelText( shell, BaseMessages.getString( PKG, "Sqoop.Method.Label" ), null );
        props.setLook( method );
        method.addModifyListener( lsMod );
        FormData fdValName1 = new FormData();
        fdValName1.left = new FormAttachment( 0, 0 );
        fdValName1.right = new FormAttachment( 100, 0 );
        fdValName1.top = new FormAttachment( wStepname, 50 );
        method.setLayoutData( fdValName1 );

        uri = new LabelText( shell, BaseMessages.getString( PKG, "Sqoop.Uri.Label" ), null );
        props.setLook( uri );
        uri.addModifyListener( lsMod );
        FormData fdValName2 = new FormData();
        fdValName2.left = new FormAttachment( 0, 0 );
        fdValName2.right = new FormAttachment( 100, 0 );
        fdValName2.top = new FormAttachment( wStepname, 75 );
        uri.setLayoutData( fdValName2 );

        yarn = new LabelText( shell, BaseMessages.getString( PKG, "Sqoop.Yarn.Label" ), null );
        props.setLook( yarn );
        yarn.addModifyListener( lsMod );
        FormData fdValName3 = new FormData();
        fdValName3.left = new FormAttachment( 0, 0 );
        fdValName3.right = new FormAttachment( 100, 0 );
        fdValName3.top = new FormAttachment( wStepname, 100 );
        yarn.setLayoutData( fdValName3 );


        output = new LabelText( shell, BaseMessages.getString( PKG, "Sqoop.Output.Label" ), null );
        props.setLook( output );
        output.addModifyListener( lsMod );
        FormData fdValName4 = new FormData();
        fdValName4.left = new FormAttachment( 0, 0 );
        fdValName4.right = new FormAttachment( 100, 0 );
        fdValName4.top = new FormAttachment( wStepname, 125);
        output.setLayoutData( fdValName4);

        // OK and cancel buttons
        wOK = new Button( shell, SWT.PUSH );
        wOK.setText( BaseMessages.getString( PKG, "System.Button.OK" ) );
        wCancel = new Button( shell, SWT.PUSH );
        wCancel.setText( BaseMessages.getString( PKG, "System.Button.Cancel" ) );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, uri );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, argument );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, method );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, uri );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, yarn );
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
        uri.addSelectionListener( lsDef );
        argument.addSelectionListener( lsDef );
        method.addSelectionListener( lsDef );
        yarn.addSelectionListener( lsDef );
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
        uri.setText(null==meta.getSqoopBean().getUri()?"": meta.getSqoopBean().getUri());
        output.setText(null==meta.getSqoopBean().getOutput()?"": meta.getSqoopBean().getOutput());
        argument.setText(null==meta.getSqoopBean().getArgument()?"": meta.getSqoopBean().getArgument());
        method.setText(null==meta.getSqoopBean().getMethod()?"": meta.getSqoopBean().getMethod());
        yarn.setText(null==meta.getSqoopBean().getYarn()?"": meta.getSqoopBean().getYarn());
    }


    private void cancel() {
        stepname = null;
        meta.setChanged( changed );
        dispose();
    }

    private void ok() {
        stepname = wStepname.getText();
        meta.getSqoopBean().setUri(uri.getText());
        meta.getSqoopBean().setOutput(output.getText() );
        meta.getSqoopBean().setYarn(yarn.getText() );
        meta.getSqoopBean().setMethod(method.getText() );
        meta.getSqoopBean().setArgument(argument.getText() );
        dispose();
    }
}