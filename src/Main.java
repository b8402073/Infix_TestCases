
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.Stack;
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
    public static Vector<String> Result2=new Vector<String>();
    public final static char Operand[]={'A','B','C','D','E','F','G','H','I' ,'J','K','L','M'};
    public final static char Operator[]={'$','*','/','+','-'};
    public final static int  InputSize=5;
//    public static int MaxP=InputSize-1;            
//    public static int Slot=InputSize+1;      
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
    public static void step2_a_line(String line,int[] L,int[] R,int InputSize,int MaxP) {
        final int Slot=InputSize+1;
        String ret="";
        for (int i=0,m=0; i<=InputSize; i++) {
            if (i==0) {
                for (int x=0; x<L[0]; x++) ret+=("(");
                ret+=(line.charAt(m));
                m+=2;
            }else if (i==InputSize) {
                for (int x=0; x<R[Slot-1]; x++) ret+=(")");
                System.out.println(ret);
                Result2.add(ret);
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
    private static boolean Valid(int[] L,int[]  R,int InputSize)  {        
        final int Slot=InputSize+1;
        final int SZ=InputSize;
        if (L[Slot-1]>0) return false;
        if (R[0]>0) return false;
        
        for (int i=0; i<Slot-1; i++) {
            int m=SZ-i;            
            if (L[i]>0 && R[i+1]>0)
                return false;
            if (L[m-1]>0 && R[m]>0)
                return false;
        }
        
        Stack<Character> Hand=new Stack<Character>();
        Stack<Integer>   Memory=new Stack<Integer>();
        if (L[0]>0) {
            Memory.push(L[0]);
            for (int x=0; x<L[0]; x++) Hand.push('@');
        }
        for (int i=1; i<=R.length-2; i++) {
            int pop=R[i];
            if (Hand.size()< pop)
                return false;     
            if (!Memory.empty() && pop==Memory.peek() && pop>1)
                return false;
            for (int x=0; x<pop; x++) Hand.pop();            
            int push=L[i];
            if (L[i]>0) {
                Memory.push(L[i]);
                for (int x=0; x<push; x++) Hand.push('@');
            }

        }
        if (Hand.size()< R[R.length-1])
            return false;
        if (!Memory.empty() && R[R.length-1]==Memory.peek() && R[R.length-1]>1 )
            return false;
        return true;
    }
    private static boolean ExactlyEqual(int[] thes, int[] that) {
        if (thes.length==that.length) {
            for (int i=0; i<thes.length; i++) {
                if (thes[i]!=that[i])
                    return false;
            }
            return true;
        }
        return false;
    }
    private static boolean VectorContain(Vector<int[]> container,int[] that) {
        for (int i=0; i<container.size(); i++) {
            int[] thes=container.get(i);
            if (ExactlyEqual(thes,that))
                return true;
        }
        return false;
    }
    private static void action(int[] hand,Vector<int[]> Collect,int InputSize,int MaxP) {
        /* for debugging
        for (int i=0; i<MaxP; i++) {
            System.out.print(""+hand[i]+" ");
        }
        System.out.println();        
        */
        final int Slot=InputSize+1;        
        int[] dest=new int[Slot];
        ClearZero(dest,Slot);
        for (int i=0; i<MaxP; i++) {
            ++dest[ hand[i] ];
        }
        if (!VectorContain(Collect,dest))
            Collect.add(dest);
    }
    private static void backtrack(int dim,int[] hand,Vector<int[]> Collect,int InputSize,int MaxP) {
        final int Slot=InputSize+1;
        if (dim==MaxP) {
            action(hand,Collect,InputSize,MaxP);
            return;
        }
        for (int i=0; i<Slot; i++) {
            hand[dim]=i;
            backtrack(dim+1,hand,Collect,InputSize,MaxP);
        }
    }
    public static void step2(String line,int InputSize,int MaxP) {

        int[] index=new int[MaxP];
        Vector<int[]> CollectL=new Vector<int[]>();
        Vector<int[]> CollectR=new Vector<int[]>();
        ClearZero(index,MaxP);
        backtrack(0,index,CollectL,InputSize,MaxP);
        backtrack(0,index,CollectR,InputSize,MaxP);
        for (int i=0; i<CollectL.size(); i++) {
            int[] L=CollectL.get(i);            
            for (int j=0; j<CollectR.size(); j++) {
                int[] R=CollectR.get(j);
                if (Valid(L,R,InputSize)) {
                    System.out.println("i="+i+"  j="+j);
                    step2_a_line(line,L,R,InputSize,MaxP);                                
                }
            }
        }

        

        
        
        
    }
    public static void main(String[] args) throws Exception{
        FileWriter fw=new FileWriter("Infix5.txt");
        step1();
        for (String line: Result1) {
           System.out.println(line);
           fw.write(line+"\r\n");          
        }
        fw.flush();           
        
        for (String line:Result1) {
            for (int P=1; P<=InputSize-1; P++) {
                step2(line,InputSize,P);
            }
        }
        for (String line:Result2) {
            fw.write(line+"\r\n");
        }
        
        fw.close();
        //step2("A+B+C");
        
        //step2("A+B+C+D+E",5,1);
        //step2("A+B+C+D+E",5,2);
    }
            
    
}
