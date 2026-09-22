package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.enchant.ModEnchants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class EnchantmentTagDataGen extends FabricTagProvider.EnchantmentTagProvider {
    public EnchantmentTagDataGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Pyrellium.MOD_ID, "exclusive_set/rebound"))).add(
                ModEnchants.REBOUND,
                Enchantments.KNOCKBACK
        );

        if (Config.instance().enchants.rebound.distribution.enchant_table_roll()){
            getOrCreateTagBuilder(EnchantmentTags.IN_ENCHANTING_TABLE).add(ModEnchants.REBOUND);
        }

        if (Config.instance().enchants.rebound.distribution.librarian_book_trade()){
            getOrCreateTagBuilder(EnchantmentTags.ON_TRADED_EQUIPMENT).add(ModEnchants.REBOUND);
        }

        if (Config.instance().enchants.rebound.distribution.found_on_enchanted_loot()){
            getOrCreateTagBuilder(EnchantmentTags.ON_RANDOM_LOOT).add(ModEnchants.REBOUND);
        }
    }
}
