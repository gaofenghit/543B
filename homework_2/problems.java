import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class Result {
    int len;
    int x, y, z;
    Result() {
        len = x = y = z = -1;
    }
}



class Solution {
    public int question1(int n, int p, int q) {
        int[] dp = new int[n+1];
        dp[0] = dp[1] = 0;
        for(int i=2;i<=n;++i) {
            dp[i] = Integer.MAX_VALUE;
            for(int j=1;j<i;++j) {
                int tmp = p*j + dp[i-j] + q*(i-j) + dp[j];
                if( tmp < dp[i] )
                    dp[i] = tmp;
            }
        }
        return dp[n];
    }

    public Result question2(int[][][] A) {
        Result res = new Result();
        int n = A.length;

        int[][][] dp = new int[n][n][n];
        for(int h=0;h<n;++h) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    dp[h][i][j] = A[h][i][j] == 1 ? 0 : 1;
                }
            }
        }
        int maxLen = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                for(int k=0;k<n;++k) {
                    if( dp[i][j][k] == 0 )
                        continue;
                    int tmp = Integer.MAX_VALUE;
                    tmp = Math.min(tmp, (j>0)?dp[i][j-1][k]:0);
                    tmp = Math.min(tmp, (k>0)?dp[i][j][k-1]:0);
                    tmp = Math.min(tmp, (j>0&&k>0)?dp[i][j-1][k-1]:0);
                    tmp = Math.min(tmp, (i>0&&j>0)?dp[i-1][j-1][k]:0);
                    tmp = Math.min(tmp, (i>0&&k>0)?dp[i-1][j][k-1]:0);
                    tmp = Math.min(tmp, (i>0&&j>0&&k>0)?dp[i-1][j-1][k-1]:0);
                    tmp = Math.min(tmp, (i>0)?dp[i-1][j][k]:0);
                    tmp++;
                    if(i==0&&j==0&&k==0)
                        tmp = 1;
                    dp[i][j][k] = tmp;
                    if (tmp > maxLen) {
                        maxLen = tmp;
                        res.len = tmp;
                        res.x = i + 1 - tmp;
                        res.y = j + 1 - tmp;
                        res.z = k + 1 - tmp;
                    }
                }
            }
        }
        return res;
    }


    public boolean checkCubeValid(int[][][] A, int x, int y, int z, int l)
    {
        for(int h=x;h<x+l;++h) {
            for(int i=y;i<y+l;++i) {
                for(int j=z;j<z+l;++j){
                    if(A[h][i][j]==1)
                        return false;
                }
            }
        }
        return true;
    }

    public Result GetMaxSubCub2(int[][][] A) {
        Result res = new Result();
        int n = A.length;

        int maxSubLen = 0;

        for(int len=1; len<=n;++len) {
            for(int h=0;h<=n-len;++h) {
                for(int i=0;i<=n-len;++i) {
                    for(int j=0;j<=n-len;++j) {
                        boolean b = isSubCubic(A, h, i, j, len);
                        if( b == true && len > maxSubLen )
                        {
                            res.x = h;
                            res.y = i;
                            res.z = j;
                            res.len = len;
                            maxSubLen = res.len;

                        }
                    }
                }
            }
        }
        return res;
    }

    private boolean isSubCubic(int[][][] A, int x, int y, int z, int len) {
        for(int h=x;h<x+len;++h) {
            for(int i=y;i<y+len;++i) {
                for(int j=z;j<z+len;++j) {
                    if( A[h][i][j] == 1 )
                        return false;
                }
            }
        }
        return true;
    }

    public void question4(int n, int p, int q) {
        int[] dp = new int[n+1];
        int[] path = new int[n+1];
        dp[0] = dp[1] = 0;
        path[0] = path[1] = 0;
        for(int i=2;i<=n;++i) {
            dp[i] = Integer.MAX_VALUE;
            for(int j=1;j<i;++j) {
                int tmp = p*j + dp[i-j] + q*(i-j) + dp[j];
                if( tmp < dp[i] ) {
                    dp[i] = tmp;
                    path[i] = j;
                }
            }
        }
        printPath(path, n);
    }

    void printPath(int[] path, int n) {
        System.out.println("n:" + n + "  left:"+path[n]);
        printPath(path, (n-path[n]));
        System.out.println("n:" + n + "  right:"+(n-path[n]));
        printPath(path, path[n]);
    }

}


public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");
        Solution s = new Solution();
        int n = 30;
        double q = 0.001;
        int[][][] A = new int[n][n][n];

        for(int index=0;index<1000;++index) {
            System.out.println("-----------------------------------------  index:"+index);
            int count = 0;
            for(int i=0;i<n;++i){
                for(int j=0;j<n;++j){
                    for(int k=0;k<n;++k){
                        double random = Math.random();
                        if( random <=q ) {
                            A[i][j][k] = 1;
                            count++;
                        }
                        else
                            A[i][j][k] = 0;
                    }
                }
            }
            System.out.println("asteroid count:"+count);

            long t1 = System.currentTimeMillis();
            Result res = s.question2(A);
            long t2 = System.currentTimeMillis();
            System.out.println("@@@@@@@@@ A    volume:" + res.len + "  (" + res.x + ", " + res.y + ", " + res.z + ")");
            boolean b = s.checkCubeValid(A, res.x, res.y, res.z, res.len);
            System.out.println("@@@@@@@@@ A    check result: " + b + "    time:" + (t2 - t1) + "\n");

            t1 = System.currentTimeMillis();
            Result res2 = s.GetMaxSubCub2(A);
            t2 = System.currentTimeMillis();
            System.out.println("@@@@@@@@@ B    volume:" + res2.len + "  (" + res2.x + ", " + res2.y + ", " + res2.z + ")");
            boolean b2 = s.checkCubeValid(A, res2.x, res2.y, res2.z, res2.len);
            System.out.println("@@@@@@@@@ B    check result: " + b2 + "    time:" + (t2 - t1));
            System.out.println("-----------------------------------------\n\n");
            if( res.len != res2.len || res.x!=res2.x || res.y!=res2.y || res.z!=res2.z ){
                System.out.println("error occurs!");
                System.exit(255);
            }
        }
        System.out.println("All tests finish success!");
    }
}



