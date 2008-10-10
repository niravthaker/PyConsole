package net.sf.pyconsole;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "net.sf.pyconsole";

	// The shared instance
	private static Activator plugin;

	private List<String> pythonKeywords;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public List<String> getKeywords() {
		if (pythonKeywords != null)
			return pythonKeywords;
		try {
			InputStream fis = getClass().getClassLoader().getResourceAsStream(
					"pythonkw.txt");
			StringWriter writer = new StringWriter();
			byte[] content = new byte[512];
			int i = -1;
			while ((i = fis.read(content)) != -1) {
				writer.write(new String(content, 0, i));
			}
			pythonKeywords = new ArrayList<String>(35);
			String string = writer.getBuffer().toString();
			String[] lines = string.split("\r\n");
			for (String line : lines) {
				String[] wrds = line.split(" ");
				List<String> list = Arrays.asList(wrds);
				for (String wrd : list) {
					if (wrd.length() > 0)
						pythonKeywords.add(wrd);
				}
			}
			return pythonKeywords;

		} catch (Exception e) {
			throw new RuntimeException("Can't load python keyword file", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
