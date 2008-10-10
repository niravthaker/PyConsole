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
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class PyConsolePreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public PyConsolePreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("PyConsole Preferences");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
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