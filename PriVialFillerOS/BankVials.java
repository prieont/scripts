package scripts.PriVialFillerOS;

import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Bank;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by prieont on 21/06/16.
 */
public class BankVials extends Task<ClientContext> {
    private final int fullVialId = 227;
    private final Tile bankTile = new Tile(3253, 3421, 0);
    private final int emptyVialId = 229;

    public BankVials(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        final int numFull = ctx.inventory.select().id(fullVialId).count();
        final int numEmpty = ctx.inventory.select().id(emptyVialId).count();
        return numFull == 28
                || numFull + numEmpty != 28;
    }

    @Override
    public void execute() {
        //GameObject bank = ctx.objects.select().id().nearest().poll();
        // Move to bank
        if (bankTile.distanceTo(ctx.players.local().tile()) > 4) {
            ctx.movement.step(bankTile);
            Condition.sleep(Random.nextInt(700, 1200));
        } else if (!ctx.bank.opened()) {  // do open bank
            ctx.bank.open();
            Condition.sleep(200);
        } else if (!ctx.inventory.select().id(fullVialId).isEmpty()) {
            // If bank is open and we have any full vials, ditch all
            //final Item vial = ctx.inventory.id(fullVialId).poll();
            //vial.interact("Deposit-All", vial.name());
            ctx.bank.depositInventory();
            Condition.sleep(200);
        }else if (ctx.inventory.select().id(emptyVialId).count() >= 14) {
            // if we have no full, but >14 empty, go fill
            ctx.bank.close();
        } else if (ctx.inventory.select().id(emptyVialId).isEmpty()
                && ctx.inventory.select().count() < 14) {
            // if bank open, no full vials, < 14 emptys and > 14 spaces
            ctx.bank.withdraw(emptyVialId, Bank.Amount.ALL);
        } else {
            // Dunno whats in invent, just deposit it and try again
            ctx.bank.depositInventory();
            Condition.sleep(200);
        }

    }

}
