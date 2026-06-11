package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.enchant.ModEnchants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class EnchantmentTagDataGen extends FabricTagProvider.EnchantmentTagProvider {
    public EnchantmentTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Pyrellium.MOD_ID, "exclusive_set/rebound"))).add(
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
