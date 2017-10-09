package io.github.mosadie.Minetana;

import io.github.mosadie.Minetana.Blocks.BlockManager;
import io.github.mosadie.Minetana.Items.ItemManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
    
    public void registerBlocks(RegistryEvent.Register<Block> event) {
    	BlockManager.createBlocks(event);
    }
    
    public void registerItems(RegistryEvent.Register<Item> event) {
    	ItemManager.createItems(event);
    }
    
    public void registerModels(ModelRegistryEvent event) {
    	ItemManager.registerModels(event);
    }
}
