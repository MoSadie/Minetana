package io.github.mosadie.Minetana.Items;

import io.github.mosadie.Minetana.Blocks.BlockManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ItemManager {
	public static final CreativeTabs CreativeTab = new CreativeTabs("minetana") {
	    @Override public ItemStack getTabIconItem() {
	        return new ItemStack(BlockManager.itemSmartSwitch);
	    }
	};
	public static void createItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(BlockManager.itemSmartSwitch);
    }
	
	public static void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(BlockManager.itemSmartSwitch, 0, new ModelResourceLocation(BlockManager.itemSmartSwitch.getRegistryName(), "inventory"));
	}
}
