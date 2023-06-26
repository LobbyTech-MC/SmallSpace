package me.CAPS123987.implementation;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.CAPS123987.items.Items;
import me.CAPS123987.smallspace.Calculator;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SizedBlock extends SimpleSlimefunItem<BlockTicker> implements EnergyNetComponent {
    private final int Tier;
    public List<Vector> blocks = new ArrayList<>();
    private int decrement = 0;


	public SizedBlock(int i, SlimefunItemStack item, ItemStack[] recipe) {
        super(Items.smallSpace, item, RecipeType.ENHANCED_CRAFTING_TABLE,
            recipe);
        blocks.add(new Vector(1, 0, 0));
        blocks.add(new Vector(-1, 0, 0));
        blocks.add(new Vector(0, 0, 1));
        blocks.add(new Vector(0, 0, -1));
        blocks.add(new Vector(0, 1, 0));
        blocks.add(new Vector(0, -1, 0));
        addItemHandler(BlockPlaceHandler());
        addItemHandler(BlockBreakHandler());
        addItemHandler(BlockUseHandler());
        this.Tier = i;
    }

    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        // TODO Auto-generated method stub
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        // TODO Auto-generated method stub
        return 2048;
    }


    public BlockUseHandler BlockUseHandler() {
        return new BlockUseHandler() {

            @Override
            public void onRightClick(PlayerRightClickEvent e) {


                Location loc = Calculator.getLoc(BlockStorage.getLocationInfo(e.getClickedBlock().get().getLocation(), "name"));
                String srt = BlockStorage.getLocationInfo(e.getClickedBlock().get().getLocation(), "Tier");
                int value = Integer.parseInt(srt.replaceAll("[^0-9]", ""));
                World world = Bukkit.getWorld("SmallSpace");
                String owner = BlockStorage.getLocationInfo(e.getClickedBlock().get().getLocation(), "owner");
                Location newloc = new Location(world, loc.getX() + 0.5, loc.getY(), loc.getZ() + 0.5);
                Location newloc2 = new Location(world, loc.getX() + 0.5, loc.getY() + 1, loc.getZ() + 0.5);
                Location newloc22 = new Location(world, loc.getX() + 0.5, loc.getY() + 2, loc.getZ() + 0.5);
                Location newloc222 = new Location(world, loc.getX() + 0.5, loc.getY() + 3, loc.getZ() + 0.5);
                Location newloc3 = new Location(world, loc.getX() + 0.5, loc.getY() - 1, loc.getZ() + 0.5);

                if (!(e.getPlayer().getName().equals(owner) || e.getPlayer().hasPermission("SmallSpace.admin") || Calculator.playersGet(BlockStorage.getLocationInfo(e.getClickedBlock().get().getLocation(), "Players")).contains(e.getPlayer().getName()))) {
                    e.getPlayer().sendMessage(ChatColor.RED + "你无法进入该空间!");
                    return;
                }

                if (e.getPlayer().isSneaking()) {
                    if (loc.getBlock().getType() == Material.BEDROCK) {
                        e.getPlayer().sendMessage(ChatColor.RED + "你的小世界暂未初始化,尝试右键一下?");
                        return;
                    }
                    if (loc.getBlock().getType() == Material.BARRIER) {
                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "你的小世界被禁用了");
                        return;
                    }

                    newloc2.getBlock().setType(Material.AIR);
                    newloc22.getBlock().setType(Material.AIR);
                    newloc222.getBlock().setType(Material.AIR);
                    e.getPlayer().sendMessage("已移除方块");
                } else {
                    if (loc.getBlock().getType() == Material.BEDROCK) {
                        switch (value) {
                            case 1:
                                Calculator.setRegi(loc, 3.0);
                                break;
                            case 2:
                                Calculator.setRegi(loc, 6.0);
                                break;
                            case 3:
                                Calculator.setRegi(loc, 9.0);
                                break;
                            case 4:
                                Calculator.setRegi(loc, 12.0);
                                break;
                            default:
                                break;
                        }
                        world.getBlockAt(newloc3).setType(Material.GOLD_BLOCK);

                        BlockStorage.store(newloc3.getBlock(), "TELEPORT");

                        BlockStorage.addBlockInfo(newloc3.getBlock(), "tpX", String.valueOf(e.getClickedBlock().get().getLocation().getX()));
                        BlockStorage.addBlockInfo(newloc3.getBlock(), "tpY", String.valueOf(e.getClickedBlock().get().getLocation().getY()));
                        BlockStorage.addBlockInfo(newloc3.getBlock(), "tpZ", String.valueOf(e.getClickedBlock().get().getLocation().getZ()));
                        BlockStorage.addBlockInfo(newloc3.getBlock(), "world", e.getClickedBlock().get().getLocation().getWorld().getName());

                    }

                    if (newloc.getBlock().getType() == Material.BARRIER) {
                        e.getPlayer().sendMessage(ChatColor.DARK_RED + "你的小世界被禁用了");
                        return;
                    }
                    if (newloc2.getBlock().getType() == Material.AIR && newloc22.getBlock().getType() == Material.AIR && newloc222.getBlock().getType() == Material.AIR) {
                        //if player is owner or admin

                        e.getPlayer().teleport(newloc);

                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "小世界已经就绪! 潜行并右键点击方块以移除");
					}

                }
            }


        };
    }


    public BlockBreakHandler BlockBreakHandler() {
        return new BlockBreakHandler(false, false) {

            @Override
            public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
                Block b = e.getBlock();
                String name = BlockStorage.getLocationInfo(b.getLocation(), "name");
                String owner = BlockStorage.getLocationInfo(e.getBlock().getLocation(), "owner");

                if (name == "null") {
                    return;
                }

                //if player is owner

                if (!e.getPlayer().getName().equals(owner)) {
                    //if player is admin
                    if (e.getPlayer().hasPermission("SmallSpace.admin")) {

                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "You are not the owner of this space!");
                        e.setCancelled(true);
                        return;
                    }
                }
                //break space interface
                Calculator.SfRemove(b, blocks);

                e.getBlock().getWorld().setChunkForceLoaded(e.getBlock().getLocation().getChunk().getX(), e.getBlock().getLocation().getChunk().getZ(), false);

                //drop item with custom lore
                ItemStack item2 = SizedBlock.this.getItem().clone();
                e.setDropItems(false);

                List<String> lore = new ArrayList<String>();
                lore.add(name);
                ItemMeta im = item2.getItemMeta();
                im.setLore(lore);
                item2.setItemMeta(im);

                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), item2);


            }

        };
    }


    public BlockPlaceHandler BlockPlaceHandler() {
        return new BlockPlaceHandler(false) {

            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                e.getBlock().getWorld().setChunkForceLoaded(e.getBlock().getLocation().getChunk().getX(), e.getBlock().getLocation().getChunk().getZ(), true);
                // TODO Auto-generated method stub
                BlockStorage.addBlockInfo(e.getBlock().getLocation(), "Tier", String.valueOf(getTier()));
                if (e.getItemInHand().getItemMeta().getLore().get(0).equals("§4Put to Block Assigner")) {
                    e.setCancelled(true);
					BlockStorage.clearBlockInfo(e.getBlock());
					e.getPlayer().sendMessage("You need to put this block to Block Block Assigner");
                    return;
                }
                BlockStorage.addBlockInfo(e.getBlock().getLocation(), "Players", "");
                BlockStorage.addBlockInfo(e.getBlock().getLocation(), "name", e.getItemInHand().getItemMeta().getLore().get(0));
                BlockStorage.addBlockInfo(e.getBlock().getLocation(), "owner", e.getPlayer().getName());

        Location loc = Calculator.getLoc(BlockStorage.getLocationInfo(e.getBlock().getLocation(), "name"));
					World world = Bukkit.getWorld("SmallSpace");
					Location newloc3 = new Location(world,loc.getX()+0.5,loc.getY()-1,loc.getZ()+0.5);
					world.getBlockAt(newloc3).setType(Material.GOLD_BLOCK);

					BlockStorage.store(newloc3.getBlock(), "TELEPORT");

					BlockStorage.addBlockInfo(newloc3.getBlock(), "tpX",String.valueOf(e.getBlock().getLocation().getX()));
					BlockStorage.addBlockInfo(newloc3.getBlock(), "tpY", String.valueOf(e.getBlock().getLocation().getY()));
					BlockStorage.addBlockInfo(newloc3.getBlock(), "tpZ", String.valueOf(e.getBlock().getLocation().getZ()));
					BlockStorage.addBlockInfo(newloc3.getBlock(), "world", String.valueOf(e.getBlock().getLocation().getWorld().getName()));


				}};
    }


    @Override
    public BlockTicker getItemHandler() {

        return new BlockTicker() {

            @Override
            // Fires first!! The method tick() fires after this
            public void uniqueTick() {


                if (decrement == 2) {
                    decrement = 0;
                    return;
                }
                decrement++;

            }

            public void tick(Block b, SlimefunItem sf, Config data) {
                if (decrement != 0) {
                    return;
                }


                if (getTier() * 256 <= getCharge(b.getLocation())) {
                    removeCharge(b.getLocation(), getTier() * 256);
                    return;
                }


                int size = Calculator.size(BlockStorage.getLocationInfo(b.getLocation(), "Tier"));
                Location loc = Calculator.getLoc(BlockStorage.getLocationInfo(b.getLocation(), "name"));
                Vector v = new Vector(size / 2, size / 2, size / 2);
                Location loc2 = loc.add(v);


                Collection<Entity> Entity = Bukkit.getWorld("SmallSpace").getNearbyEntities(loc2, size / 2, size / 2, size / 2);
                if (Entity.isEmpty()) {
                    return;
                }
                for (Entity e : Entity) {
                    if (e instanceof Player) {
                        Player p = (Player) e;
                        p.sendMessage(ChatColor.MAGIC + "III" + ChatColor.RESET + ChatColor.DARK_RED + "ERROR" + ChatColor.RESET + ChatColor.MAGIC + "III" + ChatColor.RESET + ChatColor.WHITE + " NO ENERGY");
                        p.setHealth(0.0);

                    }
                }


            }

            @Override
            public boolean isSynchronized() {
                // TODO Auto-generated method stub
                return true;
            }
        };
    }

    public int getTier() {
        return Tier;
    }


}
