/**
 * 
 */
package net.sf.pyconsole;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.pyconsole.commandhistory.CommandHistory;
import net.sf.pyconsole.preferences.PreferenceConstants;
import net.sf.pyconsole.ui.PythonConsolePage;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.AbstractConsole;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.part.IPageBookViewPage;

/**
 * @author Nirav Thaker
 * 
 */
@SuppressWarnings("serial")
public class PythonConsole extends AbstractConsole {

	private Process process;
	private CommandHistory commandHistory;
	private Map<String, String> pythonDefaultPathMap = new HashMap<String, String>() {
		{
			put(Platform.OS_WIN32, "C:\\Python25\\");
			put(Platform.OS_LINUX, "/usr/bin/");
		}
	};
	private Map<String, String> pythonInterpreterMap = new HashMap<String, String>() {
		{
			put(Platform.OS_WIN32, "python.exe");
			put(Platform.OS_MACOSX, "python");
		}
	};

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
		String intPath = Activator.getDefault().getPreferenceStore().getString(
				PreferenceConstants.P_INTERPRETER_PATH);
		int commandHistSize = Activator.getDefault().getPreferenceStore()
				.getInt(PreferenceConstants.P_COMMANDHISTORY_SIZE);
		setCommandHistory(CommandHistory.getInstance(commandHistSize));
		
		String pythonInterpreter = getPythonInterpreterName();
		
		if (intPath != null && intPath.trim().length() == 0)
			intPath = getPythonHome() + pythonInterpreter;
		else
			intPath = intPath + File.separatorChar + pythonInterpreter;
		try {
			createProcess(view, intPath);
		} catch (IOException e) {
			return null;
		}
		return new PythonConsolePage(this, view);
	}

	private String getPythonHome() {
		String home = pythonDefaultPathMap.get(Platform.getOS());
		return home == null ? "" : home;
	}

	private String getPythonInterpreterName() {
		String home = pythonInterpreterMap.get(Platform.getOS());
		return home == null ? "python" : home;
	}

	private void createProcess(IConsoleView view, String intPath)
			throws IOException {
		ProcessBuilder builder = new ProcessBuilder();
		Process process = null;
		try {
			builder.command(intPath, "-uid");
			process = builder.start();
		} catch (IOException e) {
			String message = "Can't create Process: "
					+ intPath
					+ " , Please make sure the 'Python Interpreter Home' preference points to python installation directory.";
			ErrorDialog.openError(view.getSite().getShell(),
					"Python Interpreter not found", message, new Status(
							IStatus.ERROR, "net.sf.pyconsole",
							"Error forking python process ", e));
			throw e;
		}
		setProcess(process);
	}

	public void setCommandHistory(CommandHistory commandHistory) {
		this.commandHistory = commandHistory;
	}

	public CommandHistory getCommandHistory() {
		return commandHistory;
	}
}
