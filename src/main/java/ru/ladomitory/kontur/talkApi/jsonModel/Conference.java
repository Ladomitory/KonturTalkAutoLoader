package ru.ladomitory.kontur.talkApi.jsonModel;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conference {
    @SerializedName("key")
    private String key;
    @SerializedName("roomName")
    private String roomName;
    @SerializedName("startTime")
    private Date startTime;
    @SerializedName("endTime")
    private Date endTime;
    @SerializedName("title")
    private String title;
    @SerializedName("isPlannedMeeting")
    private boolean isPlanned;
    @SerializedName("containsDeepFakeDetections")
    private boolean isTestedOnDeepFakes;

    public String toString() {
        return startTime.toString() + " " + title;
    }
}
