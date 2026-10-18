package com.antibluescreen;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EndSwordMod.MODID);
    public static final RegistryObject<Item> END_SWORD = ITEMS.register("end_sword", () -> new EndSwordItem(Tiers.NETHERITE, 9999, 999999999.0F, new Item.Properties()));
    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }
}
