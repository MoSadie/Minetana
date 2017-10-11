package io.github.mosadie.Minetana.events;

import io.github.mosadie.Minetana.Minetana;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MinetanaInteractEvent extends Event {
	
	public enum Intent { ON, OFF, LIST, UNKNOWN};
	
	public EntityPlayerMP sender;
	public Intent intent;
	public String details;
	
	public MinetanaInteractEvent(EntityPlayerMP sender, Intent intent, String details) {
		this.sender = sender;
		this.intent = intent;
		this.details = details;
		Minetana.LOGGER.info("EVENT CREATED! Sender " + sender.getName() + " Intent " + intent.toString() + " Details " +details);
	}
	
	public void sendResponse(String message) {
		sender.sendMessage(new TextComponentString("[Minetana] "+message));
	}
}
