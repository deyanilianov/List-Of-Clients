package UI;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import Utill.DateValidator;
import Utill.Util;
import XML.XMLWriter;
import client.Client;

public class ModalDialogAddClient extends AbstractModalDialog {

	public ModalDialogAddClient(Table table, Client client, List<Client> clients) {
		super(table, client, clients);
	}

	@Override
	protected SelectionAdapter btnOkListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Client newClient = new Client();
				newClient.setName(txtName.getText().trim());
				newClient.setNote(txtNote.getText().trim());
				newClient.setPlace(cboxLocation.getText().trim());
				newClient.setSigningContractDate(date.getText().trim());

				if (txtLogo.getText() != null && !txtLogo.getText().trim().isEmpty()) {
					if (Util.isValideExtention(txtLogo.getText())) {
						newClient.setLogoPath(txtLogo.getText().trim());
					} else {
						newClient.setLogoPath("");
					}
				} else {
					newClient.setLogoPath("");
				}

				if (txtName.getText().trim().isEmpty()) {
					lblNameErr.setText("Please enter client name!");
					return;
				}

				if (!Util.isNameValid(newClient.getName(), clients)) {
					lblNameErr.setText("Client name already exist!");
					return;
				}

				if (!date.getText().isEmpty()) {
					DateValidator dv = new DateValidator();
					if (!dv.validate(date.getText())) {
						lblDateErr.setText("Invalid date! dd.mm.yyyy");
						return;
					}
				}

				clients.add(newClient);

				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { newClient.getName(), newClient.getPlace(), newClient.getNote(), newClient.getSigningContractDate() });

				new Thread(new XMLWriter()).start();
				dialog.dispose();
			}
		};
	}

}
