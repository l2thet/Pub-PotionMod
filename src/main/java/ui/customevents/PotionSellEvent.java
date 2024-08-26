package ui.customevents;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import potionmod.potions.PotionOption;

public class PotionSellEvent extends AbstractImageEvent {
    public static final String ID = "potionmod:EventID";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    // This text should be set up through loading an event localization json file
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static ArrayList<PotionOption> potionOptions;
    private static final String NAME = eventStrings.NAME;

    public PotionSellEvent() {
        super(NAME, DESCRIPTIONS[0], "potionmod/images/events/potionvendor.png");

        potionOptions = GenerateDialogOptions();
        
        for (PotionOption option : potionOptions) {
            this.imageEventText.setDialogOption(option.optionText);
        }
    }

    @Override
    protected void buttonEffect(int arg0) {
        //treat the click arg as the index of the potion in the player's potion list
        //if the player clicks an option, sell the potion and give the player the gold
        if (arg0 <= potionOptions.size()) {
            PotionOption potion = potionOptions.get(arg0);
            if (potion.potion != null) {
                AbstractDungeon.topPanel.destroyPotion(potion.potion.slot);
                AbstractDungeon.player.gainGold(potion.sellPrice);
            }
            
            resetDialog();
        }
        else {
            this.imageEventText.clearAllDialogs();
            this.imageEventText.setDialogOption("Leave");
            openMap();
        }
    }

    private void resetDialog() {
        this.imageEventText.clearAllDialogs();
        potionOptions = GenerateDialogOptions();
        for (PotionOption option : potionOptions) {
            this.imageEventText.setDialogOption(option.optionText);
        }
    }

    private ArrayList<PotionOption> GenerateDialogOptions() {
        ArrayList<PotionOption> result = new ArrayList<PotionOption>();

        for (AbstractPotion potion : AbstractDungeon.player.potions) {
            if(!potion.name.toLowerCase().contains("slot")) {
                PotionOption option = new PotionOption(potion, potion.getPrice() / 2);
                option.optionText = "Sell " + potion.name + " for " + potion.getPrice() / 2 + " gold";
                result.add(option);    
            }
        }
        PotionOption leaveOption = new PotionOption(null, 0);
        leaveOption.optionText = "Leave";
        result.add(leaveOption);

        return result;
    }
}

