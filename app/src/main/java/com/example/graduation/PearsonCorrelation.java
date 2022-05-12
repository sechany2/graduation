package com.example.graduation;

import android.util.Log;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Math;


public class PearsonCorrelation {


    public HashMap<String, Double> knn(String name, HashMap<String, HashMap> user, ArrayList<String> productList, int number) { //비슷한 유저데이터 참조

        HashMap<String, Double> recommend = new HashMap<String, Double>() {};
        HashMap<String, Double> pearsonResult = new HashMap<String, Double>();
        for (Entry<String, HashMap> entry : user.entrySet()) {//pearson 상관계수 구하기
            pearsonResult.put(entry.getKey(), pearson(user.get(name), entry.getValue()));
        }
        // 비교함수 Comparator를 사용하여 내림 차순으로 정렬
        List<Entry<String, Double>> list_entries = new ArrayList<Entry<String, Double>>(pearsonResult.entrySet());
        Collections.sort(list_entries, new Comparator<Entry<String, Double>>() {
            // compare로 값을 비교
            public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2) {
                // 내림 차순으로 정렬

                return obj2.getValue().compareTo(obj1.getValue());
            }
        });


        //없는 값 대조하여 구하기
        HashMap<String, Double> map = user.get(name);
        HashMap<String, Double> map2 = null;
        String[] keys = new String[map.size()];
        HashMap<String, Double> results = new HashMap<String, Double>() {};
        int k = 0;
        for (Object object : productList) { //없는 값 찾기
            boolean resultBoolean = false;
            String key;
            key = object.toString();
            for (Entry<String, Double> entry : map.entrySet()) {
                if (entry.getKey().equals(object.toString())) {
                    resultBoolean = true;


                }
            }

            if (resultBoolean) { //값이 없을때
                double[] p = new double[number];   //상관계수 값 계산
                double[] r = new double[number];


                double[] mean = new double[number];
                double mymean = 0;


                int i = 0;
                int j = 0;

                // Log.e(list_entries.toString(), name);




                    for (j = j; j < list_entries.size(); j++) {
                        if (!(list_entries.get(j).getKey().equals(name))) {
                            map2 = user.get(list_entries.get(j).getKey());
                            if (map2.get(key) != null) {
                                if(i<number){
                                    r[i] = map2.get(key);  //대조자 에 제품 점수
                                    p[i] = list_entries.get(j).getValue();   //대조자에 상관계수
                                    mean[i] = mean(map2);                    //대조자에 평균 점수
                                    i++;
                                }

                            }
                        }

                    }



                mymean = mean(map);                     //사용자 평균점수

                double psum = 0;
                double qsum = 0;
                for (i = 0; i < number; i++) {
                    psum += p[i];
                    qsum += (r[i] - mean[i]) * p[i];
                }
                double result = 0;
                result = ( qsum/ psum )+ mymean; //식 사용자평균점수+(상관계수1*대조자1점수+상관계수2*대조자2점수)/(상관계수1+상관계수2)
                recommend.put(key, result ); //값저장
                results.put(key, Math.abs(recommend.get(key)-map.get(key))/map.get(key) * 100 );


                keys[k] = key;
                k++;

            }



        }
        double as = 0;
        for(int d =0 ;d<keys.length;d++ ){
            as =as +results.get(keys[d]);
        }
        double avg = 0;
        avg = as/ keys.length ;
        HashMap<String,Double> avgs = new HashMap<String, Double>() {};
        avgs.put("1",avg);
        return  avgs;
        //return recommend;

    }


    public Double pearson(HashMap<String, Double> s1, HashMap<String, Double> s2)//s 는 사용자
    {

        double sump = 0;
        double sumq = 0;
        double sumr = 0;


        double s1mean = mean(s1);
        double s2mean = mean(s2);

        Object[] keysx = s1.keySet().toArray();
        Object[] keysy = s2.keySet().toArray();
        int flag = 0;
        for (int i = 0; i < s1.size(); i++) {  //( s1 점수 - s1평균) * ( s2점수 - s2 평균 )

            for (int j = 0; j < s2.size(); j++) {

                if (keysx[i].equals(keysy[j])) {

                    sump = sump + ((s1.get(keysx[i]) - s1mean) * (s2.get(keysy[j]) - s2mean));
                    sumq = sumq + ((s1.get(keysx[i]) - s1mean) * (s1.get(keysx[i]) - s1mean));
                    sumr = sumr + ((s2.get(keysy[j]) - s2mean) * (s2.get(keysy[j]) - s2mean));

                    flag++;
                }
            }


        }

        double result = sump / Math.sqrt((sumq * sumr));
        if (sump == 0 || flag < 2) {
            result = -1;
        }
        return result; // pearson(s1,s2);
    }

    private double mean(HashMap<String, Double> s) {
        double sum = 0;
        int ct = 0;
        for (Entry<String, Double> entry : s.entrySet()) {
            if (entry.getValue() != null) {
                sum = sum + entry.getValue();
                ct = ct + 1;
            }
        }

        double result = sum / ct;
        return result;
    }

    /*private ArrayList<Integer> user1;
    private ArrayList<Integer> user2;
    private ArrayList<Integer> user3;
    private ArrayList<Integer> user4;
    private HashMap<String, Double> aaa;
    private HashMap<String, Double> jangsechan;
    private HashMap<String, Double> minju;
    private HashMap<String, Double> sechan;
    private HashMap<String, Double> sechany;
    private HashMap<String, HashMap> user;


    public void Example2() {
        user = new HashMap<String, HashMap>() {
        };
        aaa = new HashMap<String, Double>() {
        };
        jangsechan = new HashMap<String, Double>() {
        };
        minju = new HashMap<String, Double>() {
        };
        sechan = new HashMap<String, Double>() {
        };
        sechany = new HashMap<String, Double>() {
        };
        user.put("aaa", aaa);
        //초기값 지정

        aaa.put("Product_01", 5.0);
        aaa.put("Product_02", 4.0);
        aaa.put("Product_03", 1.0);
        aaa.put("Product_04", 4.0);
        aaa.put("Product_05", 3.0);


        user.put("jangsechan", jangsechan);

        jangsechan.put("Product_01", 4.0);
        jangsechan.put("Product_02", 5.0);
        jangsechan.put("Product_03", 2.0);
        jangsechan.put("Product_04", 5.0);
        jangsechan.put("Product_05", 1.0);


        user.put("minju", minju);

        minju.put("Product_01", 2.0);
        minju.put("Product_02", null);
        minju.put("Product_03", 5.0);
        minju.put("Product_04", 4.0);
        minju.put("Product_05", 3.0);


        user.put("sechan", sechan);

        sechan.put("Product_01", 1.0);
        sechan.put("Product_02", 3.0);
        sechan.put("Product_03", 4.0);
        sechan.put("Product_04", 5.0);
        sechan.put("Product_05", 2.0);

        user.put("sechany", sechany);

        sechany.put("Product_01", 4.0);
        sechany.put("Product_02", 3.0);
        sechany.put("Product_03", 5.0);
        sechany.put("Product_04", null);
        sechany.put("Product_05", 4.0);


        for (Entry<String, Double> entry : knn2("minju").entrySet()) {
            Log.e(entry.getKey() ,String.valueOf(entry.getValue()));


        }
    } public HashMap<String,Double> knn2 (String name){ //비슷한 유저데이터 참조

            HashMap<String, Double> recommend = new HashMap<String, Double>() {};
            HashMap <String,Double> pearsonResult = new HashMap<String,Double>();
            for (Entry<String, HashMap> entry : user.entrySet()) {//pearson 상관계수 구하기
                pearsonResult.put(entry.getKey(),pearson(user.get(name), entry.getValue()));
            }
            // 비교함수 Comparator를 사용하여 내림 차순으로 정렬
            List<Entry<String, Double>> list_entries = new ArrayList<Entry<String, Double>>(pearsonResult.entrySet());
            Collections.sort(list_entries, new Comparator<Entry<String, Double>>() {
                // compare로 값을 비교
                public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2) {
                    // 내림 차순으로 정렬
                    return obj2.getValue().compareTo(obj1.getValue());
                }
            });



            //없는 값 대조하여 구하기
            HashMap<String,Double> map = user.get(name);
            HashMap<String,Double> map2;
            for (Entry<String, Double> entry : map.entrySet()) {
            if(entry.getValue() == null) { //조건문 값이 없을때
                 //대조자 두명
                 double p1 = 0;
                 double r1 = 0;
                 double p2 = 0;
                 double r2 = 0;
                 double mean = 0;
                 double mean1 = 0;
                 double mean2 = 0;
                 double result = 0;
                 map2 = user.get(list_entries.get(1).getKey());
                 r1  = map2.get(entry.getKey());      //대조자1 에 제품 점수
                 mean1 = mean(map2);                   //대조자1에 평균 점수
                 map2 = user.get(list_entries.get(2).getKey());
                 r2 =  map2.get(entry.getKey());      //대조자2 에 제품 점수
                 mean2=mean(map2);                    //대조자2 에 평균 점수
                 p1 =  list_entries.get(1).getValue();  //대조자1 에 상관계수
                 p2 = list_entries.get(2).getValue();   //대조자2 에 상관계수
                 mean = mean(map); // 사용자1 평균점수


                 result = mean + ((r1-mean1) * p1+ (r2-mean2) * p2)/ (p1 + p2); //식 사용자1 평균점수+(상관계수1*대조자1점수+상관계수2*대조자2점수)/(상관계수1+상관계수2)


                recommend.put(entry.getKey(),result); //값저장
            }

            }


        return recommend;

        }

              아이템1 아이템2 아이템3 아이템4  평균  pearson(i,2)
         * 사용자1   1       3     5     3      3      0.79
         * 사용자2   1       4     ?     5     3.3       1
         * 사용자3   1       4     5     4     3.5     0.915
         * 사용자4   5       1     5     1      3     -0.915
         *
        public void Example () {

            user1 = new ArrayList<>();
            user1.clear();
            user1.add(1);
            user1.add(3);
            user1.add(5);
            user1.add(3);

            user2 = new ArrayList<>();
            user2.clear();
            user2.add(1);
            user2.add(4);
            user2.add(null);
            user2.add(5);

            user3 = new ArrayList<>();
            user3.clear();
            user3.add(1);
            user3.add(4);
            user3.add(5);
            user3.add(4);

            user4 = new ArrayList<>();
            user4.clear();
            user4.add(5);
            user4.add(1);
            user4.add(5);
            user4.add(1);

            knn();

        }

        public void knn () {
            HashMap<String, Double> map = new HashMap<String, Double>() {
                {//초기값 지정
                    put("user1", pearson(user1, user2));
                    put("user3", pearson(user2, user3));
                    put("user4", pearson(user4, user2));
                    put("user2", pearson(user2, user2));
                }
            };

            // 비교함수 Comparator를 사용하여 내림 차순으로 정렬
            List<Entry<String, Double>> list_entries = new ArrayList<Entry<String, Double>>(map.entrySet());
            Collections.sort(list_entries, new Comparator<Entry<String, Double>>() {
                // compare로 값을 비교
                public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2) {
                    // 내림 차순으로 정렬
                    return obj2.getValue().compareTo(obj1.getValue());
                }
            });

            for (Entry<String, Double> entry : list_entries) {
                Log.e(entry.getKey(), entry.getValue().toString());
            }


        }

        public double pearson (ArrayList < Integer > s1, ArrayList < Integer > s2)//s 는 사용자
        {

            double sump = 0;
            double sumq = 0;
            double sumr = 0;
            int e = s1.size();

            double s1mean = mean(s1);
            double s2mean = mean(s2);

            for (int i = 0; i < s1.size(); i++) {
                if (s1.get(i) != null && s2.get(i) != null) { //( s1 점수 - s1평균) * ( s2점수 - s2 평균 )
                    sump = sump + ((s1.get(i) - s1mean) * (s2.get(i) - s2mean));
                    sumq = sumq + ((s1.get(i) - s1mean) * (s1.get(i) - s1mean));
                    sumr = sumr + ((s2.get(i) - s2mean) * (s2.get(i) - s2mean));
                }


            }
            double result = sump / Math.sqrt((sumq * sumr));


            return result; // pearson(s1,s2);
        }


        private double mean (ArrayList < Integer > s) {
            double sum = 0;
            int ct = 0;
            for (int i = 0; i < s.size(); i++) {
                if (s.get(i) != null) {
                    sum = sum + s.get(i);
                    ct = ct + 1;
                }
            }

            double result = sum / ct;
            return result;
        }

         */
}

