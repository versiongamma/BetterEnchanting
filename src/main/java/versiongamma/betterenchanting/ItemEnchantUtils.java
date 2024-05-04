package versiongamma.betterenchanting;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ItemEnchantUtils {
    public static Map<Enchantment, Integer> getItemEnchantments(ItemStack item) {
        ItemMeta metadata = item.getItemMeta();
        if (metadata instanceof EnchantmentStorageMeta) {
            return ((EnchantmentStorageMeta)metadata).getStoredEnchants();
        }

        return item.getEnchantments();
    }

    public static int getEnchantLevelSum(ItemStack item) {
        if (item == null) {
            return 1;
        }

        Map<Enchantment, Integer> enchants = getItemEnchantments(item);

        final int[] levelSum = {0};
        enchants.forEach(((e, i) -> {
            levelSum[0] += i;
        }));
        return levelSum[0];
    }
}
