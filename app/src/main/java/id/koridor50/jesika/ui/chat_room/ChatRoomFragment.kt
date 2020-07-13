package id.koridor50.jesika.ui.chat_room

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import com.google.protobuf.Struct
import com.tyagiabhinav.dialogflowchatlibrary.Chatbot.ChatbotBuilder
import com.tyagiabhinav.dialogflowchatlibrary.ChatbotSettings
import com.tyagiabhinav.dialogflowchatlibrary.networkutil.ChatbotCallback
import com.tyagiabhinav.dialogflowchatlibrary.networkutil.TaskRunner
import com.tyagiabhinav.dialogflowchatlibrary.templates.*
import com.tyagiabhinav.dialogflowchatlibrary.templateutil.Constants
import com.tyagiabhinav.dialogflowchatlibrary.templateutil.OnClickCallback
import com.tyagiabhinav.dialogflowchatlibrary.templateutil.ReturnMessage
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.R
import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.databinding.ChatRoomFragmentBinding
import id.koridor50.jesika.utils.getPrefInt
import kotlinx.android.synthetic.main.chat_room_fragment.*
import kotlinx.android.synthetic.main.chat_room_fragment.view.*
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.inject.Inject


class ChatRoomFragment : Fragment(), ChatbotCallback, OnClickCallback {

    @Inject lateinit var viewModel: ChatRoomViewModel
    private lateinit var binding: ChatRoomFragmentBinding

    private val TAG = "ChatRoomFragment"
    private val USER = 10001
    private val BOT = 10002
    private val SPEECH_INPUT = 10070

    val SESSION_ID = "sessionID"

    //Variables
    private var sessionsClient: SessionsClient? = null
    private var session: SessionName? = null
    lateinit var dialogflowTaskRunner: TaskRunner
    private var isProgressRunning = false
    lateinit var queryEditText: EditText
    val localCommunityId: Int by lazy {
        requireContext().getPrefInt(PrefKey.LOCALCOMMUNITYIDPREFKEY)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as JesikaApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_room_fragment, container, false)

        return binding.root
    }

    private fun initChatBot(view: View) {
        val chatSettings = ChatbotSettings.getInstance()

        view.chatScrollView.post {
            chatScrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }

        view.sendBtn.setOnClickListener {
            Log.d(TAG, "Send click");
            sendMessage(view);
        }

        queryEditText = view.edittext_chatbox
        view.edittext_chatbox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                        sendMessage(sendBtn)
                        return@OnKeyListener true
                    }
                    else -> {
                    }
                }
            }
            false
        })

        val sessionID = UUID.randomUUID().toString();

        try {
            init(sessionID)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error creating a session!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        settingChatbot()
        initChatBot(binding.root)
    }

    fun settingChatbot() {
        DialogflowCredentialss.getInstance().inputStream = resources.openRawResource(R.raw.jesika)
        ChatbotSettings.getInstance().chatbot = ChatbotBuilder()
            .setChatBotAvatar(ContextCompat.getDrawable(requireContext(), R.drawable.profile))
            .setChatUserAvatar(ContextCompat.getDrawable(requireContext(), R.drawable.img_profile))
            .build()
    }

    override fun OnChatbotResponse(response: DetectIntentResponse?) {
        removeProcessWaitBubble();
        processResponse(response);
    }

    override fun OnUserClickAction(msg: ReturnMessage?) {
        val eventName = msg!!.eventName
        val param: Struct? = msg.param
        if (eventName != null && !eventName.trim { it <= ' ' }.isEmpty()) {
            if (param != null && param.getFieldsCount() > 0) {
                val eventInput: EventInput =
                    EventInput.newBuilder().setName(eventName).setLanguageCode("en-US")
                        .setParameters(param).build()
                send(eventInput, msg.actionText)
            } else {
                val eventInput: EventInput =
                    EventInput.newBuilder().setName(eventName).setLanguageCode("en-US").build()
                send(eventInput, msg.actionText)
            }
        } else {
            send(msg.actionText)
        }
    }

    @Throws(IOException::class)
    private fun init(UUID: String) {
        val credentialStream: InputStream? = DialogflowCredentialss.getInstance().inputStream
        val credentials: GoogleCredentials = GoogleCredentials.fromStream(credentialStream)
        val projectId: String = (credentials as ServiceAccountCredentials).getProjectId()
        val settingsBuilder: SessionsSettings.Builder = SessionsSettings.newBuilder()
        val sessionsSettings: SessionsSettings =
            settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build()
        sessionsClient = SessionsClient.create(sessionsSettings)
        session = SessionName.of(projectId, UUID)
        if (ChatbotSettings.getInstance().isAutoWelcome) {
            send("my id is $localCommunityId")
        }
    }

    private fun sendMessage(view: View) {
        val msg: String = queryEditText.getText().toString()
        if (msg.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(context, "Please enter your query!", Toast.LENGTH_LONG)
                .show()
        } else {
            send(msg)
        }
    }

    private fun send(message: String) {
        Log.d(TAG, "send: 1")
        val tmt =
            TextMessageTemplate(context, this, Constants.USER)
        if (!ChatbotSettings.getInstance().isAutoWelcome) {
            chatLayout.addView(tmt.showMessage(message))
            queryEditText.setText("")
            showProcessWaitBubble()
        } else {
            ChatbotSettings.getInstance().isAutoWelcome = false
        }
        val queryInput = QueryInput.newBuilder()
            .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build()
        dialogflowTaskRunner = TaskRunner(
            this,
            session,
            sessionsClient,
            queryInput
        )
        dialogflowTaskRunner.executeChat()
    }

    private fun send(event: EventInput, message: String) {
        Log.d(TAG, "send: 2")
        val tmt = TextMessageTemplate(
            context,
            this,
            Constants.USER
        )
        if (!ChatbotSettings.getInstance().isAutoWelcome) {
            chatLayout.addView(tmt.showMessage(message))
            queryEditText.setText("")
            showProcessWaitBubble()
        } else {
            ChatbotSettings.getInstance().isAutoWelcome = false
        }
        val queryInput = QueryInput.newBuilder().setEvent(event).build()
        dialogflowTaskRunner = TaskRunner(
            this,
            session,
            sessionsClient,
            queryInput
        )
        dialogflowTaskRunner!!.executeChat()
    }

    private fun showProcessWaitBubble() {
        val tmt = TextMessageTemplate(
            context,
            this,
            Constants.BOT
        )
        chatLayout.addView(tmt.showMessage("..."))
        isProgressRunning = true
        enableDissableChatUI(false)
    }

    private fun removeProcessWaitBubble() {
        enableDissableChatUI(true)
        if (isProgressRunning && chatLayout != null && chatLayout.childCount > 0) {
            chatLayout.removeViewAt(chatLayout.childCount - 1)
            isProgressRunning = false
        }
    }

    private fun processResponse(response: DetectIntentResponse?) {
        Log.d(TAG, "processResponse")
        if (response != null) {
            val contextList: MutableList<com.google.cloud.dialogflow.v2.Context> =
                response.queryResult.outputContextsList
            val layoutCount = chatLayout.childCount
            if (contextList.size > 0) {
                for (context in contextList) {
                    if (context.getName().contains("param_context")) {
                        val paramMap = context.getParameters().getFieldsMap()
                        if (paramMap.containsKey("template")) {
                            val template: String =
                                context.getParameters().getFieldsMap().get("template")!!
                                    .getStringValue()
                            when (template) {
                                "text" -> {
                                    Log.d(TAG, "processResponse: Text Template")
                                    val tmt = TextMessageTemplate(
                                        getContext(),
                                        this,
                                        Constants.BOT
                                    )
                                    chatLayout.addView(tmt.showMessage(response)) // move focus to text view to automatically make it scroll up if softfocus
                                    queryEditText.requestFocus()
                                }
                                "button" -> {
                                    Log.d(TAG, "processResponse: Button Template")
                                    val bmt = MsgButtonLayout(
                                        getContext(),
                                        this,
                                        Constants.BOT
                                    )
                                    chatLayout.addView(bmt.showMessage(response)) // move focus to text view to automatically make it scroll up if softfocus
                                    queryEditText.isEnabled = false
                                }
                                "hyperlink" -> {
                                    Log.d(TAG, "processResponse: Hyperlink Template")
                                    val blt = HyperLinkTemplate(
                                        getContext(),
                                        this,
                                        Constants.BOT
                                    )
                                    chatLayout.addView(blt.showMessage(response)) // move focus to text view to automatically make it scroll up if softfocus
                                    queryEditText.isEnabled = false
                                }
                                "checkbox" -> {
                                    Log.d(TAG, "processResponse: CheckBox Template")
                                    val cbmt = CheckBoxButtonLayout(
                                        getContext(),
                                        this,
                                        Constants.BOT
                                    )
                                    chatLayout.addView(cbmt.showMessage(response)) // move focus to text view to automatically make it scroll up if softfocus
                                    queryEditText.isEnabled = false
                                }
                                "list" -> {
                                    Log.d(TAG, "processResponse: CheckBox Template")
                                    val cbmt = ListLayout(
                                        getContext(),
                                        this,
                                        Constants.BOT
                                    )
                                    chatLayout.addView(cbmt.showMessage(response)) // move focus to text view to automatically make it scroll up if softfocus
//                                    queryEditText.isEnabled = false
                                }
                                "card" -> {
                                    Log.d(TAG, "processResponse: Card Template")
                                    val cmt = CardMessageTemplate(
                                        getContext(),
                                        this,
                                        Constants.BOT
                                    )
                                    chatLayout.addView(cmt.showMessage(response)) // move focus to text view to automatically make it scroll up if softfocus
                                    queryEditText.isEnabled = false
                                }
                                "carousel" -> {
                                    Log.d(TAG, "processResponse: Carousel Template")
                                    val crt = CarouselTemplate(
                                        getContext(),
                                        this,
                                        Constants.BOT
                                    )
                                    chatLayout.addView(crt.showMessage(response)) // move focus to text view to automatically make it scroll up if softfocus
                                    queryEditText.isEnabled = false
                                }
                            }
                        }
                    } else {
                        // when no param context if found... go to default
                        val tmt = TextMessageTemplate(
                            getContext(),
                            this,
                            Constants.BOT
                        )
                        chatLayout.addView(tmt.showMessage(response))
                        queryEditText.requestFocus()
                    }
                    if (chatLayout.childCount > layoutCount) {
                        break //this check is added as multiple layouts were getting added to chatLayout equal to number of loops
                    }
                }
            } else {
                // when no param context if found... go to default
                val tmt = TextMessageTemplate(
                    context,
                    this,
                    Constants.BOT
                )
                chatLayout.addView(tmt.showMessage(response))
                queryEditText.requestFocus()
            }
        } else {
            Log.e(TAG, "processResponse: Null Response")
        }
    }

    private fun enableDissableChatUI(bool: Boolean) {
        queryEditText.isEnabled = bool
        queryEditText.isClickable = bool
        view?.sendBtn?.isEnabled = bool
        view?.sendBtn?.isClickable = bool
    }

    fun moveMemberList () {
        findNavController().navigate(ChatRoomFragmentDirections.actionChatRoomFragmentToMemberListFragment())
    }

    fun popBackStack () {
        findNavController().popBackStack()
    }
}