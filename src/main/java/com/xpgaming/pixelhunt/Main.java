package com.xpgaming.pixelhunt;

import ca.landonjw.gooeylibs.inventory.api.InventoryAPI;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.xpgaming.pixelhunt.commands.Hunt;
import com.xpgaming.pixelhunt.commands.NewHunt;
import com.xpgaming.pixelhunt.commands.Reload;
import com.xpgaming.pixelhunt.listeners.CaptureListener;
import com.xpgaming.pixelhunt.listeners.PixelmonSpawnListener;
import com.xpgaming.pixelhunt.utils.Utils;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Plugin(id = Main.id, name = Main.name, version = "1.2.6",  description = "A Pixelmon sidemod to get rewards for catching certain Pokemon.",
		dependencies = {
				@Dependency(id = "pixelmon"),
				@Dependency(id = "gooeylibs")
})
public class Main {
	private static Main instance;
	
	public static Main getInstance() {
		return instance;
	}

	private static String separator = FileSystems.getDefault().getSeparator();
	public static String primaryPath = "config" + separator;
	public static String commandConfigPath = "config" + separator + "PixelHunt" + separator;
	public static Path configPath = Paths.get(primaryPath, "PixelHunt.conf");

	public static ConfigurationLoader<CommentedConfigurationNode> primaryConfigLoader =
			HoconConfigurationLoader.builder().setPath(configPath).build();
	
	public static final String id = "pixelhunt";
	public static String pokemon1 = "", nature1 = "", nature1b = "", nature1c = "";
	public static String pokemon2 = "", nature2 = "", nature2b = "", nature2c = "";
	public static String pokemon3 = "", nature3 = "", nature3b = "", nature3c = "";
	public static String pokemon4 = "", nature4 = "", nature4b = "", nature4c = "";
	public static LocalDateTime pokemon1expiry, pokemon2expiry, pokemon3expiry, pokemon4expiry;
	public static String pokemon1ballName, pokemon2ballName, pokemon3ballName, pokemon4ballName;
	public static ItemStack pokemon1ballReward, pokemon2ballReward, pokemon3ballReward, pokemon4ballReward;
	public static ItemStack pokemon1rc, pokemon2rc, pokemon3rc, pokemon4rc;
	public static String pokemon1msg, pokemon2msg, pokemon3msg, pokemon4msg;
	public static BigDecimal pokemon1moneyReward, pokemon2moneyReward, pokemon3moneyReward, pokemon4moneyReward;
	public static Boolean initialised = false;
	public static Boolean hasEconomy = false;
	public static final String name = "PixelHunt";

	private static EconomyService economyService;
	
	@Listener
	public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
		if (event.getService().equals(EconomyService.class)) {
			economyService = (EconomyService) event.getNewProviderRegistration().getProvider();
		}
	}
	
	public void giveItemStack(ItemStack i, Player player) {
		if (player.getInventory().query(Hotbar.class, GridInventory.class).size() < 36) {
			player.getInventory().offer(i);
		} else {
			World world = player.getLocation().getExtent();
			Entity it = world
					.createEntity(EntityTypes.ITEM, player.getLocation().getPosition());
			it.offer(Keys.REPRESENTED_ITEM, i.createSnapshot());
			world.spawnEntity(it);
		}
		
	}

	@Listener
	public void onPostInitialisationEvent(final GamePostInitializationEvent event)
	{
		final Optional<EconomyService> fakeEconomy = Sponge.getServiceManager().provide(EconomyService.class);
		if (fakeEconomy.isPresent()) {
			economyService = fakeEconomy.get();
			hasEconomy = true;
			consoleMsg("§f[§6PixelHunt§f] §eEconomy loaded!");
			Utils.getInstance().initialisePokemon();
		} else {
			hasEconomy = false;
			consoleMsg("§f[§6PixelHunt§f] §eEconomy not found!");
		}
	}
	
	public void addMoney(Player p, BigDecimal amount) {
		Optional<UniqueAccount> uOpt = economyService.getOrCreateAccount(p.getUniqueId());
		if (uOpt.isPresent()) {
			UniqueAccount account = uOpt.get();
			TransactionResult result = account.deposit(economyService.getDefaultCurrency(), amount, Cause.of(EventContext.empty(), this));
			if (!(result.getResult() == ResultType.SUCCESS)) {
				p.sendMessage(Text.of(Utils.prefix()+" \u00A7cUnable to give money, something broke!"));
			}
		}
	}

	CommandSpec newhunt = CommandSpec.builder()
			.description(Text.of("Renew hunted pokemon!"))
			.permission("xpgaming.pixelhunt.admin.newhunt")
			.arguments(
					GenericArguments.optional(GenericArguments.integer(Text.of("slot"))))
			.executor(new NewHunt())
			.build();

	CommandSpec reload = CommandSpec.builder()
			.description(Text.of("Reload the config!"))
			.permission("xpgaming.pixelhunt.admin.reload")
			.executor(new Reload())
			.build();
	
	CommandSpec hunt = CommandSpec.builder()
			.description(Text.of("List hunted pokemon!"))
			.permission("xpgaming.pixelhunt.base.hunt")
			.executor(new Hunt())
			.child(newhunt, "new", "nh", "newhunt")
			.child(reload, "reload", "rl", "r", "rel")
			.build();

    String path = "config" + File.separator + "PixelHunt";

    File configFile = new File(path, "PixelHunt.conf");
    ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setFile(configFile).build();

    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {
    	InventoryAPI.register();
		Pixelmon.EVENT_BUS.register(new CaptureListener());
		Pixelmon.EVENT_BUS.register(new PixelmonSpawnListener());
		MinecraftForge.EVENT_BUS.register(new PixelmonSpawnListener());
        Config.getInstance().setup(configFile, configLoader);
        Sponge.getCommandManager().register(this, hunt, "hunt", "ph", "pokehunt", "hunts");
		Sponge.getCommandManager().register(this, newhunt, "newhunt", "nh");
		Sponge.getCommandManager().register(this, reload, "huntreload", "hr");
	}
	
	@Listener(beforeModifications = true)
	public void onGameInitialization(GameInitializationEvent event) {
		reloadAnnouncementTask();
		instance = this;
		if (!initialised) {
			Utils.getInstance().initialisePokemon();
			initialised = true;
		}
		consoleMsg("§f[§6xP//§f] §ePixelHunt - Loaded v1.2.1!");
		consoleMsg("§f[§6xP//§f] §eBy Xenoyia with help from happyzlife, FrostEffects and XpanD!");
		consoleMsg("§f[§6xP//§f] §eUpdated by Blue.");

	}

	public void reloadAnnouncementTask() {
		Sponge.getScheduler().getTasksByName("PixelHunt Announcement").forEach(t -> t.cancel());
		if(Config.getInstance().getConfig().getNode("pixelhunt","general","global-msg").getBoolean()) {
			Task.builder().execute(() -> {
				if (!Sponge.getServer().getOnlinePlayers().isEmpty()) {
					Text.builder()
							.color(TextColors.DARK_GRAY)
							.append(Text.of("\u00A7b" + Main.pokemon1))
							.onHover(TextActions.showText(Text.of("\u00A73Nature 1: \u00A7f" + Main.nature1 + "\n\u00A73Nature 2: \u00A7f" + Main.nature1b + "\n\u00A73Nature 3: \u00A7f" + Main.nature1c)))
							.build();
					Text.builder()
							.color(TextColors.DARK_GRAY)
							.append(Text.of(" \u00A77[\u00A7eR\u00A77]"))
							.onHover(TextActions.showText(Text.of("\u00A7dBalls: \u00A7f" + Main.pokemon1ballReward.getQuantity() + " " + Main.pokemon1ballName + "\n\u00A72Money: \u00A7f" + Main.pokemon1moneyReward + " coins\n\u00A7bRare Candies: \u00A7f" + Main.pokemon1rc.getQuantity())))// + "\n\u00A76IVs: \u00A7f" + IVUpgrade(EnumNature.natureFromString(Main.nature1).index))))
							.build();
					Text.builder()
							.color(TextColors.DARK_GRAY)
							.append(Text.of("\u00A7b" + Main.pokemon2))
							.onHover(TextActions.showText(Text.of("\u00A73Nature 1: \u00A7f" + Main.nature2 + "\n\u00A73Nature 2: \u00A7f" + Main.nature2b + "\n\u00A73Nature 3: \u00A7f" + Main.nature2c)))
							.build();
					Text.builder()
							.color(TextColors.DARK_GRAY)
							.append(Text.of(" \u00A77[\u00A7eR\u00A77]"))
							.onHover(TextActions.showText(Text.of("\u00A7dBalls: \u00A7f" + Main.pokemon2ballReward.getQuantity() + " " + Main.pokemon2ballName + "\n\u00A72Money: \u00A7f" + Main.pokemon2moneyReward + " coins\n\u00A7bRare Candies: \u00A7f" + Main.pokemon2rc.getQuantity())))// + "\n\u00A76IVs: \u00A7f" + IVUpgrade(EnumNature.natureFromString(Main.nature1).index))))
							.build();
					Text.builder()
							.color(TextColors.DARK_GRAY)
							.append(Text.of("\u00A7b" + Main.pokemon3))
							.onHover(TextActions.showText(Text.of("\u00A73Nature 1: \u00A7f" + Main.nature3 + "\n\u00A73Nature 2: \u00A7f" + Main.nature3b + "\n\u00A73Nature 3: \u00A7f" + Main.nature3c)))
							.build();
					Text.builder()
							.color(TextColors.DARK_GRAY)
							.append(Text.of(" \u00A77[\u00A7eR\u00A77]"))
							.onHover(TextActions.showText(Text.of("\u00A7dBalls: \u00A7f" + Main.pokemon3ballReward.getQuantity() + " " + Main.pokemon3ballName + "\n\u00A72Money: \u00A7f" + Main.pokemon3moneyReward + " coins\n\u00A7bRare Candies: \u00A7f" + Main.pokemon3rc.getQuantity())))// + "\n\u00A76IVs: \u00A7f" + IVUpgrade(EnumNature.natureFromString(Main.nature1).index))))
							.build();
					Text.builder()
							.color(TextColors.DARK_GRAY)
							.append(Text.of("\u00A7b" + Main.pokemon4))
							.onHover(TextActions.showText(Text.of("\u00A73Nature 1: \u00A7f" + Main.nature4 + "\n\u00A73Nature 2: \u00A7f" + Main.nature4b + "\n\u00A73Nature 3: \u00A7f" + Main.nature4c)))
							.build();
					Text.builder()
							.color(TextColors.DARK_GRAY)
							.append(Text.of(" \u00A77[\u00A7eR\u00A77]"))
							.onHover(TextActions.showText(Text.of("\u00A7dBalls: \u00A7f" + Main.pokemon4ballReward.getQuantity() + " " + Main.pokemon4ballName + "\n\u00A72Money: \u00A7f" + Main.pokemon4moneyReward + " coins\n\u00A7bRare Candies: \u00A7f" + Main.pokemon4rc.getQuantity())))// + "\n\u00A76IVs: \u00A7f" + IVUpgrade(EnumNature.natureFromString(Main.nature1).index))))
							.build();
					Text.of("\u00A7f, ");
					Text finalMessage1 = Text.builder()
							.append(Text.of(Utils.prefix() + " " + Utils.lang("announcement-message")))
							.build();
					Sponge.getServer().getBroadcastChannel().send(finalMessage1);
				}
			})
					.interval(Config.getInstance().getConfig().getNode("pixelhunt", "general", "announcement-timer").getInt(), TimeUnit.MINUTES)
					.name("PixelHunt Announcement")
					.submit(this);
		}
	}

	private static Optional<ConsoleSource> getConsole() {
		if (Sponge.isServerAvailable())
			return Optional.of(Sponge.getServer().getConsole());
		else
			return Optional.empty();
	}

	public void consoleMsg(String str) {
		getConsole().ifPresent(console ->
				console.sendMessage(Text.of(str)));
	}
}
