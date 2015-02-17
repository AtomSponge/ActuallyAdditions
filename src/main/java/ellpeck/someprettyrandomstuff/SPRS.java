package ellpeck.someprettyrandomstuff;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ellpeck.someprettyrandomstuff.achievement.AchievementEvent;
import ellpeck.someprettyrandomstuff.achievement.InitAchievements;
import ellpeck.someprettyrandomstuff.blocks.InitBlocks;
import ellpeck.someprettyrandomstuff.config.ConfigurationHandler;
import ellpeck.someprettyrandomstuff.crafting.InitCrafting;
import ellpeck.someprettyrandomstuff.gen.OreGen;
import ellpeck.someprettyrandomstuff.inventory.GuiHandler;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.material.InitItemMaterials;
import ellpeck.someprettyrandomstuff.proxy.IProxy;
import ellpeck.someprettyrandomstuff.tile.TileEntityBase;
import ellpeck.someprettyrandomstuff.util.Util;

@Mod(modid = Util.MOD_ID, name = Util.NAME, version = Util.VERSION)
public class SPRS{

    @Mod.Instance(Util.MOD_ID)
    public static SPRS instance;

    @SidedProxy(clientSide = "ellpeck.someprettyrandomstuff.proxy.ClientProxy", serverSide = "ellpeck.someprettyrandomstuff.proxy.ServerProxy")
    public static IProxy proxy;

    @SuppressWarnings("unused")
    @Mod.EventHandler()
    public void preInit(FMLPreInitializationEvent event){
        Util.logInfo(Util.isClientSide() ? "You're on a Client, eh?" : "You're on a Server, eh?");
        Util.logInfo("Starting PreInitialization Phase...");

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        InitItemMaterials.init();
        InitBlocks.init();
        InitItems.init();
        proxy.preInit();

        Util.logInfo("PreInitialization Finished.");
    }

    @SuppressWarnings("unused")
    @Mod.EventHandler()
    public void init(FMLInitializationEvent event){
        Util.logInfo("Starting Initialization Phase...");

        InitAchievements.init();
        InitCrafting.init();
        GuiHandler.init();
        OreGen.init();
        TileEntityBase.init();
        AchievementEvent.init();
        proxy.init();

        Util.logInfo("Initialization Finished.");
    }

    @SuppressWarnings("unused")
    @Mod.EventHandler()
    public void postInit(FMLPostInitializationEvent event){
        Util.logInfo("Starting PostInitialization Phase...");

        proxy.postInit();

        Util.logInfo("PostInitialization Finished.");
    }
}
