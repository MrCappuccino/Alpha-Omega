/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.s2id90.group65;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author niklas stylianou
 */
public class Heap
{

	public static final int MINHEAP = 0;
	public static final int MAXHEAP = 1;
	
	private static int size;
	
//	public static Pair extract(List<Pair> list, int type) throws 
//			NullPointerException, IllegalArgumentException
//	{
//		// if list is empty
//		if (list.size() < 1)
//		{
//			throw new NullPointerException("empty array");
//		}
//		if (type != 0 && type != 1)
//		{
//			throw new IllegalArgumentException("illegal heap type");
//		}
//		
//		// set best move to the move at the top of the heap
//		Pair best = list.get(0);
//		System.out.println("value: " + best.getVal());
//		// bottom move to top
//		swap(list, 0, list.size() -1);
//		// remove last item
//		list.remove(list.size() - 1);
//		System.out.println("size: " + list.size());
//		// reorder
//		if (type == MINHEAP)
//		{
//			minHeapify(list, 0);			
//		}
//		if (type == MAXHEAP)
//		{
//			maxHeapify(list, 0);
//		}
//		
//		return best;
//	}
	
	public static void buildHeap(List<Pair> list, int type)
	{
		size = list.size() - 1;
		int heapsize = size / 2;
		
		if (type == MINHEAP)
		{
			for (int i = heapsize; i >= 0; i--)
			{
				minHeapify(list, i);
			}
				
		}
		if (type == MAXHEAP)
		{
			for (int i = heapsize; i >= 0; i--)
			{
				maxHeapify(list, i);
			}
				
		}
	}
	
	public static void heapSort(List<Pair> list, int type) 
		throws IllegalArgumentException
	{	
		if (type != 0 && type != 1)
		{
			throw new IllegalArgumentException("illegal heap type");
		}
		
		buildHeap(list, type);
		for (int i = size; i > 0; i--)
		{
			swap(list, 0, i);
			size--;
			
			if (type == MAXHEAP) 
			{
				maxHeapify(list, 0);
			}
			else 
			{
				minHeapify(list, 0);
			}
		}
	}
	
	private static void maxHeapify(List<Pair> list, int index) 
	{
		int lefti = index * 2;
		int righti = index * 2 + 1;
		int besti = index;

		if (lefti <= size && list.get(lefti).getVal() > list.get(index).getVal())
		{
			besti = lefti;
		}
		if (righti <= size && list.get(righti).getVal() > list.get(besti).getVal())
		{
			besti = righti;
		}
		if (besti != index)
		{
			swap(list, index, besti);
			maxHeapify(list, besti);
		}
	} 
	
	private static void minHeapify(List<Pair> list, int index) 
	{
		int lefti = index * 2;
		int righti = index * 2 + 1;
		int besti = index;
		
		if (lefti <= size && list.get(lefti).getVal() < list.get(index).getVal())
		{
			besti = lefti;
		}
		if (righti <= size && list.get(righti).getVal() < list.get(besti).getVal())
		{
			besti = righti;
		}
		if (besti != index)
		{
			swap(list, index, besti);
			maxHeapify(list, besti);
		}
	}
	
	private static void swap(List<Pair> list, int indexA, int indexB)
	{
		Pair temp = list.get(indexA);
		list.set(indexA, list.get(indexB));
		list.set(indexB, temp);
	}
	
}
