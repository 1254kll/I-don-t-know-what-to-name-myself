package com.antibluescreen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = EndSwordMod.MODID)
public class RecipeAndDisplayProtector {
    
    @SubscribeEvent
    public static void onCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (ModConfig.banRecipe) {
            // 【修复点2】先收集要删的物品，再统一删除，绕过它那个破 API
            List<ItemStack> toRemove = new ArrayList<>();
            event.getEntries().forEach(entry -> {
                if (ProtectionHandler.isBanned(entry.getKey())) {
                    toRemove.add(entry.getKey());
                }
            });
            for (ItemStack stack : toRemove) {
                event.getEntries().remove(stack);
            }
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (ModConfig.banRecipe && ProtectionHandler.isBanned(event.getCrafting())) {
            // 直接清空合成槽
            event.getInventory().setItem(0, ItemStack.EMPTY);
            event.getEntity().displayClientMessage(Component.literal("§4[系统] 世界法则已阻断该物品的合成。"), true);
        }
    }
}
