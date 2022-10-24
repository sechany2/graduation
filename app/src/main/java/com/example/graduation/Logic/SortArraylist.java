package com.example.graduation.Logic;

import com.example.graduation.Object.Product;

import java.util.Comparator;

public class SortArraylist implements Comparator<Product> {
    private int sortType;

    public SortArraylist(int sortType) {
        this.sortType = sortType;
    }

    @Override
    public int compare(Product p1, Product p2) {
        switch (sortType){
            case 0: //추천많이 받은 순서
                int a,b;
                if (p1.getRecommendation_count() != null && p2.getRecommendation_count() != null){
                    a= Integer.parseInt(p1.getRecommendation_count());
                    b= Integer.parseInt(p2.getRecommendation_count());
                    return Integer.compare(a, b)*(-1);
                }
            case 1: //평점 높은 순서
                return Integer.compare(Math.round(p1.getPd_avg()), Math.round(p2.getPd_avg()))*(-1);
            default: return 0;
        }
    }
}
