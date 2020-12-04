package up.edu.pandemic;

import java.util.ArrayList;
import up.edu.GameFramework.GameMainActivity;
import up.edu.GameFramework.GamePlayer;
import up.edu.GameFramework.LocalGame;
import up.edu.GameFramework.gameConfiguration.GameConfig;
import up.edu.GameFramework.gameConfiguration.GamePlayerType;

/** PandemicMainActivity
 * Pandemic is fully implemented. All design specifications that was listed are completely
 * functional, with two types of AIs.
 *
 * KNOWN BUGS:
 *  - None
 *
 *  NOTE: No functionality has been added since the Beta Release. The code has remained the same.
 *
 * @author Nick Scott, Sarah Strong, and Emily Vo.
 * @version 24 November 2020.
 */


public class PandemicMainActivity extends GameMainActivity {
    // the port number that this game will use when playing over the network
    private static final int PORT_NUMBER = 2234;

    /** createDefaultConfig()
     * This method sets up the default game configuration before the game has begun.
     * @return The default game configuration.
     */
    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // a human player player type (player type 0)
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new PandemicHumanPlayer(name);
            }});

        // a computer player type (player type 1)
        playerTypes.add(new GamePlayerType("Dumb AI") {
            public GamePlayer createPlayer(String name) {
                return new PandemicDumbAI(name);
            }});

        playerTypes.add(new GamePlayerType("Smart AI") {
            public GamePlayer createPlayer(String name) { return new PandemicSmartAI(name); }});

        // Create a game configuration class for Pandemic:
        // - player types as given above
        // - from 2 to 4 players
        // - name of game is "Pandemic"
        // - port number as defined above
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 4, "Pandemic",
                PORT_NUMBER);

        // Add the default players to the configuration
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 2); // player 2: a smart computer player

        // Set the default remote-player setup:
        // - player name: "Remote Player"
        // - IP code: (empty string)
        // - default player type: human player
        defaultConfig.setRemoteData("Remote Player", "", 0);

        // return the configuration
        return defaultConfig;
    } // createDefaultConfig()

    /** createLocalGame()
     * This method creates the local game for the player.
     * @return The local game.
     */
    @Override
    public LocalGame createLocalGame() {
        /*
         External Citation
         Date: 2 November 2020. (Updated on 24 November 2020).
         Problem: Had trouble getting the number of players into the local game constructor.
         Resource: Andrew Nuxoll.
         Solution: Video chatted with Nuxoll until we identified the bug in the GameFramework.
         */
        return new PandemicLocalGame(this.restartConfig.getNumPlayers());
    } // createLocalGame()
}