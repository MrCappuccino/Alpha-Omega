/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.s2id90.group65;

import java.util.List;

/**
 *
 * @author niklas stylianou
 */
public class Heap
{
	
	private static int size;
	
	public static void buildHeap(List<Pair> list)
	{
		size = list.size() - 1;
		int heapsize = size / 2;
		
		for (int i = heapsize; i >= 0; i--)
		{
			maxHeapify(list, i);
		}
	}
	
	public static void sort(List<Pair> list)
	{	
		buildHeap(list);
		
		for (int i = size; i > 0; i--)
		{
			swap(list, 0, i);
			size--;
			maxHeapify(list, 0);

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
	
//	private static void minHeapify(List<Pair> list, int index) 
//	{
//		int lefti = index * 2;
//		int righti = index * 2 + 1;
//		int besti = index;
//		
//		if (lefti <= size && list.get(lefti).getVal() < list.get(index).getVal())
//		{
//			besti = lefti;
//		}
//		if (righti <= size && list.get(righti).getVal() < list.get(besti).getVal())
//		{
//			besti = righti;
//		}
//		if (besti != index)
//		{
//			swap(list, index, besti);
//			maxHeapify(list, besti);
//		}
//	}
	
	private static void swap(List<Pair> list, int indexA, int indexB)
	{
		Pair temp = list.get(indexA);
		list.set(indexA, list.get(indexB));
		list.set(indexB, temp);
	}
	
}
