package io.github.mosadie.Minetana.FakePlayer;

import java.util.ArrayList;

import com.mojang.authlib.GameProfile;

import io.github.mosadie.Minetana.events.MinetanaInteractEvent;
import io.github.mosadie.Minetana.events.MinetanaInteractEvent.Intent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.stats.StatBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class FakePlayer extends EntityPlayerMP {

	public FakePlayer(WorldServer world, GameProfile name)
	{
		super(FMLCommonHandler.instance().getMinecraftServerInstance(), world, name, new PlayerInteractionManager(world));
	}

	@Override public Vec3d getPositionVector(){ return new Vec3d(0, 0, 0); }
	@Override public boolean canUseCommand(int i, String s){ return true; }
	@Override public void sendStatusMessage(ITextComponent chatComponent, boolean actionBar){}
	@Override public void addStat(StatBase par1StatBase, int par2){}
	@Override public void openGui(Object mod, int modGuiId, World world, int x, int y, int z){}
	@Override public boolean isEntityInvulnerable(DamageSource source){ return true; }
	@Override public boolean canAttackPlayer(EntityPlayer player){ return false; }
	@Override public void onDeath(DamageSource source){ return; }
	@Override public void onUpdate(){ return; }
	@Override public Entity changeDimension(int dim){ return this; }
	@Override public void handleClientSettings(CPacketClientSettings pkt){ return; }

	//When getting a message via tell (Player whispers to you: message )
	@Override public void sendMessage(ITextComponent component) {
		EntityPlayerMP player = getServer().getPlayerList().getPlayerByUsername(component.getUnformattedComponentText().split(" ")[0]);
		if (player == null) return;

		Intent intent = Intent.UNKNOWN;

		String message = component.getUnformattedComponentText();
		String extraData = "";
		ArrayList<String> split = new ArrayList<String>();
		for (int i = 0; i < message.split(" ").length; i++) {
			split.add(message.split(" ")[i]);
		}
		for (int i = 4; i < split.size(); i++) {
			if (split.get(i).equalsIgnoreCase("on")) {
				intent = Intent.ON;
				if (split.size() > i+2) {
					extraData = split.get(i+1);
					for (int n = i+2; n < split.size(); n++) {
						extraData.concat(" "+split.get(n));
					}
					break;
				} else if (i-1 > split.indexOf("turn")) {
					extraData = split.get(split.indexOf("turn")+1);
					for (int n = split.indexOf("turn")+2; n < i; n++) {
						extraData.concat(" "+split.get(n));
					}
				}
			} else if (split.get(i).equalsIgnoreCase("off")) {
				intent = Intent.OFF;
				if (split.size() > i+2) {
					extraData = split.get(i+1);
					for (int n = i+2; n < split.size(); n++) {
						extraData.concat(" "+split.get(n));
					}
					break;
				} else if (i-1 > split.indexOf("turn")) {
					extraData = split.get(split.indexOf("turn")+1);
					for (int n = split.indexOf("turn")+2; n < i; n++) {
						extraData.concat(" "+split.get(n));
					}
				}
			}
		}

		if (intent == Intent.UNKNOWN) {
			player.sendMessage(new TextComponentTranslation("minetana.unknownrequest",new Object[0]));
			return;
		}

		MinecraftForge.EVENT_BUS.post(new MinetanaInteractEvent(player,intent,extraData));
	}
}
