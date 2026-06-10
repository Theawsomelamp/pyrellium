package com.lankaster.pyrellium.enchant;

import com.lankaster.pyrellium.config.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.entity.EquipmentSlot;

public class ReboundEnchantment extends Enchantment {
    private final int maxLevel;

    protected ReboundEnchantment(Rarity weight, int maxLevel) {
        super(weight, EnchantmentTarget.WEAPON, EquipmentSlot.values());
        this.maxLevel = maxLevel;
    }

    @Override
    public boolean isTreasure() {
        return !Config.instance().enchants.rebound.distribution.enchant_table_roll();
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return Config.instance().enchants.rebound.distribution.librarian_book_trade();
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return Config.instance().enchants.rebound.distribution.found_on_enchanted_loot();
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return !(other instanceof KnockbackEnchantment);
    }
}
