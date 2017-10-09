package io.github.mosadie.Minetana;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Minetana.MODID, name = Minetana.MODNAME, version = Minetana.VERSION, updateJSON=Minetana.UPDATEJSON, useMetadata=true)
public class Minetana {
	public static final String MODID = "minetana";
	public static final String MODNAME = "Minetana";
	public static final String VERSION = "1.0.0.0";
	public static final String UPDATEJSON = "https://raw.githubusercontent.com/MoSadie/Minetana/master/update.json";
	
	@Instance
	public static Minetana instance;
	
	public static Logger LOGGER;
	
	@SidedProxy(clientSide="io.github.mosadie.Minetana.ClientProxy", serverSide="io.github.mosadie.Minetana.ServerProxy")
	public static CommonProxy proxy;
	
	public FakePlayer playerMinetana;
	
	public Minetana() {
		MinecraftForge.EVENT_BUS.register(new EventReceiver());
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		LOGGER = e.getModLog();
		proxy.preInit(e);
		//Create Blocks and Items
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
		//Crafting
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
		//Don't Think I need this...
	}
}
