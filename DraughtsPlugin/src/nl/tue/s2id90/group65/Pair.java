/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.s2id90.group65;

import org10x10.dam.game.Move;

/**
 *
 * @author niklas stylianou
 */
public class Pair 
{
	
	private Move move;
	private int value;
	 
	public Pair(Move move, int value)
	{
		this.move = move;
		this.value = value;
	}
	
	public Move getMove()
	{
		return move;
	}
	
	public int getVal()
	{
		return value;
	}
	
	public Pair clone()
	{
		return new Pair(move, value);
	}
}
