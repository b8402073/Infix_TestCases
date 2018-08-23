
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
    public static void step2() {
        final int SZ=InputSize;
        final int MaxP=SZ-1;
        int[] L=new int[SZ+1];
        int[] R=new int[SZ+1];
        int[] Rinit=new int[SZ+1];
        L[0]=MaxP; L[1]=0; L[2]=0; L[3]=0;
        R[0]=0; R[1]=MaxP; R[2]=0; R[3]=0;
        for (int i=0; i<=SZ; i++) 
            Rinit[i]=R[i];
        /*
        while(L[2]<MaxP) {
            while(R[3]<MaxP) {
                
            }
        }
        */
        
    }
    public static void main(String[] args) {
        step1();
        step2();
    }
            
    
}
