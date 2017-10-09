package io.github.mosadie.Minetana;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventReceiver {

	public EventReceiver() {
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		Minetana.proxy.registerBlocks(event);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		Minetana.proxy.registerItems(event);
	}

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {
		Minetana.proxy.registerModels(event);
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if (!event.getWorld().isRemote)
			Minetana.instance.playerMinetana = FakePlayerFactory.get((WorldServer) event.getWorld(), new GameProfile(null, "Minetana"));
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		Minetana.instance.playerMinetana = null;
	}

	@SubscribeEvent
	public void onChatMessageSent(ServerChatEvent event) {

	}
}
