package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.config.FeaturesConfig;
import lombok.Getter;

@Getter
public abstract class CannonCoreCommand {
    private final String name;
    private final String[] aliases;
    private final FeaturesConfig featuresConfig = CannonCore.getFeaturesConfig();


    public CannonCoreCommand(String... names) {
        this.name = names[0];
        this.aliases = names;
    }

    public abstract SYSCommand command();

    public void registerCommand(){
        command().setPermission(CannonCore.BASE_COMMAND_PERMISSION + command().getName());
        command().registerCommand(CannonCore.getCore());
    }

    public String[] getAliases() {
        return aliases;
    }
}
