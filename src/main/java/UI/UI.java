package UI;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;

import Utill.TableRePaint;
import XML.XMLReader;
import XML.XMLWriter;
import client.Client;
import client.ClientList;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class UI {

	private static Shell shell;
	private Button btnAdd;
	private Button btnDelete;
	private Button btnEdit;

	private DataPanel dp;
	private TableClients table;
	private Menu menu;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UI window = new UI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		final List<Client> clients = new XMLReader().ReadXML();

		shell = new Shell(SWT.DIALOG_TRIM | SWT.MIN);
		shell.setSize(913, 559);
		shell.setText("Deyan Kyusashki");

		dp = new DataPanel(shell, SWT.NONE);
		dp.setBounds(422, 10, 460, 450);

		table = new TableClients(shell, SWT.NO_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION, dp);
		table.initializeTable();
		table.setBounds(10, 10, 406, 450);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		btnAdd = new Button(shell, SWT.NONE);
		btnAdd.setBounds(10, 466, 75, 25);
		btnAdd.setText("Add");
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ModalDialogAddClient(table, null, clients);
			}
		});

		btnDelete = new Button(shell, SWT.NONE);
		btnDelete.setBounds(173, 466, 75, 25);
		btnDelete.setText("Delete");
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = table.getSelectionIndex();
				if (index != -1) {
					clients.remove(index);

					table.remove(index);
					table.deselect(index);

					dp.clearClient();

					new Thread(new XMLWriter()).start();
				}
			}
		});

		btnEdit = new Button(shell, SWT.NONE);
		btnEdit.setBounds(91, 466, 75, 25);
		btnEdit.setText("Edit");
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = table.getSelectionIndex();
				if (index != -1) {
					Client clientToBeEditing = clients.get(index);
					new ModalDialogEditClient(table, clientToBeEditing, clients);

					dp.showNewClient(clientToBeEditing);
				}
			}
		});

		menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("File");

		Menu menu_clear = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_clear);

		MenuItem mntmClear = new MenuItem(menu_clear, SWT.PUSH);
		mntmClear.setText("Clear");
		mntmClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ClientList.getInstance().getArrayList().clear();
				new TableRePaint(table, ClientList.getInstance().getArrayList()).repaint();
				new Thread(new XMLWriter()).start();

				dp.clearClient();
			}
		});
	}
}
