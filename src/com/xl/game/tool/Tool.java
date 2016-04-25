/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.String
 */
package com.xl.game.tool;

import android.content.Context;
import java.lang.Object;
import java.lang.String;
import java.io.DataInputStream;
import java.lang.reflect.Array;
import android.util.Log;

public class Tool
{
	/*
	 * Exception decompiling
	 */
	public static int[][] ReadMap(Context context,String mapname)
	{
		DataInputStream input;
		int x,y;

		int[][] r2_int_A_A =null;

		try
		{
			//DataInputStream r12_DataInputStream = r9_DataInputStream;
			DataInputStream r10_DataInputStream = new DataInputStream(context.getResources().getAssets().open(mapname));
			input=r10_DataInputStream;
			
			r2_int_A_A= new int[input.readInt()][input.readInt()];
			/*
			r2_int_A_A=(int[][] )
			Array.newInstance
			(
			Integer.TYPE,
			new int[]
			{input.readInt(), input.readInt()}
			);
			*/
			Log.e("XL", ""+r2_int_A_A[0].length );
			
			for(y=0;y<r2_int_A_A.length;y++)
			{
				for(x=0;x<r2_int_A_A[y].length;x++)
				{
					r2_int_A_A[y][x]=input.readInt();
				}
			}
			
			/*
			y=0;
			while(y<r2_int_A_A.length)
			{
				int x = 0;
				
				while(true)
				{
					
					if(x>=r2_int_A_A[y].length)
					{
						y+=1;
						break;
					}
					else
					{
						r2_int_A_A[y][x]=input.readInt();
						x+=1;
					}
				}
			}
			
			*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return r2_int_A_A;
	}
	
	
	
	
}

