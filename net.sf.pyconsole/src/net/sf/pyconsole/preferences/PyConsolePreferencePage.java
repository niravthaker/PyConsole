package net.sf.pyconsole.preferences;

import net.sf.pyconsole.Activator;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author Nirav Thaker
 * 
 */
public class PyConsolePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public PyConsolePreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("PyConsole Preferences");
	}

	@Override
	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(
				PreferenceConstants.P_INTERPRETER_PATH,
				"&Python Interpreter Home:", getFieldEditorParent()));
		addField(new IntegerFieldEditor(
				PreferenceConstants.P_COMMANDHISTORY_SIZE,
				"&Max size of command history:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.P_CONSOLE_KW_COLOR,
				"Python &Keyword color:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.P_CONSOLE_ERR_COLOR,
				"Python Console text color:", getFieldEditorParent()));
		addField(new FontFieldEditor(PreferenceConstants.P_CONSOLE_FONT,
				"Python Console font:", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}