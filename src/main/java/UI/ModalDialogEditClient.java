package UI;

import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;

import Utill.DateValidator;
import Utill.Util;
import XML.XMLWriter;
import client.Client;

public class ModalDialogEditClient extends AbstractModalDialog {

    public ModalDialogEditClient(Table table, Client client, List<Client> clients) {
        super(table, client, clients);
    }

    @Override
    protected SelectionAdapter btnOkListener() {
        SelectionAdapter editClient = new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent event) {
                if (txtName.getText().trim().isEmpty()) {
                    lblNameErr.setText("Please enter client name!");
                    return;
                }

                if (!name.equals(txtName.getText())) {
                    if (!Util.isNameValid(txtName.getText(), clients)) {
                        lblNameErr.setText("Client name already exist!");
                        return;
                    }
                }

                if (!date.getText().isEmpty()) {
                    DateValidator dv = new DateValidator();
                    if (!dv.validate(date.getText())) {
                        lblDateErr.setText("Invalid date! dd.mm.yyyy");
                        return;
                    }
                }

                editClient(client);
                table.getItem(clients.indexOf(client)).setText(
                        new String[] { client.getName(), client.getPlace(), client.getNote(), client.getSigningContractDate() });
                new Thread(new XMLWriter()).start();
                dialog.dispose();
            }
        };
        return editClient;
    }

    private static List<Client> editClient(Client client) {
        client.setName(txtName.getText().trim());
        client.setNote(txtNote.getText().trim());
        client.setPlace(cboxLocation.getText().trim());
        client.setSigningContractDate(date.getText().trim());
        client.setLogoPath(txtLogo.getText().trim());

        return clients;
    }
}
