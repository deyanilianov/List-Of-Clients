package UI;

import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import client.Client;
import client.ClientList;

public class TableClients extends Table {

	private static final String[] clmNames = { "Name", "Place", "Note", "Date" };
	private static List<Client> clients = ClientList.getInstance().getArrayList();

	private static int lastIndex = -1;

	private TableClients table;
	private DataPanel dp;

	public TableClients(Composite parent, int style, DataPanel dp) {
		super(parent, style);
		this.table = this;
		this.dp = dp;

		TableColumn tblclmnSecondName;
		for (int i = 0; i < clmNames.length; i++) {
			tblclmnSecondName = new TableColumn(this, SWT.NONE);
			tblclmnSecondName.setWidth(100);
			tblclmnSecondName.setText(clmNames[i]);
		}

		this.addListener(SWT.Selection, selectionListener());
		this.addListener(SWT.MouseDoubleClick, doubleClickListener());
	}

	public void initializeTable() {
		TableItem item;
		for (Client client : clients) {
			item = new TableItem(this, SWT.NONE);
			item.setText(new String[] { client.getName(), client.getPlace(), client.getNote(), client.getSigningContractDate() });
		}
	}

	private Listener selectionListener() {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				int index = table.getSelectionIndex();

				if (lastIndex == index && clients.size() > 1) {
					return;
				}
				dp.showNewClient(clients.get(index));
				lastIndex = index;
			}
		};
	}

	private Listener doubleClickListener() {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				// when double click for edit
				Point pt = new Point(event.x, event.y);
				TableItem item = table.getItem(pt);
				if (item == null) {
					return;
				}
				final Client clientToBeEditing = clients.get(table.indexOf(item));

				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						new ModalDialogEditClient(table, clientToBeEditing, clients);
						dp.showNewClient(clientToBeEditing);
					}
				});
			}
		};
	}

	@Override
	protected void checkSubclass() {
	}
}
