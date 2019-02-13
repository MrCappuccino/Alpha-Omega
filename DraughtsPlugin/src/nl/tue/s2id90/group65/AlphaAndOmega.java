package nl.tue.s2id90.group65;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import java.util.Collections;
import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 * Implementation of the DraughtsPlayer interface.
 * @author Matas Peciukonis
 */
public class AlphaAndOmega  extends DraughtsPlayer {

    private int bestValue = 0;
    int maxSearchDepth;

    /** boolean that indicates that the GUI asked the player to stop thinking. */
    private boolean stopped;

    public AlphaAndOmega(int maxSearchDepth) {
        super("best.png"); // ToDo: replace with your own icon
        this.maxSearchDepth = maxSearchDepth;
    }

    @Override
    public Move getMove(DraughtsState s) {
        Move bestMove = null;
        bestValue = 0;
        DraughtsNode node = new DraughtsNode(s);    // the root of the search tree
        try {
            // compute bestMove and bestValue in a call to alphabeta
            bestValue = alphaBeta(node, MIN_VALUE, MAX_VALUE, maxSearchDepth);

            // store the bestMove found uptill now
            // NB this is not done in case of an AIStoppedException in alphaBeat()
            bestMove  = node.getBestMove();

            // print the results for debugging reasons
            System.err.format(
                    "%s: depth= %2d, best move = %5s, value=%d\n", 
                    this.getClass().getSimpleName(),maxSearchDepth, bestMove, bestValue
                    );
        } catch (AIStoppedException ex) {  /* nothing to do */  }

        if (bestMove == null) {
            System.err.println("no valid move found!");
            return getRandomValidMove(s);
        } else {
            return bestMove;
        }
    }

    /** This method's return value is displayed in the AICompetition GUI.
     * 
     * @return the value for the draughts state s as it is computed in a call to getMove(s). 
     */
    @Override
    public Integer getValue() {
        return bestValue;
    }

    /** Tries to make alphabeta search stop. Search should be implemented such that it
     * throws an AIStoppedException when boolean stopped is set to true;
     **/
    @Override
    public void stop() {
        stopped = true;
    }

    /** returns random valid move in state s, or null if no moves exist. */
    Move getRandomValidMove(DraughtsState s) {
        List<Move> moves = s.getMoves();
        Collections.shuffle(moves);
        return moves.isEmpty()? null : moves.get(0);
    }

    /** Implementation of alphabeta that automatically chooses the white player
     *  as maximizing player and the black player as minimizing player.
     * @param node contains DraughtsState and has field to which the best move can be assigned.
     * @param alpha
     * @param beta
     * @param depth maximum recursion Depth
     * @return the computed value of this node
     * @throws AIStoppedException
     **/
    int alphaBeta(DraughtsNode node, int alpha, int beta, int depth) throws AIStoppedException
    {
        if (node.getState().isWhiteToMove()) {
            return alphaBetaMax(node, alpha, beta, depth);
        } else  {
            return alphaBetaMin(node, alpha, beta, depth);
        }
    }

    /** Does an alphabeta computation with the given alpha and beta
     * where the player that is to move in node is the minimizing player.
     *
     * <p>Typical pieces of code used in this method are:
     *     <ul> <li><code>DraughtsState state = node.getState()</code>.</li>
     *          <li><code> state.doMove(move); .... ; state.undoMove(move);</code></li>
     *          <li><code>node.setBestMove(bestMove);</code></li>
     *          <li><code>if(stopped) { stopped=false; throw new AIStoppedException(); }</code></li>
     *     </ul>
     * </p>
     * @param node contains DraughtsState and has field to which the best move can be assigned.
     * @param alpha
     * @param beta
     * @param depth  maximum recursion Depth
     * @return the compute value of this node
     * @throws AIStoppedException thrown whenever the boolean stopped has been set to true.
     */
    int alphaBetaMin(DraughtsNode node, int alpha, int beta, int depth) throws AIStoppedException {
        if (stopped) {
            stopped = false;
            throw new AIStoppedException();
        }

        int minEval = MAX_VALUE; // +infinity
        // Return minEval if we reach max depth
        if(depth == 0) {
            System.out.println("Hit max search depth");
            return evaluate(node.getState());
        }

        DraughtsState state = node.getState();
        List<Move> moves = state.getMoves();

        for (Move move : moves) {
            DraughtsNode newNode = new DraughtsNode(state.clone());
            DraughtsState newState = newNode.getState();

            newState.doMove(move);

            // TODO: Get the best state out of all leaves
            if (node.getState().isEndState()) {
                System.out.println("Reached leaf node.");
                int evaluation = evaluate(newNode.getState());

                if(minEval >= evaluation) {
                    minEval = evaluation;
                    node.setBestMove(move);
                }
                return minEval;
            }

            // For each move, see if the current minEval (+inf) is smaller than the max aBMax of it's child
            minEval = Math.min(minEval, alphaBetaMax(newNode, alpha, beta, depth - 1)); // Decrement depth
            beta = Math.min(beta, minEval);
            System.out.println(depth + "current depth");

            newState.undoMove(move);

            if (beta <= alpha) { // Prune
                break;
            }
        }

        return minEval;
    }

    int alphaBetaMax(DraughtsNode node, int alpha, int beta, int depth) throws AIStoppedException {
        if (stopped) {
            stopped = false;
            throw new AIStoppedException();
        }

        int maxEval = MIN_VALUE; // +infinity

        DraughtsState state = node.getState();
        // Return maxEval if we reach max depth
        if(depth == 0) {
            System.out.println("Hit max search depth");
            return evaluate(state);
        }

        List<Move> moves = state.getMoves();

        for (Move move : moves) { // Go through all children
            // Clone parent note
            DraughtsNode newNode = new DraughtsNode(state.clone());
            DraughtsState newState = newNode.getState();

            newState.doMove(move); // Simulate move forward

            if (node.getState().isEndState()) { // Reached leaf node
                System.out.println("Reached leaf node.");
                int evaluation = evaluate(newNode.getState());

                if(maxEval <= evaluation) {
                    maxEval = evaluation;
                    node.setBestMove(move);
                }
                return maxEval;
            }

            // For each move, see if the current maxEval (+inf) is smaller than the max aBMax of it's child
            maxEval = Math.max(maxEval, alphaBetaMin(newNode, alpha, beta, depth - 1)); // Decrement depth
            alpha = Math.max(alpha, maxEval);
            System.out.println(depth + "current depth");

            newState.undoMove(move);

            if (beta <= alpha) { // Prune
                break;
            }
        }

        return maxEval;
    }

    // TODO: Write heuristic
    /** A method that evaluates the given state. */
    // ToDo: write an appropriate evaluation function
    int evaluate(DraughtsState state) {
        //state.getPieces()
        return whiteMinusBlack(state);
    }

    int whiteMinusBlack(DraughtsState state) {
        int total = 0;
        System.out.println("STARTING WHITE MINUS BLACK");

        for (int i = 1; i < state.getPieces().length; i++) {
            if(state.getPiece(i) == state.WHITEPIECE) {
                total++;
            } else if (state.getPiece(i) == state.BLACKPIECE) {
                total--;
            } else if (state.getPiece(i) == state.WHITEKING) {
                total += 5;
            } else if (state.getPiece(i) == state.BLACKKING) {
                total -= 5;
            }
        }

        // TODO: DO NOT CHECK piece[0]
        // TODO: check heuristic for simulated moves
        //for (int piece : state.getPieces()) {
            //if(piece == state.WHITEPIECE) {
                //total++;
            //} else if (piece == state.BLACKPIECE) {
                //total--;
            //} else if (piece == state.WHITEKING) {
                //total += 3;
            //} else if (piece == state.BLACKKING) {
                //total -= 3;
            //}
        //}
        System.out.println(total + "TOTAL");
        return total;
    }
}
