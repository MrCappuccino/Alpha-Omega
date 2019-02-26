package nl.tue.s2id90.group65;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 * Implementation of the DraughtsPlayer interface.
 * @author Matas Peciukonis
 */
public class AlphaAndOmegav3  extends DraughtsPlayer {

    private int bestValue = 0;

    /** boolean that indicates that the GUI asked the player to stop thinking. */
    private boolean stopped;

    public AlphaAndOmegav3() {
        super("best.png"); // ToDo: replace with your own icon
    }

    @Override
    public Move getMove(DraughtsState s) {
        Move bestMove = null;
        DraughtsNode node = new DraughtsNode(s);    // the root of the search tree

        try {
            // compute bestMove and bestValue in a call to alphabeta
            alphaBeta(node, MIN_VALUE, MAX_VALUE);

            // print the results for debugging reasons
            System.err.format(
                    "%s: best move = %5s, value=%d\n", 
                    this.getClass().getSimpleName(), bestMove, bestValue
                    );
        } catch (AIStoppedException ex) {
            bestMove = node.getBestMove(); // store best recent move
        }

        if (bestMove == null) {
            System.err.println("no valid move found!");
            return getRandomValidMove(s);
        } else {
            System.out.println("Move taken: " + bestMove);
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
    void alphaBeta(DraughtsNode node, int alpha, int beta) throws AIStoppedException
    {
        for  (int depth = 1;; depth++) {
            if (node.getState().isWhiteToMove()) {
                bestValue = alphaBetaMax(node, alpha, beta, depth);
            } else  {
                bestValue = alphaBetaMin(node, alpha, beta, depth);
            }
            System.out.println(this.getClass().getSimpleName() + ": depth = " + depth + ", best move = " + node.getBestMove() + ", Score: " + getValue());
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

        int score = beta;

        DraughtsNode newNode = new DraughtsNode(node.getState().clone());
        DraughtsState newState = newNode.getState();
        // TODO: If there is a forced move, move to it
        // TODO: Reuse Minimax tree https://codereview.stackexchange.com/questions/190937/reusable-ai-game-tree

        // TODO: Checking leaf node can be expensive, get around that
        if(depth <= 0 || newState.isEndState()) {
            return evaluate(newState);
        }

        List<Move> moves = newState.getMoves();
		List<Pair> pairs = new ArrayList<>();
		
		for (Move move : moves) {
			int value;
			newState.doMove(move);
			value = evaluate(newState);
			newState.undoMove(move);
			pairs.add(new Pair(move, value));
		}
		
		System.out.print("before: ");
		for (int i = 0; i < pairs.size(); i++)
		{
			System.out.print(pairs.get(i).getVal() + " ");
		}
		System.out.println();
		
		Heap.sort(pairs);
		
		System.out.print("after: ");
		for (int i = 0; i < pairs.size(); i++)
		{
			System.out.print(pairs.get(i).getVal() + " ");
		}
		System.out.println();
		
        Move bestMove = pairs.get(0).getMove();
	
        for (int i = 0; i < pairs.size(); i++) { // Go through all children
			Move move = pairs.get(i).getMove();
		
            newState.doMove(move);

            score = alphaBetaMax(newNode, alpha, beta, depth - 1);
            newState.undoMove(move);

            if (score < beta) {
                beta = score;
                bestMove = move;
            }

            if (beta <= alpha) { // Prune
                break;
            }
        }
        node.setBestMove(bestMove);
        return beta;
    }

    int alphaBetaMax(DraughtsNode node, int alpha, int beta, int depth) throws AIStoppedException {
        if (stopped) {
            stopped = false;
            throw new AIStoppedException();
        }

        int score = alpha;

        DraughtsNode newNode = new DraughtsNode(node.getState().clone());
        DraughtsState newState = newNode.getState();

        // TODO: Checking leaf node can be expensive, get around that
        if(depth <= 0 || newState.isEndState()) {
            return evaluate(newState);
        }

        List<Move> moves = newState.getMoves();
		List<Pair> pairs = new ArrayList<>();
		
		for (Move move : moves) {
			int value;
			newState.doMove(move);
			value = evaluate(newState);
			newState.undoMove(move);
			pairs.add(new Pair(move, value));
		}
		
		Heap.sort(pairs);

        Move bestMove = pairs.get(pairs.size() - 1).getMove();

        for (int i = pairs.size() - 1; i >= 0; i--) { // Go through all children
			Move move = pairs.get(i).getMove();
            newState.doMove(move); // Simulate move forward

            score = alphaBetaMin(newNode, alpha, beta, depth - 1);
            newState.undoMove(move);

            if (score > alpha) {
                alpha = score;
                bestMove = move;
            }

            if (beta <= alpha) { // Prune
                break;
            }
        }
        node.setBestMove(bestMove);
        return alpha;
    }

    /** A method that evaluates the given state. */
    public int evaluate(DraughtsState state) {
        return whiteMinusBlack(state);
    }

    int whiteMinusBlack(DraughtsState state) {
        /*Possible heuristics:
          piece count
          kings count
          trapped kings
          turn
          runaway checkers (free path to king)
          larger values on the sides of the board (smaller going in)
          kings on diagonals
          */

        int total = 0;

        if (state.isEndState()) { // Leaf
            if (state.isWhiteToMove()) {
                total -= 10000;
            } else if (!state.isWhiteToMove()) {
                total += 10000;
            }
        }

        int[] boardValues = new int[] {
          999,  2,  2,  2,  2,  2, 
                1,  1,  1,  1,  1, 
                1,  1,  1,  1,  1, 
                2,  3,  3,  3,  2, 
                2,  3,  4,  3,  2, 
                2,  3,  4,  3,  2, 
                2,  3,  3,  3,  2, 
                1,  1,  1,  1,  1, 
                1,  1,  1,  1,  1, 
                2,  2,  2,  2,  2 
        };

        for (int i = 1; i < 51; i++) { // 51 - all placements on board
            if(state.getPiece(i) == state.WHITEPIECE) {
                total += 100;
                total += boardValues[i] * 10;
            } else if (state.getPiece(i) == state.BLACKPIECE) {
                total -= 100;
                total -= boardValues[i] * 10;
            } else if (state.getPiece(i) == state.WHITEKING) {
                total += 300;
                total += boardValues[i] * 10;
            } else if (state.getPiece(i) == state.BLACKKING) {
                total -= 300;
                total -= boardValues[i] * 10;
            }
        }


        return total;
    }
}
