package com.petiyaak.box.event;

import com.petiyaak.box.model.bean.PetiyaakBoxInfo;

/**
 * Created by chenzhaolin on 2019/11/8.
 */
public class BindSucessEvent {
    private PetiyaakBoxInfo info;

    public PetiyaakBoxInfo getInfo() {
        return info;
    }

    public void setInfo(PetiyaakBoxInfo info) {
        this.info = info;
    }

    public BindSucessEvent(PetiyaakBoxInfo info) {
        this.info = info;
    }
}
