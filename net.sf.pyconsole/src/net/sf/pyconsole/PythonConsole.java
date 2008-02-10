/**
 * 
 */
package net.sf.pyconsole;

import java.io.IOException;

import net.sf.pyconsole.ui.PythonConsolePage;

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
		String py = "C:\\Python25\\python.exe";
		ProcessBuilder builder = new ProcessBuilder();
		Process process = null;
		try {
			builder.command(py, "-uid");
			process = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setProcess(process);
		return new PythonConsolePage(this, view);
	}
}
