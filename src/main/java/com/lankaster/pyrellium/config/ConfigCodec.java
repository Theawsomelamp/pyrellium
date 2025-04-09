package com.lankaster.pyrellium.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ConfigCodec(BlocksConfig blocksConfig) {
    public static final Codec<ConfigCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlocksConfig.CODEC.fieldOf("blocks").orElse(BlocksConfig.DEFAULT).forGetter(ConfigCodec::blocksConfig)
    ).apply(instance, ConfigCodec::new));

    public record BlocksConfig(float redBounce, float brownBounce, float explodeStrength) {
        public static final Codec<BlocksConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.FLOAT.fieldOf("red_bounceshroom_bounce").orElse(1.0F).forGetter(BlocksConfig::redBounce),
                Codec.FLOAT.fieldOf("brown_bounceshroom_bounce").orElse(0.75F).forGetter(BlocksConfig::brownBounce),
                Codec.FLOAT.fieldOf("bomb_flower_explosion_strength").orElse(1.0F).forGetter(BlocksConfig::explodeStrength)
        ).apply(instance, BlocksConfig::new));
        public static final BlocksConfig DEFAULT = new BlocksConfig(1.0F, 0.75F, 1.0F);
    }
}
