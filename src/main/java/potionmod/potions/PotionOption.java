package potionmod.potions;

import com.megacrit.cardcrawl.potions.AbstractPotion;

public class PotionOption{
    public AbstractPotion potion;
    public int sellPrice;
    public String optionText;
    

    public PotionOption(AbstractPotion potion, int sellPrice){
        this.potion = potion;
        this.sellPrice = sellPrice;
    }
}