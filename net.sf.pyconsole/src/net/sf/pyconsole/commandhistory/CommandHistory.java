/**
 * 
 */
package net.sf.pyconsole.commandhistory;

/**
 * @author Nirav Thaker
 * 
 */
public class CommandHistory {
	private static CommandHistory instance = new CommandHistory();
	private static LIFOQueue<String> commands;

	public static CommandHistory getInstance() {
		return getInstance(100);
	}

	public static CommandHistory getInstance(int count) {
		if (commands == null) {
			commands = new LIFOQueue<String>(count);
		}
		return instance;
	}

	public void add(String command) {
		commands.push(command);
	}

	public String getNext() {
		return commands.next();
	}

	public String getPrevious() {
		return commands.previous();
	}

	public static void destroy() {
		instance = null;
		commands = null;
	}

}
