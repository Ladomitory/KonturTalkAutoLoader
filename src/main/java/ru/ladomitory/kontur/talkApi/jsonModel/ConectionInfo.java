package ru.ladomitory.kontur.talkApi.jsonModel;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConectionInfo {
    @SerializedName("appPlatform")
    private String platform;
    @SerializedName("isViaProxy")
    private boolean isViaProxy;
    @SerializedName("participantRealIp")
    private String realIp;
    @SerializedName("country")
    private String country;
}
