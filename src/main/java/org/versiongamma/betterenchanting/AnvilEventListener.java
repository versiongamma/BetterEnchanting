package org.versiongamma.betterenchanting;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

public class AnvilEventListener implements Listener {
    private enum Transaction {
        ENCHANTMENT,
        REPAIR,
        RENAME,
    }

    private Map<Enchantment, Integer> getItemEnchantments(ItemStack item) {
        ItemMeta metadata = item.getItemMeta();
        if (metadata instanceof EnchantmentStorageMeta) {
            return ((EnchantmentStorageMeta)metadata).getStoredEnchants();
        }

        return item.getEnchantments();
    }

    private int getEnchantLevelSum(ItemStack item) {
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

    private int getEnchantmentCost(int level) {
        return (int)Math.pow((double)level, 1.7) / 5 + 1;
    }

    private @Nullable Transaction getTransactionType(AnvilInventory inventory) {
        ItemStack targetItem = inventory.getFirstItem();
        ItemStack sacrificeItem = inventory.getSecondItem();

        if (sacrificeItem == null) {
            if (!Objects.equals(inventory.getRenameText(), "")) {
                return Transaction.RENAME;
            }

            return null;
        }

        if (getItemEnchantments(sacrificeItem).isEmpty()) {
            return Transaction.REPAIR;
        }

        return Transaction.ENCHANTMENT;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();

        Transaction transactionType = getTransactionType(anvilInventory);
        if (transactionType == null) {
            anvilInventory.setRepairCost(0);
            return;
        }

        if (transactionType == Transaction.REPAIR) {
            anvilInventory.setRepairCost(2);
            return;
        }

        if (transactionType == Transaction.RENAME) {
            anvilInventory.setRepairCost(1);
            return;
        }

        int resultLevel = getEnchantLevelSum(event.getResult());
        int cost = getEnchantmentCost(resultLevel);
        event.getInventory().setRepairCost(cost);
    }
}
