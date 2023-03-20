package com.antar.merchant.utils.local_interface;

import com.antar.merchant.json.TopUpRequestResponse;

public interface OnItemClicked {
    void onClick(TopUpRequestResponse response);
}
