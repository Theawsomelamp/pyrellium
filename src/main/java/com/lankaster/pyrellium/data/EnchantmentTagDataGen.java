package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.enchant.ModEnchants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class EnchantmentTagDataGen extends FabricTagProvider<Enchantment>{
    public EnchantmentTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, RegistryKeys.ENCHANTMENT, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.@NonNull WrapperLookup wrapperLookup) {
        getTagBuilder(TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Pyrellium.MOD_ID, "exclusive_set/rebound")))
                .add(ModEnchants.REBOUND.getValue())
                .add(Enchantments.KNOCKBACK.getValue());

        if (Config.instance().enchants.rebound.distribution.enchant_table_roll()){
            getTagBuilder(EnchantmentTags.IN_ENCHANTING_TABLE).add(ModEnchants.REBOUND.getValue());
        }

        if (Config.instance().enchants.rebound.distribution.librarian_book_trade()){
            getTagBuilder(EnchantmentTags.ON_TRADED_EQUIPMENT).add(ModEnchants.REBOUND.getValue());
        }

        if (Config.instance().enchants.rebound.distribution.found_on_enchanted_loot()){
            getTagBuilder(EnchantmentTags.ON_RANDOM_LOOT).add(ModEnchants.REBOUND.getValue());
        }
    }
}