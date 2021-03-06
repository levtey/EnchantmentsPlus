package de.geolykt.enchantments_plus.enchantments;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.geolykt.enchantments_plus.CustomEnchantment;
import de.geolykt.enchantments_plus.enums.BaseEnchantments;
import de.geolykt.enchantments_plus.enums.Hand;
import de.geolykt.enchantments_plus.enums.Tool;

import static de.geolykt.enchantments_plus.enums.Tool.LEGGINGS;
import static org.bukkit.entity.EntityType.DROPPED_ITEM;

public class Magnetism extends CustomEnchantment {

    public static final int ID = 35;

    @Override
    public Builder<Magnetism> defaults() {
        return new Builder<>(Magnetism::new, ID)
            .maxLevel(3)
            .loreName("Magnetism")
            .probability(0)
            .enchantable(new Tool[]{LEGGINGS})
            .conflicting()
            .description("Slowly attracts nearby items to the players inventory")
            .cooldown(0)
            .power(1.0)
            .handUse(Hand.NONE)
            .base(BaseEnchantments.MAGNETISM);
    }

    @Override
    public boolean onFastScan(Player player, int level, boolean usedHand) {
        int radius = (int) Math.round(power * level * 2 + 3);
        for (Entity e : player.getNearbyEntities(radius, radius, radius)) {
            if (e.getType().equals(DROPPED_ITEM) && e.getTicksLived() > 160) {
                e.setVelocity(player.getLocation().toVector().subtract(e.getLocation().toVector()).multiply(.05));
            }
        }
        return true;
    }
}
