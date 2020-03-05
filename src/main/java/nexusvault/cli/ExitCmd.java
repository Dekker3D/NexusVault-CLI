package nexusvault.cli;

import java.util.function.Consumer;

import nexusvault.cli.core.cmd.Arguments;
import nexusvault.cli.core.cmd.CommandDescription;
import nexusvault.cli.core.cmd.CommandHandler;

final class ExitCmd implements CommandHandler {

	private final Consumer<Arguments> onCall;

	public ExitCmd(Consumer<Arguments> onCmd) {
		if (onCmd == null) {
			throw new IllegalArgumentException("'onCall' must not b enull");
		}
		this.onCall = onCmd;
	}

	@Override
	public CommandDescription getCommandDescription() {
		// @formatter:off
		return CommandDescription.newInfo()
				.setCommandName("exit")
				.setDescription("Instructs the application to finish all running tasks and close it on complition")
				.setNoNamedArguments()
				.build();
		//@formatter:on
	}

	@Override
	public void onCommand(Arguments args) {
		this.onCall.accept(args);
	}

	@Override
	public String onHelp(Arguments args) {
		return null;
	}
}
