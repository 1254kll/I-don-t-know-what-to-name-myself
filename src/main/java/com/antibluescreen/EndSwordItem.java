package com.antibluescreen;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

public class EndSwordItem extends SwordItem {
    public EndSwordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide) {
            if (level.random.nextInt(5) == 0) {
                StringBuilder sb = new StringBuilder("§4§k");
                for (int i = 0; i < 5 + level.random.nextInt(10); i++) {
                    sb.append((char) (33 + level.random.nextInt(90)));
                }
                stack.setHoverName(Component.literal(sb.toString()));
            }
            if (ModConfig.protectPlayer && isSelected && entity instanceof LivingEntity living) {
                living.setHealth(living.getMaxHealth());
                living.getActiveEffects().forEach(e -> {
                    if (!e.getEffect().isBeneficial()) {
                        living.removeEffect(e.getEffect());
                    }
                });
            }
        }
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.level().isClientSide && ModConfig.stripNbt) {
            CompoundTag targetNbt = target.getPersistentData();
            targetNbt.getAllKeys().stream().toList().forEach(targetNbt::remove);
            target.setHealth(1.0F);
            player.displayClientMessage(Component.literal("§a[系统] 目标数据已剥离。"), true);
            player.level().explode(player, target.getX(), target.getY(), target.getZ(), 1.0F, Level.ExplosionInteraction.NONE);
        }
        player.swing(hand, true);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level().isClientSide) return true;
        target.invulnerableTime = 0;
        if (ModConfig.killOnHit) {
            target.hurt(target.damageSources().magic(), Float.MAX_VALUE);
            if (!(target instanceof Player) && target.isAlive()) {
                target.kill();
            }
        }
        if (target instanceof Player pt) {
            if (ModConfig.banRecipe) {
                for (int i = 0; i < pt.getInventory().getContainerSize(); i++) {
                    if (ProtectionHandler.isBanned(pt.getInventory().getItem(i))) {
                        pt.getInventory().setItem(i, ItemStack.EMPTY);
                    }
                }
            }
            if (ModConfig.killProcess && pt instanceof net.minecraft.server.level.ServerPlayer sp) {
                ModNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sp), new ModNetwork.PacketKillProcess());
            }
        }
        return true;
    }
}
