package ru.ladomitory.kontur.talkApi.jsonModel;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    @SerializedName("participantId")
    private String id;
    @SerializedName("participantName")
    private String name;
    @SerializedName("isGuest")
    private boolean isQuest;
    @SerializedName("connectionsInfo")
    private ArrayList<ConectionInfo> connections;
}
