package de.xenodev.commands;

import de.xenodev.mysql.*;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.utils.UUIDFetcher;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SQLAdminCommand implements CommandExecutor, TabCompleter, Listener {

    private static File file = new File("plugins/" + xLobby.getInstance().getName(), "save.yml");
    private static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(!player.hasPermission("tmb.command.sqladmin")){
                player.sendMessage(xLobby.getPrefix() + " §7Dir fehlt folgende Permission: §6" + "tmb.command.sqladmin");
                return true;
            }

            if(args.length == 1){
                setName(player, args[0].toLowerCase());
                updateInv(player);
            }else if(args.length == 4){
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUIDFetcher.getUUID(args[0]));

                Integer newAmount = null;
                try{
                    newAmount = Integer.valueOf(args[3]);
                }catch(NumberFormatException e1){
                    player.sendMessage("§7Die Anzahl ist inkorrekt! §8[ " + args[3] + " §8]");
                }

                if(args[1].equalsIgnoreCase("set")){
                    if(args[2].equalsIgnoreCase("bytes")){
                        Integer oldBytes = BytesAPI.getBytes(offlinePlayer.getUniqueId());
                        BytesAPI.setBytes(offlinePlayer.getUniqueId(), newAmount);
                        player.sendMessage(xLobby.getPrefix() + "§a" + offlinePlayer.getName() + " §7Bytes geändert von §e" + oldBytes + " §7zu §6" + newAmount);
                    }else if(args[2].equalsIgnoreCase("coins")){
                        Integer oldCoins = CoinAPI.getCoins(offlinePlayer.getUniqueId());
                        CoinAPI.setCoins(offlinePlayer.getUniqueId(), newAmount);
                        player.sendMessage(xLobby.getPrefix() + "§a" + offlinePlayer.getName() + " §7Coins geändert von §e" + oldCoins + " §7zu §6" + newAmount);
                    }else if(args[2].equalsIgnoreCase("tickets")){
                        Integer oldTickets = TicketAPI.getTickets(offlinePlayer.getUniqueId());
                        TicketAPI.setTickets(offlinePlayer.getUniqueId(), newAmount);
                        player.sendMessage(xLobby.getPrefix() + "§a" + offlinePlayer.getName() + " §7Coins geändert von §e" + oldTickets + " §7zu §6" + newAmount);
                    }else{
                        player.sendMessage("§cDiesen Datatypen gibt es nicht! §8[ " + args[2] + " §8]");
                    }
                }else if(args[1].equalsIgnoreCase("remove")){
                    if(args[2].equalsIgnoreCase("bytes")){
                        Integer oldBytes = BytesAPI.getBytes(offlinePlayer.getUniqueId());
                        BytesAPI.removeBytes(offlinePlayer.getUniqueId(), newAmount);
                        player.sendMessage(xLobby.getPrefix() + "§a" + offlinePlayer.getName() + " §7Bytes geändert von §e" + oldBytes + " §7zu §6" + (oldBytes - newAmount));
                    }else if(args[2].equalsIgnoreCase("coins")){
                        Integer oldCoins = CoinAPI.getCoins(offlinePlayer.getUniqueId());
                        CoinAPI.removeCoins(offlinePlayer.getUniqueId(), newAmount);
                        player.sendMessage(xLobby.getPrefix() + "§a" + offlinePlayer.getName() + " §7Coins geändert von §e" + oldCoins + " §7zu §6" + (oldCoins - newAmount));
                    }else if(args[2].equalsIgnoreCase("tickets")){
                        Integer oldTickets = TicketAPI.getTickets(offlinePlayer.getUniqueId());
                        TicketAPI.removeTickets(offlinePlayer.getUniqueId(), newAmount);
                        player.sendMessage(xLobby.getPrefix() + "§a" + offlinePlayer.getName() + " §7Coins geändert von §e" + oldTickets + " §7zu §6" + (oldTickets - newAmount));
                    }else{
                        player.sendMessage("§cDiesen Datatypen gibt es nicht! §8[ " + args[2] + " §8]");
                    }
                }else if(args[1].equalsIgnoreCase("add")){
                    if(args[2].equalsIgnoreCase("bytes")){
                        Integer oldBytes = BytesAPI.getBytes(offlinePlayer.getUniqueId());
                        BytesAPI.addBytes(offlinePlayer.getUniqueId(), newAmount);
                        player.sendMessage(xLobby.getPrefix() + "§a" + offlinePlayer.getName() + " §7Bytes geändert von §e" + oldBytes + " §7zu §6" + (oldBytes + newAmount));
                    }else if(args[2].equalsIgnoreCase("coins")){
                        Integer oldCoins = CoinAPI.getCoins(offlinePlayer.getUniqueId());
                        CoinAPI.addCoins(offlinePlayer.getUniqueId(), newAmount);
                        player.sendMessage(xLobby.getPrefix() + "§a" + offlinePlayer.getName() + " §7Coins geändert von §e" + oldCoins + " §7zu §6" + (oldCoins + newAmount));
                    }else if(args[2].equalsIgnoreCase("tickets")){
                        Integer oldTickets = TicketAPI.getTickets(offlinePlayer.getUniqueId());
                        TicketAPI.addTickets(offlinePlayer.getUniqueId(), newAmount);
                        player.sendMessage(xLobby.getPrefix() + "§a" + offlinePlayer.getName() + " §7Coins geändert von §e" + oldTickets + " §7zu §6" + (oldTickets + newAmount));
                    }else{
                        player.sendMessage("§cDiesen Datatypen gibt es nicht! §8[ " + args[2] + " §8]");
                    }
                }else{
                    player.sendMessage("§cDieser Befehl exisitiert nicht! §8[ " + args[1] + " §8]");
                }
            }else{
                player.sendMessage("§cBitte Benutze: /sqladmin <player> (<set, add, remove> <bytes, coins, tickets> <amount>)");
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        if(!sender.hasPermission("tmb.command.sqladmin")) return null;

        if(args.length == 1){
            for(Player all : Bukkit.getOnlinePlayers()){
                arrayList.add(all.getName());
            }
            return arrayList;
        }else if(args.length == 2){
            arrayList.add("set");
            arrayList.add("remove");
            arrayList.add("add");
            return arrayList;
        }else if(args.length == 3){
            arrayList.add("bytes");
            arrayList.add("coins");
            arrayList.add("tickets");
            return arrayList;
        }else{
            return arrayList;
        }
    }

    @EventHandler
    public void handleGadgetSelcet(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Gadgets von §5" + getName(player) + " §7«")) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(getName(player));
            event.setCancelled(true);
            if (event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem() == null)
                return;
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Zurück §7«")) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 1f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Switch Bow" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Switchbow").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Switchbow", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Switchbow").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Switchbow", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Eggbomb" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Eggbomb").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Eggbomb", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Eggbomb").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Eggbomb", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Flugstab" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Flugstab").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Flugstab", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Flugstab").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Flugstab", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Enterhaken" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enterhaken").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Enterhaken", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enterhaken").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Enterhaken", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Enderperl" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enderperl").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Enderperl", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enderperl").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Enderperl", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Enderperl" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enderperl").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Enderperl", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enderperl").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Enderperl", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Notetrail" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Notetrail").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Notetrail", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Notetrail").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Notetrail", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Hearttrail" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Hearttrail").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Hearttrail", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Hearttrail").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Hearttrail", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Ghosttrail" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Ghosttrail").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Ghosttrail", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Ghosttrail").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Ghosttrail", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Flametrail" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Flametrail").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Flametrail", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Flametrail").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Flametrail", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Colortrail" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Colortrail").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Colortrail", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Colortrail").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Colortrail", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6" + "Christmastrail" + " §7«")){
                if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Christmastrail").equals("false")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Christmastrail", "true");
                } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Christmastrail").equals("true")) {
                    BuyAPI.setBuy(offlinePlayer.getUniqueId(), "Christmastrail", "false");
                }
                updateInv(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }
        }
    }

    public static void setName(Player player, String name){
        cfg.set(player.getName(), name);
        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getName(Player player){
        return cfg.getString(player.getName());
    }

    private void updateInv(Player player){
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(getName(player));
        Inventory inventory = Bukkit.createInventory(null, 9*3, "§7» §6Gadgets von §5" + getName(player) + " §7«");

        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Colortrail").equals("true")) {
            inventory.setItem(0, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Colortrail" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Colortrail").equals("false")) {
            inventory.setItem(0, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Colortrail" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Flametrail").equals("true")) {
            inventory.setItem(1, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Flametrail" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Flametrail").equals("false")) {
            inventory.setItem(1, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Flametrail" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Ghosttrail").equals("true")) {
            inventory.setItem(2, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Ghosttrail" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Ghosttrail").equals("false")) {
            inventory.setItem(2, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Ghosttrail" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Hearttrail").equals("true")) {
            inventory.setItem(3, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Hearttrail" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Hearttrail").equals("false")) {
            inventory.setItem(3, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Hearttrail" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Notetrail").equals("true")) {
            inventory.setItem(4, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Notetrail" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Notetrail").equals("false")) {
            inventory.setItem(4, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Notetrail" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enderperl").equals("true")) {
            inventory.setItem(5, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Enderperl" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enderperl").equals("false")) {
            inventory.setItem(5, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Enderperl" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enterhaken").equals("true")) {
            inventory.setItem(6, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Enterhaken" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Enterhaken").equals("false")) {
            inventory.setItem(6, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Enterhaken" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Flugstab").equals("true")) {
            inventory.setItem(7, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Flugstab" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Flugstab").equals("false")) {
            inventory.setItem(7, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Flugstab" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Eggbomb").equals("true")) {
            inventory.setItem(8, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Eggbomb" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Eggbomb").equals("false")) {
            inventory.setItem(8, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Eggbomb" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Switchbow").equals("true")) {
            inventory.setItem(9, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Switch Bow" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Switchbow").equals("false")) {
            inventory.setItem(9, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Switch Bow" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }
        if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Christmastrail").equals("true")) {
            inventory.setItem(10, new ItemBuilder(Material.MAGMA_CREAM).setName("§7» §6" + "Christmastrail" + " §7«").setLore("", "§7Gadget §2gekauft").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        } else if (BuyAPI.getBuy(offlinePlayer.getUniqueId(), "Christmastrail").equals("false")) {
            inventory.setItem(10, new ItemBuilder(Material.SLIME_BALL).setName("§7» §6" + "Christmastrail" + " §7«").setLore("", "§7Gadget §cnicht gekauft").build());
        }

        inventory.setItem(26, new ItemBuilder(Material.BARRIER).setName("§7» §6Zurück §7«").build());

        player.openInventory(inventory);
    }
}
