package com.dkalsan.wplugins.Detail;

public interface DetailContract {
    interface View {

    }

    interface Presenter {
        void downloadZip(String zipUrl);
    }
}
