package com.xpgaming.pixelhunt.commands;


import com.xpgaming.pixelhunt.Config;
import com.xpgaming.pixelhunt.Main;
import com.xpgaming.pixelhunt.ui.HuntUI;
import com.xpgaming.pixelhunt.utils.Utils;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class Hunt implements CommandExecutor {

    public static String rewardList(int num) {
        String strList = "";
        String balls = Config.getInstance().getConfig().getNode("pixelhunt","lang","balls").getString();
        String money = Config.getInstance().getConfig().getNode("pixelhunt","lang","money").getString();
        String candies = Config.getInstance().getConfig().getNode("pixelhunt","lang","candies").getString();
        String other = Config.getInstance().getConfig().getNode("pixelhunt","lang","other").getString();
        if(num == 1) {

            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-balls").getBoolean())
                strList = "\u00A7d"+balls+": \u00A7f" + Main.pokemon1ballReward.getQuantity() + " ";

            if(Main.hasEconomy && Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-money").getBoolean())
                strList = strList + "\u00A72"+money+": \u00A7f" + Main.pokemon1moneyReward + " ";

            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-candy").getBoolean())
                strList = strList + "\u00A73"+candies+": \u00A7f" + Main.pokemon1rc.getQuantity() + " ";

            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","custom-toggle").getBoolean())
                strList = strList + "\u00A74"+other+": \u00A7f" + Main.pokemon1msg + " ";
        }
        else if(num == 2) {
            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-balls").getBoolean())
            strList = "\u00A7d"+balls+": \u00A7f" + Main.pokemon2ballReward.getQuantity() + " ";

            if(Main.hasEconomy && Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-money").getBoolean())
                strList = strList + "\u00A72"+money+": \u00A7f" + Main.pokemon2moneyReward + " ";

            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-candy").getBoolean())
                strList = strList + "\u00A73"+candies+": \u00A7f" + Main.pokemon2rc.getQuantity() + " ";

            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","custom-toggle").getBoolean())
                strList = strList + "\u00A74"+other+": \u00A7f" + Main.pokemon2msg + " ";
        }
        else if(num == 3) {
            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-balls").getBoolean())
                strList = "\u00A7d"+balls+": \u00A7f" + Main.pokemon3ballReward.getQuantity() + " ";

            if(Main.hasEconomy && Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-money").getBoolean())
                strList = strList + "\u00A72"+money+": \u00A7f" + Main.pokemon3moneyReward + " ";

            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-candy").getBoolean())
                strList = strList + "\u00A73"+candies+": \u00A7f" + Main.pokemon3rc.getQuantity() + " ";

            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","custom-toggle").getBoolean())
                strList = strList + "\u00A74"+other+": \u00A7f" + Main.pokemon3msg + " ";
        }
        else if(num == 4) {
            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-balls").getBoolean())
                strList = "\u00A7d"+balls+": \u00A7f" + Main.pokemon4ballReward.getQuantity() + " ";

            if(Main.hasEconomy && Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-money").getBoolean())
                strList = strList + "\u00A72"+money+": \u00A7f" + Main.pokemon4moneyReward + " ";

            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","give-candy").getBoolean())
                strList = strList + "\u00A73"+candies+": \u00A7f" + Main.pokemon4rc.getQuantity() + " ";

            if(Config.getInstance().getConfig().getNode("pixelhunt","rewards","custom-toggle").getBoolean())
                strList = strList + "\u00A74"+other+": \u00A7f" + Main.pokemon4msg + " ";
        } else {
            strList = "Error!";
        }

        return strList;
    }

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(src instanceof Player){
            EntityPlayerMP player = (EntityPlayerMP) src;
            HuntUI.menuUI(player).forceOpenPage(player);
        }else{
            src.sendMessage(Text.of(Utils.regex("&cOnly players can run this command.")));
        }
        return CommandResult.success();
    }

    public String IVUpgrade(int nature) {
        switch (nature) {
            case 5:
                return "Maxed ATTACK IVs";
            case 6:
                return "Maxed ATTACK IVs";
            case 7:
                return "Maxed ATTACK IVs";
            case 8:
                return "Maxed ATTACK IVs";
            case 9:
                return "Maxed DEFENCE IVs";
            case 10:
                return "Maxed DEFENCE IVs";
            case 11:
                return "Maxed DEFENCE IVs";
            case 12:
                return "Maxed DEFENCE IVs";
            case 13:
                return "Maxed SPEED IVs";
            case 14:
                return "Maxed SPEED IVs";
            case 15:
                return "Maxed SPEED IVs";
            case 16:
                return "Maxed SPEED IVs";
            case 17:
                return "Maxed SP. ATK IVs";
            case 18:
                return "Maxed SP. ATK IVs";
            case 19:
                return "Maxed SP. ATK IVs";
            case 20:
                return "Maxed SP. ATK IVs";
            case 21:
                return "Maxed SP. DEF IVs";
            case 22:
                return "Maxed SP. DEF IVs";
            case 23:
                return "Maxed SP. DEF IVs";
            case 24:
                return "Maxed SP. DEF IVs";
            default:
                return "20% increase in IVs";
        }
    }
}

