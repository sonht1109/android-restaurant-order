package com.example.model;

import com.example.R;

public class Values {
    public static final int ORDER_STATUS_PENDING = 0;
    public static final int ORDER_STATUS_ACCEPTED = 1;
    public static final int ORDER_STATUS_PAID = 2;
    public static final Integer[] TABLE_NUMBERS = new Integer[]{1, 2, 3, 4, 5, 6};

    // adeptus: Súp cua dăm bông
    // sandwich: Trứng ốp
    // tofu: Đậu hũ hạnh nhân
    // bamboo: Măng chua
    // bery: Mochi mâm xôi
    // sushi: Sushi trứng chim
    public static final int[] FOOD_IMAGES = {R.drawable.adeptus,
            R.drawable.sandwich, R.drawable.almond_tofu,
            R.drawable.bamboo, R.drawable.berry_mizu, R.drawable.sushi};

    public static final int[] DRINK_IMAGES = {R.drawable.coke,
            R.drawable.pepsi, R.drawable.mirinda,
            R.drawable.water, R.drawable.sochu};

    public static enum EnumDiskType {
        FOOD, DRINK
    }
}
