package com.shashwat.hibernate.sample;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.shashwat.hibernate.sample.editor.UserEditor;
import com.shashwat.hibernate.sample.editor.UserEditorInput;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(1000, 600));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
    }
    
    @Override
    public void postWindowOpen() {
    	IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    	try {
    		activeWorkbenchWindow.getActivePage().openEditor(new UserEditorInput(), UserEditor.ID);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
