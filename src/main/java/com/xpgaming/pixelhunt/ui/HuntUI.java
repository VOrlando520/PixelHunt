package com.xpgaming.pixelhunt.ui;

import ca.landonjw.gooeylibs.inventory.api.Button;
import ca.landonjw.gooeylibs.inventory.api.Page;
import ca.landonjw.gooeylibs.inventory.api.Template;
import com.xpgaming.pixelhunt.Config;
import com.xpgaming.pixelhunt.utils.ButtonUtils;
import com.xpgaming.pixelhunt.utils.Utils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import java.util.Collections;

import static com.xpgaming.pixelhunt.utils.Utils.*;

public class HuntUI {

    public static Page menuUI(EntityPlayerMP player) {



        Button book = Button.builder()
                .item(new ItemStack(Items.BOOK))
                .displayName(regex(Config.getInstance().getConfig().getNode("pixelhunt","gui","gui-book-display").getString()))
                .lore(Collections.singletonList(regex(Config.getInstance().getConfig().getNode("pixelhunt","gui","gui-book-text").getString())))
                .build();


        Template template = Template.builder(3)
                .checker(0, 0, 3, 9, colouredPane(EnumDyeColor.byMetadata(getButtonPos("gui-glass-pane", 1))), colouredPane(EnumDyeColor.byMetadata(getButtonPos("gui-glass-pane", 2))))
                .set(1,6, book)
                .build();

        return Page.builder()
                .title(Utils.regex(Config.getInstance().getConfig().getNode("pixelhunt","gui","gui-title").getString()))
                .template(template)
                .dynamicContentArea(1, 2, 1, 4)
                .dynamicContents(ButtonUtils.getHuntButtons(player))
                .build();
    }


}
