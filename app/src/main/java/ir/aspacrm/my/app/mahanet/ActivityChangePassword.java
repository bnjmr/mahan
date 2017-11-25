package ir.aspacrm.my.app.mahanet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.component.CustomEditText;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnChangePasswordResponse;


public class ActivityChangePassword extends AppCompatActivity {

    @Bind(R.id.edtOldPass)
    CustomEditText edtOldPass;
    @Bind(R.id.edtNewPassword)
    CustomEditText edtNewPassword;
    @Bind(R.id.edtConfirmNewPassword)
    CustomEditText edtConfirmNewPassword;
    @Bind(R.id.layBtnDone)
    CardView layBtnDone;

    @Bind(R.id.layLoading)
    LinearLayout layLoading;

    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;

    DialogClass dialogClass;

    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aativity_change_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        layLoading.setVisibility(View.GONE);
        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialogClass = new DialogClass();
        layBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = edtOldPass.getText().toString();
                String newPass = edtNewPassword.getText().toString();
                String confirmNewPass = edtConfirmNewPassword.getText().toString();

                if (oldpass.equals("")) {
                    dialogClass.showMessageDialog("خطا", "لطفا رمز عبور فعلی خود را وارد کنید .");

                } else if (newPass.equals("")) {
                    dialogClass.showMessageDialog("خطا", "لطفا رمز عبور جدید خود را وارد کنید .");

                } else if (!newPass.equals(confirmNewPass)) {
                    dialogClass.showMessageDialog("خطا", "رمز عبور جدید با تکرار آن مطابقت ندارد. لطفا رمز عبور جدید را مجددا وارد نمایید.");

                } else {
                    WebService.sendChangePasswordRequest(oldpass, newPass);
                    layLoading.setVisibility(View.VISIBLE);
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }


    public void onEventMainThread(final EventOnChangePasswordResponse passwordResponse) {
        layLoading.setVisibility(View.GONE);
        Intent intent = new Intent(this,ActivityLogin.class);
        startActivity(intent);
        finish();

        if (passwordResponse.getStatus()== EnumResponse.OK){
            edtOldPass.setText("");
            edtNewPassword.setText("");
            edtConfirmNewPassword.setText("");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogClass.showMessageDialog("", passwordResponse.getMessage()+"\n لطفا دوباره وارد شوید");
                }
            },700);
        }

    }
}
