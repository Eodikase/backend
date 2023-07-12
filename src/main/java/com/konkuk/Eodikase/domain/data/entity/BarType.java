package com.konkuk.Eodikase.domain.data.entity;

public enum BarType {
    //요리주점/ 이자카야/ 포장마차/ 술집/ 와인/ 전통,민속주점/ 오뎅,꼬치 /바(BAR)/맥주,호프/ 유흥주점 /
    BAR_DISH("BAR_DISH"),
    BAR_IZA("BAR_IZA"),
    BAR_POZ("BAR_POZ"),
    BAR_ORG("BAR_ORG"),
    BAR_WINE("BAR_WINE"),
    BAR_TRAD("BAR_TRAD"),
    BAR_ODEN("BAR_ODEN"),
    BAR_BAR("BAR_BAR"),
    BAR_HOP("BAR_HOP"),
    BAR_KRK("BAR_KRK");

    private final String type;

    BarType(final String type) {
        this.type = type;
    }
}
