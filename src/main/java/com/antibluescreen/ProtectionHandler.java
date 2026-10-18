package com.antibluescreen;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = EndSwordMod.MODID)
public class ProtectionHandler {
    public static boolean isBanned(ItemStack stack) {
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();
        var key = ForgeRegistries.ITEMS.getKey(item);
        return key != null && ModConfig.BANNED_ITEMS.contains(key.toString());
    }

    @SubscribeEvent
    public static void onItemSpawn(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (event.getEntity() instanceof ItemEntity item && isBanned(item.getItem())) {
            item.discard();
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof Player player) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                if (isBanned(player.getInventory().getItem(i))) {
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
            }
            // 修正：1.20.1 的 NBT 清理不能直接用 forEach 遍历 keySet
            player.getPersistentData().getAllKeys().stream().toList().forEach(k -> player.getPersistentData().remove(k));
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && ModConfig.triggerClearMobs) {
            event.getServer().getAllLevels().forEach(level -> 
                level.getEntities().getAll().forEach(e -> {
                    if (!(e instanceof Player)) {
                        e.discard();
                    }
                })
            );
            ModConfig.triggerClearMobs = false;
        }
    }
}
