package dev.tom.cannoncore.commands;

import dev.splityosis.commandsystem.SYSCommand;
import dev.tom.cannoncore.CannonCore;
import dev.tom.cannoncore.config.FeaturesConfig;
import lombok.Getter;

import java.util.Arrays;

@Getter
public abstract class CannonCoreCommand {
    private final String name;
    private final FeaturesConfig featuresConfig = CannonCore.getFeaturesConfig();


    public CannonCoreCommand(String... names) {
        this.name = names[0];
    }

    public abstract SYSCommand command();

    public void registerCommand(){
        command().registerCommand(CannonCore.getPlugin());
    }

}
