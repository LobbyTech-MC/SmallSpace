package me.CAPS123987.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.CAPS123987.smallspace.*;

public class Items {
	public static final ItemGroup smallSpace = new ItemGroup(new NamespacedKey(SmallSpace.getInstance(),
	        "Small_Space"),
	        new CustomItemStack(Material.TUFF, "&a小世界")
	    );
	public static final SlimefunItemStack SIZED_BLOCK1 = new SlimefunItemStack("SIZED_BLOCK1",
			Material.DEEPSLATE_COAL_ORE,
	        "&r小世界空间 &7(T1 3x3)",
	        "&l§4放入空间绑定器",
	        LoreBuilder.powerPerSecond(256)
	    );
	public static final SlimefunItemStack SIZED_BLOCK2 = new SlimefunItemStack("SIZED_BLOCK2",
	        Material.DEEPSLATE_COAL_ORE,
	        "&r小世界空间 &7(T2 6x6)",
	        "&l§4放入空间绑定器",
	        LoreBuilder.powerPerSecond(512)
	    );
	public static final SlimefunItemStack SIZED_BLOCK3 = new SlimefunItemStack("SIZED_BLOCK3",
			Material.DEEPSLATE_COAL_ORE,
	        "&r小世界空间 &7(T3 9x9)",
			"&l§4放入空间绑定器",
	        LoreBuilder.powerPerSecond(768)
	    );
	public static final SlimefunItemStack SIZED_BLOCK4 = new SlimefunItemStack("SIZED_BLOCK4",
			Material.DEEPSLATE_COAL_ORE,
	        "&r小世界空间 &7(Tier 4 12x12)",
			"&l§4放入空间绑定器",
	        LoreBuilder.powerPerSecond(1024)
	    );
	public static final SlimefunItemStack TELEPORT = new SlimefunItemStack("TELEPORT",
	        Material.GOLD_BLOCK,
	        "传送",
	        ""
	    );
	
	public static final SlimefunItemStack BLOCK_ASSIGNER = new SlimefunItemStack("BLOCK_ASSIGNER",
	        Material.DIAMOND_BLOCK,
	        "&r空间绑定器",
	        "&7将小世界空间放入该机器",
			"&7以进行绑定",
	        LoreBuilder.machine(MachineTier.AVERAGE, MachineType.MACHINE),
	        LoreBuilder.powerPerSecond(12),
	        LoreBuilder.powerBuffer(512)
	    );
	
	
	public static final SlimefunItemStack SPACE_STABILIZER = new SlimefunItemStack("SPACE_STABILIZER",
	        Material.BLACK_WOOL,
	        "&r空间稳定器",
	        "&7稳定方块内部的空间",
	        LoreBuilder.machine(MachineTier.AVERAGE, MachineType.MACHINE),
	        LoreBuilder.powerPerSecond(12),
	        LoreBuilder.powerBuffer(512)
	    );
	
	public static final SlimefunItemStack IMPORT_BUS = new SlimefunItemStack("IMPORT_BUS",
	        Material.STONE,
	        "输入接口",
	        ""
	    );
	public static final SlimefunItemStack EXPORT_BUS = new SlimefunItemStack("EXPORT_BUS",
	        Material.MAGMA_BLOCK,
	        "输出接口",
	        ""
	    );
	
	
	public static final SlimefunItemStack SPACE_INTERFACE = new SlimefunItemStack("SPACE_INTERFACE",
			//https://minecraft-heads.com/custom-heads/miscellaneous/57245-file-explorer
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzczZThiZDNjNDNjNDUxNGM3NjQ4MWNhMWRhZjU1MTQ5ZGZjOTNiZDFiY2ZhOGFiOTQzN2I5ZjdlYjMzOTJkOSJ9fX0=",
	        "&r空间接口",
	        "&7将物品送入小世界",
			"&7或将物品从中取出",
	        LoreBuilder.machine(MachineTier.AVERAGE, MachineType.MACHINE),
	        LoreBuilder.powerPerSecond(12),
	        LoreBuilder.powerBuffer(512)
	    );
	
	public static final ItemStack[] recipe_SPACE_STABILIZER= {
			SlimefunItems.BLISTERING_INGOT,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.BLISTERING_INGOT,
			SlimefunItems.LEAD_INGOT,SlimefunItems.NUCLEAR_REACTOR,SlimefunItems.LEAD_INGOT,
			SlimefunItems.ELECTRO_MAGNET,SlimefunItems.LEAD_INGOT,SlimefunItems.ELECTRO_MAGNET
	};
	
	public static final ItemStack[] recipe_SPACE_INTERFACE= {
			null,SlimefunItems.ELECTRO_MAGNET,null,
			SlimefunItems.LEAD_INGOT,SlimefunItems.CARGO_OUTPUT_NODE_2,SlimefunItems.LEAD_INGOT,
			null,SlimefunItems.LEAD_INGOT,null
	};
	
	public static final ItemStack[] recipe_SIZED_BLOCK1= {
			SlimefunItems.MAGNESIUM_DUST,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.MAGNESIUM_DUST,
			new ItemStack(Material.IRON_INGOT),Items.SPACE_STABILIZER,new ItemStack(Material.IRON_INGOT),
			SlimefunItems.MAGNESIUM_DUST,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.MAGNESIUM_DUST
	};
	
	public static final ItemStack[] recipe_SIZED_BLOCK2= {
			SlimefunItems.ELECTRIC_MOTOR,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.ELECTRIC_MOTOR,
			SlimefunItems.STEEL_INGOT,Items.SPACE_STABILIZER,SlimefunItems.STEEL_INGOT,
			SlimefunItems.MAGNESIUM_DUST,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.MAGNESIUM_DUST
	};
	
	public static final ItemStack[] recipe_SIZED_BLOCK3= {
			SlimefunItems.BLISTERING_INGOT,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.ELECTRIC_MOTOR,
			SlimefunItems.STEEL_PLATE,Items.SPACE_STABILIZER,SlimefunItems.STEEL_PLATE,
			SlimefunItems.ELECTRIC_MOTOR,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.BLISTERING_INGOT
	};
	
	public static final ItemStack[] recipe_SIZED_BLOCK4= {
			SlimefunItems.BLISTERING_INGOT,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.REINFORCED_PLATE,
			SlimefunItems.STEEL_PLATE,Items.SPACE_STABILIZER,SlimefunItems.STEEL_PLATE,
			SlimefunItems.REINFORCED_PLATE,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.BLISTERING_INGOT
	};
	
	public static final ItemStack[] recipe_BLOCK_ASSIGNER= {
			SlimefunItems.ELECTRO_MAGNET,SlimefunItems.ELECTRO_MAGNET,SlimefunItems.ELECTRO_MAGNET,
			SlimefunItems.STEEL_PLATE,null,SlimefunItems.STEEL_PLATE,
			SlimefunItems.ELECTRO_MAGNET,SlimefunItems.ELECTRIC_MOTOR,SlimefunItems.ELECTRO_MAGNET
	};
	
	
	
	public static final ItemStack[] recipe_TEST_ITEM= {
			null,null,null,
			null,new ItemStack(Material.PINK_WOOL),null,
			null,null,null
	};
}
