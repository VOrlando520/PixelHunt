package com.xpgaming.pixelhunt.commands;


import com.envyful.acaf.api.command.Command;
import com.envyful.acaf.api.command.executor.CommandProcessor;
import com.xpgaming.pixelhunt.ui.HuntUI;
import net.minecraft.entity.player.EntityPlayerMP;

@Command(
       value = "hunt",
        description = "A command to open the PixelHunt UI",
        aliases = {
               "pixelhunt",
                "pokemonhunt",
                "pokehunt"
        }
)
public class PixelHuntCommand {

    @CommandProcessor("")
    public void executeCommand(EntityPlayerMP sender) {
        HuntUI.menuUI(sender);
    }
}

