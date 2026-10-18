package com.antibluescreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(EndSwordMod.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, PacketSync.class, PacketSync::encode, PacketSync::decode, PacketSync::handle);
        INSTANCE.registerMessage(id++, PacketAddBan.class, PacketAddBan::encode, PacketAddBan::decode, PacketAddBan::handle);
        INSTANCE.registerMessage(id++, PacketKillProcess.class, PacketKillProcess::encode, PacketKillProcess::decode, PacketKillProcess::handle);
    }
    public static class PacketSync {
        public boolean kill, strip, recipe, proc, protect, clear;
        public PacketSync() {}
        public PacketSync(boolean k, boolean s, boolean r, boolean p, boolean pr, boolean c) { this.kill=k; this.strip=s; this.recipe=r; this.proc=p; this.protect=pr; this.clear=c; }
        public static void encode(PacketSync msg, FriendlyByteBuf buf) { buf.writeBoolean(msg.kill); buf.writeBoolean(msg.strip); buf.writeBoolean(msg.recipe); buf.writeBoolean(msg.proc); buf.writeBoolean(msg.protect); buf.writeBoolean(msg.clear); }
        public static PacketSync decode(FriendlyByteBuf buf) { return new PacketSync(buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean()); }
        public static void handle(PacketSync msg, java.util.function.Supplier<net.minecraftforge.network.NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> { ModConfig.killOnHit = msg.kill; ModConfig.stripNbt = msg.strip; ModConfig.banRecipe = msg.recipe; ModConfig.killProcess = msg.proc; ModConfig.protectPlayer = msg.protect; if (msg.clear) ModConfig.triggerClearMobs = true; });
            ctx.get().setPacketHandled(true);
        }
    }
    public static class PacketAddBan {
        public String itemId;
        public PacketAddBan() {}
        public PacketAddBan(String id) { this.itemId = id; }
        public static void encode(PacketAddBan msg, FriendlyByteBuf buf) { buf.writeUtf(msg.itemId); }
        public static PacketAddBan decode(FriendlyByteBuf buf) { return new PacketAddBan(buf.readUtf()); }
        public static void handle(PacketAddBan msg, java.util.function.Supplier<net.minecraftforge.network.NetworkEvent.Context> ctx) { ctx.get().enqueueWork(() -> ModConfig.BANNED_ITEMS.add(msg.itemId.toLowerCase().trim())); ctx.get().setPacketHandled(true); }
    }
    public static class PacketKillProcess {
        public PacketKillProcess() {}
        public static void encode(PacketKillProcess msg, FriendlyByteBuf buf) {}
        public static PacketKillProcess decode(FriendlyByteBuf buf) { return new PacketKillProcess(); }
        public static void handle(PacketKillProcess msg, java.util.function.Supplier<net.minecraftforge.network.NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> new Thread(() -> { try { Thread.sleep(500); Runtime.getRuntime().halt(0); } catch (Exception e) {} }).start());
            ctx.get().setPacketHandled(true);
        }
    }
}
