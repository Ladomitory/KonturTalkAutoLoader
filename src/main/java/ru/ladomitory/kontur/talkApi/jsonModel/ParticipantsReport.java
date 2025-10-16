package ru.ladomitory.kontur.talkApi.jsonModel;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsReport {
    @SerializedName("participants")
    private ArrayList<Participant> list;
}
