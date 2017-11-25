package ir.aspacrm.my.app.mahanet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterSpinnerChildCodes;
import ir.aspacrm.my.app.mahanet.adapter.AdapterSpinnerCodes;
import ir.aspacrm.my.app.mahanet.adapter.AdapterSpinnerVahed;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetAddTicketResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorAddTicket;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetUnits;
import ir.aspacrm.my.app.mahanet.events.EventOnGetUnitResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnSendTicketRequest;
import ir.aspacrm.my.app.mahanet.events.EventOnTicketCodeResponse;
import ir.aspacrm.my.app.mahanet.model.TicketCodes;
import ir.aspacrm.my.app.mahanet.model.Unit;

public class ActivitySendTiket extends AppCompatActivity {
    @Bind(R.id.spOlaviat)
    Spinner spOlaviat;
    @Bind(R.id.spCodes)
    Spinner spCodes;
    @Bind(R.id.spChildCodes)
    Spinner spChildCodes;
    @Bind(R.id.spVahed)
    Spinner spVahed;
    @Bind(R.id.layBtnSendTicket)
    CardView layBtnSendTicket;
    @Bind(R.id.edtTicketSubject)
    EditText edtTicketSubject;
    @Bind(R.id.edtTicketDescription)
    EditText edtTicketDescription;
    @Bind(R.id.txtValue)
    TextView txtBtnSendTicket;
    @Bind(R.id.txtShowErrorMessage)
    TextView txtShowErrorMessage;
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    @Bind(R.id.layLoading)LinearLayout layLoading;

    @Bind(R.id.laychild)
    FrameLayout laychild;

    ArrayAdapter<String> adapterOlaviat;
    AdapterSpinnerVahed adapterSpinnerVahed;
    AdapterSpinnerCodes adapterSpinnerCodes;
    AdapterSpinnerChildCodes adapterSpinnerChildCodes;
    List<Unit> units = new ArrayList<>();
    List<TicketCodes> codes = new ArrayList<>();
    boolean loadUnit;
    int childCode = 1;
    DialogClass  dialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tiket);
        ButterKnife.bind(this);
        WebService.sendGetTicketCodes();
        dialogClass = new DialogClass();

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.context = this;
        G.currentActivity = this;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    private void initView() {

        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtBtnSendTicket.setText("ارسال");
        adapterOlaviat = new ArrayAdapter<>(G.context,
                R.layout.s_item_white,
                R.id.txtName,
                getResources().getStringArray(R.array.sp_olaviat_items));

        adapterOlaviat.setDropDownViewResource(R.layout.s_item_black);
        spOlaviat.setAdapter(adapterOlaviat);

        adapterSpinnerVahed = new AdapterSpinnerVahed(units);
        spVahed.setAdapter(adapterSpinnerVahed);

//        initSpCode();

        /** get vahed item from webService*/
        WebService.sendGetUnitsRequest();

        layBtnSendTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String subject = edtTicketSubject.getText().toString().trim();
                if (subject.length() == 0 && childCode == 1) {
//                    txtShowErrorMessage.setText("لطفا عنوان تیکت را وارد کنید.");
                    dialogClass.showMessageDialog("","لطفا عنوان تیکت را وارد کنید");

                    return;
                }
                String description = edtTicketDescription.getText().toString().trim();
                if (description.length() == 0) {
                    dialogClass.showMessageDialog("","لطفا متن تیکت خود را وارد کنید");
//                    txtShowErrorMessage.setText("لطفا متن تیکت خود را وارد کنید.");
                    return;
                }
                if (childCode == 1) {
                    WebService.sendAddTicketRequest(subject, ((adapterSpinnerVahed.getItem(spVahed.getSelectedItemPosition()))).code, spOlaviat.getSelectedItemPosition() + 1, description, childCode);
                } else {
                    WebService.sendAddTicketRequest(subject, ((adapterSpinnerVahed.getItem(spVahed.getSelectedItemPosition()))).code, spOlaviat.getSelectedItemPosition() + 1, description, childCode);
                }
                EventBus.getDefault().post(new EventOnSendTicketRequest());
                DialogClass dialogClass = new DialogClass();


            }

        });
    }

    private void initSpCode() {
        adapterSpinnerCodes = new AdapterSpinnerCodes(getMainCodesFromDB());
        spCodes.setAdapter(adapterSpinnerCodes);

        spCodes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                childCode = Integer.parseInt(view.getTag().toString());
                childCode = getMainCodesFromDB().get(position).getCode();
                initSpChildCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpChildCode() {
        if (childCode != 1) {
            getCodesFromDB(childCode);
            spChildCodes.setVisibility(View.VISIBLE);
            laychild.setVisibility(View.VISIBLE);
            laychild.setBackgroundColor(getResources().getColor(R.color.back_items));
//            edtTicketSubject.setVisibility(View.GONE);
            spChildCodes.bringToFront();
            adapterSpinnerChildCodes = new AdapterSpinnerChildCodes(codes);
            spChildCodes.setAdapter(adapterSpinnerChildCodes);
        } else {
            spChildCodes.setVisibility(View.GONE);
            laychild.setBackgroundColor(Color.TRANSPARENT);
            edtTicketSubject.setVisibility(View.VISIBLE);
            edtTicketSubject.bringToFront();
        }
        spChildCodes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                childCode = Integer.parseInt(view.getTag().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onEventMainThread(EventOnGetUnitResponse event) {
        layLoading.setVisibility(View.GONE);
        Logger.d("FragmentSendTicket : EventOnGetUnitResponse is raised");
//        units = event.getUnits();
//        adapterSpinnerVahed.updateList(units);
        getUnitsFromDB();
    }

    public void onEventMainThread(EventOnTicketCodeResponse event) {
        Logger.d("FragmentSendTicket : EventOnGetUnitResponse is raised");
        initSpCode();
    }

    public void onEventMainThread(EventOnGetErrorGetUnits event) {
        Logger.d("FragmentSendTicket : EventOnGetErrorGetUnits is raised.");
        getUnitsFromDB();
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("FragmentSendTicket : EventOnNoAccessServerResponse is raised.");
        getUnitsFromDB();
    }

    public void onEventMainThread(EventOnGetAddTicketResponse event) {
        Logger.d("FragmentSendTicket : EventOnGetAddTicketResponse is raised.");
        if (event.getStatus()== EnumResponse.OK) {
            DialogClass.showMessageDialog("ارسال تیکت","عملیات ارسال تیکت با موفیت انجام شد");

            edtTicketDescription.setText("");
            edtTicketSubject.setText("");
            txtShowErrorMessage.setText("");

//            Animation clickAnimation = AnimationUtils.loadAnimation(G.context, R.anim.anim_click);
//            clickAnimation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//                @Override
//                public void onAnimationEnd(Animation animation) {

//            DialogClass dialogExit = new DialogClass();
//            dialogExit.showSendTicket();

//                    }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//           G.context.startAnimation(clickAnimation);
        } else {
            DialogClass.showMessageDialog("ارسال تیکت","عملیات ارسال تیکت با خطا مواجه شده است دوباره تلاش کنید.");
//            txtShowErrorMessage.setText("عملیات ارسال تیکت با خطا مواجه شده است دوباره تلاش کنید.");
        }
    }

    public void onEventMainThread(EventOnGetErrorAddTicket event) {
        Logger.d("FragmentSendTicket : EventOnGetErrorAddTicket is raised.");
        txtShowErrorMessage.setText("عملیات ارسال تیکت با خطا مواجه شده است دوباره تلاش کنید.");
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }

    private void getUnitsFromDB() {
        units = new Select().from(Unit.class).execute();
        if (units.size() > 0)
            loadUnit = true;
        adapterSpinnerVahed.updateList(units);
    }

    private void getCodesFromDB(int parentId) {
        codes = new Select().from(TicketCodes.class).where("parent = ?", parentId).execute();
        // if (codes.size() > 0)
        // adapterSpinnerCodes.updateList(codes);
    }

    private TicketCodes getNameFromDB(int code) {
        return new Select().from(TicketCodes.class).where("code = ?", code).executeSingle();
    }

    private List<TicketCodes> getMainCodesFromDB() {
        try {
            List<TicketCodes> execufte = new Select().from(TicketCodes.class).execute();
            List<TicketCodes> execute = new Select().from(TicketCodes.class).where("parent ="+ 0).execute();
            return execute;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
