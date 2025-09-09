package org.mozilla.javascript.tools.debugger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import org.mozilla.javascript.tools.debugger.Dim;

/* loaded from: classes5.dex */
class FileWindow extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = -6212382604952082370L;
    int currentPos;
    private SwingGui debugGui;
    private FileHeader fileHeader;

    /* renamed from: p, reason: collision with root package name */
    private JScrollPane f26766p;
    private Dim.SourceInfo sourceInfo;
    FileTextArea textArea;

    public FileWindow(SwingGui swingGui, Dim.SourceInfo sourceInfo) {
        super(SwingGui.getShortName(sourceInfo.url()), true, true, true, true);
        this.debugGui = swingGui;
        this.sourceInfo = sourceInfo;
        updateToolTip();
        this.currentPos = -1;
        FileTextArea fileTextArea = new FileTextArea(this);
        this.textArea = fileTextArea;
        fileTextArea.setRows(24);
        this.textArea.setColumns(80);
        this.f26766p = new JScrollPane();
        this.fileHeader = new FileHeader(this);
        this.f26766p.setViewportView(this.textArea);
        this.f26766p.setRowHeaderView(this.fileHeader);
        setContentPane(this.f26766p);
        pack();
        updateText(sourceInfo);
        this.textArea.select(0);
    }

    private void updateToolTip() {
        int i2 = 1;
        int componentCount = getComponentCount() - 1;
        if (componentCount <= 1) {
            if (componentCount < 0) {
                return;
            } else {
                i2 = componentCount;
            }
        }
        JComponent component = getComponent(i2);
        if (component == null || !(component instanceof JComponent)) {
            return;
        }
        component.setToolTipText(getUrl());
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String actionCommand = actionEvent.getActionCommand();
        if (actionCommand.equals("Cut")) {
            return;
        }
        if (actionCommand.equals("Copy")) {
            this.textArea.copy();
        } else {
            actionCommand.equals("Paste");
        }
    }

    public void clearBreakPoint(int i2) {
        if (this.sourceInfo.breakableLine(i2) && this.sourceInfo.breakpoint(i2, false)) {
            this.fileHeader.repaint();
        }
    }

    public void dispose() {
        this.debugGui.removeWindow(this);
        super.dispose();
    }

    public int getPosition(int i2) {
        try {
            return this.textArea.getLineStartOffset(i2);
        } catch (BadLocationException unused) {
            return -1;
        }
    }

    public String getUrl() {
        return this.sourceInfo.url();
    }

    public boolean isBreakPoint(int i2) {
        return this.sourceInfo.breakableLine(i2) && this.sourceInfo.breakpoint(i2);
    }

    void load() {
        String url = getUrl();
        if (url != null) {
            RunProxy runProxy = new RunProxy(this.debugGui, 2);
            runProxy.fileName = url;
            runProxy.text = this.sourceInfo.source();
            new Thread(runProxy).start();
        }
    }

    public void select(int i2, int i3) {
        int length = this.textArea.getDocument().getLength();
        this.textArea.select(length, length);
        this.textArea.select(i2, i3);
    }

    public void setBreakPoint(int i2) {
        if (this.sourceInfo.breakableLine(i2) && this.sourceInfo.breakpoint(i2, true)) {
            this.fileHeader.repaint();
        }
    }

    public void setPosition(int i2) {
        this.textArea.select(i2);
        this.currentPos = i2;
        this.fileHeader.repaint();
    }

    public void toggleBreakPoint(int i2) {
        if (isBreakPoint(i2)) {
            clearBreakPoint(i2);
        } else {
            setBreakPoint(i2);
        }
    }

    public void updateText(Dim.SourceInfo sourceInfo) {
        this.sourceInfo = sourceInfo;
        String strSource = sourceInfo.source();
        if (!this.textArea.getText().equals(strSource)) {
            this.textArea.setText(strSource);
            int i2 = this.currentPos;
            if (i2 == -1) {
                i2 = 0;
            }
            this.textArea.select(i2);
        }
        this.fileHeader.update();
        this.fileHeader.repaint();
    }
}
