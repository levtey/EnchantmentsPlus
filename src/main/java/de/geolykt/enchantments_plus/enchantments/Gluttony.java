package de.geolykt.enchantments_plus.enchantments;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.Storage;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.enums.Tool;
import de.geolykt.enchantments_plus.util.Utilities;

import static de.geolykt.enchantments_plus.enums.Tool.HELMET;
import static org.bukkit.Material.*;

public class Gluttony extends CustomEnchantment {

    public static final int ID = 21;

    @Override
    public Builder<Gluttony> defaults() {
        return new Builder<>(Gluttony::new, ID)
            .maxLevel(1)
            .loreName("Gluttony")
            .probability(0)
            .enchantable(new Tool[]{HELMET})
            .conflicting()
            .description("Automatically eats for the player")
            .cooldown(0)
            .power(-1.0)
            .handUse(Hand.NONE)
            .base(BaseEnchantments.GLUTTONY);
    }

    @Override
    public boolean onScan(Player player, int level, boolean usedHand) {
        for (int i = 0; i < Storage.COMPATIBILITY_ADAPTER.gluttonyFoodItems().length; i++) {
            if (player.getInventory().containsAtLeast(
                new ItemStack(Storage.COMPATIBILITY_ADAPTER.gluttonyFoodItems()[i]), 1)
                && player.getFoodLevel() <= 20 - Storage.COMPATIBILITY_ADAPTER.gluttonyFoodLevels()[i]) {
                Utilities.removeItem(player, Storage.COMPATIBILITY_ADAPTER.gluttonyFoodItems()[i], 1);
                player.setFoodLevel(player.getFoodLevel() + Storage.COMPATIBILITY_ADAPTER.gluttonyFoodLevels()[i]);
                player.setSaturation(
                    (float) (player.getSaturation() + Storage.COMPATIBILITY_ADAPTER.gluttonySaturations()[i]));
                if (Storage.COMPATIBILITY_ADAPTER.gluttonyFoodItems()[i] == RABBIT_STEW
                    || Storage.COMPATIBILITY_ADAPTER.gluttonyFoodItems()[i] == MUSHROOM_STEW
                    || Storage.COMPATIBILITY_ADAPTER.gluttonyFoodItems()[i] == BEETROOT_SOUP) {
                    player.getInventory().addItem(new ItemStack(BOWL));
                }
            }
        }
        return true;
    }
}
