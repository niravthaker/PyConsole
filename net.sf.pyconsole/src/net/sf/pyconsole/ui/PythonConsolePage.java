package net.sf.pyconsole.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.pyconsole.Activator;
import net.sf.pyconsole.PythonConsole;
import net.sf.pyconsole.PythonConsoleFactory;
import net.sf.pyconsole.PythonShellInputReaderJob;
import net.sf.pyconsole.StylePrinter;
import net.sf.pyconsole.commandhistory.CommandHistory;
import net.sf.pyconsole.preferences.PreferenceConstants;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.part.Page;

/**
 * @author Nirav Thaker
 * 
 */
public class PythonConsolePage extends Page {

	class ListenerImplementation implements Listener {
		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.TRAVERSE_TAB_PREVIOUS)
				event.doit = false;
		}
	}

	private Text txtConsole;
	private StyledText styledText;
	private Composite parent;
	private final InputStream errorStream;
	private final InputStream inputStream;
	private final OutputStream outputStream;
	private PythonShellInputReaderJob pyShellInputReader;
	private PythonShellInputReaderJob pyShellErrorReader;
	private StylePrinter inPrinter;
	private PythonConsoleErrPrinter errPrinter;
	private final CommandHistory commandHistory;

	public PythonConsolePage(PythonConsole pythonConsole, IConsoleView view) {
		Process process = pythonConsole.getProcess();
		commandHistory = pythonConsole.getCommandHistory();
		errorStream = process.getErrorStream();
		inputStream = process.getInputStream();
		outputStream = process.getOutputStream();
	}

	@Override
	public void createControl(Composite para) {
		parent = new Composite(para, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 5;
		gridLayout.verticalSpacing = 5;
		gridLayout.horizontalSpacing = 5;
		parent.setLayout(gridLayout);
		styledText = new StyledText(parent, SWT.V_SCROLL | SWT.READ_ONLY);
		styledText.setEditable(false);
		styledText.setWordWrap(true);
		styledText.setTopPixel(50);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		styledText.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				txtConsole.setFocus();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				txtConsole.setFocus();
			}
		});
		FontData fontData = PreferenceConverter.getFontData(Activator
				.getDefault().getPreferenceStore(),
				PreferenceConstants.P_CONSOLE_FONT);
		Font font = new Font(styledText.getDisplay(), fontData);
		styledText.addLineStyleListener(new PythonLineStyleListener(this));
		styledText.setFont(font);
		txtConsole = new Text(parent, SWT.MULTI | SWT.LEAD | SWT.BORDER);
		txtConsole
				.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		inPrinter = new PythonConsolePrinter(this.styledText);
		errPrinter = new PythonConsoleErrPrinter(this, this.styledText);
		pyShellInputReader = new PythonShellInputReaderJob(
				"Python Shell Input Reader", this.inputStream, inPrinter);
		pyShellErrorReader = new PythonShellInputReaderJob(
				"Python Shell Error Reader", this.errorStream, errPrinter);
		pyShellErrorReader.setSystem(true);
		pyShellInputReader.setSystem(true);
		pyShellInputReader.schedule();
		pyShellErrorReader.schedule();
		pyShellErrorReader.schedule();

		txtConsole.setFocus();
		txtConsole.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				run();
			}

		});
		txtConsole.addListener(SWT.Traverse, new ListenerImplementation());
		txtConsole.addKeyListener(new CommandTextboxKeyAdapter(this,
				txtConsole, commandHistory));
	}

	protected void run() {
		String text = txtConsole.getText();
		commandHistory.add(text);
		if ("quit()".equalsIgnoreCase(text)) {
			PythonConsoleFactory.closeConsole();
			return;
		}
		pump(text);
		if (pyShellInputReader.getState() != Job.RUNNING)
			pyShellInputReader.schedule();
		if (pyShellErrorReader.getState() != Job.RUNNING)
			pyShellErrorReader.schedule();
		txtConsole.setText("");
	}

	protected void pump(String text) {
		try {
			if (text.length() == 0)
				return;
			if ("clear".equalsIgnoreCase(text)) {
				styledText.setText("");
				return;
			}
			String prompt = ">>>";
			if ("\r\n".equals(text)) {
				PythonConsolePrinter.append(styledText, prompt);
				outputStream.write("\n".getBytes());
			} else {
				PythonConsolePrinter.append(styledText, prompt + text);
				outputStream.write((text + "\n").getBytes());
			}
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Control getControl() {
		return parent;
	}

	@Override
	public void setFocus() {
		txtConsole.setFocus();
	}

	public void clearConsoleText() {
		this.styledText.setText("");
	}

	public StyledText getStyledText() {
		return styledText;
	}
}
