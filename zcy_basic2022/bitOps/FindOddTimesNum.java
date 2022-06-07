package bitOps;

public class FindOddTimesNum {
    /*
    fun: find A if only A in arr[] occurs odd times
    */
    public static int findOddTimesNum(int[] arr){
        int num = 0;
        for(int i = 0; i < arr.length; i++) num ^= arr[i];
        return num;
    }
    
    /*
    fun: find A and B if only A and B in arr[] occur odd times
    */
    public static int[] findOddTimesNums(int[] arr){
        /**
         * each element in arr[] occurs even times except A and B
         * 
         * eg: arr:{1,1,2,2,3,4,5,5}, A and B are 3，4 respectively
         * 1.提取包含A,B的nums: 3^4=0111
         * 2.nums系统2进制表示最右侧的1: rightmostOne=0001
         * 3.32 bits of element in arr[] has the same rightmost 1 like rightmostOne to do '^'
         * => num1 = 1^1^3^5^5=3, 即A
         * 4.num2= nums ^ num1=4, 即B
         */
        //1.
        int nums = 0;
        for(int i = 0; i < arr.length; i++) nums ^= arr[i];  //nums = a ^ b
        //2.
        int rightmostOne = nums & (-nums);  
        //3.
        int num1 = 0;
        for(int i = 0; i < arr.length; i++){
            if((arr[i] & rightmostOne) == 1) num1 ^= arr[i];
        }
        //4.
        int num2 = nums ^ num1;
        return new int[]{num1, num2};
    }
    //test entry
    public static void main(String[] args){
        int testTime = 5000;
        int maxSize = 100;
        int maxValue = 100;
        int[] arr = generateRandArray(maxSize, maxValue);
		int[] result = findOddTimesNums(arr);
    }

    public static int[] generateRandArray(int maxSize, int maxValue){
        
        int evenTimes = (int)(5 * Math.random() + 1) * 2;
        int oddTimes = evenTimes + 1;
        int num_even = (int)(5 * Math.random() + 1);  //num of numbers occurring evenTime 
        int[] arr = new int[(int)(num_even*evenTimes) + 2 * oddTimes];
        //fill in A
        arr[0] = (int)(maxValue * Math.random()) - (int)(maxValue * Math.random());
        for(int j = 1; j < oddTimes; j++) arr[j] = arr[0];
        //fill in numbers occurring evenTime but different from  A
        for(int i = oddTimes; i < arr.length - oddTimes; i += evenTimes){
            int e = 0;
            do{
                e = (int)(maxValue * Math.random()) -  (int)(maxValue * Math.random());
            }while( arr[i-1] == arr[i]);
            for(int j = i; j < i + evenTimes; j++) arr[j] = e;
        }
        //fill in B
        int num2 = 0;
        do{
            num2 = (int)(maxValue * Math.random()) -  (int)(maxValue * Math.random());
        } while( arr[arr.length - oddTimes -2] == num2);
        for(int j = arr.length - oddTimes; j < arr.length; j++) arr[j] = arr[arr.length - oddTimes];

        return arr;
    }
}
