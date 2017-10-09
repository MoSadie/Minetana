package io.github.mosadie.Minetana.Blocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {
	public static Block smartSwitch;
	public static Item itemSmartSwitch;

	public static void createBlocks(RegistryEvent.Register<Block> event) {
		smartSwitch = new SmartSwitch();
		event.getRegistry().register(smartSwitch.setRegistryName("smartswitch"));
		GameRegistry.registerTileEntity(SmartSwitchTE.class, "minetana_smartswitch_tile_entity");
		itemSmartSwitch = new ItemBlock(smartSwitch).setRegistryName(smartSwitch.getRegistryName());
	}
}
