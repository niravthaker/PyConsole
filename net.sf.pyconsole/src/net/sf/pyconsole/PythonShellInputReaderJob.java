/**
 * 
 */
package net.sf.pyconsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public final class PythonShellInputReaderJob extends Job {
	private final InputStream streamToRead;
	private final StylePrinter out;

	public PythonShellInputReaderJob(String name, InputStream stream,
			StylePrinter conStream) {
		super(name);
		streamToRead = stream;
		out = conStream;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					streamToRead));
			String line;
			while ((line = reader.readLine()) != null)
				out.write(line);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return Status.OK_STATUS;
	}
}