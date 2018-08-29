
import java.math.BigInteger;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author easterday
 */
public class Main {
    public static Vector<String> Result1=new Vector<String>();
    public final static char Operand[]={'A','B','C','D','E','F','G','H','I','J','K','L','M'};
    public final static char Operator[]={'$','*','/','+','-'};
    public final static int  InputSize=3;
    private static void step1_a_line(Vector<String> dest,int[] index,char[] Letter,char[] OP,int SZ) {
        String ret="";
        for (int i=0; i<SZ-1; i++) {
            ret+=(""+Letter[i]+OP[index[i]]);
        }
        ret+=(Letter[SZ-1]);
        dest.add(ret);
        System.out.println(ret);
    }
    public static void step1(){
        BigInteger Limit=BigInteger.ONE;
        BigInteger mul=BigInteger.valueOf(Operator.length);
        for (int i=0; i<InputSize-1; i++) {
            Limit= Limit.multiply(mul);
        }
        int[] index=new int[InputSize-1];
        for (BigInteger iter=BigInteger.ZERO; iter.compareTo(Limit)==(-1); iter=iter.add(BigInteger.ONE)) {
            BigInteger Hand=new BigInteger(iter.toByteArray());
            for (int i=0; i<InputSize-1; i++) {
                index[i]=Hand.mod(mul).intValue();
                Hand=Hand.divide(mul);
            }
            step1_a_line(Result1,index,Operand,Operator,InputSize);
        }
    }
    private static void ClearZero(int[] dest,int sz) {
        for (int i=0; i<sz; i++) {
            dest[i]=0;
        }
    }
    public static void step2_a_line(String line,int[] L,int[] R) {
        final int SZ=InputSize;
        final int Slot=SZ+1;        
        String ret="";
        for (int i=0,m=0; i<=SZ; i++) {
            if (i==0) {
                for (int x=0; x<L[0]; x++) ret+=("(");
                ret+=(line.charAt(m));
                m+=2;
            }else if (i==SZ) {
                for (int x=0; x<R[Slot-1]; x++) ret+=(")");
                System.out.println(ret);
                return;
            }else {
                for (int x=0; x<R[i]; x++) ret+=(")");
                ret+=line.charAt(m-1);
                for (int x=0; x<L[i]; x++) ret+=("(");
                ret+=line.charAt(m);
                m+=2;
            }
        }
        System.out.println("BAD_EXECUTION");
        System.exit(0);
    }
    private static boolean Valid(int[] L,int[]  R)  {
        final int SZ=InputSize;        
        final int Slot=SZ+1;  
        if (L[Slot-1]>0) return false;
        if (R[0]>0) return false;
        for (int i=0; i<Slot-1; i++) {
            if (R[i+1]>L[i])    
                return false;
            int m=SZ-i;
            if (L[m-1]>R[m])
                return false;
            if (L[i]>0 && R[i+1]>0)
                return false;
            if (L[m-1]>0 && R[m]>0)
                return false;
        }
        return true;
    }
    public static void SetByBigInteger(BigInteger that,int[] dest,int Slot) {        
        //先全部歸0
        for (int i=0; i<Slot; i++) {
            dest[i]=0;
        }
        //依照BigInteger的標記設定dest陣列
        for (int i=0; i<Slot; i++) {
            //System.out.print(that.testBit(i)+" ");   //testBit的參數是從LSBit開始算
            if (that.testBit(i)) {
                ++dest[i];
            }
        }        
    }
    public static void step2(String line) {
        final int SZ=InputSize;
        final int MaxP=SZ-1;
        final int Slot=SZ+1;
        int[] index=new int[MaxP];
        int[] R=new int[Slot];
        int[] L=new int[Slot];
        BigInteger Bmaxp=BigInteger.valueOf(MaxP);        
        BigInteger BSlot=BigInteger.valueOf(Slot);        
        BigInteger Limit= BigInteger.valueOf(2).pow(Slot);
        for (BigInteger iter1=BigInteger.ONE; iter1.compareTo(Limit)==(-1); iter1=iter1.add(BigInteger.ONE)) { 
            if (iter1.bitCount()==MaxP) {
                SetByBigInteger(iter1,L,Slot);
                for (BigInteger iter2=BigInteger.ONE; iter2.compareTo(Limit)==(-1); iter2=iter2.add(BigInteger.ONE)) {
                    if (iter2.bitCount()==MaxP) {
                        SetByBigInteger(iter2,R,Slot);
                        if (Valid(L,R))
                            step2_a_line(line,L,R);
                    }
                }
            }                                        
        }
        

        
        
        
    }
    public static void main(String[] args) {
        //step1();
        step2("A+B+C");
    }
            
    
}
