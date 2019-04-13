package AliProjTest;
import java.io.*;
import java.util.*;
import java.text.*;


public class Ali {

/** 请完成下面这个函数，实现题目要求的功能 **/
/** 当然，你也可以不按照这个模板来作答，完全按照自己的想法来 ^-^ **/
private static int totalPrice(int categoryCount, int totalVolume, int totalWeight, int[] volume, int[] weight,
int[] stock, int[] price, int[] itemType) {
int dp [][] = new int[totalVolume][] ;
for(int i = 0 ; i < totalVolume ; i ++)
dp[i] = new int [totalWeight] ;
for(int i = 0 ; i < totalVolume ; i ++)
{
for(int j = 0 ; j < totalWeight ; j ++)
dp[i][j] = 0 ;
}
int maxnum1 = 0 , maxnum2 = 0 ;
for(int i = 0 ; i < categoryCount ; i ++)
{
if(itemType[i]==3)
continue ;
for(int j = 1 ; j <= stock[i] ; i ++)
{
if(j*volume[i]<totalVolume&&j*weight[i]<totalWeight)
{
if( dp[totalVolume-j*volume[i]][totalWeight-j*weight[i]] < j*price[i] )
dp[totalVolume-j*volume[i]][totalWeight-j*weight[i]] = j*price[i] ;
}
}
}
for(int i = 0 ; i < totalVolume ; i ++)
{
for(int j = 0 ; j < totalWeight ; j ++)
{
if(maxnum1<dp[i][j])
maxnum1 = dp[i][j] ;
}
}
for(int i = 0 ; i < totalVolume ; i ++)
{
for(int j = 0 ; j < totalWeight ; j ++)
dp[i][j] = 0 ;
}
for(int i = 0 ; i < categoryCount ; i ++)
{
if(itemType[i]==1)
continue ;
for(int j = 1 ; j <= stock[i] ; i ++)
{
if(j*volume[i]<totalVolume&&j*weight[i]<totalWeight)
{
if( dp[totalVolume-j*volume[i]][totalWeight-j*weight[i]] < j*price[i] )
dp[totalVolume-j*volume[i]][totalWeight-j*weight[i]] = j*price[i] ;
}
}
}
for(int i = 0 ; i < totalVolume ; i ++)
{
for(int j = 0 ; j < totalWeight ; j ++)
{
if(maxnum2<dp[i][j])
maxnum2 = dp[i][j] ;
}
}
if(maxnum1>maxnum2)
return maxnum1 ;
else
return maxnum2 ;
}
public static void main(String[] args) {
	Scanner in = new Scanner(System.in);
	String[] line = in.nextLine().split(",");
	
	//总共商品种类
	int categoryCount = Integer.valueOf(line[0]);
	//快递体积
	int totalVolume = Integer.valueOf(line[1]);
	//快递重量
	int totalWeight = Integer.valueOf(line[2]);
	//物品体积
	int[] volume = new int[50];
	//重量
	int[] weight = new int[50];
	//件数
	int[] stock = new int[50];
	//价格
	int[] price = new int[50];
	//类型
	int[] itemT = new int[50];
	int n = categoryCount; 
	for(int i = 0 ; i < n ; i ++)
	{
		line = in.nextLine().split(",");
		volume[i] = Integer.valueOf(line[0]) ;
		weight[i] = Integer.valueOf(line[1]) ;
		stock[i] = Integer.valueOf(line[2]) ;
		price[i] = Integer.valueOf(line[3]) ;
		itemT[i] = Integer.valueOf(line[4]) ;
	}
	int maxnum = totalPrice(categoryCount, totalVolume, totalWeight, volume, weight, stock, price,itemT) ;
	System.out.println(maxnum) ;
}
}