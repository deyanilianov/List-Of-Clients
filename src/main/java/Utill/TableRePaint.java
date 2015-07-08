package Utill;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import client.Client;

public class TableRePaint {

    private Table table;
    private List<Client> clients;

    public TableRePaint(Table table, List<Client> clients) {
        this.table = table;
        this.clients = clients;
    }

    public void repaint() {
        table.removeAll();
        TableItem item;

        for (int i = 0; i < clients.size(); i++) {
            item = new TableItem(table, SWT.NONE);
            item.setText(new String[] { clients.get(i).getName(), clients.get(i).getPlace(), clients.get(i).getNote(),
                    clients.get(i).getSigningContractDate() });
        }
    }

}
