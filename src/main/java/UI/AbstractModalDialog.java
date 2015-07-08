package UI;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import Utill.Util;
import client.Client;

public abstract class AbstractModalDialog {

    private static Display display;
    protected static Shell dialog;

    protected static List<Client> clients;
    protected static Table table;
    protected static Client client;
    protected String name;

    protected static Text txtName;
    protected static Text txtNote;
    protected static ComboBox cboxLocation;
    protected static DateTextField date;
    protected static Text txtLogo;

    protected Label lblNameErr;
    protected Label lblDateErr;

    protected static Button btnLogo;
    protected static Button btnOk;
    protected static Button btnCancel;

    @SuppressWarnings("static-access")
    public AbstractModalDialog(Table table, Client client, List<Client> clients) {
        display = Display.getDefault();
        dialog = new Shell(display, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

        this.clients = clients;
        this.table = table;
        this.client = client;

        Point pt = display.getCursorLocation();
        dialog.setLocation(pt.x, pt.y);
        dialog.setText("Dialog Shell");
        dialog.setSize(409, 350);

        txtName = new Text(dialog, SWT.BORDER);
        txtName.setBounds(10, 10, 250, 21);
        txtName.setTextLimit(50);
        txtName.setToolTipText("Name of the client");
        txtName.setText(client != null ? client.getName() : "");
        txtName.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent arg0) {
            }

            @Override
            public void focusGained(FocusEvent arg0) {
                lblNameErr.setText("");
            }
        });

        this.name = txtName.getText();

        txtNote = new Text(dialog, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        txtNote.setBounds(10, 70, 250, 120);
        txtNote.setTextLimit(2000);
        txtNote.setToolTipText("Note");
        txtNote.setText(client != null ? client.getNote() : "");

        cboxLocation = new ComboBox(dialog, SWT.NONE);
        cboxLocation.setBounds(10, 40, 250, 21);
        cboxLocation.setToolTipText("City");
        cboxLocation.setText(client != null ? client.getPlace() : "");

        date = new DateTextField(dialog, SWT.BORDER);
        date.setBounds(10, 200, 250, 21);
        date.setToolTipText("Date format dd.mm.yyyy");
        date.setText(client != null ? client.getSigningContractDate() : "");
        date.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent arg0) {
            }

            @Override
            public void focusGained(FocusEvent arg0) {
                lblDateErr.setText("");
            }
        });

        txtLogo = new Text(dialog, SWT.BORDER);
        txtLogo.setBounds(90, 232, 290, 21);
        txtLogo.setEditable(false);
        txtLogo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        txtLogo.setText(client != null ? client.getLogoPath() != null ? client.getLogoPath() : "" : "");

        btnLogo = new Button(dialog, SWT.PUSH);
        btnLogo.setBounds(10, 230, 75, 25);
        btnLogo.setText("Logo");
        btnLogo.addSelectionListener(addClientLogo());

        btnOk = new Button(dialog, SWT.PUSH);
        btnOk.setBounds(10, 260, 75, 25);
        btnOk.setText("Ok");
        btnOk.addSelectionListener(btnOkListener());

        btnCancel = new Button(dialog, SWT.NONE);
        btnCancel.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                dialog.dispose();
            }
        });
        btnCancel.setText("Cancel");
        btnCancel.setBounds(90, 260, 75, 25);

        lblNameErr = new Label(dialog, SWT.NONE);
        lblNameErr.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
        lblNameErr.setBounds(266, 13, 136, 15);

        lblDateErr = new Label(dialog, SWT.NONE);
        lblDateErr.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
        lblDateErr.setBounds(266, 201, 136, 15);

        dialog.open();
        while (!dialog.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        dialog.dispose();
    }

    protected SelectionAdapter btnOkListener() {
        return null;
    }

    private static SelectionAdapter addClientLogo() {
        SelectionAdapter addLogo = new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                txtLogo.setText("");
                FileDialog dlg = new FileDialog(dialog, SWT.OPEN);
                String fn = dlg.open();
                if (Util.isValideExtention(dlg.getFileName())) {
                    txtLogo.setText(fn);
                } else {
                    txtLogo.setText("");
                }
            }
        };
        return addLogo;
    }
}
