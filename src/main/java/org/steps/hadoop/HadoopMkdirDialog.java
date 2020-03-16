package org.steps.hadoop;

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
public class HadoopMkdirDialog extends BaseStepDialog implements StepDialogInterface {
    private static final Class<?> PKG = HadoopMkdirMeta.class;
    private HadoopMkdirMeta meta;
    private LabelText user;
    private LabelText uri;
    private LabelText path;
    private LabelText output;

    public HadoopMkdirDialog(Shell parent, Object in, TransMeta transMeta, String sname ) {
        super( parent, (BaseStepMeta) in, transMeta, sname );
        meta = (HadoopMkdirMeta) in;
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
        shell.setText( BaseMessages.getString( PKG, "Tika.Shell.Title" ) );
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

        user = new LabelText( shell, BaseMessages.getString( PKG, "Hadoop.Exectutor.Label" ), null );
        props.setLook( user );
        user.addModifyListener( lsMod );
        FormData fdValName = new FormData();
        fdValName.left = new FormAttachment( 0, 0 );
        fdValName.right = new FormAttachment( 100, 0 );
        fdValName.top = new FormAttachment( wStepname, 25 );
        user.setLayoutData( fdValName );

        path = new LabelText( shell, BaseMessages.getString( PKG, "Hadoop.Path.Label" ), null );
        props.setLook( path );
        path.addModifyListener( lsMod );
        FormData fdValName1 = new FormData();
        fdValName1.left = new FormAttachment( 0, 0 );
        fdValName1.right = new FormAttachment( 100, 0 );
        fdValName1.top = new FormAttachment( wStepname, 50 );
        path.setLayoutData( fdValName1 );

        uri = new LabelText( shell, BaseMessages.getString( PKG, "Hadoop.Uri.Label" ), null );
        props.setLook( uri );
        uri.addModifyListener( lsMod );
        FormData fdValName2 = new FormData();
        fdValName2.left = new FormAttachment( 0, 0 );
        fdValName2.right = new FormAttachment( 100, 0 );
        fdValName2.top = new FormAttachment( wStepname, 75 );
        uri.setLayoutData( fdValName2 );

        output = new LabelText( shell, BaseMessages.getString( PKG, "Hadoop.output.Label" ), null );
        props.setLook( output );
        output.addModifyListener( lsMod );
        FormData fdValName3 = new FormData();
        fdValName3.left = new FormAttachment( 0, 0 );
        fdValName3.right = new FormAttachment( 100, 0 );
        fdValName3.top = new FormAttachment( wStepname, 100 );
        output.setLayoutData( fdValName3);

        // OK and cancel buttons
        wOK = new Button( shell, SWT.PUSH );
        wOK.setText( BaseMessages.getString( PKG, "System.Button.OK" ) );
        wCancel = new Button( shell, SWT.PUSH );
        wCancel.setText( BaseMessages.getString( PKG, "System.Button.Cancel" ) );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, user );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, uri );
        setButtonPositions( new Button[] { wOK, wCancel }, margin, path );
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
        user.addSelectionListener( lsDef );
        uri.addSelectionListener( lsDef );
        path.addSelectionListener( lsDef );
        output.addSelectionListener( lsDef );
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
        user.setText(null==meta.getHadoop().getUser()?"": meta.getHadoop().getUser());
        uri.setText(null==meta.getHadoop().getUri()?"": meta.getHadoop().getUri());
        path.setText(null==meta.getHadoop().getPath()?"": meta.getHadoop().getPath());
        output.setText(null==meta.getHadoop().getOutput()?"": meta.getHadoop().getOutput());
    }

    /**
     * Called when the user cancels the dialog.
     */
    private void cancel() {
        stepname = null;
        meta.setChanged( changed );
        dispose();
    }

    /**
     * Called when the user confirms the dialog
     */
    private void ok() {
        stepname = wStepname.getText();
        meta.getHadoop().setUser(user.getText() );
        meta.getHadoop().setUri(uri.getText() );
        meta.getHadoop().setPath(path.getText() );
        meta.getHadoop().setOutput(output.getText() );
        dispose();
    }
}