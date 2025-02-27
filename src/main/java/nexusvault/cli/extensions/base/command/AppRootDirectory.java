package nexusvault.cli.extensions.base.command;

import java.nio.file.Path;

import nexusvault.cli.core.App;
import nexusvault.cli.core.AutoInstantiate;
import nexusvault.cli.core.cmd.Argument;
import nexusvault.cli.core.cmd.ArgumentDescription;
import nexusvault.cli.core.cmd.ArgumentHandler;
import nexusvault.cli.extensions.base.AppBaseExtension;

@AutoInstantiate
public final class AppRootDirectory implements ArgumentHandler {

	@Override
	public ArgumentDescription getArgumentDescription() {
		// @formatter:off
		return ArgumentDescription.newInfo()
				.setName("app")
				.setDescription("application root directory. If not set, the directory of the executeable is used as the root directory")
				.setRequired(false)
				.setArguments(false)
				.setNumberOfArguments(1)
				.setNamesOfArguments("path")
				.build();
		//@formatter:on
	}

	@Override
	public void execute(Argument args) {
		final Path path = Path.of(args.getValue());
		App.getInstance().getExtensionManager().getExtension(AppBaseExtension.class).setApplicationPath(path);
	}
}