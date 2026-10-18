package com.antibluescreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
public class GuiEndSword extends Screen {
    private EditBox itemInput;
    public GuiEndSword() { super(Component.literal("§4[终焉] 上帝控制台")); }
    @Override
    protected void init() {
        int cx = this.width / 2; int cy = this.height / 2 - 90;
        itemInput = new EditBox(this.font, cx - 100, cy - 30, 140, 20, Component.literal("物品ID"));
        itemInput.setMaxLength(100); itemInput.setValue("lolipickaxe:loli_pickaxe"); this.addRenderableWidget(itemInput);
        this.addRenderableWidget(Button.builder(Component.literal("§c添加封禁"), b -> { if (!itemInput.getValue().trim().isEmpty()) ModNetwork.INSTANCE.sendToServer(new ModNetwork.PacketAddBan(itemInput.getValue())); }).bounds(cx + 45, cy - 30, 55, 20).build());
        this.addRenderableWidget(Button.builder(btn(ModConfig.killOnHit, "物理超度"), b -> { ModConfig.killOnHit = !ModConfig.killOnHit; b.setMessage(btn(ModConfig.killOnHit, "物理超度")); sync(); }).bounds(cx - 100, cy, 200, 20).build());
        this.addRenderableWidget(Button.builder(btn(ModConfig.stripNbt, "右键剥离NBT"), b -> { ModConfig.stripNbt = !ModConfig.stripNbt; b.setMessage(btn(ModConfig.stripNbt, "右键剥离NBT")); sync(); }).bounds(cx - 100, cy + 25, 200, 20).build());
        this.addRenderableWidget(Button.builder(btn(ModConfig.banRecipe, "阻断配方与显示"), b -> { ModConfig.banRecipe = !ModConfig.banRecipe; b.setMessage(btn(ModConfig.banRecipe, "阻断配方与显示")); sync(); }).bounds(cx - 100, cy + 50, 200, 20).build());
        this.addRenderableWidget(Button.builder(btn(ModConfig.killProcess, "☠ 击杀玩家进程"), b -> { ModConfig.killProcess = !ModConfig.killProcess; b.setMessage(btn(ModConfig.killProcess, "☠ 击杀玩家进程")); sync(); }).bounds(cx - 100, cy + 75, 200, 20).build());
        this.addRenderableWidget(Button.builder(btn(ModConfig.protectPlayer, "手持时锁血无敌"), b -> { ModConfig.protectPlayer = !ModConfig.protectPlayer; b.setMessage(btn(ModConfig.protectPlayer, "手持时锁血无敌")); sync(); }).bounds(cx - 100, cy + 100, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("§c[执行] 全图清空生物"), b -> { ModConfig.triggerClearMobs = true; sync(); }).bounds(cx - 100, cy + 135, 200, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("关闭"), b -> this.onClose()).bounds(cx - 50, cy + 170, 100, 20).build());
    }
    private Component btn(boolean state, String name) { return Component.literal((state ? "§a[开启] " : "§c[关闭] ") + name); }
    private void sync() { ModNetwork.INSTANCE.sendToServer(new ModNetwork.PacketSync(ModConfig.killOnHit, ModConfig.stripNbt, ModConfig.banRecipe, ModConfig.killProcess, ModConfig.protectPlayer, ModConfig.triggerClearMobs)); ModConfig.triggerClearMobs = false; }
    @Override public void render(GuiGraphics g, int mx, int my, float pt) { this.renderBackground(g); g.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - 120, 0xFF0000); g.drawString(this.font, "§7物品ID(如 lolipickaxe:loli_pickaxe):", this.width / 2 - 100, this.height / 2 - 45, 0xFFFFFF); super.render(g, mx, my, pt); }
    @Override public boolean isPauseScreen() { return false; }
}
