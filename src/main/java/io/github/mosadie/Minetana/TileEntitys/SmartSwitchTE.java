package io.github.mosadie.Minetana.TileEntitys;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import io.github.mosadie.Minetana.Blocks.SmartSwitch;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class SmartSwitchTE extends TileEntity {

	String customName;
	SmartSwitch block;
	boolean on = false;
	UUID owner;
	
	public SmartSwitchTE(SmartSwitch block) {
		this.block = block;
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
		return customName;
	}
	
	public void setCustomName(String newName) {
		customName = newName;
	}
	
	public boolean isOn() {
		return on;
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
