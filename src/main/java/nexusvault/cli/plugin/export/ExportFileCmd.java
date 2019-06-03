package nexusvault.cli.plugin.export;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nexusvault.cli.App;
import nexusvault.cli.CommandArguments;
import nexusvault.cli.CommandInfo;
import nexusvault.cli.plugin.AbstCommand;
import nexusvault.cli.plugin.export.ExportPlugIn.ExportConfig;

final class ExportFileCmd extends AbstCommand {
	@Override
	public CommandInfo getCommandInfo() {
		// @formatter:off
		return CommandInfo.newInfo()
				.setName("convert-file")
				.setDescription("Reads and converts files, which were previously extracted from an archive, but not converted yet")
				.setRequired(false)
				.setArguments(true)
				.setNumberOfArgumentsUnlimited()
				.setNamesOfArguments("files ...")
				.build();
		//@formatter:on
	}

	@Override
	public void onCommand(CommandArguments args) {
		if (args.getNumberOfArguments() == 0) {
			sendMsg(() -> String.format("At least one file path is required"));
			return;
		}

		final List<Path> targets = new LinkedList<>();
		boolean allFilesFound = true;
		for (int i = 0; i < args.getNumberOfArguments(); ++i) {
			final Path p = Paths.get(args.getArg(0));
			if (Files.exists(p)) {
				targets.add(p);
			} else {
				sendMsg(() -> String.format("Unable to convert file. Not found: %s", p));
				allFilesFound = false;
			}
		}

		if (!allFilesFound) {
			sendMsg(() -> "Missing files. Abort convertion.");
			return;
		}

		final ExportConfig exportConfig = new ExportConfig();
		App.getInstance().getPlugIn(ExportPlugIn.class).exportPath(targets, exportConfig);
	}

	@Override
	public void onHelp(CommandArguments args) {
		sendMsg("Converts a Wildstar specific file type into a more readable format");
		final Set<String> supportedFileTypes = new HashSet<>();
		for (final Exporter exporter : App.getInstance().getPlugIn(ExportPlugIn.class).getExporters()) {
			supportedFileTypes.addAll(exporter.getAcceptedFileEndings());
		}
		sendMsg("File types with converter: " + String.join(", ", supportedFileTypes));
	}
}