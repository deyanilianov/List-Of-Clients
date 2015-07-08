package UI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import client.Client;

public class DataPanel extends Composite {

    private Text txtName;
    private Text txtNote;

    private Label lblNote;
    private Text txtCity;
    private Text txtDate;
    private Label lblDate;

    private Composite logoPlace;

    private Client selectedClient;

    // default
    private static int logoHeight = 0;
    private static int logoWidth = 0;

    private static final int MAX_ACCEPTABLE_HEIGHT = 200;
    private static final int MAX_ACCEPTABLE_WIDTH = 200;

    public DataPanel(Composite parent, int style) {
        super(parent, SWT.BORDER);

        txtName = new Text(this, SWT.WRAP);
        txtName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        txtName.setEditable(false);
        txtName.setBounds(71, 10, 357, 33);

        txtNote = new Text(this, SWT.WRAP | SWT.V_SCROLL);
        txtNote.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        txtNote.setBounds(71, 49, 357, 126);
        txtNote.setEditable(false);
        txtNote.addListener(SWT.Resize, scrollBarListener());
        txtNote.addListener(SWT.Modify, scrollBarListener());
        txtNote.setText("");

        Label lblName = new Label(this, SWT.NONE);
        lblName.setAlignment(SWT.RIGHT);
        lblName.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
        lblName.setBounds(10, 7, 55, 26);
        lblName.setText("Name:");

        lblNote = new Label(this, SWT.NONE);
        lblNote.setAlignment(SWT.RIGHT);
        lblNote.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
        lblNote.setBounds(10, 46, 55, 26);
        lblNote.setText("Note:");

        txtCity = new Text(this, SWT.NONE);
        txtCity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        txtCity.setEditable(false);
        txtCity.setBounds(71, 181, 357, 21);

        Label lblCity = new Label(this, SWT.NONE);
        lblCity.setAlignment(SWT.RIGHT);
        lblCity.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
        lblCity.setBounds(10, 178, 55, 21);
        lblCity.setText("City:");

        txtDate = new Text(this, SWT.NONE);
        txtDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        txtDate.setBounds(71, 208, 357, 21);
        txtDate.setEditable(false);

        lblDate = new Label(this, SWT.NONE);
        lblDate.setAlignment(SWT.RIGHT);
        lblDate.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
        lblDate.setBounds(10, 205, 55, 21);
        lblDate.setText("Date:");

        Label lblLogo = new Label(this, SWT.NONE);
        lblLogo.setAlignment(SWT.RIGHT);
        lblLogo.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
        lblLogo.setBounds(10, 232, 55, 21);
        lblLogo.setText("Logo");

        logoPlace = new Composite(this, SWT.NONE);
        logoPlace.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
        logoPlace.setBounds(71, 235, 201, 201);

        logoPlace.addListener(SWT.Paint, new Listener() {
            @Override
            public void handleEvent(Event e) {
                if (selectedClient != null) {
                    selectedClient.setClienHaveLogo(false);
                }

                if (selectedClient != null && selectedClient.getLogoPath() != null && !selectedClient.getLogoPath().isEmpty()) {
                    GC gc2 = new GC(logoPlace);
                    Image img = null;
                    try {
                        img = new Image(Display.getDefault(), selectedClient.getLogoPath());
                        calcNewBounds(img);

                        gc2.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 0, 0, logoWidth, logoHeight);
                        selectedClient.setClienHaveLogo(true);
                    } catch (Exception exc) {
                        selectedClient.setClienHaveLogo(false);
                    } finally {
                        if (img != null) {
                            img.dispose();
                        } else {
                        }
                        gc2.dispose();
                    }
                }

            }
        });

        logoPlace.addListener(SWT.MouseDown, new Listener() {
            @Override
            public void handleEvent(Event event) {
                if (selectedClient != null && selectedClient.getLogoPath() != null && !selectedClient.getLogoPath().isEmpty()) {
                    if (selectedClient.isClienHaveLogo()) {
                        if ((logoWidth >= event.x) && (logoHeight >= event.y)) {
                            Program.launch(selectedClient.getLogoPath());
                        }
                    }
                }
            }
        });

    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

    }

    public void showNewClient(Client client) {
        this.setSelectedClient(client);

        txtName.setText(client.getName());
        txtNote.setText(client.getNote());
        txtCity.setText(client.getPlace());
        txtDate.setText(client.getSigningContractDate());

        logoPlace.redraw();
    }

    public void clearClient() {
        txtName.setText("");
        txtNote.setText("");
        txtCity.setText("");
        txtDate.setText("");

        this.setSelectedClient(null);
        logoPlace.redraw();
    }

    public Listener scrollBarListener() {
        Listener scListener = new Listener() {
            @Override
            public void handleEvent(Event event) {
                Text t = (Text) event.widget;
                Rectangle r1 = t.getClientArea();
                Rectangle r2 = t.computeTrim(r1.x, r1.y, r1.width, r1.height);
                Point p = t.computeSize(r1.x, SWT.DEFAULT, true);
                t.getVerticalBar().setVisible(r2.height <= p.y);
                if (event.type == SWT.Modify) {
                    t.getParent().layout(true);
                    t.showSelection();
                }
            }
        };
        return scListener;
    }

    private static void calcNewBounds(Image image) {
        if (image == null) {
            return;
        }
        int newHeight = 0;
        int newWidth = 0;
        int height = image.getBounds().height;
        int width = image.getBounds().width;

        if (height > MAX_ACCEPTABLE_HEIGHT || width > MAX_ACCEPTABLE_WIDTH) {
            if (height > width) {
                float ratio = (float) height / width;
                newHeight = MAX_ACCEPTABLE_HEIGHT;
                newWidth = (int) (newHeight / ratio);

                logoHeight = MAX_ACCEPTABLE_HEIGHT;
                logoWidth = (int) (newHeight / ratio);
            } else {
                float ratio = (float) width / height;
                newWidth = MAX_ACCEPTABLE_WIDTH;
                newHeight = (int) (newWidth / ratio);

                logoWidth = MAX_ACCEPTABLE_WIDTH;
                logoHeight = (int) (newWidth / ratio);
            }
        } else {
            logoHeight = height;
            logoWidth = width;
        }
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }
}
