package com.xpgaming.pixelhunt.utils;

import ca.landonjw.gooeylibs.inventory.api.Button;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.xpgaming.pixelhunt.Config;
import com.xpgaming.pixelhunt.Main;
import com.xpgaming.pixelhunt.enums.EnumItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xpgaming.pixelhunt.commands.Hunt.rewardList;

public class Utils {

    private static Utils instance = new Utils();

    public static Utils getInstance() {
        return instance;
    }

    private static final SecureRandom random = new SecureRandom();


    public void randomisePokemon(int slot) {
        switch (slot) {
            case 1:
                cancelTimers(1);
                if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                    Main.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                else Main.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                while (getExcludedPokemon().contains(Main.pokemon1)) {
                    if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                        Main.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                    else Main.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                }
                Main.nature1 = randomEnum(EnumNature.class).toString();
                Main.nature1b = randomEnum(EnumNature.class).toString();
                while (Main.nature1b.equalsIgnoreCase(Main.nature1))
                    Main.nature1b = randomEnum(EnumNature.class).toString();
                Main.nature1c = randomEnum(EnumNature.class).toString();
                while (Main.nature1c.equalsIgnoreCase(Main.nature1b) || Main.nature1c.equalsIgnoreCase(Main.nature1))
                    Main.nature1c = randomEnum(EnumNature.class).toString();
                if (getUncommonPokemon().contains(Main.pokemon1)) Main.pokemon1rc = randomRareCandy(1);
                else Main.pokemon1rc = randomRareCandy(0);
                if (getUncommonPokemon().contains(Main.pokemon1)) {
                    Main.pokemon1ballReward = randomBall(1, 1);
                    Main.pokemon1moneyReward = randomMoney(1);
                    Main.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
                } else if (Utils.getRarePokemon().contains(Main.pokemon1)) {
                    Main.pokemon1rc = randomRareCandy(2);
                    Main.pokemon1ballReward = randomBall(1, 2);
                    Main.pokemon1moneyReward = randomMoney(2);
                    Main.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
                } else {
                    Main.pokemon1ballReward = randomBall(1, 0);
                    Main.pokemon1moneyReward = randomMoney(0);
                    Main.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
                }
                startTimer(1);
                break;
            case 2:
                cancelTimers(2);
                if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                    Main.pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                else Main.pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                while (getExcludedPokemon().contains(Main.pokemon2)) {
                    if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                        Main.pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                    else Main.pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                }
                Main.nature2 = randomEnum(EnumNature.class).toString();
                Main.nature2b = randomEnum(EnumNature.class).toString();
                while (Main.nature2b.equalsIgnoreCase(Main.nature2))
                    Main.nature2b = randomEnum(EnumNature.class).toString();
                Main.nature2c = randomEnum(EnumNature.class).toString();
                while (Main.nature2c.equalsIgnoreCase(Main.nature2b) || Main.nature2c.equalsIgnoreCase(Main.nature2))
                    Main.nature2c = randomEnum(EnumNature.class).toString();
                if (getUncommonPokemon().contains(Main.pokemon2)) Main.pokemon2rc = randomRareCandy(1);
                else Main.pokemon2rc = randomRareCandy(0);
                if (getUncommonPokemon().contains(Main.pokemon2)) {
                    Main.pokemon2ballReward = randomBall(2, 1);
                    Main.pokemon2moneyReward = randomMoney(1);
                    Main.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
                } else if (Utils.getRarePokemon().contains(Main.pokemon2)) {
                    Main.pokemon2rc = randomRareCandy(2);
                    Main.pokemon2ballReward = randomBall(2, 2);
                    Main.pokemon2moneyReward = randomMoney(2);
                    Main.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
                } else {
                    Main.pokemon2ballReward = randomBall(2, 0);
                    Main.pokemon2moneyReward = randomMoney(0);
                    Main.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
                }
                startTimer(2);
                break;
            case 3:
                cancelTimers(3);
                if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                    Main.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                else Main.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                while (getExcludedPokemon().contains(Main.pokemon3)) {
                    if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                        Main.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                    else Main.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                }
                Main.nature3 = randomEnum(EnumNature.class).toString();
                Main.nature3b = randomEnum(EnumNature.class).toString();
                while (Main.nature3b.equalsIgnoreCase(Main.nature3))
                    Main.nature3b = randomEnum(EnumNature.class).toString();
                Main.nature3c = randomEnum(EnumNature.class).toString();
                while (Main.nature3c.equalsIgnoreCase(Main.nature3b) || Main.nature3c.equalsIgnoreCase(Main.nature3))
                    Main.nature3c = randomEnum(EnumNature.class).toString();
                if (getUncommonPokemon().contains(Main.pokemon3)) Main.pokemon3rc = randomRareCandy(1);
                else Main.pokemon3rc = randomRareCandy(0);
                if (getUncommonPokemon().contains(Main.pokemon3)) {
                    Main.pokemon3ballReward = randomBall(3, 1);
                    Main.pokemon3moneyReward = randomMoney(1);
                    Main.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
                } else if (Utils.getRarePokemon().contains(Main.pokemon3)) {
                    Main.pokemon3rc = randomRareCandy(2);
                    Main.pokemon3ballReward = randomBall(3, 2);
                    Main.pokemon3moneyReward = randomMoney(2);
                    Main.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
                } else {
                    Main.pokemon3ballReward = randomBall(3, 0);
                    Main.pokemon3moneyReward = randomMoney(0);
                    Main.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
                }
                startTimer(3);
                break;
            case 4:
                cancelTimers(4);
                if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                    Main.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                else Main.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                while (getExcludedPokemon().contains(Main.pokemon4)) {
                    if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                        Main.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                    else Main.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                }
                Main.nature4 = randomEnum(EnumNature.class).toString();
                Main.nature4b = randomEnum(EnumNature.class).toString();
                while (Main.nature4b.equalsIgnoreCase(Main.nature4))
                    Main.nature4b = randomEnum(EnumNature.class).toString();
                Main.nature4c = randomEnum(EnumNature.class).toString();
                while (Main.nature4c.equalsIgnoreCase(Main.nature4b) || Main.nature4c.equalsIgnoreCase(Main.nature4))
                    Main.nature4c = randomEnum(EnumNature.class).toString();
                if (getUncommonPokemon().contains(Main.pokemon4)) Main.pokemon4rc = randomRareCandy(1);
                else Main.pokemon4rc = randomRareCandy(0);
                if (getUncommonPokemon().contains(Main.pokemon4)) {
                    Main.pokemon4ballReward = randomBall(4, 1);
                    Main.pokemon4moneyReward = randomMoney(1);
                    Main.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
                } else if (Utils.getRarePokemon().contains(Main.pokemon4)) {
                    Main.pokemon4rc = randomRareCandy(2);
                    Main.pokemon4ballReward = randomBall(4, 2);
                    Main.pokemon4moneyReward = randomMoney(2);
                    Main.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
                } else {
                    Main.pokemon4ballReward = randomBall(4, 0);
                    Main.pokemon4moneyReward = randomMoney(0);
                    Main.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
                }
                startTimer(4);
                break;
        }

    }

    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        int x = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }


    private static final String regex = "&(?=[0-9a-fk-or])";
    private static final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    public static String regex(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            line = line.replaceAll(regex, "§");
        }
        return line;
    }

    public static Button colouredPane(EnumDyeColor color) {
        return Button.builder()
                .item(new net.minecraft.item.ItemStack(Blocks.STAINED_GLASS_PANE, 1, color.getMetadata()))
                .displayName("")
                .build();
    }

    public static int getButtonPos(String node, int pos) {
        String[] numString = Config.getInstance().getConfig().getNode("pixelhunt", "gui", node).getString().split(", ");
        switch (pos) {
            case 1:
                return Integer.valueOf(numString[0]);
            case 2:
                return Integer.valueOf(numString[1]);
            default:
                return -1;
        }
    }


    public static List<String> getHuntInfo(String nature, String reward, String expiry) {
        List<String> lore = new ArrayList<>();
        lore.add(regex("&a&l" + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "natures").getString() + ":"));
        switch (nature) {
            case "nature1":
                lore.add(regex("&e- " + Main.nature1));
                lore.add(regex("&e- " + Main.nature1b));
                lore.add(regex("&e- " + Main.nature1c));
                break;
            case "nature2":
                lore.add(regex("&e- " + Main.nature2));
                lore.add(regex("&e- " + Main.nature2b));
                lore.add(regex("&e- " + Main.nature2c));
                break;
            case "nature3":
                lore.add(regex("&e- " + Main.nature3));
                lore.add(regex("&e- " + Main.nature3b));
                lore.add(regex("&e- " + Main.nature3c));
                break;
            case "nature4":
                lore.add(regex("&e- " + Main.nature4));
                lore.add(regex("&e- " + Main.nature4b));
                lore.add(regex("&e- " + Main.nature4c));
                break;
        }

        lore.add(regex("&a&l" + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "rewards").getString() + ":"));
        switch (reward) {
            case "reward1":
                lore.add(regex("&e- " + rewardList(1)));
                break;
            case "reward2":
                lore.add(regex("&e- " + rewardList(2)));
                break;
            case "reward3":
                lore.add(regex("&e- " + rewardList(3)));
                break;
            case "reward4":
                lore.add(regex("&e- " + rewardList(4)));
                break;
        }

        lore.add(regex("&a&l" + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "expiry").getString() + ":"));

        LocalDateTime timeNow = LocalDateTime.now();
        switch (expiry) {
            case "expiry1":
                lore.add(regex("&e- " + Utils.getInstance().calculateTimeDifference(timeNow, Main.pokemon1expiry)));
                break;
            case "expiry2":
                lore.add(regex("&e- " + Utils.getInstance().calculateTimeDifference(timeNow, Main.pokemon2expiry)));
                break;
            case "expiry3":
                lore.add(regex("&e- " + Utils.getInstance().calculateTimeDifference(timeNow, Main.pokemon3expiry)));
                break;
            case "expiry4":
                lore.add(regex("&e- " + Utils.getInstance().calculateTimeDifference(timeNow, Main.pokemon4expiry)));
                break;
        }
        return lore;

    }

    public static net.minecraft.item.ItemStack getPokemonPhoto(Pokemon pokemon) {
        return ItemPixelmonSprite.getPhoto(pokemon);
    }


    public static String sanitisePokemon(String poke) {
        if (poke.equalsIgnoreCase("MrMime")) return "Mr. Mime";
        else if (poke.equalsIgnoreCase("MimeJr")) return "Mime Jr.";
        else if (poke.equalsIgnoreCase("Nidoranfemale")) return "Nidoran\u2640";
        else if (poke.equalsIgnoreCase("Nidoranmale")) return "Nidoran\u2642";
        else if (poke.equalsIgnoreCase("Farfetchd")) return "Farfetch'd";
        return poke;
    }

    public String calculateTimeDifference(LocalDateTime from, LocalDateTime to) {

        LocalDateTime fromTemp = LocalDateTime.from(from);
        long years = fromTemp.until(to, ChronoUnit.YEARS);
        fromTemp = fromTemp.plusYears(years);

        long months = fromTemp.until(to, ChronoUnit.MONTHS);
        fromTemp = fromTemp.plusMonths(months);

        long days = fromTemp.until(to, ChronoUnit.DAYS);
        fromTemp = fromTemp.plusDays(days);

        long hours = fromTemp.until(to, ChronoUnit.HOURS);
        fromTemp = fromTemp.plusHours(hours);

        long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
        fromTemp = fromTemp.plusMinutes(minutes);

        long seconds = fromTemp.until(to, ChronoUnit.SECONDS);
        fromTemp = fromTemp.plusSeconds(seconds);

        String unitDays = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "unitDays").getString();
        String unitHours = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "unitHours").getString();
        String unitSeconds = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "unitSeconds").getString();

        return "\u00A7f" + days + "\u00A7b" + unitDays + "\u00A7f" + unitHours + "\u00A7b" + unitSeconds + "\u00A7f" + minutes + "\u00A7b" + unitSeconds + "\u00A7f" + seconds + "\u00A7bS\u00A77";
    }

    public int isInHunt(Pokemon pokemon) {
        if(pokemon == null){
            return 0;
        }
        Pokemon pokemon1 = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(Main.pokemon1));
        Pokemon pokemon2 = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(Main.pokemon2));
        Pokemon pokemon3 = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(Main.pokemon3));
        Pokemon pokemon4 = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(Main.pokemon4));

        if (pokemon.getSpecies() == pokemon1.getSpecies()) {
            return 1;
        } else if (pokemon.getSpecies() == pokemon2.getSpecies()) {
            return 2;
        } else if (pokemon.getSpecies() == pokemon3.getSpecies()) {
            return 3;
        } else if (pokemon.getSpecies() == pokemon4.getSpecies()) {
            return 4;
        } else {
            return 0;
        }
    }

    public void startTimer(int slot) {
        int min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "common-pokemon-timer-min").getInt();
        int max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "common-pokemon-timer-max").getInt();
        int hours = random.nextInt(max - min + 1) + min;
        LocalDateTime date = LocalDateTime.now();
        switch (slot) {
            case 1:
                cancelTimers(1);
                if (getUncommonPokemon().contains(Main.pokemon1)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-max").getInt();
                } else if (Utils.getRarePokemon().contains(Main.pokemon1)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-max").getInt();
                }
                hours = random.nextInt(max - min + 1) + min;
                if (min == max) hours = max;
                date = date.plusHours(hours);
                Main.pokemon1expiry = date;

                Task.builder().execute(() -> {
                    Sponge.getServer().getBroadcastChannel().send(Text.of(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "hunt-ended").getString().replace("<pokemon>", Main.pokemon1)));
                    randomisePokemon(slot);
                    Sponge.getServer().getBroadcastChannel().send(Text.of(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "new-hunt").getString().replace("<pokemon>", Main.pokemon1)));
                })
                        .delay(hours, TimeUnit.HOURS)
                        .name(slot + "HuntTimer")
                        .submit(Main.getInstance());
                break;
            case 2:
                cancelTimers(2);
                if (getUncommonPokemon().contains(Main.pokemon2)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-max").getInt();
                } else if (Utils.getRarePokemon().contains(Main.pokemon2)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-max").getInt();
                }
                hours = random.nextInt(max - min + 1) + min;
                date = date.plusHours(hours);
                if (min == max) hours = max;
                Main.pokemon2expiry = date;
                Task.builder().execute(() -> {
                    Sponge.getServer().getBroadcastChannel().send(Text.of(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "hunt-ended").getString().replace("<pokemon>", Main.pokemon2)));
                    randomisePokemon(slot);
                    Sponge.getServer().getBroadcastChannel().send(Text.of(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "new-hunt").getString().replace("<pokemon>", Main.pokemon2)));
                })
                        .delay(hours, TimeUnit.HOURS)
                        .name(slot + "HuntTimer")
                        .submit(Main.getInstance());
                break;
            case 3:
                cancelTimers(3);
                if (getUncommonPokemon().contains(Main.pokemon3)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-max").getInt();
                } else if (Utils.getRarePokemon().contains(Main.pokemon3)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-max").getInt();
                }
                hours = random.nextInt(max - min + 1) + min;
                if (min == max) hours = max;
                date = date.plusHours(hours);
                Main.pokemon3expiry = date;
                Task.builder().execute(() -> {
                    Sponge.getServer().getBroadcastChannel().send(Text.of(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "hunt-ended").getString().replace("<pokemon>", Main.pokemon3)));
                    randomisePokemon(slot);
                    Sponge.getServer().getBroadcastChannel().send(Text.of(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "new-hunt").getString().replace("<pokemon>", Main.pokemon3)));
                })
                        .delay(hours, TimeUnit.HOURS)
                        .name(slot + "HuntTimer")
                        .submit(Main.getInstance());
                break;
            case 4:
                cancelTimers(4);
                if (getUncommonPokemon().contains(Main.pokemon4)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-max").getInt();
                } else if (Utils.getRarePokemon().contains(Main.pokemon4)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-max").getInt();
                }
                hours = random.nextInt(max - min + 1) + min;
                if (min == max) hours = max;
                date = date.plusHours(hours);
                Main.pokemon4expiry = date;
                Task.builder().execute(() -> {
                    Sponge.getServer().getBroadcastChannel().send(Text.of(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "hunt-ended").getString().replace("<pokemon>", Main.pokemon4)));
                    randomisePokemon(slot);
                    Sponge.getServer().getBroadcastChannel().send(Text.of(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "new-hunt").getString().replace("<pokemon>", Main.pokemon4)));
                })
                        .delay(hours, TimeUnit.HOURS)
                        .name(slot + "HuntTimer")
                        .submit(Main.getInstance());
                break;
        }
    }

    public void cancelTimers(int slot) {
        if (slot == 0) {
            Set<Task> tasks = Sponge.getScheduler().getTasksByName(1 + "HuntTimer");
            tasks.addAll(Sponge.getScheduler().getTasksByName(2 + "HuntTimer"));
            tasks.addAll(Sponge.getScheduler().getTasksByName(3 + "HuntTimer"));
            tasks.addAll(Sponge.getScheduler().getTasksByName(4 + "HuntTimer"));
            for (Task t : tasks) {
                t.cancel();
            }
        } else {
            Set<Task> tasks = Sponge.getScheduler().getTasksByName(slot + "HuntTimer");
            for (Task t : tasks) {
                t.cancel();
            }
        }
    }

    public int getRarity(String name) {
        if (getUncommonPokemon().contains(name)) {
            return 1;
        } else if (getRarePokemon().contains(name)) {
            return 2;
        } else {
            return 0;
        }
    }

    public void initialisePokemon() {
        cancelTimers(0);
        if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
            Main.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
        else Main.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        while (getExcludedPokemon().contains(Main.pokemon1)) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                Main.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
            else Main.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        }
        Main.nature1 = randomEnum(EnumNature.class).toString();
        Main.nature1b = randomEnum(EnumNature.class).toString();
        while (Main.nature1b.equalsIgnoreCase(Main.nature1)) Main.nature1b = randomEnum(EnumNature.class).toString();
        Main.nature1c = randomEnum(EnumNature.class).toString();
        while (Main.nature1c.equalsIgnoreCase(Main.nature1b) || Main.nature1c.equalsIgnoreCase(Main.nature1))
            Main.nature1c = randomEnum(EnumNature.class).toString();
        Main.pokemon1ballReward = randomBall(1, 0);
        Main.pokemon1moneyReward = randomMoney(0);
        Main.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
        if (getUncommonPokemon().contains(Main.pokemon1)) {
            Main.pokemon1moneyReward = randomMoney(1);
            Main.pokemon1rc = randomRareCandy(1);
            Main.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
        } else if (Utils.getRarePokemon().contains(Main.pokemon1)) {
            Main.pokemon1moneyReward = randomMoney(2);
            Main.pokemon1rc = randomRareCandy(2);
            Main.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
        } else Main.pokemon1rc = randomRareCandy(0);
        startTimer(1);
        if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
            Main.pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
        else Main.pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        while (getExcludedPokemon().contains(Main.pokemon2)) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                Main.pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
            else Main.pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        }
        Main.nature2 = randomEnum(EnumNature.class).toString();
        Main.nature2b = randomEnum(EnumNature.class).toString();
        while (Main.nature2b.equalsIgnoreCase(Main.nature2)) Main.nature2b = randomEnum(EnumNature.class).toString();
        Main.nature2c = randomEnum(EnumNature.class).toString();
        while (Main.nature2c.equalsIgnoreCase(Main.nature2b) || Main.nature2c.equalsIgnoreCase(Main.nature2))
            Main.nature2c = randomEnum(EnumNature.class).toString();
        Main.pokemon2ballReward = randomBall(2, 0);
        Main.pokemon2moneyReward = randomMoney(0);
        Main.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
        if (getUncommonPokemon().contains(Main.pokemon2)) {
            Main.pokemon2moneyReward = randomMoney(1);
            Main.pokemon2rc = randomRareCandy(1);
            Main.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
        } else if (Utils.getRarePokemon().contains(Main.pokemon2)) {
            Main.pokemon2moneyReward = randomMoney(2);
            Main.pokemon2rc = randomRareCandy(2);
            Main.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
        } else Main.pokemon2rc = randomRareCandy(0);
        startTimer(2);
        if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
            Main.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
        else Main.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        while (getExcludedPokemon().contains(Main.pokemon3)) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                Main.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
            else Main.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        }
        Main.nature3 = randomEnum(EnumNature.class).toString();
        Main.nature3b = randomEnum(EnumNature.class).toString();
        while (Main.nature3b.equalsIgnoreCase(Main.nature3)) Main.nature3b = randomEnum(EnumNature.class).toString();
        Main.nature3c = randomEnum(EnumNature.class).toString();
        while (Main.nature3c.equalsIgnoreCase(Main.nature3b) || Main.nature3c.equalsIgnoreCase(Main.nature3))
            Main.nature3c = randomEnum(EnumNature.class).toString();
        Main.pokemon3ballReward = randomBall(3, 0);
        Main.pokemon3moneyReward = randomMoney(0);
        Main.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
        if (getUncommonPokemon().contains(Main.pokemon3)) {
            Main.pokemon3moneyReward = randomMoney(1);
            Main.pokemon3rc = randomRareCandy(1);
            Main.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
        } else if (Utils.getRarePokemon().contains(Main.pokemon3)) {
            Main.pokemon3moneyReward = randomMoney(2);
            Main.pokemon3rc = randomRareCandy(2);
            Main.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
        } else Main.pokemon3rc = randomRareCandy(0);
        startTimer(3);
        if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
            Main.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
        else Main.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        while (getExcludedPokemon().contains(Main.pokemon4)) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                Main.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
            else Main.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        }
        Main.nature4 = randomEnum(EnumNature.class).toString();
        Main.nature4b = randomEnum(EnumNature.class).toString();
        while (Main.nature4b.equalsIgnoreCase(Main.nature4)) Main.nature4b = randomEnum(EnumNature.class).toString();
        Main.nature4c = randomEnum(EnumNature.class).toString();
        while (Main.nature4c.equalsIgnoreCase(Main.nature4b) || Main.nature4c.equalsIgnoreCase(Main.nature4))
            Main.nature4c = randomEnum(EnumNature.class).toString();
        Main.pokemon4ballReward = randomBall(4, 0);
        Main.pokemon4moneyReward = randomMoney(0);
        Main.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
        if (getUncommonPokemon().contains(Main.pokemon4)) {
            Main.pokemon4moneyReward = randomMoney(1);
            Main.pokemon4rc = randomRareCandy(1);
            Main.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
        } else if (Utils.getRarePokemon().contains(Main.pokemon4)) {
            Main.pokemon4moneyReward = randomMoney(2);
            Main.pokemon4rc = randomRareCandy(2);
            Main.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
        } else Main.pokemon4rc = randomRareCandy(0);
        startTimer(4);
    }

    public BigDecimal randomMoney(int rarity) {
        if (Main.hasEconomy) {
            Random rn = new Random();
            int min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-money-min").getInt(), max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-money-max").getInt();
            if (rarity == 1) {
                min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-money-min").getInt();
                max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-money-max").getInt();
            } else if (rarity == 2) {
                min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-money-min").getInt();
                max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-money-max").getInt();
            }
            BigDecimal total = BigDecimal.valueOf(rn.nextInt(max - min + 1) + min);
            return total;
        } else {
            return BigDecimal.valueOf(0);
        }
    }

    public void executeCommand(int rarity, String name) {
        List<String> cmd1list = new ArrayList<>();
        List<String> cmd2list = new ArrayList<>();
        List<String> cmd3list = new ArrayList<>();
        String cmd1 = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-cmd").getString().replace("%player%", name);
        String cmd2 = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-cmd").getString().replace("%player%", name);
        String cmd3 = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-cmd").getString().replace("%player%", name);

        if (rarity == 1) {
            if (cmd2.contains(";")) cmd2list = Arrays.asList(cmd2.split("\\s*;\\s*"));
            else cmd2list.add(cmd2);

            for (String command : cmd2list) {
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), command);
            }

        } else if (rarity == 2) {
            if (cmd3.contains(";")) cmd3list = Arrays.asList(cmd3.split("\\s*;\\s*"));
            else cmd3list.add(cmd3);

            for (String command : cmd3list) {
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), command);
            }

        } else {
            if (cmd1.contains(";")) cmd1list = Arrays.asList(cmd1.split("\\s*;\\s*"));
            else cmd1list.add(cmd1);

            for (String command : cmd1list) {
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), command);
            }

        }
    }

    public ItemStack randomRareCandy(int rarity) {
        Random rn = new Random();
        int min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-rarecandy-min").getInt(), max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-rarecandy-max").getInt();
        if (rarity == 1) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-rarecandy-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-rarecandy-max").getInt();
        } else if (rarity == 2) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-rarecandy-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-rarecandy-max").getInt();
        }
        ItemStack is = ItemStack.builder()
                .itemType(Type("pixelmon:rare_candy"))
                .quantity(rn.nextInt(max - min + 1) + min)
                .build();
        return is;
    }

    public ItemStack randomBall(int slot, int rarity) {
        String name = randomEnum(EnumItems.class).toString();
        switch (slot) {
            case 1:
                switch (name) {
                    case "level_ball":
                        Main.pokemon1ballName = "Level Balls";
                        break;
                    case "moon_ball":
                        Main.pokemon1ballName = "Moon Balls";
                        break;
                    case "friend_ball":
                        Main.pokemon1ballName = "Friend Balls";
                        break;
                    case "love_ball":
                        Main.pokemon1ballName = "Love Balls";
                        break;
                    case "timer_ball":
                        Main.pokemon1ballName = "Timer Balls";
                        break;
                    case "nest_ball":
                        Main.pokemon1ballName = "Nest Balls";
                        break;
                    case "dive_ball":
                        Main.pokemon1ballName = "Dive Balls";
                        break;
                    case "luxury_ball":
                        Main.pokemon1ballName = "Luxury Balls";
                        break;
                    case "heal_ball":
                        Main.pokemon1ballName = "Heal Balls";
                        break;
                    case "dusk_ball":
                        Main.pokemon1ballName = "Dusk Balls";
                        break;
                    case "lure_ball":
                        Main.pokemon1ballName = "Lure Balls";
                        break;
                    case "sport_ball":
                        Main.pokemon1ballName = "Sport Balls";
                        break;
                    case "ultra_ball":
                        Main.pokemon1ballName = "Ultra Balls";
                        break;
                    case "poke_ball":
                        Main.pokemon1ballName = "Poke Balls";
                        break;
                    case "quick_ball":
                        Main.pokemon1ballName = "Quick Balls";
                        break;
                    case "heavy_ball":
                        Main.pokemon1ballName = "Heavy Balls";
                        break;
                    case "fast_ball":
                        Main.pokemon1ballName = "Fast Balls";
                        break;
                    case "repeat_ball":
                        Main.pokemon1ballName = "Repeat Balls";
                        break;
                    case "gs_ball":
                        Main.pokemon1ballName = "GS Balls";
                        break;
                    case "great_ball":
                        Main.pokemon1ballName = "Great Balls";
                        break;
                    case "master_ball":
                        Main.pokemon1ballName = "Master Balls";
                        break;
                    case "park_ball":
                        Main.pokemon1ballName = "Park Balls";
                        break;
                    default:
                        Main.pokemon1ballName = "??? Balls";
                        break;
                }
                break;
            case 2:
                switch (name) {
                    case "level_ball":
                        Main.pokemon2ballName = "Level Balls";
                        break;
                    case "moon_ball":
                        Main.pokemon2ballName = "Moon Balls";
                        break;
                    case "friend_ball":
                        Main.pokemon2ballName = "Friend Balls";
                        break;
                    case "love_ball":
                        Main.pokemon2ballName = "Love Balls";
                        break;
                    case "timer_ball":
                        Main.pokemon2ballName = "Timer Balls";
                        break;
                    case "nest_ball":
                        Main.pokemon2ballName = "Nest Balls";
                        break;
                    case "dive_ball":
                        Main.pokemon2ballName = "Dive Balls";
                        break;
                    case "luxury_ball":
                        Main.pokemon2ballName = "Luxury Balls";
                        break;
                    case "heal_ball":
                        Main.pokemon2ballName = "Heal Balls";
                        break;
                    case "dusk_ball":
                        Main.pokemon2ballName = "Dusk Balls";
                        break;
                    case "lure_ball":
                        Main.pokemon2ballName = "Lure Balls";
                        break;
                    case "sport_ball":
                        Main.pokemon2ballName = "Sport Balls";
                        break;
                    case "ultra_ball":
                        Main.pokemon2ballName = "Ultra Balls";
                        break;
                    case "poke_ball":
                        Main.pokemon2ballName = "Poke Balls";
                        break;
                    case "quick_ball":
                        Main.pokemon2ballName = "Quick Balls";
                        break;
                    case "heavy_ball":
                        Main.pokemon2ballName = "Heavy Balls";
                        break;
                    case "fast_ball":
                        Main.pokemon2ballName = "Fast Balls";
                        break;
                    case "repeat_ball":
                        Main.pokemon2ballName = "Repeat Balls";
                        break;
                    case "gs_ball":
                        Main.pokemon2ballName = "GS Balls";
                        break;
                    case "great_ball":
                        Main.pokemon2ballName = "Great Balls";
                        break;
                    case "master_ball":
                        Main.pokemon2ballName = "Master Balls";
                        break;
                    case "park_ball":
                        Main.pokemon2ballName = "Park Balls";
                        break;
                    default:
                        Main.pokemon2ballName = "??? Balls";
                        break;
                }
                break;
            case 3:
                switch (name) {
                    case "level_ball":
                        Main.pokemon3ballName = "Level Balls";
                        break;
                    case "moon_ball":
                        Main.pokemon3ballName = "Moon Balls";
                        break;
                    case "friend_ball":
                        Main.pokemon3ballName = "Friend Balls";
                        break;
                    case "love_ball":
                        Main.pokemon3ballName = "Love Balls";
                        break;
                    case "timer_ball":
                        Main.pokemon3ballName = "Timer Balls";
                        break;
                    case "nest_ball":
                        Main.pokemon3ballName = "Nest Balls";
                        break;
                    case "dive_ball":
                        Main.pokemon3ballName = "Dive Balls";
                        break;
                    case "luxury_ball":
                        Main.pokemon3ballName = "Luxury Balls";
                        break;
                    case "heal_ball":
                        Main.pokemon3ballName = "Heal Balls";
                        break;
                    case "dusk_ball":
                        Main.pokemon3ballName = "Dusk Balls";
                        break;
                    case "lure_ball":
                        Main.pokemon3ballName = "Lure Balls";
                        break;
                    case "sport_ball":
                        Main.pokemon3ballName = "Sport Balls";
                        break;
                    case "ultra_ball":
                        Main.pokemon3ballName = "Ultra Balls";
                        break;
                    case "poke_ball":
                        Main.pokemon3ballName = "Poke Balls";
                        break;
                    case "quick_ball":
                        Main.pokemon3ballName = "Quick Balls";
                        break;
                    case "heavy_ball":
                        Main.pokemon3ballName = "Heavy Balls";
                        break;
                    case "fast_ball":
                        Main.pokemon3ballName = "Fast Balls";
                        break;
                    case "repeat_ball":
                        Main.pokemon3ballName = "Repeat Balls";
                        break;
                    case "gs_ball":
                        Main.pokemon3ballName = "GS Balls";
                        break;
                    case "great_ball":
                        Main.pokemon3ballName = "Great Balls";
                        break;
                    case "master_ball":
                        Main.pokemon3ballName = "Master Balls";
                        break;
                    case "park_ball":
                        Main.pokemon3ballName = "Park Balls";
                        break;
                    default:
                        Main.pokemon3ballName = "??? Balls";
                        break;
                }
                break;
            case 4:
                switch (name) {
                    case "level_ball":
                        Main.pokemon4ballName = "Level Balls";
                        break;
                    case "moon_ball":
                        Main.pokemon4ballName = "Moon Balls";
                        break;
                    case "friend_ball":
                        Main.pokemon4ballName = "Friend Balls";
                        break;
                    case "love_ball":
                        Main.pokemon4ballName = "Love Balls";
                        break;
                    case "timer_ball":
                        Main.pokemon4ballName = "Timer Balls";
                        break;
                    case "nest_ball":
                        Main.pokemon4ballName = "Nest Balls";
                        break;
                    case "dive_ball":
                        Main.pokemon4ballName = "Dive Balls";
                        break;
                    case "luxury_ball":
                        Main.pokemon4ballName = "Luxury Balls";
                        break;
                    case "heal_ball":
                        Main.pokemon4ballName = "Heal Balls";
                        break;
                    case "dusk_ball":
                        Main.pokemon4ballName = "Dusk Balls";
                        break;
                    case "lure_ball":
                        Main.pokemon4ballName = "Lure Balls";
                        break;
                    case "sport_ball":
                        Main.pokemon4ballName = "Sport Balls";
                        break;
                    case "ultra_ball":
                        Main.pokemon4ballName = "Ultra Balls";
                        break;
                    case "poke_ball":
                        Main.pokemon4ballName = "Poke Balls";
                        break;
                    case "quick_ball":
                        Main.pokemon4ballName = "Quick Balls";
                        break;
                    case "heavy_ball":
                        Main.pokemon4ballName = "Heavy Balls";
                        break;
                    case "fast_ball":
                        Main.pokemon4ballName = "Fast Balls";
                        break;
                    case "repeat_ball":
                        Main.pokemon4ballName = "Repeat Balls";
                        break;
                    case "gs_ball":
                        Main.pokemon4ballName = "GS Balls";
                        break;
                    case "great_ball":
                        Main.pokemon4ballName = "Great Balls";
                        break;
                    case "master_ball":
                        Main.pokemon4ballName = "Master Balls";
                        break;
                    case "park_ball":
                        Main.pokemon4ballName = "Park Balls";
                        break;
                    default:
                        Main.pokemon4ballName = "??? Balls";
                        break;
                }
                break;
        }

        Random rn = new Random();
        int min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-balls-min").getInt(), max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-balls-max").getInt();
        if (rarity == 1) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-balls-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-balls-max").getInt();
        } else if (rarity == 2) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-balls-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-balls-max").getInt();
        }
        ItemStack is = ItemStack.builder()
                .itemType(Type("pixelmon:" + name))
                .quantity(rn.nextInt(max - min + 1) + min)
                .build();
        return is;
    }

    public ItemType Type(String item) {
        ItemType i = Sponge.getGame().getRegistry().getType(ItemType.class, item).get();
        return i;
    }

    public static String formatText(final String input) {
        // Set up a list of valid formatting codes.
        final List<Character> validFormattingCharacters = Arrays.asList
                (
                        // Color numbers.
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                        // Color letters, lower and upper case.
                        'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F',
                        // Other formatting codes.
                        'k', 'l', 'm', 'n', 'o', 'r'
                );

        // Start replacing our ampersands.
        final StringBuilder mutableInput = new StringBuilder(input);
        for (int i = 0; i < mutableInput.length(); i++) {
            // Is the character that's currently being checked an ampersand?
            if (mutableInput.charAt(i) == '&') {
                // Is the loop value lower than the total length of the input String? Let's not check out of bounds.
                if ((i + 1) < mutableInput.length()) {
                    // Look ahead: Does the next character contain a known formatting character? Replace the ampersand!
                    if (validFormattingCharacters.contains(mutableInput.charAt(i + 1)))
                        mutableInput.setCharAt(i, '§');
                }
            }
        }

        // Replace our old input String with the one that we fixed formatting on.
        return mutableInput.toString();
    }

    // Takes a config String, and replaces a single placeholder with the proper replacement as many times as needed.
    public static String replacePlaceholder(final String input, final String placeholder, final String replacement) {
        // If our input has a placeholder inside, replace it with the provided replacement String. Case-insensitive.
        if (input.toLowerCase().contains(placeholder))
            return input.replaceAll("(?i)" + placeholder, replacement);
        else
            return input;
    }

    public static List<String> getExcludedPokemon() {
        List<String> items = Arrays.asList(Config.getInstance().getConfig().getNode("pixelhunt", "lists", "excluded-pokemon-list").getString().split("\\s*,\\s*"));
        return items;
    }

    public static List<String> getUncommonPokemon() {
        List<String> items = Arrays.asList(Config.getInstance().getConfig().getNode("pixelhunt", "lists", "uncommon-pokemon-list").getString().split("\\s*,\\s*"));
        return items;
    }

    public static List<String> getRarePokemon() {
        List<String> items = Arrays.asList(Config.getInstance().getConfig().getNode("pixelhunt", "lists", "rare-pokemon-list").getString().split("\\s*,\\s*"));
        return items;
    }

    public static String prefix() {
        return Utils.formatText(Config.getInstance().getConfig().getNode("pixelhunt", "lang", "prefix").getString());
    }

    public static String lang(String node) {
        return Utils.formatText(Config.getInstance().getConfig().getNode("pixelhunt", "lang", node).getString());
    }
}
