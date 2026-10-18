package com.antibluescreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
@Mod.EventBusSubscriber(modid = EndSwordMod.MODID, value = Dist.CLIENT)
public class KeyBindings {
    public static final KeyMapping OPEN_GUI = new KeyMapping("key.zhzl.open", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, "key.categories.zhzl");
    @Mod.EventBusSubscriber(modid = EndSwordMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents { @SubscribeEvent public static void onKeyRegister(RegisterKeyMappingsEvent event) { event.register(OPEN_GUI); } }
    @SubscribeEvent public static void onKeyInput(InputEvent.Key event) { if (OPEN_GUI.consumeClick()) net.minecraft.client.Minecraft.getInstance().setScreen(new GuiEndSword()); }
}
