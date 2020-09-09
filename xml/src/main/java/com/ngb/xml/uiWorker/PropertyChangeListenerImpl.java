//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ngb.xml.uiWorker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JProgressBar;

public class PropertyChangeListenerImpl implements PropertyChangeListener {
    private JProgressBar uploadProgressbar;

    public PropertyChangeListenerImpl(JProgressBar uploadProgressbar) {
        this.uploadProgressbar = uploadProgressbar;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            this.uploadProgressbar.setValue((Integer)evt.getNewValue());
        }

    }
}
