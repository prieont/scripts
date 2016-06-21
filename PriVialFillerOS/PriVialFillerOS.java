package scripts.PriVialFillerOS;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by prieont on 20/06/16.
 */
@Script.Manifest(name="PriVialFillerOS",
        properties = "author=prieont; topic=1; client=4;",
        description="Fills vials at Varrock East bank.")
public class PriVialFillerOS extends PollingScript<ClientContext> {
    private List<Task> taskList = new ArrayList<Task>();

    @Override
    public void start() {
        taskList.addAll(Arrays.asList(new Fill(ctx), new BankVials(ctx)));
    }

    @Override
    public void poll() {
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
            }
        }
    }
}
