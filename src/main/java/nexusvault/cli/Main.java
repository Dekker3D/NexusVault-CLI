package nexusvault.cli;

import java.io.IOException;

public final class Main {

	public static void main(String[] args) throws IOException {
		final String triggerWord = HeadlessModeCmd.CLI_SYNTAX;
		boolean isHeadlessMode = false;
		for (final String arg : args) {
			if ((arg != null) && arg.toLowerCase().endsWith(triggerWord)) {
				isHeadlessMode = true;
				break;
			}
		}

		final App app = new App();
		app.initializeApp(isHeadlessMode);

		try {
			app.startApp(args);
			// app.runCommands(args);
			// if (app.isHeadlessMode())
			// return; //TODO

			// app.processConsole();
		} finally {
			app.closeApp();
		}
	}

}
