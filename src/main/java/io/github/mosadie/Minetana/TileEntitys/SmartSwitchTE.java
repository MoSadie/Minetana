package io.github.mosadie.Minetana.TileEntitys;

import java.util.UUID;

import com.google.common.base.Optional;

import io.github.mosadie.Minetana.Minetana;
import io.github.mosadie.Minetana.Blocks.SmartSwitch;
import io.github.mosadie.Minetana.events.MinetanaInteractEvent;
import io.github.mosadie.Minetana.events.MinetanaInteractEvent.Intent;
import io.github.mosadie.Minetana.util.Util;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SmartSwitchTE extends TileEntity {

	String customName;
	boolean on = false;
	UUID owner;
	
	public SmartSwitchTE() {
		super();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onMinetanaInteractEvent(MinetanaInteractEvent event) {
		if (hasCustomName()) {
			Minetana.LOGGER.info("EVENT THINGS NameResult: "+ Util.areNamesCloseEnough(getCustomName(), event.details));
			if (Util.areNamesCloseEnough(getCustomName(), event.details) && (event.intent == Intent.ON || event.intent == Intent.OFF)) {
				event.sendResponse("Ok! Switch "+getCustomName()+" turned " + (event.intent == Intent.ON ? "on!" : "off!"));
				setOn(event.intent == Intent.ON ? true : false);
			} else if (event.intent == Intent.LIST) {
				event.sendResponse("Switch "+ getCustomName() + ", currently turned "+ (getWorld().getBlockState(getPos()).getValue(SmartSwitch.on) == true ? "on." : "off.")) ;
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("IsOn", on);
		
		if (owner != null) {
			nbt.setUniqueId("owner.uuid", owner);
		}
		
		if (this.hasCustomName()) {
			nbt.setString("CustomName", this.getCustomName());
		}

		return nbt;
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("IsOn")) {
			on = nbt.getBoolean("IsOn");
		}
		
		if (nbt.hasKey("owner.uuid")) {
			owner = nbt.getUniqueId("owner.uuid");
		}

		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomName"));
		}
	}
	
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.exponentialpower_endergenerator_tile_entity";
	}

	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}
	
	public String getCustomName() {
		return customName != null ? customName : "Unnamed";
	}
	
	public void setCustomName(String newName) {
		customName = newName;
	}
	
	public boolean isOn() {
		return on;
	}
	
	public void setOn(boolean on) {
		this.on = on;
		world.setBlockState(pos, world.getBlockState(pos).withProperty(SmartSwitch.on,on));
		markDirty();
	}

	public boolean toggleOwner(EntityPlayer playerIn) {
		if (owner == null) {
			owner = playerIn.getUniqueID();
			return true;
		}
		else if (owner == playerIn.getUniqueID()) {
			owner = null;
			return true;
		}
		return false;
	}
}
