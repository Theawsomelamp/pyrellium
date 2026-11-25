package com.lankaster.pyrellium.enchant;

import com.lankaster.pyrellium.config.Config;
import com.lankaster.pyrellium.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;

public class WindRiderEnchantment extends Enchantment {
    private final int maxLevel;

    protected WindRiderEnchantment(Rarity weight, int maxLevel) {
        super(weight, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
        this.maxLevel = maxLevel;
    }

    @Override
    public boolean isTreasure() {
        return !Config.instance().enchants.wind_rider.distribution.enchant_table_roll();
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return Config.instance().enchants.wind_rider.distribution.librarian_book_trade();
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return Config.instance().enchants.wind_rider.distribution.found_on_enchanted_loot();
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() == ModItems.SILK_UMBRELLA || (stack.getItem() instanceof ElytraItem && Config.instance().misc.elytra_catch_updrafts);
    }
}
