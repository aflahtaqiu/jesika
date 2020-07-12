package id.koridor50.jesika.ui.chat_room;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.protobuf.Value;
import com.tyagiabhinav.dialogflowchatlibrary.templates.ButtonMessageTemplate;
import com.tyagiabhinav.dialogflowchatlibrary.templates.MessageLayoutTemplate;
import com.tyagiabhinav.dialogflowchatlibrary.templateutil.OnClickCallback;

import java.util.List;
import java.util.Map;

class MsgButtonLayout extends MessageLyt {

    private static final String TAG = ButtonMessageTemplate.class.getSimpleName();

    public MsgButtonLayout(Context context, OnClickCallback callback, int type) {
        super(context, callback, type);
        setOnlyTextResponse(false);
    }

    @Override
    FrameLayout populateRichMessageContainer() {
        FrameLayout richMessageContainer = getRichMessageContainer();
        DetectIntentResponse response = getResponse();
        List<com.google.cloud.dialogflow.v2.Context> contextList = response.getQueryResult().getOutputContextsList();

        LinearLayout btnLayout = getVerticalContainer();
//        btnLayout.setGravity(Gravity.CENTER);
        btnLayout.setFocusableInTouchMode(true);
        for (com.google.cloud.dialogflow.v2.Context context : contextList) {
            Map<String, Value> contextParam = context.getParameters().getFieldsMap();
            if (contextParam.get("buttonItems") == null) continue;
            List<Value> buttonList = (contextParam.get("buttonItems").getListValue() != null) ? contextParam.get("buttonItems").getListValue().getValuesList() : null;
            String align = context.getParameters().getFieldsMap().get("align").getStringValue();
            String sizeValue = context.getParameters().getFieldsMap().get("size").getStringValue();
            String eventName = context.getParameters().getFieldsMap().get("eventToCall").getStringValue();

            if (align.equalsIgnoreCase("horizontal") || align.equalsIgnoreCase("h")) {
                btnLayout = getHorizontalContainer();
            }

            if (buttonList != null) {
                for (Value item : buttonList) {
                    btnLayout.addView(getBtn("button", item.getStructValue().getFieldsMap(), sizeValue, eventName));
                }
            }
        }
        Log.d(TAG, "populateRichMessageContainer: btn layout count: " + btnLayout.getChildCount());
        richMessageContainer.addView(btnLayout);

        return richMessageContainer;
    }
}
