package io.github.mosadie.Minetana;

import java.util.ArrayList;

import io.github.mosadie.Minetana.events.MinetanaInteractEvent;
import io.github.mosadie.Minetana.events.MinetanaInteractEvent.Intent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.ServerChatEvent;
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
	public void onChatMessageSent(ServerChatEvent event) {
		
		if (!event.getMessage().toLowerCase().startsWith("minetana")) return;
		
		EntityPlayerMP player = event.getPlayer();

		Intent intent = Intent.UNKNOWN;
		
		String message = event.getMessage();
		String extraData = "";
		ArrayList<String> split = new ArrayList<String>();
		for (int i = 1; i < message.split(" ").length; i++) {
			split.add(message.split(" ")[i]);
		}
		for (int i = 0; i < split.size(); i++) {
			//Find the word "on" or "off"			
			if (split.get(i).equalsIgnoreCase("on")) {
				intent = Intent.ON;
				//Goes like turn on (Name here)
				if (split.size() > i+1) {
					extraData = split.get(i+1);
					for (int n = i+2; n < split.size(); n++) {
						extraData = extraData.concat(" "+split.get(n));
					}
					break;
				//Goes like turn (Name here) on
				} else if (i-1 > split.indexOf("turn")) {
					extraData = split.get(split.indexOf("turn")+1);
					for (int n = split.indexOf("turn")+2; n < i; n++) {
						extraData = extraData.concat(" "+split.get(n));
					}
					break;
				}
			} else if (split.get(i).equalsIgnoreCase("off")) {
				intent = Intent.OFF;
				if (split.size() > i+1) {
					extraData = split.get(i+1);
					for (int n = i+2; n < split.size(); n++) {
						extraData = extraData.concat(" "+split.get(n));
					}
					break;
				} else if (i-1 > split.indexOf("turn")) {
					extraData = split.get(split.indexOf("turn")+1);
					for (int n = split.indexOf("turn")+2; n < i; n++) {
						extraData = extraData.concat(" "+split.get(n));
					}
					break;
				}
			} else if (split.get(i).equalsIgnoreCase("list")) {
				intent = Intent.LIST;
			}
		}

		if (intent == Intent.UNKNOWN) {
			player.sendMessage(new TextComponentTranslation("minetana.unknownrequest",new Object[0]));
			return;
		}
		
		Minetana.LOGGER.info("Data: " + extraData);

		MinecraftForge.EVENT_BUS.post(new MinetanaInteractEvent(player,intent,extraData));
	}
}
