package nl.tue.s2id90.group65;

import nl.tue.s2id90.group65.samples.UninformedPlayer;
import nl.tue.s2id90.group65.samples.OptimisticPlayer;
import nl.tue.s2id90.group65.samples.BuggyPlayer;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import nl.tue.s2id90.draughts.DraughtsPlayerProvider;
import nl.tue.s2id90.draughts.DraughtsPlugin;



/**
 *
 * @author huub
 */
@PluginImplementation
public class AlphaAndOmegaPlugin extends DraughtsPlayerProvider implements DraughtsPlugin {
    public AlphaAndOmegaPlugin() {
        // make one or more players available to the AICompetition tool
        // During the final competition you should make only your 
        // best player available. For testing it might be handy
        // to make more than one player available.
        super(new AlphaAndOmega(),
                new UninformedPlayer(),
                new OptimisticPlayer(),
                new BuggyPlayer()
        );
    }
}
