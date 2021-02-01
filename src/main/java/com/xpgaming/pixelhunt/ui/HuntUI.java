package com.xpgaming.pixelhunt.ui;

import ca.landonjw.gooeylibs.inventory.api.Button;
import ca.landonjw.gooeylibs.inventory.api.Page;
import ca.landonjw.gooeylibs.inventory.api.Template;
import com.xpgaming.pixelhunt.Config;
import com.xpgaming.pixelhunt.config.PixelHuntConfig;
import com.xpgaming.pixelhunt.utils.ButtonUtils;
import com.xpgaming.pixelhunt.utils.UtilConcurrency;
import com.xpgaming.pixelhunt.utils.Utils;
import com.xpgaming.pixelhunt.utils.item.ItemBuilder;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.text.Style;

public class HuntUI {

    private static final Button BACKGROUND_FILLER = Button.of(new ItemBuilder()
            .type(Item.getByNameOrId(PixelHuntConfig.getInstance().getBackgroundItem()))
            .damage(PixelHuntConfig.getInstance().getBackgroundItemDamage())
            .build());

    public static void open(EntityPlayerMP player) {
        UtilConcurrency.runAsync(() -> {
            Template.Builder template = Template.builder(PixelHuntConfig.getInstance().getGuiHeight())
                    .fill(BACKGROUND_FILLER);



            Page.builder()
                    .title(PixelHuntConfig.getInstance().getGuiName())
                    .template(template.build())
                    .build().forceOpenPage(player);
        });
    }
}
