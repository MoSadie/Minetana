package io.github.mosadie.Minetana.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MinetanaInteractEvent extends Event {
	
	public enum Intent { ON, OFF, UNKNOWN};
	
	EntityPlayerMP sender;
	Intent intent;
	String details;
	
	public MinetanaInteractEvent(EntityPlayerMP sender, Intent intent, String details) {
		this.sender = sender;
		this.intent = intent;
		this.details = details;
	}
	
	public void sendResponse(String message) {
		sender.sendMessage(new TextComponentString(message));
	}
}
