package versiongamma.betterenchanting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class AnvilEventListener implements Listener {
    private ArrayList<Transaction> getTransactionTypes(AnvilInventory inventory) {
        ItemStack sacrificeItem = inventory.getSecondItem();
        ArrayList<Transaction> types = new ArrayList<>();

        boolean isRenaming = !Objects.equals(inventory.getRenameText(), "");
        if (isRenaming) {
            types.add(Transaction.RENAME);
        }

       if (sacrificeItem == null) {
           return types;
       }

        types.add(ItemEnchantUtils.getItemEnchantments(sacrificeItem).isEmpty() ? Transaction.REPAIR : Transaction.ENCHANTMENT);
        return types;
    }

    private int getEnchantmentCost(int level) {
        return (int)Math.pow((double)level, 1.7) / 5 + 1;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();

        ArrayList<Transaction> transactionTypes = getTransactionTypes(anvilInventory);

        int resultCost = 0;

        if (transactionTypes.contains(Transaction.REPAIR)) {
            resultCost += 2;
        }

        if (transactionTypes.contains(Transaction.RENAME)) {
            resultCost += 1;
        }

        if (transactionTypes.contains(Transaction.ENCHANTMENT)) {
            int resultLevel = ItemEnchantUtils.getEnchantLevelSum(event.getResult());
            resultCost += getEnchantmentCost(resultLevel);
        }

        event.getInventory().setRepairCost(resultCost);
    }
}
