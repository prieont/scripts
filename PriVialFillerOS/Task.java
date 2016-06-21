package scripts.PriVialFillerOS;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

/**
 * Created by prieont on 21/06/16.
 */
public abstract class Task<C extends ClientContext> extends ClientAccessor{

    public Task(C ctx) {
        super(ctx);
    }

    public abstract boolean activate();
    public abstract void execute();
}