package com.antibluescreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(EndSwordMod.MODID)
public class EndSwordMod {
    public static final String MODID = "zhzl";
    public EndSwordMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModNetwork.register();
        MinecraftForge.EVENT_BUS.register(new ProtectionHandler());
        MinecraftForge.EVENT_BUS.register(new RecipeAndDisplayProtector());
    }
}
