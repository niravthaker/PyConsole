/**
 * 
 */
package net.sf.pyconsole;

import java.io.File;
import java.io.IOException;

import net.sf.pyconsole.preferences.PreferenceConstants;
import net.sf.pyconsole.ui.PythonConsolePage;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.AbstractConsole;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.part.IPageBookViewPage;

/**
 * @author nthaker
 * 
 */
public class PythonConsole extends AbstractConsole {

	private Process process;

	public PythonConsole(String name, String consoleType,
			ImageDescriptor imageDescriptor, boolean autoLifecycle) {
		super(name, consoleType, imageDescriptor, autoLifecycle);
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	@Override
	public IPageBookViewPage createPage(IConsoleView view) {
		String intPath = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_INTERPRETER_PATH);
		if(intPath == null || intPath.length() == 0) intPath = "C:\\Python25\\python.exe";
		else intPath = intPath + File.separatorChar + "python.exe";
		ProcessBuilder builder = new ProcessBuilder();
		Process process = null;
		try {
			builder.command(intPath, "-uid");
			process = builder.start();
		} catch (IOException e) {
			ErrorDialog.openError(view.getSite().getShell(), "Python Interpreter not found", "Can't create Process: " + intPath, new Status(IStatus.ERROR,"net.sf.pyconsole","Error forking python",e));
			e.printStackTrace();
			return null;
		}
		setProcess(process);
		return new PythonConsolePage(this, view);
	}
}
