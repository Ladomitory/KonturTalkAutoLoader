package ru.ladomitory.kontur.talkApi.jsonModel;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceHistory {
    @SerializedName("conferences")
    private ArrayList<Conference> list;
}
