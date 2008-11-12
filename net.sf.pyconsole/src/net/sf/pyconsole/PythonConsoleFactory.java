/**
 * 
 */
package net.sf.pyconsole;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;

/**
 * @author Nirav Thaker
 * 
 */
public class PythonConsoleFactory implements IConsoleFactory {

	private static final String PYTHON_SHELL = "Python Shell";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.console.IConsoleFactory#openConsole()
	 */
	@Override
	public void openConsole() {
		createConsole(PYTHON_SHELL);
	}

	private PythonConsole createConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (PythonConsole) existing[i];
		// no console found, so create a new one
		PythonConsole myConsole = new PythonConsole(name, "", null, true);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}

	public static void closeConsole() {
		IConsoleManager manager = ConsolePlugin.getDefault()
				.getConsoleManager();
		IConsole[] consoles = manager.getConsoles();
		IConsole con = null;
		for (IConsole console : consoles)
			if (PYTHON_SHELL.equals(console.getName()))
				con = console;
		manager.removeConsoles(new IConsole[] { con });
	}
}
