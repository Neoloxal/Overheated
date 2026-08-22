package com.neoloxal.overheated;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class ModEffects {
    public static void coolingEffect(ServerLevel level, BlockPos pos) {
        level.sendParticles(ParticleTypes.WHITE_SMOKE, pos.getX()+.5, pos.getY()+.5, pos.getZ()+.5, 25, .5, .5, .5, .1);
        level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS);
    }

    public static void coolingEffect(ServerLevel level, BlockPos pos, boolean particles) {
        if (particles) {
            level.sendParticles(ParticleTypes.WHITE_SMOKE, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 25, .5, .5, .5, .1);
        }
        level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS);
    }

    public static void overheatingEffect(ServerLevel level, BlockPos pos) {
        level.sendParticles(ParticleTypes.LAVA, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 10, .5, .5, .5, .1);
        level.playSound(null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS);
    }
}
