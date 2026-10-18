package com.antibluescreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(modid = EndSwordMod.MODID)
public class RecipeAndDisplayProtector {
    @SubscribeEvent public static void onCreativeTab(BuildCreativeModeTabContentsEvent event) { if (ModConfig.banRecipe) event.getEntries().keySet().removeIf(ProtectionHandler::isBanned); }
    @SubscribeEvent public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) { if (ModConfig.banRecipe && ProtectionHandler.isBanned(event.getCrafting())) { event.getCrafting().setCount(0); event.getEntity().displayClientMessage(Component.literal("§4[系统] 世界法则已阻断该物品的合成。"), true); } }
}
