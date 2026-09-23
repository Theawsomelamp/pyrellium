package com.lankaster.pyrellium.world;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.world.tree.BurningTrunkPlacer;
import com.lankaster.pyrellium.world.tree.HangingTreeDecorator;
import com.lankaster.pyrellium.world.tree.WeepingFoliagePlacer;
import com.lankaster.pyrellium.world.tree.WillowFoliagePlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModWorldGeneration {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, Pyrellium.MOD_ID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, Pyrellium.MOD_ID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, Pyrellium.MOD_ID);

    public static final Supplier<TrunkPlacerType<BurningTrunkPlacer>> BURNING_TRUNK_PLACER = TRUNK_PLACERS.register("burning_trunk_placer", () -> new TrunkPlacerType<>(BurningTrunkPlacer.CODEC));
    public static final Supplier<TreeDecoratorType<HangingTreeDecorator>> HANGING_TREE_DECORATOR = TREE_DECORATORS.register("hanging_vines", () -> new TreeDecoratorType<>(HangingTreeDecorator.CODEC));
    public static final Supplier<FoliagePlacerType<WeepingFoliagePlacer>> WEEPING_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("weeping_foliage_placer", () -> new FoliagePlacerType<>(WeepingFoliagePlacer.CODEC));
    public static final Supplier<FoliagePlacerType<WillowFoliagePlacer>> WILLOW_FOLIAGE_PLACER = ModWorldGeneration.FOLIAGE_PLACERS.register("willow_foliage_placer", () -> new FoliagePlacerType<>(WillowFoliagePlacer.CODEC));


    public static void register(IEventBus eventBus) {
        TRUNK_PLACERS.register(eventBus);
        FOLIAGE_PLACERS.register(eventBus);
        TREE_DECORATORS.register(eventBus);
    }
}