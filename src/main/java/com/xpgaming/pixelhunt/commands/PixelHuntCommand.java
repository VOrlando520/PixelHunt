package com.xpgaming.pixelhunt.commands;


import com.envyful.acaf.api.command.Command;
import com.envyful.acaf.api.command.Permissible;
import com.envyful.acaf.api.command.executor.CommandProcessor;
import com.envyful.acaf.api.command.executor.Sender;
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

    @CommandProcessor
    public void executeCommand(@Sender EntityPlayerMP sender) {
        HuntUI.open(sender);
    }

    @CommandProcessor("reload")
    @Permissible("pixelhunt.admin.reload")
    public void executeReloadCommand(@Sender EntityPlayerMP sender) {
        //TODO: reload configs
    }
}

