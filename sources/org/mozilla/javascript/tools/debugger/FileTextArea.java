package org.mozilla.javascript.tools.debugger;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextArea;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;

/* loaded from: classes5.dex */
class FileTextArea extends JTextArea implements ActionListener, PopupMenuListener, KeyListener, MouseListener {
    private static final long serialVersionUID = -25032065448563720L;
    private FilePopupMenu popup;

    /* renamed from: w, reason: collision with root package name */
    private FileWindow f26765w;

    public FileTextArea(FileWindow fileWindow) {
        this.f26765w = fileWindow;
        FilePopupMenu filePopupMenu = new FilePopupMenu(this);
        this.popup = filePopupMenu;
        filePopupMenu.addPopupMenuListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setFont(new Font("Monospaced", 0, 12));
    }

    private void checkPopup(MouseEvent mouseEvent) {
        if (mouseEvent.isPopupTrigger()) {
            this.popup.show(this, mouseEvent.getX(), mouseEvent.getY());
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        int lineOfOffset;
        FilePopupMenu filePopupMenu = this.popup;
        int iViewToModel = viewToModel(new Point(filePopupMenu.f26763x, filePopupMenu.f26764y));
        this.popup.setVisible(false);
        String actionCommand = actionEvent.getActionCommand();
        try {
            lineOfOffset = getLineOfOffset(iViewToModel);
        } catch (Exception unused) {
            lineOfOffset = -1;
        }
        if (actionCommand.equals("Set Breakpoint")) {
            this.f26765w.setBreakPoint(lineOfOffset + 1);
        } else if (actionCommand.equals("Clear Breakpoint")) {
            this.f26765w.clearBreakPoint(lineOfOffset + 1);
        } else if (actionCommand.equals("Run")) {
            this.f26765w.load();
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 127) {
            switch (keyCode) {
            }
        }
        keyEvent.consume();
    }

    public void keyReleased(KeyEvent keyEvent) {
        keyEvent.consume();
    }

    public void keyTyped(KeyEvent keyEvent) {
        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        checkPopup(mouseEvent);
        requestFocus();
        getCaret().setVisible(true);
    }

    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseExited(MouseEvent mouseEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {
        checkPopup(mouseEvent);
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        checkPopup(mouseEvent);
    }

    public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
    }

    public void select(int i2) {
        if (i2 >= 0) {
            try {
                int lineOfOffset = getLineOfOffset(i2);
                Rectangle rectangleModelToView = modelToView(i2);
                if (rectangleModelToView == null) {
                    select(i2, i2);
                    return;
                }
                try {
                    Rectangle rectangleModelToView2 = modelToView(getLineStartOffset(lineOfOffset + 1));
                    if (rectangleModelToView2 != null) {
                        rectangleModelToView = rectangleModelToView2;
                    }
                } catch (Exception unused) {
                }
                Rectangle viewRect = getParent().getViewRect();
                if (viewRect.y + viewRect.height > rectangleModelToView.y) {
                    select(i2, i2);
                    return;
                }
                rectangleModelToView.y += (viewRect.height - rectangleModelToView.height) / 2;
                scrollRectToVisible(rectangleModelToView);
                select(i2, i2);
            } catch (BadLocationException unused2) {
                select(i2, i2);
            }
        }
    }
}
