package net.sf.pyconsole.ui;

import java.io.IOException;

import net.sf.pyconsole.PythonConsole;
import net.sf.pyconsole.PythonConsoleFactory;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.internal.console.ConsolePluginImages;
import org.eclipse.ui.part.IPageBookViewPage;

/**
 * @author Nirav Thaker
 * 
 */
@SuppressWarnings("restriction")
public class PythonConsolePageParticipant implements IConsolePageParticipant {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.console.IConsolePageParticipant#activated()
	 */
	@Override
	public void activated() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.console.IConsolePageParticipant#deactivated()
	 */
	@Override
	public void deactivated() {
		System.out.println("deactivated");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.console.IConsolePageParticipant#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.console.IConsolePageParticipant#init(org.eclipse.ui.part
	 * .IPageBookViewPage, org.eclipse.ui.console.IConsole)
	 */
	@Override
	public void init(final IPageBookViewPage page, final IConsole console) {
		IAction stopPythonAction = new Action() {
			@Override
			public ImageDescriptor getImageDescriptor() {
				return ConsolePluginImages
						.getImageDescriptor(IConsoleConstants.IMG_VIEW_CONSOLE);
			}

			@Override
			public void run() {
				Process process = ((PythonConsole) console).getProcess();
				try {
					process.getOutputStream().write("quit()\n".getBytes());
					PythonConsoleFactory.closeConsole();
				} catch (IOException e) {
					e.printStackTrace();
				}
				process.destroy();
			}
		};
		page.getSite().getActionBars().getToolBarManager().appendToGroup(
				IConsoleConstants.LAUNCH_GROUP, stopPythonAction);
		IAction clearConsoleAction = new Action() {
			@Override
			public ImageDescriptor getImageDescriptor() {
				return ConsolePluginImages
						.getImageDescriptor(IConsoleConstants.IMG_LCL_CLEAR);
			}

			@Override
			public void run() {
				((PythonConsolePage) page).clearConsoleText();
			}

		};
		page.getSite().getActionBars().getToolBarManager().appendToGroup(
				IConsoleConstants.LAUNCH_GROUP, clearConsoleAction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

}
