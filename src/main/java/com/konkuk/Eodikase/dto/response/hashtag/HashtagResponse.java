package com.konkuk.Eodikase.dto.response.hashtag;

import com.konkuk.Eodikase.domain.hashtag.entity.HashTagName;
import com.konkuk.Eodikase.domain.hashtag.entity.Hashtag;
import lombok.Data;

@Data
public class HashtagResponse {
    private Long hasgtagId;
    private HashTagName hashtagCode;
    private String hashtagName;

    public HashtagResponse(Hashtag hashtag){
        this.hasgtagId = hashtag.getId();
        this.hashtagCode = hashtag.getHashTagName();
        this.hashtagName = hashtag.getHashTagName().description();
    }
}
