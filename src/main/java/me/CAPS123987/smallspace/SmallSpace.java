package me.CAPS123987.smallspace;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import me.CAPS123987.cargo.ExportBus;
import me.CAPS123987.cargo.ImportBus;
import me.CAPS123987.cargo.SpaceInterface;
import me.CAPS123987.dimension.SmallSpaceDim;
import me.CAPS123987.implementation.SizedBlock;
import me.CAPS123987.implementation.Teleporterss;
import me.CAPS123987.items.Items;
import me.CAPS123987.machines.BlockAssigner;
import me.CAPS123987.tabCompleater.TabC;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.guizhanss.guizhanlibplugin.updater.GuizhanUpdater;


public class SmallSpace extends JavaPlugin implements SlimefunAddon, Listener {
    public static SmallSpace instance;
    Config cfg = new Config(this);

    public static SmallSpace getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {

		if (!getServer().getPluginManager().isPluginEnabled("GuizhanLibPlugin")) {
			getLogger().log(Level.SEVERE, "本插件需要 鬼斩前置库插件(GuizhanLibPlugin) 才能运行!");
			getLogger().log(Level.SEVERE, "从此处下载: https://50l.cc/gzlib");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
    	
    	this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);

    	cfg.setHeader("#WARNING! Set radius smaller than border size(12 blocks+)");
    	cfg.save();

		if (cfg.getBoolean("options.auto-update") && getDescription().getVersion().startsWith("Build ")) {
			GuizhanUpdater.start(this,getFile(), "SlimefunGuguProject", "SmallSpace", "master");
		}

        instance = this;

        getCommand("SmallSpace").setTabCompleter(new TabC());
        //setup dimension
        WorldCreator worldCreator = new WorldCreator("SmallSpace");
        worldCreator.generator(new SmallSpaceDim());
        worldCreator.createWorld();

        new SlimefunItem(Items.smallSpace, Items.SPACE_STABILIZER, RecipeType.ENHANCED_CRAFTING_TABLE, Items.recipe_SPACE_STABILIZER).register(this);
        //this.getServer().getPluginManager().registerEvents(CommandListener.onCommand(null, null, getBugTrackerURL(), null),this);
        new SizedBlock(1, Items.SIZED_BLOCK1, Items.recipe_SIZED_BLOCK1).register(this);
        new SizedBlock(2, Items.SIZED_BLOCK2, Items.recipe_SIZED_BLOCK2).register(this);
        new SizedBlock(3, Items.SIZED_BLOCK3, Items.recipe_SIZED_BLOCK3).register(this);
        new SizedBlock(4, Items.SIZED_BLOCK4, Items.recipe_SIZED_BLOCK4).register(this);
        new Teleporterss().register(this);
        new BlockAssigner().register(this);

        new SpaceInterface().register(this);
        ImportBus ibus = new ImportBus();
        ibus.register(this);
        ibus.setHidden(true);

        ExportBus ebus = new ExportBus();
        ebus.register(this);
        ebus.setHidden(true);


    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new SmallSpaceDim();
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return null;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        /*
         * You will need to return a reference to your Plugin here.
         * If you are using your main class for this, simply return "this".
         */
        return this;
    }

    @EventHandler
    public void playerTeleportEvent(PlayerTeleportEvent e) {
    	TeleportCause tp = e.getCause();
    	String world = e.getFrom().getWorld().getName();
    	if(tp==TeleportCause.CHORUS_FRUIT&&world.equals("SmallSpace")) {
    		e.setCancelled(true);
    	}
    	if(tp==TeleportCause.ENDER_PEARL&&world.equals("SmallSpace")) {
    		e.setCancelled(true);
    	}
    }
    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
    	Material m = e.getBlock().getType();
    	String name = e.getBlock().getWorld().getName();
    	if(m==Material.BEDROCK&&name.equals("SmallSpace")&&!e.getPlayer().hasPermission("SmallSpace.admin")) {
    		e.setCancelled(true);
    	}

    }

    /*
     * set bukkit.yml
     */
    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {


        switch (args[0]) {
case "help":
    			help(p);
    			break;
            case "tptoId":
                if (!p.hasPermission("SmallSpace.admin")) {
                    p.sendMessage(ChatColor.RED + "你没有权限");
                    return false;
                }
                if (p instanceof Player) {
                    try {
                        String id = args[1];
                        int value = Integer.parseInt(id.replaceAll("[^0-9]", ""));
                    } catch (Exception e) {
                        p.sendMessage("需要提供小世界的ID");
                        return true;
                    }
                    String id = args[1];
                    int value = Integer.parseInt(id.replaceAll("[^0-9]", ""));
                    tryToTeleport(Calculator.getLoc(id), p);
                } else {
                    notSeder(p);
                    return true;
                }

                break;


            case "blockId":
                if (!p.hasPermission("SmallSpace.admin")) {
                    p.sendMessage(ChatColor.RED + "你没有权限");
                    return false;
                }
                try {
                    String id1 = args[1];
                    int value1 = Integer.parseInt(id1.replaceAll("[^0-9]", ""));
                } catch (Exception e) {
                    p.sendMessage("需要提供小世界的ID");
                    return true;
                }
                String id1 = args[1];
                int value1 = Integer.parseInt(id1.replaceAll("[^0-9]", ""));
                Calculator.getLoc(id1).getBlock().setType(Material.BARRIER);

                break;


            case "unblockId":
                if (!p.hasPermission("SmallSpace.admin")) {
                    p.sendMessage(ChatColor.RED + "没有权限");
                    return false;
                }
                try {
                    String id2 = args[1];
                    int value2 = Integer.parseInt(id2.replaceAll("[^0-9]", ""));
                } catch (Exception e) {
                    p.sendMessage("需要提供小世界的ID");
                    return true;
                }
                String id2 = args[1];
                int value2 = Integer.parseInt(id2.replaceAll("[^0-9]", ""));
                Calculator.getLoc(id2).getBlock().setType(Material.AIR);

                break;


            case "teleportRemove":
                if (!p.hasPermission("SmallSpace.admin")) {
                    p.sendMessage(ChatColor.RED + "你没有权限");
                    return false;
                }
                if (!(p instanceof Player)) {
                    notSeder(p);
                    return true;
                }

                Player pp;
                pp = (Player) p;
                Block b = pp.getTargetBlockExact(20);
                if (!BlockStorage.hasBlockInfo(b)) {
                    p.sendMessage("该方块没有存储信息");
                    return true;
                }
                if (!BlockStorage.getLocationInfo(b.getLocation(), "id").equals("TELEPORT")) {
                    p.sendMessage("这个方块不是小世界空间里的方块");
                    return true;
                }
                BlockStorage.clearBlockInfo(b);

                break;

            case "memberAdd":
                if (1 < args.length) {
                    if (p instanceof Player) {
                        groupAdd(p, args);
                    } else {
                        notSeder(p);
                    }
                }
                return true;


            case "memberRemove":
                if (1 < args.length) {
                    if (p instanceof Player) {
                        groupRemove(p, args);
                    } else {
                        notSeder(p);
                    }
                }
                return true;

            default:
                p.sendMessage("未知指令");
        }

        return true;

    }

    public void tryToTeleport(Location l, CommandSender p) {
        World world = Bukkit.getWorld("SmallSpace");
        Location newloc2 = new Location(world, l.getX() + 0.5, l.getY() + 1, l.getZ() + 0.5);
        if (newloc2.getBlock().getType() == Material.BEDROCK) {
            p.sendMessage("小世界未初始化");
            return;
        }
        Player p2 = (Player) p;
        p2.teleport(newloc2);
    }

    public void notSeder(CommandSender p) {
        p.sendMessage("只有玩家才能执行该指令");
    }
public void help(CommandSender p) {
    	p.sendMessage(ChatColor.GOLD+"memberAdd: "+ChatColor.WHITE+"This command adds Players to your Space(to use you need to look on Sized Block)");
    	p.sendMessage(ChatColor.GOLD+"memberRemove: "+ChatColor.WHITE+"This command removes Players to your Space(to use you need to look on Sized Block)");
    	p.sendMessage(ChatColor.GOLD+"help: "+ChatColor.WHITE+"This command displaies help list");
    	p.sendMessage(ChatColor.GOLD+"tptoId: "+ChatColor.WHITE+"This command teleports you to Space with given id");
    	p.sendMessage(ChatColor.GOLD+"blockId: "+ChatColor.WHITE+"This command blocks Space with given id");
    	p.sendMessage(ChatColor.GOLD+"unblockId: "+ChatColor.WHITE+"This command unblocks Space with given id");
    	p.sendMessage(ChatColor.GOLD+"teleportRemove: "+ChatColor.WHITE+"This command removes teleporter block(to use you need to look on TELEPORTER block)");

    }
    public boolean groupAdd(CommandSender p, String[] args) {

        final Player pp = (Player) p;
        Block b = pp.getTargetBlockExact(20);
        if (!BlockStorage.hasBlockInfo(b)) {
            p.sendMessage("该方块没有信息");
            return false;
        }

        SlimefunItem item = BlockStorage.check(b);
        if (!(item instanceof SizedBlock)) {
            p.sendMessage("这个方块不是小世界空间里的方块");
            return false;
        }

        if (!(BlockStorage.getLocationInfo(b.getLocation(), "owner").equals(pp.getName())) && !p.hasPermission("SmallSpace.admin")) {
            pp.sendMessage("你不是这个方块的拥有者");
            return false;
        }

        List<String> info = Calculator.playersGet(BlockStorage.getLocationInfo(b.getLocation(), "Players"));
        if (info.contains(args[1])) {
            return false;
        }

        String old = BlockStorage.getLocationInfo(b.getLocation(), "Players");

        if (old == null) {
            BlockStorage.addBlockInfo(b, "Players", args[1] + ";");
        } else {
            BlockStorage.addBlockInfo(b, "Players", old + args[1] + ";");
        }
        pp.sendMessage("玩家 " + args[1] + " 已成为这个小世界空间的成员");
        return true;


    }


    public boolean groupRemove(CommandSender p, String[] args) {

        final Player pp = (Player) p;
        Block b = pp.getTargetBlockExact(20);
        if (!BlockStorage.hasBlockInfo(b)) {
            p.sendMessage("这个方块没有存储信息");
            return false;
        }

        SlimefunItem item = BlockStorage.check(b);
        if (!(item instanceof SizedBlock)) {
            p.sendMessage("这个方块不是小世界空间里的方块");
            return false;
        }

        if (!(BlockStorage.getLocationInfo(b.getLocation(), "owner").equals(pp.getName())) && !p.hasPermission("SmallSpace.admin")) {
            pp.sendMessage("你不是这个方块的拥有者");
            return false;
        }

        String arg = args[1];

        List<String> info = Calculator.playersGet(BlockStorage.getLocationInfo(b.getLocation(), "Players"));

        if (info.contains(args[1])) {

            if (info.contains(arg)) {
                info.remove(arg);
            }
            String text = "";
            for (String s : info) {
                text = text + s + ";";

            }
            BlockStorage.addBlockInfo(b, "Players", text);
            pp.sendMessage("玩家 " + args[1] + " 不再是这个小世界空间的成员");

            return true;
        } else {
            pp.sendMessage("玩家 " + args[1] + " 不是这个小世界空间的成员");
            return false;
        }


    }

}
