package scripts.PriVialFillerOS;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

/**
 * Created by prieont on 21/06/16.
 */
public class Fill extends Task<ClientContext> {
    private final int emptyVialId = 229;
    private final int fillingActionId = 832;
    private final int fountainId = 5125;
    private long last = 0;

    public Fill(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        if (ctx.players.local().animation() == fillingActionId) {
            last = System.currentTimeMillis();
            //System.out.println("Currently filling");
        }
        if (System.currentTimeMillis() - last < 3000) {
            //System.out.println("Filled in last 3 seconds");
            return false;  // Last vial fill was <3 sec ago
        }

        return !ctx.inventory.select().id(emptyVialId).isEmpty()
                && ctx.players.local().animation() == -1;
    }

    @Override
    public void execute() {
        // If not near fountain move to it
        GameObject fountain = ctx.objects.select().id(fountainId).nearest().poll();
        Item vial = ctx.inventory.id(emptyVialId).peek();
        if (fountain.inViewport()) {
            vial.interact("Use", vial.name());
            fountain.interact("Use", fountain.name());
            last = System.currentTimeMillis();
            //Condition.sleep(200);
            //vial.click();
            //fountain.click();
            //fountain.interact("Chop");
        } else {
            ctx.movement.step(fountain);
            ctx.camera.turnTo(fountain);
        }

    }
}
