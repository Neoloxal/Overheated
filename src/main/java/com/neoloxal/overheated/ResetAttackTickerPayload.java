package com.neoloxal.overheated;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ResetAttackTickerPayload() implements CustomPacketPayload {
    public static final Type<ResetAttackTickerPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Overheated.MODID, "reset_attack_ticker"));

    public static final StreamCodec<ByteBuf, ResetAttackTickerPayload> STREAM_CODEC =
            StreamCodec.unit(new ResetAttackTickerPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
